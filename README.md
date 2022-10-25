#单兵设备认证系统
**一个很简单很简单的spring boot项目**
##目录结构说明
它采用maven的多module构建,共分为七个版块
+ business-center:是业务中心模块，此模块主要包含一个application启动模块，其它模块均为业务相关的模块
    + application:系统启动入口模块，因此需要组装哪些模块，只需要在此入口的pom中引入就可以
+ common-component-center:是系统的一些复用较用的模块，也称公共业务模块。它包含了:
    + file-center:文件中心模块，包含文件的整体和分片上传,支持本地存储和FTP存储
    + param-center:基础参数配置中心模块，主要包含三块业务功能：
        + 1、参数类型功能，这个基础参数的入口，类型有：基础参数列表、基础参数树形。
        + 2、基础参数列表结构。
        + 3、基础参数树形结构
    + websocket-center:websocket中心模块，目前支持单台的内存模式和分布式的redis发布订阅模式
+ inner-intergration:是一个工具类的核心包模块，主要是把系统中一些公共的代码抽离，封闭成功能单一的工具类包，它包含了:
    + basic-spring-boot-starter:系统是最核心的模块，定义了公共的config、枚举的处理、异常的定义是处理、一此工具类、标准的Web请求的输入输出
    + common-spring-boot-starter:在basic-spring-boot-starter的基础上对系统再次封装，这义了用户认证、异步线程的代理、系统中用户相关的枚举类、系统的访问拦截器、系统中的基础model及用户模块相关model、vo的定义
    + data-log-spring-boot-starter:系统中审计日志的模块
    + db-spring-boot-starter:连接数据的必要的模块，同时也支持多数据源的切换
    + log-spring-boot-starter:系统中请求的流水日志记录模块
    + mongo-spring-boot-starter:mongoDb的核心模块
    + mybatis-plus-spring-boot-starter:mybatis-plus的核心模块
    + redis-spring-boot-starter:redis的配置的核心模块，支持单节点和集群配置
    + rsa-spring-boot-starter:是一个非对称加解密的模块，主要用于和第三方接口通信，对数据安全处理
    + security-spring-boot-starter:是核心中安全处理的核心
    + swagger-spring-boot-starter:用户生成系统请求的api模块
+ job:是一个开源的定时任务管理模块
+ sql:这是一个数据脚本文件
+ system-center:这是系统用户相关的功能模块
    + 权限:系统中的公共权限池
    + 菜单:系统中所拥有的菜单
    + 菜单权限:标记着每一个菜单页面项所有拥有的权限
    + 角色:用户所拥有的角色，它和用户的关系:M:1
    + 角色授权:每一个角色所拥有的菜单及菜单页面权限
    + 用户:主要分为管理和普通用户
+ web-portal:后端功能对应的前端页面模块，
    + individual-soldier-auth-front:本系统采用了前后端分离模式，前端使用Vue+Element UI+Ts模式进行开发，使用Vuex进行信息存储
        + assets:存在前端系统中所使用的图标
        + common:前端封装的一些公共模块
            + directives:指令的封装
            + event-hub:事件的总线
            + exception:异常处理
            + filters:过滤器
            + form-validator:系统验证工具类
            + utils:工具集合
            + web-socket:web-socket的封装
        + components:系统组件的封装
            + cell:单元格的封装，主要是配合text组件一起使用
            + file-uploader:上传组件，支持分片上传
            + header:前端页面框架上的header的封装
            + left:前端页面框架的左边菜单封装
            + page-tabs:前端页面多页签的封装
            + select-tree:在下拉列表框中封装树型结构列表
            + table:前端分页查询的封装
            + text:单元格组件封装
        + plugins:主是把各种插件加入到Vue中
        + router:公共路由的处理
        + store:基于vuex的各种信息存储
            + index:对外导出
            + menu-collapse-store:菜单折叠状态的存储
            + menu-store:菜单的存储
            + permission-store:权限信息的存储
            + storage:使用widows存储
            + user-store:用户信息存储
            + web-socket-store:web-socket与后端交互信息的存储
        + views:前端的vue页面
            + index:登录后跳转的页面目录
                + business:业务页面
                + common:公共页面
                    + 审计日志
                    + 文件上传
                    + 基础参数管理
                + home:默认主页
                + system:用户相关页面
            + login:登录页面
    + fetch:对Axios的封装
    + permission:对页面登录权限的处理
    + settings:一些公共的系统属性定义
    + shims-vue.d:vue全局属性定义
        + KWRequest:标准请求
        + KWResponse:标准输出
        + Model:公共model定义
        + KWRule:验证规则    
            
##功能模块的使用说明
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
