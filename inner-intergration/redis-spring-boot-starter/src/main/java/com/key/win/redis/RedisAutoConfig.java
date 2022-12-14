package com.key.win.redis;


import io.lettuce.core.RedisClient;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.util.ReflectionUtils;
import com.key.win.redis.serializer.RedisObjectSerializer;
import com.key.win.redis.util.RedisUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

@Configuration
@EnableCaching
@SuppressWarnings("all")
@AutoConfigureBefore(RedisTemplate.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisAutoConfig {

	@Autowired(required = false)
	private RedissonProperties redissonProperties;

	@Autowired
	private RedisProperties redisProperties;

	@Autowired
	private ApplicationContext ctx;

	@Bean(destroyMethod = "destroy")
	@ConditionalOnClass(RedisClient.class)
	public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
		Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
		Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
		Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
		RedisConfiguration redisConfiguration = null;
		LettuceClientConfiguration clientConfig = null;
		if (redisProperties.getSentinel() != null) {
			// ????????????
			Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
			Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

			String[] nodes = null;
			Set<String> sentinelHostAndPorts = new HashSet<>();
			if (nodesValue instanceof String) {
				nodes = convert(Arrays.asList(((String) nodesValue).split(",")));
				sentinelHostAndPorts.addAll(Arrays.asList(((String) nodesValue).split(",")));
			} else {
				nodes = convert((List<String>) nodesValue);
				sentinelHostAndPorts.addAll((List<String>) nodesValue);
			}
			redisConfiguration = new RedisSentinelConfiguration(redisProperties.getSentinel().getMaster(),
					sentinelHostAndPorts);
			((RedisSentinelConfiguration) redisConfiguration)
					.setPassword(RedisPassword.of(redisProperties.getPassword()));
			((RedisSentinelConfiguration) redisConfiguration).setDatabase(redisProperties.getDatabase());
			clientConfig = LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout())
					.poolConfig(genericObjectPoolConfig).build();

		} else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
			// ????????????
			List<String> clusterNodes = redisProperties.getCluster().getNodes();
			Set<RedisNode> nodes = new HashSet<RedisNode>();
			clusterNodes.forEach(address -> nodes
					.add(new RedisNode(address.split(":")[0].trim(), Integer.valueOf(address.split(":")[1]))));
			redisConfiguration = new RedisClusterConfiguration();
			((RedisClusterConfiguration) redisConfiguration).setClusterNodes(nodes);
			((RedisClusterConfiguration) redisConfiguration)
					.setPassword(RedisPassword.of(redisProperties.getPassword()));

			/**
			 * ClusterTopologyRefreshOptions?????????????????????????????????????????????????????????????????????????????????
			 * Redis??????????????????????????????????????????
			 */
			ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
					// ?????????????????????
					.enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
							ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
					// ??????????????????????????????MOVED???ASK???PERSISTENT????????????
					// .enableAllAdaptiveRefreshTriggers()
					// ???????????????????????????(??????30???)
					.adaptiveRefreshTriggersTimeout(Duration.ofSeconds(25)) // ??????????????????????????????30???
					// ???????????????
					.enablePeriodicRefresh(Duration.ofSeconds(20)) // ??????????????????????????????60???
					// ClusterTopologyRefreshOptions.DEFAULT_REFRESH_PERIOD
					// 60 .enablePeriodicRefresh(Duration.ofSeconds(2)) =
					// .enablePeriodicRefresh().refreshPeriod(Duration.ofSeconds(2))
					.build();
			clientConfig = LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout())
					.poolConfig(genericObjectPoolConfig)
					.clientOptions(
							ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build())
					// ???appID?????????????????????Redis???????????????
					// .clientName(appName + "_lettuce")
					.build();

		} else {
			// ???????????????
			redisConfiguration = new RedisStandaloneConfiguration();
			((RedisStandaloneConfiguration) redisConfiguration).setDatabase(redisProperties.getDatabase());
			((RedisStandaloneConfiguration) redisConfiguration).setHostName(redisProperties.getHost());
			((RedisStandaloneConfiguration) redisConfiguration).setPort(redisProperties.getPort());
			((RedisStandaloneConfiguration) redisConfiguration)
					.setPassword(RedisPassword.of(redisProperties.getPassword()));

			clientConfig = LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout())
					.poolConfig(genericObjectPoolConfig).build();

		}

		if (redisProperties.isSsl()) {
			clientConfig.isUseSsl();
		}

		LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfiguration, clientConfig);
		return factory;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		return new StringRedisTemplate(lettuceConnectionFactory(genericObjectPoolConfig()));
	}

	/**
	 * GenericObjectPoolConfig ???????????????
	 */
	@Bean
	public GenericObjectPoolConfig genericObjectPoolConfig() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
		poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
		poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
		poolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().getSeconds());
		Duration timeOut = redisProperties.getTimeout();
		Duration shutdownTimeout = redisProperties.getLettuce().getShutdownTimeout();
		return poolConfig;
	}

 
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory ) {
	 
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L)) // ????????????????????????????????????30??????
				.disableCachingNullValues() // ???????????????????????????
				.computePrefixWith(cacheKeyPrefix())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string())) // ??????key????????????
//				.serializeValuesWith(
//						RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())); // ??????value????????????
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}

	/**
	 * ??????redis cluster?????????
	 */
	@Primary
	@Bean("redisTemplate")
	// ??????????????????????????????bean ???????????????redis ?????????????????????
	@ConditionalOnProperty(name = "spring.redis.cluster.nodes", matchIfMissing = false)
	public RedisTemplate<String, Object> getRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		RedisSerializer stringSerializer = new StringRedisSerializer();
		// RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		// RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		RedisSerializer redisObjectSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer); // key??????????????????
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(redisObjectSerializer); // value??????????????????
		redisTemplate.setHashValueSerializer(redisObjectSerializer); // value??????????????????
		redisTemplate.setDefaultSerializer(redisObjectSerializer);//default use String
		redisTemplate.afterPropertiesSet();
		//redisTemplate.opsForValue().set("hello", "wolrd");
		return redisTemplate;
	}

	/**
	 * ??????redis?????????
	 */
	@Primary
	@Bean("redisTemplate")
	@ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
	public RedisTemplate<String, Object> getSingleRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		// RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		RedisSerializer redisObjectSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer()); // key??????????????????
		redisTemplate.setValueSerializer(redisObjectSerializer); // value??????????????????
		redisTemplate.setHashValueSerializer(redisObjectSerializer);
		redisTemplate.setDefaultSerializer(redisObjectSerializer); //default use String
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public HashOperations<String, String, String> hashOperations(StringRedisTemplate stringRedisTemplate) {
		return stringRedisTemplate.opsForHash();
	}

	/**
	 * redis?????????
	 */
	@Bean("redisUtil")
	public RedisUtil redisUtil(LettuceConnectionFactory lettuceConnectionFactory,
                               StringRedisTemplate stringRedisTemplate, HashOperations<String, String, String> hashOperations) {
		RedisUtil redisUtil = new RedisUtil(lettuceConnectionFactory, stringRedisTemplate, hashOperations);
		return redisUtil;
	}

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnProperty(name = "spring.redis.redisson.enable", matchIfMissing = false, havingValue = "true")
	@ConditionalOnMissingBean(RedissonClient.class)
	public RedissonClient redissonClient() throws IOException {
		Config config = null;
		Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
		Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
		Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
		int timeout;
		if (null == timeoutValue) {
			timeout = 60000;
		} else if (!(timeoutValue instanceof Integer)) {
			Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
			timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
		} else {
			timeout = (Integer) timeoutValue;
		}
		// spring.redis.redisson.config=classpath:redisson.yaml
		if (redissonProperties.getConfig() != null) {

			try {
				InputStream is = getConfigStream();
				config = Config.fromJSON(is);
			} catch (IOException e) {
				// trying next format
				try {
					InputStream is = getConfigStream();
					config = Config.fromYAML(is);
				} catch (IOException ioe) {
					throw new IllegalArgumentException("Can't parse config", ioe);
				}
			}
		} else if (redisProperties.getSentinel() != null) {
			// ????????????
			Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
			Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

			String[] nodes;
			if (nodesValue instanceof String) {
				nodes = convert(Arrays.asList(((String) nodesValue).split(",")));
			} else {
				nodes = convert((List<String>) nodesValue);
			}

			config = new Config();
			config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster())
					.addSentinelAddress(nodes).setDatabase(redisProperties.getDatabase()).setConnectTimeout(timeout)
					.setPassword(redisProperties.getPassword());
		} else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
			// ????????????
			Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
			Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
			List<String> nodesObject = (List) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);
			String[] nodes = convert(nodesObject);
			config = new Config();
			config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout)
					.setPassword(redisProperties.getPassword());
		} else {
			// ??????redssion????????????
			config = new Config();
			String prefix = "redis://";
			Method method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
			if (method != null && (Boolean) ReflectionUtils.invokeMethod(method, redisProperties)) {
				prefix = "rediss://";
			}

			config.useSingleServer().setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
					.setConnectTimeout(timeout).setDatabase(redisProperties.getDatabase())
					.setPassword(redisProperties.getPassword());

		}

		return Redisson.create(config);
	}

	private String[] convert(List<String> nodesObject) {
		List<String> nodes = new ArrayList<String>(nodesObject.size());
		for (String node : nodesObject) {
			if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
				nodes.add("redis://" + node);
			} else {
				nodes.add(node);
			}
		}
		return nodes.toArray(new String[nodes.size()]);
	}

	private InputStream getConfigStream() throws IOException {
		Resource resource = ctx.getResource(redissonProperties.getConfig());
		InputStream is = resource.getInputStream();
		return is;
	}

    /**
     * ??????redis????????????????????????
     *
     * @return
     */
    @Bean
    public CacheKeyPrefix cacheKeyPrefix() {
        return cacheName -> {
            if (!cacheName.endsWith(":")) {
                cacheName += ":";
            }
            return cacheName;
        };
    }

}
