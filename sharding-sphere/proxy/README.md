# shardingSphere 5.2.1
基于sharding-sphere-proxy组件进行分库分表，共分四种场景进行集成
+ 0、apache-shardingsphere-5.2.1目录，是本地代理运行工作目录，
```
1、bin目录是启动目录，start.bat为win启动，默认启动端口3307，可通过在cmd命令中start.cmd 8988(本项目中所使用端口8988),start.sh、stop.sh为linux中的启停命令
2、conf目录是配置项工作目录，根据配置项把对应的配置copy至此目录进行对应模式的项目装载
3、server.yaml为分库分表的逻辑库的用户名密码配置地方
```
+ 1、sharding-red-write-splitting:读写分离
````
1.1、对目录为redwrite-splitting
1.2、redwrite-splitting共有三个文件
1.2.1、application-readwrite-splitting-local.yml文件:是spring boot启动的加载文件，需要时把它copy至applcation项目的resources目录中
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
3.2.1、application-sharding-horizontal-local.yml文件:是spring boot启动的加载文件，需要时把它copy至applcation项目的resources目录中
3.2.2、sql目录:对应的数据库文件
3.2.2.1、param-center.sql:是基础参数表，在水平分库分表中是broadcast类型，没有专的的库来存储，需要在每一个数据库中均存一份，因此需要把param-center.sql，在business-center.sql、file-center.sql、log-center.sql、user-center.sql都需要执行
3.2.3、mysql水平分片.yml:是mysql水平分表分库的docker运行文件，在操作完成此文件后，需要把business-center.sql、file-center.sql、log-center.sql、param-center.sql、user-center.sql分别在对应的数据库在执行
````