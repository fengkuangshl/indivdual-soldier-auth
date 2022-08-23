package com.key.win.websocket.config;

import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.manager.impl.RedisWebSocketManager;
import com.key.win.websocket.redis.DefaultRedisReceiver;
import com.key.win.websocket.redis.RedisReceiver;
import com.key.win.websocket.redis.action.ActionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.key.win.redis.serializer.RedisObjectSerializer;

@ConditionalOnProperty(prefix = "spring.web.socket", name = "cluster", havingValue = "true")
@Configuration
@Import(ActionConfig.class)
public class RedisWebSocketConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    @ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public RedisWebSocketManager webSocketManager(@Autowired RedisTemplate<String, Object> redisTemplate) {
        return new RedisWebSocketManager(redisTemplate);
    }

    @Bean(RedisReceiver.REDIS_RECEIVER_NAME)
    public RedisReceiver redisReceiver() {
        return new DefaultRedisReceiver(getApplicationContext());
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(@Qualifier(RedisReceiver.REDIS_RECEIVER_NAME) RedisReceiver redisReceiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisReceiver, RedisReceiver.RECEIVER_METHOD_NAME);
        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        messageListenerAdapter.setSerializer(redisObjectSerializer);
        return messageListenerAdapter;
    }

    @Bean("redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisWebSocketManager.CHANNEL));
        return container;
    }
}