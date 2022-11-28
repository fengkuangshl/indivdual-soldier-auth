package com.key.win.shardingsphere;

import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.apache.shardingsphere.spring.boot.registry.AbstractAlgorithmProvidedBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

public final class MyShardingAlgorithmProvidedBeanRegistry extends AbstractAlgorithmProvidedBeanRegistry<ShardingAlgorithm> implements Ordered  {

    private static final String SHARDING_ALGORITHMS = "spring.shardingsphere.rules.sharding.sharding-algorithms.";

    public MyShardingAlgorithmProvidedBeanRegistry(Environment environment) {
        super(environment);
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        this.registerBean("spring.shardingsphere.rules.sharding.sharding-algorithms.", ShardingAlgorithm.class, registry);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
