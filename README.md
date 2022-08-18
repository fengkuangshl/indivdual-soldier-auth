#单兵系统
**一个很简单很简单的spring boot项目**
##目录结构说明
+ img目录:readme说明的图片
+ sql目录:数据库脚本文件及初始化数据
+ src目录
   + base目录:主要是一些系统的基础组件
   + common目录:主要是一些公共组件,这些组件可选
   + config目录:系统中的一些配置类
   + system目录:系统用户权鉴
+ resources:资源文件
##base-package说明
+ auth:
    + 1、Authentication：登录后用户信息
    + 2、AuthenticationUtil：本地线程类
+ enumjson
    + json 输入序列的问题
+ exception
    + 定义一些异常信息
    + 全局异常处理
+ interceptor
    + 对系统的每次请求拦截，校验用户的是否存在，并把用户邦定到本次请求的线程中
+ model
    + 实体的基类
+ page
    + 系统分页模板
+ redis
    + redis序列化的设定
+ util 
    + 系统的一些工具类
+ web
    + 后端输入输出的一些标准类
##common-package说明
+ datalog
    + 记录系统中增删改的数据变化日志记录
    + 新增：新插入数据：[sys_user]
         {"ID":"1477994134986207233","USER_NAME":"ADMIN90","PASSWORD":"32AAB1730E778602B8383EAA62EEFF37","NICK_NAME":"ADMIN91","IS_ENABLED":"1","CREATE_DATE":"2022-01-0321:23:56.23","UPDATE_DATE":"2022-01-0321:23:56.23","CREATE_USER_ID":"ANONYMOUS","ENABLE_FLAG":"1","CREATE_USER_NAME":"ANONYMOUS","VERSION":"0"}
    + 修改：修改表：[sys_user]id：[1474194342540156929] 把字段[password]从[123]改为[e10adc3949ba59abbe56e057f20f883e]
    + 删除时会日志:[xx]id：[xx] 的记录被删除
    + 使用:只需要在目标方法上加@DataLog
    + 此组件可选，可通过@EnableDataLog开启
+ datasource
    + 多数据源组件
    + 需要配合着application-ds.yml使用， 在目标方法或类上添加@DataSource(name = "log")
    + 此组件可选，可通过spring.datasource.dynamic.enable为true开启
+ log
    + 记录系统的输入输出参数
    + 使用:只需要在目标方法上加@LogAnnotation(module = "system", recordRequestParam = false)
    + recordRequestParam为true时会将日志记录到数据中
+ param
    + 数据字典组件
+ security
    + 系统权鉴组件，类似于spring security的@PreAuthorize注解的功能
    + 使用:只需要在目标方法上加@PreAuthorize("@rbacService.hasRole('abc')")
    + 此组件可选，可通过spring.global.method.security.enable为true时开启
+ websocket
    + 使用javax.websocket开封装的
    + 可用于集群,主要是通过redis的消息发布订阅来实现
    + 此组件可选，可通过spring.web.socket.exporter.enable为true时开启
    + websocket架构图
    + ![输入图片说明](./img/163635_6f336f08_420908.png "20210123163339292.png")
    + 此组件参考https://gitee.com/kubilewang/update-websocket
##config-package说明
+ 些package下的都一些配置类，一些组件是否启用都在此package下
+ AsyncConfiguration
    + 此配置类是log和datalog的数据保存时异步线程池
+ AutoFillObjectHandler
    + 主要是mybaatis保存或更新时填充一些公共的字段
+ CorsConfig
    + 跨域处理
+ DataLogConfig
    + 是datalog的配置类
+ DataSourceAutoConfig
    + 多数据源的配置类
+ MybatisConfig
    + mybatis配置类
+ MyPasswordEncoder
    + 密码的配置类
+ RedisConfig
    + redis的配置类
    + Swagger2的配置类
+ TxAdviceConfig
    + 事务的全局配置类
+ WebAppConfigurer
    WebApp的一些配置类
+ WebSocketConfig
    webSocket的配置类

##system-package说明
+ 用户
+ 用户角色
+ 角色
+ 角色菜单
+ 菜单
+ 角色权限
+ 权限
+ 用户组织机构
+ 组织机构
+ 用户用户组
+ 用户组
+ 用户设备
+ 组织机构设备
+ 设备
