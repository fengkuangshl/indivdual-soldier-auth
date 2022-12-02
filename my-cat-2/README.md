#Docker 部署Mycat2(推荐)
+ 1、在linux中创建目录
```shell script
#创建目录
mkdir -p /mydata/mycat2
```
+ 2、将目录中的Dockerfile、mycat2-1.22-release-jar-with-dependencies-2022-10-13.jar、mycat2-install-template-1.21.zip都上传至/mydata/mycat2目录中
```shell script
自行上传
```
+ 3、编译镜像
```shell script
docker build -t mycat.org.cn/mycat2:1.22 .
```
+ 4、复制容器内配置
```shell script
#运行
docker run -d --name=mycat2 -p 8066:8066 -p 1984:1984 mycat.org.cn/mycat2:1.22
# 复制容器内配置
docker cp mycat2:/usr/local/mycat/conf .
docker cp mycat2:/usr/local/mycat/logs .
# 停止 mycat2
docker stop mycat2
```
+ 5、挂载目录，重启启动
```shell script
#运行
docker run -d --name=mycat2 \
-p 8066:8066 \
-p 1984:1984 \
-v /mydata/mycat2/conf:/usr/local/mycat/conf \
-v /mydata/mycat2/logs:/usr/local/mycat/logs \
mycat.org.cn/mycat2:1.22

# 以root用户权限进入容器
docker exec -it -u root mycat2 bash
# 查看容器启动日志
docker logs -f mycat2
```
+ 6、docker-compose方式安装
```shell script
version: '3'
services:
  mycat-service:
    build:
      context: ./mycat/
      dockerfile: Dockerfile
    image: mycat2:1.21
    container_name: mycat2
    restart: always
    network_mode: "host"
    volumes:
      - /mydata/mycat2/conf:/usr/local/mycat/conf
      - /mydata/mycat2/logs:/usr/local/mycat/logs
```
#linux 部署Mycat2
+ 1、在linux中创建目录
```shell script
#创建目录
mkdir -p /mydata/mycat2
```
+ 2、将mycat2-1.21-release-jar-with-dependencies.jar、mycat2-install-template-1.20.zip都上传至/mydata/mycat2目录中
```shell script
#自行上传
```
+ 3、解压
```shell script
#安装unzip
yum install unzip -y
#开始解压
unzip mycat2-install-template-1.20.zip
```
+ 4、授权
```shell script
#切换目录
cd /mydata/mycat2/mycat/bin
#添加权限
chmod +x *
```
+ 5、移动jar包
```shell script
#将jar移动到mycat文件夹的lib文件夹下面
mv mycat2-1.21-release-jar-with-dependencies.jar /mydata/mycat2/mycat/lib/
```
+ 6、防火墙设置
```shell script
#开放8066端口
firewall-cmd --zone=public --add-port=8066/tcp --permanent
#配置立即生效
firewall-cmd --reload
 
#或关闭防火墙
systemctl stop firewalld.service
```
+ 7、mycat启停
````shell script
#启动
mycat start
#关闭
mycat stop
````
#win10 部署Mycat2
+ 1、以管理员方式运行cmd，进行./local/mycat/bin目录下
```shell script
#1、进入目录
cd D:\dev-env\workspace-boot\individual-soldier-auth\my-cat-2\local\mycat\bin
#2、安装
mycat install
#3、启动
mycat start
#4、关闭
mycat stop
```
#Mycat2的prototype(原形库)的配置
```shell script
#修改配置
vim conf/datasources/prototypeDs.datasource.json
{
	"dbType":"mysql",
	"idleTimeout":60000,
	"initSqls":[],
	"initSqlsGetConnection":true,
	"instanceType":"READ_WRITE",
	"maxCon":1000,
	"maxConnectTimeout":3000,
	"maxRetryCount":5,
	"minCon":1,
	"name":"prototypeDs",
	"password":"root", #密码
	"type":"JDBC",
    #jdbc的连接
	"url":"jdbc:mysql://192.168.1.222:3307/individual-soldier-auth?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8",
	"user":"root", #用户密码
	"weight":0
}
#重启mycat2
docker restart mycat2
```
#Mycat2的读写分离配置
```shell script
#1、按照readwrite-splitting目录中的mysql主从集群.yml文件进行mysql的docker化集群配置
#2、在Mycat里创建 read_write_splitting逻辑库
create database read_write_splitting;
#3、修改mread_write_splitting.schema.json 指定数据源 "targetName": "prototype"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/read_write_splitting.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"individual-soldier-auth",
        "targetName": "prototype",
        "shardingTables":{},
        "views":{}
}
#4、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"rwSepw","url":"jdbc:mysql://192.168.1.222:3307/individual-soldier-auth?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:createDataSource{ "name":"rwSepr","url":"jdbc:mysql://192.168.1.222:3317/individual-soldier-auth?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#5、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"prototype","masters":["rwSepw"],"replicas":["rwSepr"]} */;
/*+ mycat:showClusters{} */
#6、重启mycat2
docker restart mycat2
#5、readwrite-splitting目录中application-readwrite-splitting.yml copy至application中的resources下，并修改spring.profiles.active为readwrite-splitting
#6、启动项目验证
```
#Mycat2的垂直分库分表配置
```shell script
#1、按照sharding目录中的vertical目录下的mysql垂直分片.yml文件进行mysql的docker化集群配置
#2、在Mycat里创建垂直分库分表逻辑库

#2.1、business_center逻辑库创建
#2.1.1、business_center
create database business_center;
#2.1.2、修改mread_write_splitting.schema.json 指定数据源 "targetName": "business_center"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/business_center.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"business_center",
        "targetName": "business_center",
        "shardingTables":{},
        "views":{}
}
#2.1.3、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"business_center","url":"jdbc:mysql://192.168.1.222:3308/business_center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#2.1.4、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"business_center","masters":["business_center"],"replicas":["business_center"]} */;
/*+ mycat:showClusters{} */

#2.2、file-center逻辑库创建
#2.2.1、file_center
create database file_center;
#2.2.2、修改mread_write_splitting.schema.json 指定数据源 "targetName": "business_center"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/file_center.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"file_center",
        "targetName": "file_center",
        "shardingTables":{},
        "views":{}
}
#2.2.3、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"file_center","url":"jdbc:mysql://192.168.1.222:3309/file_center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#2.2.4、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"file_center","masters":["file_center"],"replicas":["file_center"]} */;
/*+ mycat:showClusters{} */

#2.3、log-center逻辑库创建
#2.3.1、log_center
create database log_center;
#2.3.2、修改mread_write_splitting.schema.json 指定数据源 "targetName": "business_center"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/log_center.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"log_center",
        "targetName": "log_center",
        "shardingTables":{},
        "views":{}
}
#2.3.3、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"log_center","url":"jdbc:mysql://192.168.1.222:3310/log_center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#2.3.4、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"log_center","masters":["log_center"],"replicas":["log_center"]} */;
/*+ mycat:showClusters{} */

#2.4、param_center逻辑库创建
#2.4.1、param_center
create database param_center;
#2.4.2、修改mread_write_splitting.schema.json 指定数据源 "targetName": "business_center"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/param_center.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"param_center",
        "targetName": "param_center",
        "shardingTables":{},
        "views":{}
}
#2.4.3、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"param_center","url":"jdbc:mysql://192.168.1.222:3311/param_center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#2.4.4、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"param_center","masters":["param_center"],"replicas":["param_center"]} */;
/*+ mycat:showClusters{} */

#2.5、user_center逻辑库创建
#2.5.1、user_center
create database user_center;
#2.5.2、修改mread_write_splitting.schema.json 指定数据源 "targetName": "business_center"，配置主机数据源,
# !!!(注意schemaName,一定是和读写数据库名字一样，不一样的话导致表加载不出来)
vi conf/schemas/user_center.schema.json
{
        "customTables":{},
        "globalTables":{},
        "normalProcedures":{},
        "normalTables":{},
        "schemaName":"user_center",
        "targetName": "user_center",
        "shardingTables":{},
        "views":{}
}
#2.5.3、在Mycat里，注解方式添加数据源，指向从机
/*+ mycat:createDataSource{ "name":"user_center","url":"jdbc:mysql://192.168.1.222:3312/user_center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "user":"root","password":"root" } */;
/*+ mycat:showDataSources{} */;
#2.5.4、更新集群信息,添加主从节点
/*! mycat:createCluster{"name":"user_center","masters":["user_center"],"replicas":["user_center"]} */;
/*+ mycat:showClusters{} */

#3、重启mycat2
docker restart mycat2

#4、sharding目录中的vertical目录下目录中application-readwrite-splitting.yml copy至application中的resources下，并修改spring.profiles.active为readwrite-splitting

#5、启动项目验证
```