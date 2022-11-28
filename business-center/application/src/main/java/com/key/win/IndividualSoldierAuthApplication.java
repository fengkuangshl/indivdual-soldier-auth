package com.key.win;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.shardingsphere.sharding.spring.boot.*;

@EnableTransactionManagement
// @EnableLogging
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ShardingRuleSpringBootConfiguration.class})
public class IndividualSoldierAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndividualSoldierAuthApplication.class, args);
    }

}
