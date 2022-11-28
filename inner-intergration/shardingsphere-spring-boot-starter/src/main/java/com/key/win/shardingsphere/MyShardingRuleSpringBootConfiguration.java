//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.key.win.shardingsphere;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.sharding.algorithm.config.AlgorithmProvidedShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.apache.shardingsphere.sharding.spi.ShardingAuditAlgorithm;
import org.apache.shardingsphere.sharding.spring.boot.algorithm.KeyGenerateAlgorithmProvidedBeanRegistry;
import org.apache.shardingsphere.sharding.spring.boot.algorithm.ShardingAlgorithmProvidedBeanRegistry;
import org.apache.shardingsphere.sharding.spring.boot.algorithm.ShardingAuditAlgorithmProvidedBeanRegistry;
import org.apache.shardingsphere.sharding.spring.boot.condition.ShardingSpringBootCondition;
import org.apache.shardingsphere.sharding.spring.boot.rule.YamlShardingRuleSpringBootConfiguration;
import org.apache.shardingsphere.sharding.yaml.config.YamlShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.yaml.swapper.YamlShardingRuleAlgorithmProviderConfigurationSwapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties({YamlShardingRuleSpringBootConfiguration.class})
@ConditionalOnClass({YamlShardingRuleConfiguration.class})
@Conditional({ShardingSpringBootCondition.class})
public class MyShardingRuleSpringBootConfiguration {
    private final YamlShardingRuleAlgorithmProviderConfigurationSwapper swapper = new YamlShardingRuleAlgorithmProviderConfigurationSwapper();
    private final YamlShardingRuleSpringBootConfiguration yamlConfig;

    @Bean
    public RuleConfiguration shardingRuleConfiguration(ObjectProvider<Map<String, ShardingAlgorithm>> shardingAlgorithmProvider, ObjectProvider<Map<String, KeyGenerateAlgorithm>> keyGenerateAlgorithmProvider, ObjectProvider<Map<String, ShardingAuditAlgorithm>> shardingAuditAlgorithmProvider) {
        Map<String, ShardingAlgorithm> shardingAlgorithmMap = (Map)Optional.ofNullable(shardingAlgorithmProvider.getIfAvailable()).orElse(Collections.emptyMap());
        Map<String, KeyGenerateAlgorithm> keyGenerateAlgorithmMap = (Map)Optional.ofNullable(keyGenerateAlgorithmProvider.getIfAvailable()).orElse(Collections.emptyMap());
        Map<String, ShardingAuditAlgorithm> shardingAuditAlgorithmMap = (Map)Optional.ofNullable(shardingAuditAlgorithmProvider.getIfAvailable()).orElse(Collections.emptyMap());
        AlgorithmProvidedShardingRuleConfiguration result = this.swapper.swapToObject(this.yamlConfig.getSharding());
        result.setShardingAlgorithms(shardingAlgorithmMap);
        result.setKeyGenerators(keyGenerateAlgorithmMap);
        result.setAuditors(shardingAuditAlgorithmMap);
        return result;
    }

    @Bean
    public static MyShardingAlgorithmProvidedBeanRegistry shardingAlgorithmProvidedBeanRegistry(Environment environment) {
        return new MyShardingAlgorithmProvidedBeanRegistry(environment);
    }

    @Bean
    public static KeyGenerateAlgorithmProvidedBeanRegistry keyGenerateAlgorithmProvidedBeanRegistry(Environment environment) {
        return new KeyGenerateAlgorithmProvidedBeanRegistry(environment);
    }

    @Bean
    public static ShardingAuditAlgorithmProvidedBeanRegistry shardingAuditAlgorithmProvidedBeanRegistry(Environment environment) {
        return new ShardingAuditAlgorithmProvidedBeanRegistry(environment);
    }

    @Generated
    public MyShardingRuleSpringBootConfiguration(YamlShardingRuleSpringBootConfiguration yamlConfig) {
        this.yamlConfig = yamlConfig;
    }
}
