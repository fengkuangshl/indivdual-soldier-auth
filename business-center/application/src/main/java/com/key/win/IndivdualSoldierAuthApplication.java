package com.key.win;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
// @EnableLogging
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IndivdualSoldierAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndivdualSoldierAuthApplication.class, args);
    }

}
