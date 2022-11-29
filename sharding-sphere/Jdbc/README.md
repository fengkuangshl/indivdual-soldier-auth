# shardingSphere 5.2.1
基于sharding-sphere-jdbc组件进行分库分表，在inner-intergration基础中心，新建shardingsphere-spring-boot-starter项目，不影响全局配置，需要分库分表，并因需要引入此starter既可.
与Druid数据源进行了整合，需要在IndividualSoldierAuthApplication启动类排除ShardingRuleSpringBootConfiguration.class，共分四种场景进行集成
+ 0、pom依赖
```
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>${shardingsphere-jdbc-core.version}</version>
    <exclusions>
        <exclusion><!--默认引入的版本底，此因排队-->
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
</dependency>
```
+ 1、sharding-red-write-splitting:读写分离
````
1.1、对目录为redwrite-splitting
1.2、redwrite-splitting共有三个文件
1.2.1、application-readwrite-splitting.yml文件:是spring boot启动的加载文件，需要时把它copy至applcation项目的resources目录中
1.2.2、individual-soldier-auth.sql:对应的数据库文件
1.2.3、mysql主从集群.yml:此文件是mysql主存集群的docker运行文件，在操作完成此文件后，需要把individual-soldier-auth.sql分别在主存数据库中执行
`````
+ 2、sharding-vertical:垂直分片(既直分库)
````
2.1、对目录为sharding下的vertical
2.2、vertical共有三类文件
2.2.1、application-sharding-vertical.yml文件:是spring boot启动的加载文件，需要时把它copy至applcation项目的resources目录中
2.2.2、sql目录:对应的数据库文件
2.2.3、mysql垂直分片.yml:是mysql垂直分库的docker运行文件，在操作完成此文件后，需要把business-center.sql、file-center.sql、log-center.sql、param-center.sql、user-center.sql分别在对应的数据库在执行
````
+ 3、sharding-horizontal:水平分片(既分库分表)
````
3.1、对目录为sharding下的horizontal
3.2、vertical共有三类文件
3.2.1、application-sharding-horizontal.yml文件:是spring boot启动的加载文件，需要时把它copy至applcation项目的resources目录中
3.2.2、sql目录:对应的数据库文件
3.2.2.1、param-center.sql:是基础参数表，在水平分库分表中是broadcast类型，没有专的的库来存储，需要在每一个数据库中均存一份，因此需要把param-center.sql，在business-center.sql、file-center.sql、log-center.sql、user-center.sql都需要执行
3.2.3、mysql水平分片.yml:是mysql水平分表分库的docker运行文件，在操作完成此文件后，需要把business-center.sql、file-center.sql、log-center.sql、param-center.sql、user-center.sql分别在对应的数据库在执行
````