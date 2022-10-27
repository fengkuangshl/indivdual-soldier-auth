#设备认证系统
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
            
##后端系统功能或模块的使用说明
+ 1、任何后端功能模块的使用，需先在pom中引用
````
pom.xml
<dependencies>
    <!--公共模块 -->
    <dependency>
        <groupId>com.key.win</groupId>
        <artifactId>common-spring-boot-starter</artifactId>
    </dependency>
    ...
</dependencies>
````
+ 2、系统中所有的model都需要继承MybatisID实体类，标注着有统一的结构体，这样在保存时系统才能自动填充一些公共的属性，如操作人、创建时间等等
````
SysUser.java
@ApiModel("用户实体")
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends MybatisID {
    ...
}
````
+ 3、系统的异步处理：目前系统异常处理主要包含：业务异常类:BizException、用户非法异常:UserIllegalException、权限异常:AccessDeniedException,如需要自定义异常，首页请定义自己异常 XxxException 类，并继承BizException异常类，其次在GlobalExceptionHandler类中完成自己的异常处理
````
UserIllegalException:
 if (refreshTokenVo == null) {
    logger.error("refreshToken is null !!");
    throw new UserIllegalException("refreshToken过期");
}
AccessDeniedException:
if (!dbUser.getEnabled()) {
    logger.error("用户{}帐号被禁用", dbUser.getUserName());
    throw new AccountDisabledException("您的帐号被禁用！");
}
BizException:
if (!CollectionUtils.isEmpty(existUsers)) {
    logger.error("{}用户已经存在!", sysUser.getUserName());
    throw new BizException("用户已经存在！");
}
````
+ 4、系统中枚举的使用：创建枚举之后并添加注解：@JsonSerialize(using = TextureEnumSerializerCode.class)，其目的是在对象输入是json序列化时，对其按时指定的方式序列化
````
@JsonSerialize(using = TextureEnumSerializerCode.class)
public enum SexEnum {
    ...
}
````
+ 5、系统的标准输出
```
1、分页返回对象PageResult<T>
@PostMapping("/findSysUserByPaged")
@ApiOperation(value = "User分页")
@LogAnnotation(module = "system", recordRequestParam = false)
@PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
public PageResult<SysUser> findSysUserByPaged(@RequestBody PageRequest<SysUser> pageRequest) {
    return sysUserService.findSysUserByPaged(pageRequest);
}
2、其它均返回Result<T>
@PostMapping("/updateSysUser")
@ApiOperation(value = "用户更新")
@LogAnnotation(module = "system", recordRequestParam = false)
@PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "MODIFY')")
public Result updateSysUser(@RequestBody SysUser sysUser) {
    boolean b = sysUserService.updateSysUser(sysUser);
    return Result.result(b);
}
```
+ 6、系统登录用户的使用
```
1、用户登录后，通过token把用户数据存储redis中,可通过AuthenticationUtil工具类获取当前登录的用户信息
2、AuthenticationUtil.getAuthentication()获取当前登录的用户信息
3、AuthenticationUtil.setCurrentUser(Authentication sysUser)设计用户信息到ThreadLocal中
4、AuthenticationUtil.getUserId()获取当前登录用户的Id
5、AuthenticationUtil.getUserName()获取当前登录用户的用户名
6、AuthenticationUtil.getToken()获取当前登录用户的token
```
+ 7、data-log-spring-boot-starter组件的使用,记录系统中增删改的数据变化日志记录
```
1、在pom中加入data-log-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>data-log-spring-boot-starter</artifactId>
</dependency>
2、开启@EnableDataLog(不需要关注,因为在DataLogConfig类中已经开启)
3、具体使用方法
  3.1、在方法前添加注解
    @DataLog
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = customerInfoService.removeById(id);
        return Result.result(b);
    }
  3.2、手动调用
    @Autowired
    private SysDataLogService sysDataLogService;
    @Override
    public synchronized DeviceAuthResponseVo saveOrUpdateDeviceAuth(DeviceAuth deviceAuth) {
        ...业务处理...
         sysDataLogService.saveDataLog("接收设备重复提交认证信息，进行覆盖操作", getDataLogFkId(deviceAuthByUniqueCode.getId()));   
        ...业务处理...
    }
4、审计结果如下：
  4.1、新增：新插入数据：[DEVICE_CUSTOMER_INFO]{"sequence":"CNO.606160180316098562","authDeviceCode":"9999","expireDeviceDate":"2022-09-3000:00:00.0","authDeviceNum":"999","isVerify":"1","companyName":"999","companyAddress":"999","companyPhone":"999","leadName":"999","leadMobile":"13599999999","leadPhone":"","projectNo":"999","projectName":"999","createDate":"2022-09-2714:37:06.436","updateDate":"2022-09-2714:37:06.436","createUserId":"19","enableFlag":"1","createUserName":"admin","version":"0","id":"21"}
  4.2、修改：修改表：[DEVICE_CUSTOMER_INFO]id：[18]把字段[authDeviceNum]从[10]改为[100]
  4.3、删除：修改表：[DEVICE_CUSTOMER_INFO]id：[39]记录被删除！
```
+ 8、db-spring-boot-starter组件的使用
```
1、在pom中加入db-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>db-spring-boot-starter</artifactId>
</dependency>
2、非多数据的使用
spring: 
    datasource:
        username: root
        password: key-win123
        url: jdbc:mysql://127.0.0.1:3307/individual-soldier-auth?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai
        driver-class-name: com.mysql.cj.jdbc.Driver
        ...
3、多数据源的使用：请注意spring.datasource.dynamic.enable为true开启
    3.1、首先完成多数据源的yml配置(请参考application-ds.yml的配置)
    spring: 
        datasource:
            dynamic:
              enable: true
              primary: core #设置默认的数据源或者数据源组,默认值即为master
            druid:
              # JDBC 配置(驱动类自动从url的mysql识别,数据源类型自动识别)
              core:
                url: jdbc:mysql://127.0.0.1:3307/single-soldier-wireless?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai
                username: root
                password: key-win123
                driver-class-name:  com.mysql.cj.jdbc.Driver
              log:
                url: jdbc:mysql://127.0.0.1:3307/log-center?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai
                username: root
                password: key-win123
                driver-class-name:  com.mysql.cj.jdbc.Driver
            ...
    3.2、在目标方法或类上完成@DataSource(name = "log")注解的配置，标明此方法或类使用数据log数据源
    3.3、如果还想配置更多的数据源，请参考mybatis-plus中的DS组件
```
9、log-spring-boot-starter组件的使用，记录系统的输入输出参数
```
1、在pom中加入db-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>db-spring-boot-starter</artifactId>
</dependency>
2、只需要在目标方法上加@LogAnnotation(module = "system", recordRequestParam = false)
@DeleteMapping("/delete/{id}")
@ApiOperation(value = "删除")
@LogAnnotation(module = "system", recordRequestParam = false)
@PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
public Result delete(@PathVariable Long id) {
    boolean b = sysUserService.deleteById(id);
    return Result.result(b);
}
3、需要注意的是，如果recordRequestParam的值为true时，会将此流水记录保存至数据库的sys_log表中
```
10、mongo-spring-boot-starter组件的使用
```
1、在pom中加入mongo-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>mongo-spring-boot-starter</artifactId>
</dependency>
2、数据源配置
spring:
  data:
    mongodb:
      uri: mongodb://127.0.0.1:27017/ssw
3、XxxService继承IMongoService<T>接口
public interface EventLogService extends IMongoService<EventLog> {
    ...
}
4、XxxServiceImp实现类继承MongoServiceImpl<T>抽像类,实现XxxService接口,实现可调用父类的方法进行增删改查操作
@Service
public class EventLogServiceImpl extends MongoServiceImpl<EventLog> implements EventLogService {
    ...
}
```
+ 11、mybatis-plus-spring-boot-starter组件的使用
```
1、在pom中加入mybatis-plus-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>mybatis-plus-spring-boot-starter</artifactId>
</dependency>
2、XxxDao继承BaseMapper<T>或KeyWinMapper<T>
@Mapper
public interface DeviceAuthVoDao extends KeyWinMapper<DeviceAuthVo> {
}
3、XxxService继承IService<T>接口
  public interface DeviceAuthService extends IService<DeviceAuth> {
    ...  
  }
4、XxxServiceImp实现类继承ServiceImpl<XxxDao, T>抽像类,实现XxxService接口,实现可调用父类的方法进行增删改查操作
@Service
public class DeviceAuthServiceImpl extends ServiceImpl<DeviceAuthDao, DeviceAuth> implements DeviceAuthService {
    ...
}
5、分页查询的实现，传入参数的类型是PageRequest<T>,返回参数的类型PageResult<T>,只需要创建一MybatisPageServiceTemplate<T,RT>对象，转入baseMapper,重写constructWrapper方法就可以
@Override
public PageResult<DeviceAuthVo> findDeviceAuthByPaged(PageRequest<DeviceAuthVo> t) {
    MybatisPageServiceTemplate<DeviceAuthVo, DeviceAuthVo> query = new MybatisPageServiceTemplate<DeviceAuthVo, DeviceAuthVo>(deviceAuthVoDao) {
        @Override
        protected AbstractWrapper constructWrapper(DeviceAuthVo deviceAuth) {
            return buildDeviceAuthLambdaQueryWrapper(deviceAuth);
        }

        //select da.*,dci.company_name,dci.project_no,dci.enabled_verification from device_auth da INNER JOIN device_customer_info dci on da.auth_code = dci.auth_device_code
        protected String constructNativeSql() {
            return "select * from (select da.*,dci.company_name,dci.project_no,dci.is_verify,dci.project_name,dci.sequence from device_auth da INNER JOIN device_customer_info dci on da.auth_code = dci.auth_device_code where da.enable_flag = 1 and  dci.enable_flag = 1 ) tmp";
        }
    };
    return query.doPagingQuery(t);
}
6、请参考https://editor.csdn.net/md/?articleId=122747931
```
+ 12、redis-spring-boot-starter组件的使用
```
1、在pom中加入redis-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>redis-spring-boot-starter</artifactId>
</dependency>
2、单节点的配置
spring:
  redis:
    ################### redis 单机版 start ##########################
    host: 127.0.0.1
    port: 6379
    timeout: 6000
    database: 8
    lettuce:
      pool:
        max-active: 10 # 连接池最大连接数（使用负值表示没有限制）,如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
        max-idle: 8   # 连接池中的最大空闲连接 ，默认值也是8
        max-wait: 100 # # 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
        min-idle: 2    # 连接池中的最小空闲连接 ，默认值也是0
      shutdown-timeout: 100ms
3、集群节点的配置
spring:
  redis:
    cluster:
      nodes: 130.75.131.237:7000,130.75.131.238:7000,130.75.131.239:7000,130.75.131.237:7001,130.75.131.238:7001,130.75.131.239:7001
        #130.75.131.237:7000,130.75.131.238:7000,130.75.131.239:7000,130.75.131.237:7001,130.75.131.238:7001,130.75.131.239:7001
        #192.168.3.157:7000,192.168.3.158:7000,192.168.3.159:7000,192.168.3.157:7001,192.168.3.158:7001,192.168.3.159:7001
    timeout: 1000 # 连接超时时间（毫秒）
    lettuce:
      pool:
        max-active: 10 # 连接池最大连接数（使用负值表示没有限制）,如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
        max-idle: 8   # 连接池中的最大空闲连接 ，默认值也是8
        max-wait: 100 # # 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
        min-idle: 2    # 连接池中的最小空闲连接 ，默认值也是0
      shutdown-timeout: 100ms
4、使用
@Autowired
private static RedisTemplate<String, Object> redisTemplate;
```
+ 13、rsa-spring-boot-starter组件的使用
```
1、在pom中加入rsa-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>rsa-spring-boot-starter</artifactId>
</dependency>
2、生成非对称加密的证书，请参考rsa-spring-boot-starter模块中的rsa-encryptor文件中的README.md文件
3、输出加密：只需要返回对象是EncryptResponse，就可以自动加密
@PostMapping("/auto")
@ApiOperation(value = "新增/更新")
@LogAnnotation(module = "device-auth", recordRequestParam = false)
public EncryptResponse saveOrUpdate(@RequestBody DeviceAuthRequestVo deviceAuth) {
     DeviceAuthResponseVo deviceAuthResponseVo = deviceAuthService.saveOrUpdateDeviceAuth((DeviceAuth) deviceAuth);
     return EncryptResponse.succeed(deviceAuthResponseVo);
}
4、请求解密，只需要在对就的实体类中继承IEncryptor，就会自动解密转换成实体对象
@Data
@ApiModel("设备端提交设备信息Vo")
public class DeviceAuthRequestVo extends DeviceAuth implements IEncryptor {

}
```
+ 14、security-spring-boot-starter组件的使用，主是系统权鉴功能，类似于spring security的@PreAuthorize注解的功能
```
1、在pom中加入security-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>security-spring-boot-starter</artifactId>
</dependency>
2、通过spring.global.method.security.enable为true来开启鉴权功能
3、鉴权使用，@PreAuthorize("hasAuthority('具体的权限code')")
@PostMapping("/updateExpireDeviceDateAndSendAuthInfo")
@ApiOperation(value = "所有认证设备信息")
@LogAnnotation(module = "device-auth", recordRequestParam = false)
@PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "MODIFY')")
public Result updateExpireDeviceDateAndSendAuthInfo(@RequestBody DeviceAuth deviceAuth) {
    return Result.succeed(deviceAuthService.updateExpireDeviceDate(deviceAuth));
}
```
+ 15、swagger-spring-boot-starter组件的使用
```
1、在pom中加入swagger-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>swagger-spring-boot-starter</artifactId>
</dependency>
2、在Controller上的使用,@Api("XxxApi")
@RestController
@RequestMapping("/deviceAuth/*")
@Api("客户相关的api")
public class DeviceAuthCtrl {
    ...
}
3、在方法上的使用, @ApiOperation(value = "xxx")
@PostMapping("/findDeviceAuthByPaged")
@ApiOperation(value = "客户信息分页")
@LogAnnotation(module = "device-auth", recordRequestParam = false)
@PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
public PageResult<DeviceAuthVo> findDeviceAuthByPaged(@RequestBody PageRequest<DeviceAuthVo> t) throws Exception {
    PageResult<DeviceAuthVo> deviceAuthByPaged = deviceAuthService.findDeviceAuthByPaged(t);
    List<DeviceAuthVo> data = deviceAuthByPaged.getData();
    if (!CollectionUtils.isEmpty(data)) {
        for (DeviceAuthVo datum : data) {
            datum.setIsOnLine(DeviceAuthUtils.isOnLineByUniqueCode(datum.getUniqueCode()));
        }
    }
    return deviceAuthByPaged;
}
4、在实体上的使用，@ApiModel("xxx")
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("客户实体Vo")
public class DeviceAuthVo extends DeviceAuth {
    ...
}
5、请注意swagger的开启，只在dev、uat、ds环境在会生成swagger-api
@Configuration
@EnableSwagger2
@Profile({"dev", "uat", "ds"})
public class Swagger2Config implements WebMvcConfigurer {
    ...
}
```
+ 16、系统的登录流程梳理
```
1、页面组装用户名和密码为json格式，并提交至SysUserCtrl的login方法
2、login方法会对提交提交进行初步的校验
@PostMapping("/login")
@ApiOperation(value = "登录")
@LogAnnotation(module = "system", recordRequestParam = false)
public Result login(@RequestBody SysUser sysUser, HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    if (sysUser == null) {
        logger.error("user is null !!!");
        throw new BizException("用户或密码为空！");
    }
    if (StringUtils.isBlank(sysUser.getUserName())) {
        logger.error("username is null !!!");
        throw new BizException("用户或密码为空！");
    }
    if (StringUtils.isBlank(sysUser.getPassword())) {
        logger.error("password is null !!!");
        throw new BizException("用户或密码为空！");
    }
    Map<String, Object> token = sysUserService.login(sysUser, userAgent);
    return Result.succeed(token, "登录成功！");
}
3、调用sysUserService.login方法进行登录验证：
    3.1、根据用户名在数据库查找当前用户信息
    3.2、判断当前用户是否存在
    3.3、当前当前用户是否出现多个
    3.4、校验密码
    3.5、检查帐号状态
    List<SysUser> list = this.findSysUserByUserName(sysUser.getUserName());
    if (list == null || list.size() == 0) {
        logger.error("{}用户不存在！", sysUser.getUserName());
        throw new AccountNotFoundException("用户名或密码有误！");
    }
    if (list.size() > 1) {
        logger.error("{}用户存在{}个", sysUser.getUserName(), list.size());
        throw new AccountException("帐号不唯一,请联系管理员！");
    }

    SysUser dbUser = list.get(0);
    String encode = myPasswordEncoder.encode(sysUser.getPassword());
    if (!encode.equals(dbUser.getPassword())) {
        logger.error("用户{}的密码有误", dbUser.getUserName());
        throw new BadCredentialsException("用户名或密码有误！");
    }
    checkIsEnabled(dbUser);
4、创建登录用户Authentication
Authentication loginUser = new Authentication();
5、根据用户类型设置用户鉴权信息，角色、菜单、权限
 private void setUserExtInfo(String userAgent, SysUser dbUser, Authentication loginUser) {
    BeanUtils.copyProperties(dbUser, loginUser);
    List<SysGroup> groupByUserId = sysUserGroupDao.findGroupByUserId(dbUser.getId());
    List<SysRole> rolesByUserId = sysUserRoleDao.findRolesByUserId(dbUser.getId());
    loginUser.setSysGroups(groupByUserId);
    loginUser.setSysRoles(rolesByUserId);
    if (dbUser.getType() == UserTypeEnum.ADMIN) {
        //List<SysMenuPermission> permissionDaoByRoleIds = sysMenuPermissionService.list();
        List<SysMenu> menus = sysMenuService.list();
        List<SysPermission> sysPermissions = sysPermissionService.list();
        loginUser.setPermissions(getMenuPermissions(menus, sysPermissions));
        loginUser.setMenus(menus);
    } else if (!CollectionUtils.isEmpty(rolesByUserId)) {
        Set<Long> roleIds = rolesByUserId.stream().map(SysRole::getId).collect(Collectors.toSet());
        List<SysRoleMenuPermission> grantMenus = sysRoleMenuPermissionService.findGrantMenus(roleIds);
        Set<Long> menuIds = grantMenus.stream().map(SysRoleMenuPermission::getMenuId).collect(Collectors.toSet());
        List<SysMenu> menus = sysMenuService.findSysMenuByMenuIds(menuIds);
        List<SysRoleMenuPermission> grantMenuPermissions = sysRoleMenuPermissionService.findGrantMenuPermissions(roleIds);
        Set<Long> menuPermissionIds = grantMenuPermissions.stream().map(SysRoleMenuPermission::getMenuPermissionId).collect(Collectors.toSet());
        List<SysMenuPermission> sysMenuPermissionByIds = sysMenuPermissionService.findSysMenuPermissionByIds(menuPermissionIds);
        loginUser.setPermissions(sysMenuPermissionByIds);
        loginUser.setMenus(menus);
    }
    Collections.sort(loginUser.getMenus(), new Comparator<SysMenu>() {
        @Override
        public int compare(SysMenu o1, SysMenu o2) {
            return o1.getSort() - o2.getSort();
        }
    });
    setLoginType(loginUser, userAgent);
}
6、创建用户认证信息保存redis并返回
String guid = UUIDUtils.getGUID();
loginUser.setToken(guid);
loginUser.setOnLine(true);
loginUser.setRefreshToken(refreshToken);
// loginUser.setDataPermissionVo(this.getCurrentUserDataPermissions(loginUser));
//缓存用户
AuthenticationUtil.setAuthenticationToRedis(loginUser);
AuthenticationUtil.setRefreshTokenToRedis(refreshToken, loginUser);
return this.getUserToken(guid, refreshToken);
7、前端获取用户信息的api
访问地址:http://x.x.x.x:9902/user/current
@GetMapping("/current")
@ApiOperation(value = "获取当前登录用户")
@LogAnnotation(module = "system", recordRequestParam = false)
public Result getLoginAppUser() {
    return Result.succeed(AuthenticationUtil.getAuthentication());
}
8、token失效刷新api
访问地址:http://x.x.x.x:9902/user/refresh/{token}
@GetMapping("/refresh/{token}")
@ApiOperation(value = "refresh")
@LogAnnotation(module = "system", recordRequestParam = false)
public Result refreshToken(@PathVariable String token, HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    try {
        Map<String, Object> res = sysUserService.refreshToken(token, userAgent);
        return Result.succeed(res, "刷新成功！");
    } catch (Exception e) {
        logger.error("refreshToken error:{}", e.getMessage(), e);
        return Result.failed(401, e.getMessage());
    }
}
9、获取当前用户的访问菜单Api
访问地址:http://x.x.x.x:9902/menu/current
@GetMapping("/current")
@ApiOperation(value = "获取当前用户授权的Menus")
@LogAnnotation(module = "system", recordRequestParam = false)
public Result getCurrentMenus() {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return Result.succeed(MenuUtils.treeBuilder(authentication.getMenus()));
}
```
+ 17、系统的登出流程梳理
```
访问地址:http://x.x.x.x:9902/user/logout
@GetMapping("/logout")
@ApiOperation(value = "登出当前登录用户")
@LogAnnotation(module = "system", recordRequestParam = false)
public Result logout() {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    if (authentication != null) {
        authentication.setOnLine(false);
    }
    return AuthenticationUtil.logout() ? Result.succeed("操作成功") : Result.succeed("操作失败");
}
```
+ 18、系统拦截请求验证用户，并设置当前登录用户信息本地线程上
```
1、系统默认会拦截所有请求，如有要旅行的url,请在yml中进行配置
spring:
  web:
    request:
      white: /user/login,/user/refresh/**,/user/register,/swagger-ui.html/**,/swagger-resources/**,/v2/**,/webjars/**,/csrf,/favicon.ico,/error,/,/individual-soldier-auth/**,/api/auth/**
2、拦截验证用户
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = this.extractToken(request);
        if (StringUtils.isBlank(token)) {
            throw new UserIllegalException("token缺失！");
        }
        logger.info("获取用户token：{}", token);
        Authentication user = AuthenticationUtil.getAuthenticationToRedis(token);
        if (user == null) {
            throw new UserIllegalException("用户不存在");
        }
        AuthenticationUtil.setCurrentUser(user);
        AuthenticationUtil.setAuthenticationTokenExpires(token);
        return true;
    }

    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            logger.warn("Token not found in headers. Trying request parameters.");
            token = request.getParameter(IndividualSoldierAuthConstantUtils.REQUEST_TOKEN_KEY);
            if (token == null) {
                logger.error("Token not found in request parameters.  illegal request.");
            }
        }
        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders(IndividualSoldierAuthConstantUtils.REQUEST_HEADER_AUTHORIZATION);
        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String) headers.nextElement();
        } while (!value.toLowerCase().startsWith(IndividualSoldierAuthConstantUtils.TOKEN_BEARER_VAUE.toLowerCase()));

        String authHeaderValue = value.substring(IndividualSoldierAuthConstantUtils.TOKEN_BEARER_VAUE.length()).trim();
//        int commaIndex = authHeaderValue.indexOf(44);
//        if (commaIndex > 0) {
//            authHeaderValue = authHeaderValue.substring(0, commaIndex);
//        }

        return authHeaderValue;
    }
}

```
+ 19、CorsConfig跨域处理
```
@Configuration
public class CorsConfig {
	 /**
     * 跨域支持
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
```
+ 20、事务处理，采用全局事务处理，无需要在每个方法添加注解
```
1、yml中的配置,主要是设置是否启用，切面及超时间
spring:
  tx:
    manager:
      enabled: true
    service:
      pointcut:
        expression: execution (* com.key.win..service.impl.*Impl.*(..))
      method:
        timeout: -1
2、配置类
/**
 * AOP事务处理
 * <p>
 * spring事务处理优先使用事务配置顺序
 * 方法级别@Transactional -> 类级别@Transactional -> AOP配置
 */
@Aspect
@Configuration
@ConditionalOnProperty(name = "spring.tx.manager.enabled", matchIfMissing = false, havingValue = "true")
public class TxAdviceConfig {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * service事务aop
     */
    // module.tx.service.enabled
    public static String TX_SERVICE_ENABLED = "true";
    public static String TX_SERVICE_POINTCUT_EXPRESSION = "execution (* com.key.win..service.impl.*Impl.*(..))";
    public static int TX_SERVICE_METHOD_TIMEOUT = 5;

    @Value("${spring.tx.service.pointcut.expression}")
    public void setTX_SERVICE_POINTCUT_EXPRESSION(String TX_SERVICE_POINTCUT_EXPRESSION) {
        TxAdviceConfig.TX_SERVICE_POINTCUT_EXPRESSION = TX_SERVICE_POINTCUT_EXPRESSION;
    }

    @Value("${spring.tx.service.method.timeout:5}")
    public void setTX_SERVICE_METHOD_TIMEOUT(int TX_SERVICE_METHOD_TIMEOUT) {
        TxAdviceConfig.TX_SERVICE_METHOD_TIMEOUT = TX_SERVICE_METHOD_TIMEOUT;
    }

    /**
     * 配置事务管理器使用springboot默认的
     *
     * 关于事务管理器，不管是JPA还是JDBC等都实现自接口 PlatformTransactionManager 
     * 如果你添加的是 spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager 实例。
     * 如果你添加的是 spring-boot-starter-data-jpa 依赖，框架会默认注入 JpaTransactionManager 实例。
     *
     * 手工注解@Bean 将被优先加载，框架不会重新实例化其他的 PlatformTransactionManager 实现类。
     * @param dataSource
     * @return
     * @throws Exception
     */
    /*@Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }*/

    /**
     * 事务的实现Advice
     *
     * @return
     * @see org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration$TransactionTemplateConfiguration
     */
    @Bean
    public TransactionInterceptor txAdvice(/*@Qualifier("txManager") */PlatformTransactionManager transactionManager) {
        logger.warn("#[Tx Config:TX_SERVICE_METHOD_TIMEOUT]" + TX_SERVICE_METHOD_TIMEOUT);

        /* 只读事务，不做更新操作， 不超时 */
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setName("PTA-READONLY-TX");
        readOnlyTx.setDescriptor("PTA READONLY TX");
        readOnlyTx.setReadOnly(true);
        //readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setName("PTA-REQUIRED-TX");
        requiredTx.setDescriptor("PTA REQUIRED TX");
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_SERVICE_METHOD_TIMEOUT);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        txMap.put("qry*", readOnlyTx);
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("*", requiredTx);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * 切面的定义    pointcut及advice
     *
     * @param txAdvice
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor(/*@Qualifier("txAdvice") */TransactionInterceptor txAdvice) {
        logger.warn("#[Tx Config:TX_SERVICE_POINTCUT_EXPRESSION]" + TX_SERVICE_POINTCUT_EXPRESSION);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(TX_SERVICE_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

}
```
+ 21、websocket的使用
```
1、在pom中加入websocket-spring-boot-starter依赖
<dependency>
    <groupId>com.key.win</groupId>
    <artifactId>websocket-spring-boot-starter</artifactId>
</dependency>
2、yml中的配置,spring.web.socket.exporter.enable值为ture是启用websocket,而spring.web.socket.exporter.cluster的值为true时是webscoke集群方式，采用redis的发布订阅方式
spring:
  web:
    socket:
      exporter:
        enable: true
      cluster: true
      path: /ws/{token}
3、websocket的使用,请求地址为: ws://x.x.x.x:9902/ws/{token}
  3.1、websocket client端一般都是通过http发起请请求，地址http://x.x.x.x:9902/ws/**,入口类：Controller:WebSocketCtrl
  3.2、websocket server端会直接将消息推送至websocket的client端，MessageSendUtil.java和WebSocketUtil是两个推送的工具类
```
##前端系统功能或模块的使用说明
+ 1、前端的路径在项目中的web-portal文件夹下的individual-soldier-auth-front下的view文件中，目前有三个环境变量，既dev、uat、prod。主要配置了build文件之后的输入目录信息，环境变量、及访问的api，在dev环境中采用的代理模式，主要解决在dev环境中，因为没有登录而造成的跨域问题。修改后端的请求路径，请在这三个文件中修改
```
1、.env.development
//生成地址
outputDir = "production"
VUE_APP_MODE = 'production'
NODE_ENV = 'prod'
VUE_APP_HTTP_BASE_URL = '/api'
VUE_APP_WEBSOCKET_BASE_WS_URL='ws://127.0.0.1:9902/ws/'
VUE_APP_TEXT = '生产环境'
1.1、代理配置:vue.config.js
module.exports = {
  outputDir: 'build/' + (process.env.outputDir ? process.env.outputDir : 'dist'),
  devServer: {
    proxy: {
      '/api': {
        // target: 'https://192.168.1.147:8443',
        target: 'http://127.0.0.1:9902',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
2、.env.uat
//生成地址
outputDir = "uat"
VUE_APP_MODE = 'uat'
NODE_ENV = 'uat'
VUE_APP_HTTP_BASE_URL = 'http://127.0.0.1:9002'
VUE_APP_WEBSOCKET_BASE_WS_URL='ws://127.0.0.1:9002/ws/'
VUE_APP_TEXT = 'uat环境'
3、.env.production
//生成地址
outputDir = "production"
VUE_APP_MODE = 'production'
NODE_ENV = 'prod'
VUE_APP_HTTP_BASE_URL = 'http://127.0.0.1:9002'
VUE_APP_WEBSOCKET_BASE_WS_URL='ws://127.0.0.1:9002/ws/'
VUE_APP_TEXT = '生产环境'

```
+ 2、fetch.ts文件是所有访问后端的http请求的拦截器，主要定义的请求的头的处理，对token的处理，正常返回值和异常情况的处理
````
1、创建Axios实例，获取访问后端的api及超时时间
const instance: AxiosInstance = axios.create({
  baseURL: getHttpDomain(),
  timeout: 3000
})
1.1、在getHttpDomain方法中对访问环境变量的api进行获取
get-env.ts
export function getHttpDomain(): string {
  const baseURL = process.env.VUE_APP_HTTP_BASE_URL
  return processBaseURL(baseURL)
}
2、请求头的处理
instance.interceptors.request.use(
  (config: AxiosRequestConfig): AxiosRequestConfig => {
    config.headers = config.headers || {}
    config.headers['x-requested-with'] = 'XMLHttpRequest'
    if (!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    }
    const token: string | null = localStorage.getItem('access_token')
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  },
  (err: AxiosError) => {
    return errorHandle(err)
  }
)
3、返回值的处理，这主要分为正常的返回值处理和异常情况下的处理:正常情况的下的处理直接转为KWResponse.Result | KWResponse.PageResult | void，而异常情况处理就要分很多情况
instance.interceptors.response.use(
  (result: AxiosResponse): KWResponse.Result | KWResponse.PageResult | void => {
    return result.data as KWResponse.Result | KWResponse.PageResult | void
  },
  (err: AxiosError) => {
    return errorHandle(err)
  }
)
4、异常情况的处理
function errorHandle(err: AxiosError): Promise<unknown> {
  if (err.code === 'ECONNABORTED' && err.message.indexOf('timeout') !== -1) {
    return onTimeoutError(err)
  } else if (err.response === undefined) {
    return onNetWorkError(err)
  }
  let message = ''
  switch (err.response?.status) {
    case 400:
      message = '请求错误'
      break
    case 401:
      return onAuthenticationError(err)
    case 403:
      message = '拒绝访问'
      break
    case 404:
      message = `请求地址出错: ${err.response?.config.url}`
      break
    case 408:
      message = '请求超时'
      break
    case 500:
      message = '服务器内部错误'
      break
    case 501:
      message = '服务未实现'
      break
    case 502:
      message = '网关错误'
      break
    case 503:
      message = '服务不可用'
      break
    case 504:
      message = '网关超时'
      break
    case 505:
      message = 'HTTP版本不受支持'
      break
    default:
      message = '其他情况'
  }
  Message.error((err.response.data as string) || message)
  return Promise.reject(err)
}
5、token失效后根据refreshToken来刷新token,当token失效后会返回http状态码为401，这个时候就执行了onAuthenticationError(err)方法进行token刷新动作，如成功刷新后，再次请求目标api，如刷新失败后，清空相关token，跳转至登录页面
async function onAuthenticationError(error: AxiosError) {
  local.clear(settings.activePath)
  const { code, data }: KWResponse.Result<LoginResponse> = await getNewToken()
  if (code === 200) {
    local.save(settings.accessToken, data.access_token)
    local.save(settings.refreshToken, data.refresh_token)
    return await instance.request(error.config)
  } else {
    Message.error('用户失效，请重新登录！')
    local.clear(settings.accessToken)
    local.clear(settings.refreshToken)
    router.replace({
      path: '/login'
    })
    return error
  }
}
async function getNewToken(): Promise<KWResponse.Result<LoginResponse>> {
  const refreshToken = local.getStr(settings.refreshToken)
  return await instance.get('user/refresh/' + refreshToken, {})
}
````
+ 3、shims-vue.d.ts文件说明，此文件中定义系统的全局公共模型，主要分：KWResponse、Model、KWRequest、KWRule几部分。
```
1、系统中的标准model基类，在Model命令空间下，需要说明的是id在后端是long类型，但由于后端long类型长度过大，前端接后会导致精度丢失的问题，因为前端将使用string来接收这个字段返回的值(返回这也需要后端的配合，在该字段的输出json时需以字符串输出)
namespace Model {
  interface Id {
    id: string
  }
  interface Version extends Id {
    version: number
  }
  interface BaseField extends Version {
    createDate: number
    updateDate: number | null
    createUserId: string
    updateUserId: string | null
    enableFlag: boolean
    createUserName: string
    updateUserName: string | null
  }
  interface Name {
    name: string
  }
  interface CodeField extends Name {
    code: string
  }
  interface ParentId {
    parentId: string
  }
  interface EnumEntity {
    code: string
    stringValue: string
    text: string
  }
}
2、KWRequest标准的分页请求需要模型，T代表具体的实体模型
namespace KWRequest {
  type OrderDir = 'ASC' | 'DESC'
  type MethodType = 'POST' | 'GET'
  interface PageRequest<T = undefined> {
    pageNo: number
    pageSize: number
    sortName?: string
    sortDir?: OrderDir
    t?: T
  }
}
3、KWResponse 标准的返回值模型，非分页统一返回Result<T>，分页统一返回PageResult<T>，T代表具体的实体模型
namespace KWResponse {
  interface BaseResult {
    code: number
    msg: string
  }
  interface Result<T = undefined> extends BaseResult {
    data: T
  }
  interface PageResult<T = undefined> extends BaseResult {
    pageNo: number
    pageSize: number
    count: number
    data: T[]
    totalPage: number
  }
}
4、KWRule Form验证模型，定义了最常见的验证规则
namespace KWRule {
  interface CallbackFunction {
    (error?: Error): void
  }
  interface TriggerRule {
    trigger: string | Array<string>
  }
  interface MessageRule extends TriggerRule {
    message: string
  }
  interface Rule extends MessageRule {
    required: boolean
  }
  interface DateRule extends MessageRule {
    type: string
  }
  interface ArrayRule extends MessageRule {
    type: string
  }
  interface MixinRule extends MessageRule {
    min: number
    max: number
  }
  interface ValidatorFunction {
    // eslint-disable-next-line no-use-before-define
    (rule: ValidatorRule, value: string, cb: CallbackFunction): void
  }
  interface ValidatorRule extends TriggerRule {
    validator: ValidatorFunction
  }
  interface TransformFunction {
    (value: string): number
  }
  interface NumberRule extends MessageRule {
    type: string
    transform: TransformFunction
  }
}
```
+ 4、setting.ts 系统设置，主要定义一些利用比较高的变更及系统的中的一些开关
```
interface ISettings {
  title: string // Overrides the default title
  activePath: string
  accessToken: string
  refreshToken: string
  menuTypeItem: string
  menuTypeDirectory: string
  defaultAvatar: string
  isEnablePermission: boolean
  isEnableWebSocket: boolean
}

// You can customize below settings :)
const settings: ISettings = {
  title: 'key-win后台管理',
  activePath: 'activePath',
  accessToken: 'access_token',
  refreshToken: 'refresh_token',
  menuTypeItem: '菜单',
  menuTypeDirectory: '目录',
  defaultAvatar: require('./assets/head.png'),
  isEnablePermission: true,
  isEnableWebSocket: true
}

export default settings
```
+ 5、permission.ts 前端的请求拦截器，用来判断当前用户是否登录，如果没有就直接跳转至登录，在每个请求开始对请求做登录校验，结束时设置html的title
```
router.beforeEach(async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  NProgress.start()
  // to 将访问哪一个路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数,表示放行
  //   next() 放行 next('/login') 强制跳转
  if (to.path === '/login') {
    return next()
  }
  // 获取token
  const refreshToken = local.getAny(settings.refreshToken)
  if (!refreshToken) {
    return next('/login')
  } else {
    const dynamicRoutes: Array<RouteConfig> = PermissionModule.getDynamicRoutes
    // 如果是第一次登录成功中转过来，dynamicRoutes的长度必为0，这个时候就需要获取用户信息、当前登录用户的菜单及操作权限
    if (dynamicRoutes.length === 0) {
      getUserInfo(to, from, next)
    } else {
      next()
    }
  }
})

router.afterEach((to: Route) => {
  NProgress.done()
  // set page title
  document.title = getPageTitle((to.meta as RouteMeta).title)
})
```
+ 6、登录流程解析
```
1、用户登录Login.vue,前端用户名密码不为空后，封装json后，调用user/login进行登录，登录成功后返回token信息对象，跳转至index.vue
login(): void {
    this.loginFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      console.log(valid)
      const { code, data, msg }: KWResponse.Result<LoginResponse> = await LoginApi(this.loginForm)
      if (code === 200) {
        // 登录成功
        local.save(settings.accessToken, data.access_token)
        local.save(settings.refreshToken, data.refresh_token)
        local.clear(settings.activePath)
        this.$router.push('/index')
      } else {
        this.$message.error(msg)
      }
    })
}
2、根据路由跳转至index.vue过程中，进入请求拦截器中(上面第5部分)
2.1、获取用户信息
export const getUserInfo = async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  const { code, data, msg }: KWResponse.Result<LoginSuccessUserInfo> = await UserInfoApi()
  console.log(data)
  if (code === 200) {
    UserModule.changeUser(data)
    getMenus(to, from, next)
  } else {
    Message.error(msg || '获取用户失败！')
  }
}
2.2、保存登录用户信息到UserModule的Vuex中
export interface IUserState {
  user: LoginSuccessUserInfo | null
}

@Module({ dynamic: true, store, name: 'user' })
class UserStore extends VuexModule implements IUserState {
  public user: LoginSuccessUserInfo | null = null

  get loginUser(): LoginSuccessUserInfo | null {
    return this.user
  }

  @Mutation
  public SET_USERINFO(user: LoginSuccessUserInfo | null): void {
    this.user = user
  }

  @Action({ commit: 'SET_USERINFO' })
  public changeUser(user: LoginSuccessUserInfo): LoginSuccessUserInfo {
    return user
    // this.CHANGE_USER(user)
  }

  @Action({ commit: 'SET_USERINFO' })
  public clearUser(): null {
    return null
  }
}
export const UserModule = getModule(UserStore)
2.3、获取菜单信息
export const getMenus = async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  const { code, data, msg }: KWResponse.Result<Array<MenuResponse>> = await CurrentMenuApi()
  if (code === 200) {
    console.log(data)
    const menus: Array<MenuResponse> = data // data.filter(item => item.name.indexOf('vue') > -1) // 暂时先这么处理
    // this.menus = data
    MenuModule.changeMenu(menus)
    PermissionModule.generateRoutes()
    // router.addRoutes(PermissionModule.getDynamicRoutes)
    if (settings.isEnableWebSocket) {
      SocketModule.initSocket()
    }

    next({ ...to, replace: true } as RawLocation)
  } else {
    Message.error(msg || '获取当前用户菜单失败！')
  }
}
2.4、把菜单信息存储到中MenuModule的vuex中
export interface IMenuState {
  menus: Array<MenuResponse>
}

@Module({ dynamic: true, store, name: 'menu' })
class MenuStore extends VuexModule implements IMenuState {
  public menus: Array<MenuResponse> = []

  get getMenus() {
    return this.menus
  }

  @Mutation
  public CHANGE_MENU(menus: Array<MenuResponse>): void {
    this.menus = menus
  }

  @Action
  public changeMenu(menus: Array<MenuResponse>): void {
    console.log('action:' + menus)
    this.context.commit('CHANGE_MENU', menus)
  }

  @Action({ commit: 'CHANGE_MENU' })
  public clearMenu(): Array<MenuResponse> {
    return new Array<MenuResponse>()
  }
}

export const MenuModule = getModule(MenuStore)
2.5、PermissionModule的generateRoutes方法通过进行动态路由解析组装，并把路由信息动态加载到router中，进行实际路由
export interface IPermissionState {
  routes: Array<RouteConfig>
  dynamicRoutes: Array<RouteConfig>
}

@Module({ dynamic: true, store, name: 'permission' })
class PermissionStore extends VuexModule implements IPermissionState {
  public routes: Array<RouteConfig> = []
  public dynamicRoutes: Array<RouteConfig> = []
  public indexRoute: RouteConfig = {
    path: '/index',
    name: 'Index',
    redirect: '/home',
    component: () => import(/* webpackChunkName: "index" */ '@/views/index/Index.vue'),
    children: [
      // 这个是空白页面，重新加载当前页面会用到
      {
        name: 'blank',
        path: '/blank'
      },
      {
        path: '/home',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '@/views/index/home/Home.vue'),
        meta: {
          title: '首页'
        }
      }
    ]
  }

  public defaultRout: RouteConfig = {
    path: '/',
    redirect: '/index'
  }

  public route404: RouteConfig = {
    path: '*',
    name: '/404',
    component: () => import(/* webpackChunkName: "test" */ '@/components/404.vue')
  }

  get getDynamicRoutes() {
    return this.dynamicRoutes
  }

  @Mutation
  public SET_ROUTES(childrenRoutes: Array<RouteConfig>): void {
    this.routes = []
    this.dynamicRoutes = []
    if (childrenRoutes.length > 0) {
      childrenRoutes.forEach(item => {
        const children = this.indexRoute.children as Array<RouteConfig>
        children.push(item)
      })
    }
    // (this.indexRoute.children as Array<RouteConfig>).push(this.route404)
    this.routes = constantRoutes.concat(this.indexRoute)
    this.routes.push(this.defaultRout)
    this.routes.push(this.route404)
    this.dynamicRoutes = [this.indexRoute, this.defaultRout, this.route404]
    // router.addRoutes(this.dynamicRoutes)
    router.addRoute(this.indexRoute)
    router.addRoute(this.defaultRout)
    router.addRoute(this.route404)
  }

  @Mutation
  public CLEAR_ROUTES(): void {
    this.routes = []
    this.dynamicRoutes = []
    router.replace({ path: '/login' })
  }

  @Action({ commit: 'CLEAR_ROUTES' })
  public clearRoutes(): void {
    console.log('clearRoutes')
  }

  @Action
  public generateRoutes(): void {
    const menus: Array<MenuResponse> = MenuModule.getMenus
    const routes: Array<RouteConfig> = []
    asyncRouter(menus, routes)
    this.context.commit('SET_ROUTES', routes)
    // this.SET_ROUTES(routes)
  }
}

export const asyncRouter = (menus: Array<MenuResponse>, routes: Array<RouteConfig>): void => {
  if (menus != null && menus.length > 0) {
    menus.forEach(menu => {
      if (menu.subMenus != null && menu.subMenus.length > 0) {
        asyncRouter(menu.subMenus, routes)
      } else {
        /**
           * Note: sub-menu only appear when route children.length >= 1
           * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
           *
           * hidden: true                   if set true, item will not show in the sidebar(default is false)
           * alwaysShow: true               if set true, will always show the root menu
           *                                if not set alwaysShow, when item has more than one children route,
           *                                it will becomes nested mode, otherwise not show the root menu
           * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
           * name:'router-name'             the name is used by <keep-alive> (must set!!!)
           * meta : {
                roles: ['admin','editor']    control the page roles (you can set multiple roles)
                title: 'title'               the name show in sidebar and breadcrumb (recommend set)
                icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
                noCache: true                if set true, the page will no be cached(default is false)
                affix: true                  if set true, the tag will affix in the tags-view
                breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
                activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
              }
           */
        const route: RouteConfig = {
          path: menu.url,
          name: menu.url.substr(1),
          component: loadViewsd(menu.path),
          meta: {
            title: menu.name
          }
        }
        routes.push(route)
      }
    })
  }
}

export const loadViewsd = (view: string) => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return (resolve: (...modules: any[]) => void): void => require([`@/views/index/${view}.vue`], resolve)
}

export const PermissionModule = getModule(PermissionStore)
2.6、通过上面的路由信息看到，路由到index之后，其实跳转到home路由，进入到Home.vue页面
index.vue
<template>
  <div class="index">
    <el-container class="index-container">
      <LeftMenu :menusList="menus"></LeftMenu>
      <el-container>
        <el-header>
          <HeaderNav></HeaderNav>
        </el-header>
        <PageTabs :keep-alive-component-instance="keepAliveComponentInstance" />
        <el-main>
          <div ref="keepAliveContainer" style="padding-top:20px;background-color: #fff;">
            <keep-alive>
              <router-view :key="$route.fullPath" />
            </keep-alive>
          </div>
        </el-main>
        <el-footer>
          <div class="footer">
            Copyright © {{ new Date().getFullYear() }} key-win All rights reserved.
            <span class="pull-right">Version 2.0</span>
          </div>
        </el-footer>
      </el-container>
    </el-container>
    <KWUploader></KWUploader>
  </div>
</template>
2.6.1、LeftMenu组件，进行菜单渲染
<template>
  <el-aside :width="collapseMenuState ? '64px' : '200px'" style="overflow:hidden">
    <div  v-if="collapseMenuState == false" style="padding: 10px 20px 10px 20px;" >
      <img src="../../assets/keywin.png" alt="" />
    </div>
    <div  v-if="collapseMenuState == true" style="padding: 10px 20px 10px 10px;">
      <img src="../../assets/logo.png" alt="" />
    </div>
    <el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" unique-opened :collapse="collapseMenuState" :collapse-transition="false" router :default-active="activePath">
      <!-- 一级菜单 -->
      <template v-for="item in menusList">
        <el-submenu v-if="item.isHidden === false && item.isMenu === 1" :index="item.id+''" :key="item.id">
          <!-- 一级菜单模板区 -->
          <template slot="title">
            <i :class="item.css"></i>
            <span>{{ item.name }}</span>
          </template>
          <!--二级菜单-->
          <template v-for="subItem in item.subMenus">
            <el-menu-item v-if="subItem.isHidden === false && subItem.isMenu === 1" :index="subItem.url" :key="subItem.id" @click="saveActivePath(subItem.url)">
              <!-- 二级菜单模板区 -->
              <template slot="title">
                <i :class="subItem.css"></i>
                <span>{{ subItem.name }}</span>
              </template>
            </el-menu-item>
          </template>
        </el-submenu>
      </template>
    </el-menu>
  </el-aside>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'
import { local } from '@/store'
import { MenuCollapseModule } from '@/store/menu-collapse-store'
import settings from '@/settings'

@Component({
  components: {}
})
export default class LeftMenu extends Vue {
  activePath = ''
  @Prop({ default: [] })
  readonly menusList!: Array<MenuResponse> | []

  created(): void {
    this.activePath = local.getStr(settings.activePath)
  }

  saveActivePath(activePath: string): void {
    local.save(settings.activePath, activePath)
    this.activePath = activePath
  }

  get collapseMenuState(): boolean {
    return MenuCollapseModule.getCollapseMenuState
  }
}
</script>
2.6.2、HeaderNav组件，进行主页面头部渲染
<template>
  <div class="el-header-div">
    <div>
      <!-- <div class="toggle-button" @click="toggleCollpase">|||</div> -->
      <i :class="collapseMenuIcon" @click="toggleCollpase"></i>
      <span>后台管理系统</span>
    </div>
    <div>
      <el-tooltip class="fullscreen" :content="tipFullScrollContent" effect="dark" placement="bottom" fullscreen>
        <i :class="fullScrollClass" @click="fullScroll" />
      </el-tooltip>
      <el-tooltip content="站内消息" effect="dark" placement="bottom">
      </el-tooltip>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <!-- src="@/assets/404-images/404-cloud.png" -->
        <div class="avatar-wrapper">
          <img v-src="headImgUrl" class="user-avatar" />
          <div>
            <span>{{ nickName }}</span>
            <i class="el-icon-caret-bottom" />
          </div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="userInfoCenter">个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="showEditDialog">修改密码</el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出系统</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog title="修改密码" @close="aditUserPasswordClosed" :visible.sync="userPasswordDialogVisble" width="20%">
      <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="100px">
        <el-form-item label="原 密 码" prop="password">
          <el-input v-model="userForm.password" type="password" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="新 密 码" prop="newPassword">
          <el-input v-model="userForm.newPassword" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePassword">
          <el-input v-model="userForm.rePassword" style="max-width: 220px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userPasswordDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editUserPassword">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { UserModule } from '@/store/user-store'
import { MenuCollapseModule } from '@/store/menu-collapse-store'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { LoginSuccessUserInfo, ModifyPassword } from '@/views/index/system/user/interface/sys-user'
import { UpdatePasswordApi, LogoutApi } from '@/views/index/system/user/user-api'
import { local } from '@/store'
import settings from '@/settings'
import { MenuModule } from '@/store/menu-store'
import { PermissionModule } from '@/store/permission-store'
@Component
export default class HeaderNav extends Vue {
  fullScrollClass = 'iconfont icon-quanping_o'
  tipFullScrollContent = '全屏浏览'
  collapseMenuIcon = 'el-icon-s-fold'
  userPasswordDialogVisble = false
  userForm: ModifyPassword = { id: 0, password: '', newPassword: '', rePassword: '' }
  @Ref('userFormRef')
  readonly userFormRef!: ElForm

  validatePassword(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (value === '') {
      cb(new Error('请输入密码'))
    } else {
      console.log(this)
      if (this.userForm.rePassword !== '') {
        console.log(this.userFormRef.validateField)
        this.userFormRef.validateField('rePassword')
      }
      cb()
    }
  }

  // <!--二次验证密码-->
  validateRePassword(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (value === '') {
      cb(new Error('请再次输入密码'))
    } else if (value !== this.userForm.newPassword) {
      cb(new Error('两次输入密码不一致!'))
    } else {
      cb()
    }
  }

  readonly userFormRules: { password: Array<KWRule.Rule>; newPassword: Array<KWRule.Rule | KWRule.MixinRule | KWRule.ValidatorRule>; rePassword: Array<KWRule.Rule | KWRule.MixinRule | KWRule.ValidatorRule> } = {
    password: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
    newPassword: [
      { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 6, max: 15, message: '密码的长度6~15个字符之间', trigger: 'blur' },
      { validator: this.validatePassword, trigger: 'blur' }
    ],
    rePassword: [
      { required: true, message: '请再次输入密码', trigger: 'blur' },
      { min: 6, max: 15, message: '密码的长度6~15个字符之间', trigger: 'blur' },
      { validator: this.validateRePassword, trigger: 'blur' }
    ]
  }

  get nickName(): string {
    const user = (UserModule.loginUser as LoginSuccessUserInfo).user
    console.log(user && user.nickName)
    if (user !== null) {
      return user.nickName
    }
    return ''
  }

  get headImgUrl(): string | null {
    return (UserModule.loginUser as LoginSuccessUserInfo).user.headImgUrl as string
  }

  mounted(): void {
    this.$nextTick(() => {
      document.addEventListener('fullscreenchange', () => {
        this.isFullscreen()
      })
    })
  }

  destroyed(): void {
    this.$nextTick(() => {
      document.addEventListener('fullscreenchange', () => {
        this.isFullscreen()
      })
    })
  }

  toggleCollpase(): void {
    const isCollapseMenu = MenuCollapseModule.isCollapseMenu
    if (isCollapseMenu) {
      this.collapseMenuIcon = 'el-icon-s-fold'
    } else {
      this.collapseMenuIcon = 'el-icon-s-unfold'
    }
    MenuCollapseModule.changeCollapseMenu(!isCollapseMenu)
    console.log('MenuCollapseModule.getCollapseMenuState:', MenuCollapseModule.getCollapseMenuState)
  }

  userInfoCenter(): void {
    this.$router.push('/myinfo')
    console.log('userInfoCenter')
  }

  async logout(): Promise<void> {
    const { code, msg } = await LogoutApi()
    if (code !== 200) {
      this.$message.error(msg || '用户登出失败!')
    } else {
      local.clear(settings.accessToken)
      local.clear(settings.refreshToken)
      MenuModule.changeMenu([])
      UserModule.clearUser()
      PermissionModule.clearRoutes()
      location.reload()
    }
  }

  dropdownClick(): void {
    console.log('dropdownClick')
  }

  // 判断是否全屏
  public isFullscreen(): boolean {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const doc: any = document
    if (doc.fullscreenElement) {
      this.fullScrollClass = 'iconfont icon-quanping_o'
      this.tipFullScrollContent = '退出全屏'
      return true
    } else {
      this.fullScrollClass = 'iconfont icon-quxiaoquanping_o'
      this.tipFullScrollContent = '全屏浏览'
      return false
    }
  }

  // 点击按钮全屏事件
  public fullScroll(): void {
    if (this.isFullscreen()) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const doc: any = document
      // 是全屏就退出全屏
      if (doc.exitFullscreen) {
        doc.exitFullscreen()
      } else if (doc.mozCancelFullScreen) {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const docAny = doc as any
        docAny.mozCancelFullScreen()
      } else if (doc.webkitCancelFullScreen) {
        doc.webkitCancelFullScreen()
      }
    } else {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const doc: any = document.documentElement
      // 否则将页面全屏
      if (doc.requestFullscreen) {
        doc.requestFullscreen()
      } else if (doc.mozRequestFullScreen) {
        // FireFox
        doc.mozRequestFullScreen()
      } else if (doc.webkitRequestFullScreen) {
        // Chrome等
        doc.webkitRequestFullScreen()
      } else if (doc.msRequestFullscreen) {
        // IE11
        doc.msRequestFullscreen()
      }
    }
  }

  // 展示编辑用于的对话框
  showEditDialog(): void {
    this.userPasswordDialogVisble = true
  }

  aditUserPasswordClosed(): void {
    this.userPasswordDialogVisble = false
    this.userFormRef.resetFields()
  }

  editUserPassword(): void {
    console.log(this)
    this.userFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.modifyPassword()
    })
  }

  modifyPassword(): void {
    this.$confirm('确定要修改密码, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        this.userForm.id = (UserModule.loginUser as LoginSuccessUserInfo).user.id
        const { code, msg } = await UpdatePasswordApi(this.userForm)
        if (code !== 200) {
          this.$message.error(msg || '用户密码修改失败!')
        } else {
          this.userPasswordDialogVisble = false
          this.$message.success('用户密码修改成功!')
        }
      })
      .catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消密码修改'
        })
      })
  }
}
</script>
2.6.2、PageTabs组件，进行页面多页签渲染
<template>
  <div class="index">
    <el-container class="index-container">
      <LeftMenu :menusList="menus"></LeftMenu>
      <el-container>
        <el-header>
          <HeaderNav></HeaderNav>
        </el-header>
        <PageTabs :keep-alive-component-instance="keepAliveComponentInstance" />
        <el-main>
          <div ref="keepAliveContainer" style="padding-top:20px;background-color: #fff;">
            <keep-alive>
              <router-view :key="$route.fullPath" />
            </keep-alive>
          </div>
        </el-main>
        <el-footer>
          <div class="footer">
            Copyright © {{ new Date().getFullYear() }} key-win All rights reserved.
            <span class="pull-right">Version 2.0</span>
          </div>
        </el-footer>
      </el-container>
    </el-container>
    <KWUploader></KWUploader>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import HeaderNav from '@/components/header/HeaderNav.vue'
import LeftMenu from '@/components/left/LeftMenu.vue'
import PageTabs from '@/components/page-tabs/PageTabs.vue'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'
// import * as qs from 'qs'
import { MenuModule } from '@/store/menu-store'
import KWUploader from '@/components/file-uploader/GlobalUploader.vue'
@Component({
  components: {
    HeaderNav,
    LeftMenu,
    PageTabs,
    KWUploader
  }
})
export default class Index extends Vue {
  menus: Array<MenuResponse> | [] = []
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  keepAliveComponentInstance: any = null

  @Ref('pageTabs')
  readonly pageTabs!: PageTabs

  created(): void {
    this.menus = MenuModule.getMenus
  }

  mounted(): void {
    if (this.$refs.keepAliveContainer) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      this.keepAliveComponentInstance = (this.$refs.keepAliveContainer as any).childNodes[0].__vue__ // 获取keep-alive的控件实例对象
    }
  }
}
</script>
2.6.3、KWUploader组件，进行上传页面渲染
<template>
  <div id="global-uploader" :class="{'global-uploader-single': !global}">
    <!-- 上传 -->
    <uploader ref="uploader" :options="initOptions" :fileStatusText="fileStatusText" :autoStart="false"
      @file-added="onFileAdded" @file-success="onFileSuccess" @file-progress="onFileProgress" @file-error="onFileError"
      class="uploader-app">
      <uploader-unsupport></uploader-unsupport>

      <uploader-btn id="global-uploader-btn" ref="uploadBtn">选择文件</uploader-btn>

      <uploader-list v-show="panelShow">
        <div class="file-panel" slot-scope="props" :class="{ collapse: collapse }">
          <div class="file-title">
            <div class="title">文件列表</div>
            <div class="operate">
              <el-button @click="collapse = !collapse" type="text" :title="collapse ? '展开' : '折叠'">
                <i class="iconfont" :class="collapse ? 'el-icon-full-screen' : 'el-icon-minus'"></i>
              </el-button>
              <el-button @click="close" type="text" title="关闭">
                <i class="el-icon-close"></i>
              </el-button>
            </div>
          </div>

          <ul class="file-list">
            <li class="file-item" :class="`file-${file.id}`" v-for="file in props.fileList" :key="file.id">
              <uploader-file :class="'file_' + file.id" ref="files" :file="file" :list="true"></uploader-file>
            </li>
            <div class="no-file" v-if="!props.fileList.length">
              <i class="iconfont icon-empty-file"></i> 暂无待上传文件
            </div>
          </ul>
        </div>
      </uploader-list>
    </uploader>
  </div>
</template>

<script lang="ts">
/**
 *  全局上传插件，两种调用方式
 *   1. 作为全局页面的组件，使用event bus
 *   调用方法：EventHub.$emit('openUploader', {params: {}, options: {}})
 *               params: 发送给服务器的额外参数；
 *               options：上传选项，目前支持 target、testChunks、mergeFn、accept
 *
 *   监听函数：EventHub.$on('fileAdded', fn); 文件选择后的回调
 *           EventHub.$on('fileSuccess', fn); 文件上传成功的回调，监听后记得释放
 *
 *   2. 作为普通组件在单个页面中调用，使用props
 */
import { ACCEPT_CONFIG } from './config/config'
import EventHub from '../../common/event-hub/event-hub'
import SparkMD5 from 'spark-md5'
import { Component, Prop, Vue, Watch } from 'vue-property-decorator'
import { IOption, CheckFile, IFileStatusText, IChunk, IUploaderFile, IUPloader } from './config/file-option'
import { getHttpDomain } from '@/common/utils/get-env'
import { FileInfoBase } from '@/views/index/common/file/interface/file'
@Component
export default class KWUploader extends Vue {
  initOptions = {
    target: getHttpDomain() + 'chunk',
    headers: {
      Authorization: 'Bearer ' + localStorage.getItem('access_token')
    },
    chunkSize: '2048000',
    fileParameterName: 'file',
    maxChunkRetries: 3,
    // 是否开启服务器分片校验
    testChunks: true,
    // 服务器分片校验函数，秒传及断点续传基础
    checkChunkUploadedByResponse: function (chunk: IChunk, message: string): boolean {
      let skip = false

      try {
        const objMessage = JSON.parse(message)
        if (objMessage.code === 200) {
          if (objMessage.data.skipUpload) {
            skip = true
          } else {
            skip = (objMessage.data.uploaded || []).indexOf(chunk.offset + 1) >= 0
          }
        }
      } catch (e) {}

      return skip
    },
    query: function (params: CheckFile): CheckFile {
      return {
        ...params
      }
    }
  }

  panelShow = false // 选择文件后，展示上传panel
  collapse = false
  customParams: CheckFile = {
    identifier: '',
    chunkNumber: 0,
    chunkSize: 0,
    currentChunkSize: 0,
    totalSize: 0,
    totalChunks: 0,
    filename: '',
    relativePath: '',
    bizType: '',
    name: ''
  }

  mergeFn = (param: FileInfoBase): Promise<KWResponse.Result<string>> => {
    console.info(param)
    // return new Promise({ code: 200, msg: '', data: '' })
    return Promise.resolve({ code: 200, msg: '', data: '' })
  }

  fileStatusText: IFileStatusText = {
    success: '上传成功',
    error: '上传失败',
    uploading: '上传中',
    paused: '已暂停',
    waiting: '等待上传'
  }

  @Prop({ type: Boolean, default: true })
  private global!: boolean

  // 发送给服务器的额外参数
  @Prop({
    type: Object,
    default: function () {
      return {}
    }
  })
  private params!: CheckFile

  @Prop({
    type: Object,
    default: function () {
      return {}
    }
  })
  private options!: IOption

  @Watch('params', { immediate: true })
  onParams(data: CheckFile): void {
    if (data) {
      this.customParams = data
    }
  }

  @Watch('options', { immediate: true })
  onOptions(data: IOption): void {
    if (data) {
      setTimeout(() => {
        this.customizeOptions(data)
      }, 0)
    }
  }

  mounted(): void {
    EventHub.$on(
      'openUploader',
      (
        res = {
          params: { identifier: '', chunkNumber: 0, chunkSize: 0, currentChunkSize: 0, totalSize: 0, totalChunks: 0, filename: '', relativePath: '', bizType: '', name: '' },
          options: {}
        }
      ) => {
        this.customParams = res.params

        this.customizeOptions(res.options)

        if (this.$refs.uploadBtn) {
          const btn = (this.$refs.uploadBtn as Vue).$el as HTMLElement
          btn.click()
        }
      }
    )
  }

  get uploader(): IUPloader {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return (this.$refs.uploader as any).uploader
  }

  // 自定义options
  customizeOptions(opts: IOption): void {
    if (opts) {
      // 自定义上传url
      if (opts.target) {
        this.uploader.opts.target = opts.target
      }

      // 是否可以秒传、断点续传
      if (opts.testChunks !== undefined) {
        this.uploader.opts.testChunks = opts.testChunks
      }

      // merge 的方法，类型为Function，返回Promise
      this.mergeFn = opts.mergeFn || this.mergeFn
      // 自定义文件上传类型
      const input = document.querySelector('#global-uploader-btn input') as Element
      let accept = ACCEPT_CONFIG.getAll()
      if (opts.accept) {
        accept = opts.accept
      }
      input.setAttribute('accept', accept.join())
    }
  }

  onFileAdded(file: IUploaderFile): void {
    this.panelShow = true
    this.emit('fileAdded')

    // 将额外的参数赋值到每个文件上，以不同文件使用不同params的需求
    // file.params = this.customParams
    Object.keys(this.customParams).forEach(prop => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const f = file as any
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      f[prop] = (this.customParams as any)[prop]
    })

    // 计算MD5
    this.computeMD5(file).then(result => this.startUpload(result))
  }

  /**
   * 计算md5值，以实现断点续传及秒传
   * @param file
   * @returns Promise
   */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  computeMD5(file: IUploaderFile): Promise<any> {
    const fileReader = new FileReader()
    const time = new Date().getTime()
    const blobSlice = File.prototype.slice
    let currentChunk = 0
    const chunkSize = 10 * 1024 * 1000
    const chunks = Math.ceil(file.size / chunkSize)
    const spark = new SparkMD5.ArrayBuffer()
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const that = this
    // 文件状态设为"计算MD5"
    this.statusSet(file.id, 'md5')
    file.pause()

    // 计算MD5时隐藏”开始“按钮
    this.$nextTick(() => {
      const up = document.querySelector(`.file-${file.id} .uploader-file-resume`) as HTMLElement
      up.style.marginTop = '-15px'
    })

    loadNext()

    return new Promise((resolve, reject) => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      fileReader.onload = (e: any) => {
        spark.append(e.target.result)

        if (currentChunk < chunks) {
          currentChunk++
          loadNext()

          // 实时展示MD5的计算进度
          this.$nextTick(() => {
            const md5ProgressText = '校验MD5 ' + ((currentChunk / chunks) * 100).toFixed(0) + '%'
            const f = document.querySelector(`.custom-status-${file.id}`) as HTMLElement
            f.innerText = md5ProgressText
          })
        } else {
          const md5 = spark.end()

          // md5计算完毕
          resolve({ md5, file })

          const up = document.querySelector(`.file-${file.id} .uploader-file-resume`) as HTMLElement
          up.style.marginTop = '16px'

          console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`)
        }
      }

      fileReader.onerror = function () {
        that.error(`文件${file.name}读取出错，请检查该文件`)
        file.cancel()
        // eslint-disable-next-line prefer-promise-reject-errors
        reject()
      }
    })

    function loadNext() {
      const start = currentChunk * chunkSize
      const end = start + chunkSize >= file.size ? file.size : start + chunkSize

      fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end))
    }
  }

  // md5计算完毕，开始上传
  startUpload(res: { md5: string; file: IUploaderFile }): void {
    res.file.uniqueIdentifier = res.md5
    res.file.resume()
    this.statusRemove(res.file.id)
  }

  onFileSuccess(rootFile: IUploaderFile, file: IUploaderFile, response: string): void {
    const res = JSON.parse(response)

    // 服务端自定义的错误（即http状态码为200，但是是错误的情况），这种错误是Uploader无法拦截的
    if (res.code !== 200) {
      this.$notify({
        title: '操作',
        message: file.name + '上传出错啦！',
        type: 'error',
        duration: 2000
      })
      // 文件状态设为“失败”
      this.statusSet(file.id, 'failed')
      return
    }

    // 如果服务端返回了需要合并的参数
    if (res.data.needMerge) {
      // 文件状态设为“合并中”
      this.statusSet(file.id, 'merging')

      this.mergeFn({
        name: file.name,
        md5: file.uniqueIdentifier,
        contentType: '',
        size: 0,
        physicalPath: '',
        path: '',
        accessPath: '',
        physicalPathProcess: '',
        pathProcess: '',
        fileSuffix: ''
      })
        .then(() => {
          // 文件合并成功
          this.emit('fileSuccess')

          this.statusRemove(file.id)
        })
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        .catch((e: any) => {
          console.error('出错啦！', e)
        })

      // 不需要合并
    } else {
      this.emit('fileSuccess')
      console.log('上传成功')
      this.$notify({
        title: '操作',
        message: file.name + '上传成功',
        type: 'success',
        duration: 2000
      })
    }
  }

  onFileProgress(rootFile: IUploaderFile, file: IUploaderFile, chunk: IChunk): void {
    console.log(`上传中 ${file.name}，chunk：${chunk.startByte / 1024 / 1024} ~ ${chunk.endByte / 1024 / 1024}`)
  }

  onFileError(rootFile: IUploaderFile, file: IUploaderFile, error: string, chunk: IChunk): void {
    console.log(chunk.retries)
    this.error(error)
  }

  close(): void {
    this.uploader.cancel()
    this.panelShow = false
  }

  /**
   * 新增的自定义的状态: 'md5'、'merging'、'transcoding'、'failed'
   * @param id
   * @param status
   */
  statusSet(id: number, status: string): void {
    const statusMap: { [key: string]: { text: string; bgc: string } } = {
      md5: {
        text: '校验MD5',
        bgc: '#fff'
      },
      merging: {
        text: '合并中',
        bgc: '#e2eeff'
      },
      transcoding: {
        text: '转码中',
        bgc: '#e2eeff'
      },
      failed: {
        text: '上传失败',
        bgc: '#e2eeff'
      }
    }

    this.$nextTick(() => {
      const statusTag = document.createElement('p')
      statusTag.className = `custom-status-${id} custom-status`
      statusTag.innerText = statusMap[status].text
      statusTag.style.backgroundColor = statusMap[status].bgc

      const statusWrap = document.querySelector(`.file_${id} .uploader-file-status`) as Element
      statusWrap.appendChild(statusTag)
    })
  }

  statusRemove(id: number): void {
    this.$nextTick(() => {
      const statusTag = document.querySelector(`.custom-status-${id}`) as Element
      statusTag.remove()
    })
  }

  emit(e: string): void {
    EventHub.$emit(e)
    this.$emit(e)
  }

  error(msg: string): void {
    this.$notify({
      title: '错误',
      message: msg,
      type: 'error',
      duration: 2000
    })
  }
}
</script>
2.6.4、Home.vue页面渲染
<template>
  <div class="home">
    <h3>
      {{ message }}
    </h3>
    <div style="height: 687px;">
      <el-divider content-position="left">Tip示例</el-divider>
      <KWCell :gap="15" label="滕王阁序" style="width: 300px">
        <KWText value="豫章故郡，洪都新府。星分翼轸，地接衡庐。襟三江而带五湖，控蛮荆而引瓯越。物华天宝，龙光射牛斗之墟；人杰地灵，徐孺下陈蕃之榻。雄州雾列，俊采星驰。" :row="1" />
      </KWCell>
      <el-divider content-position="left">上传示例</el-divider>
      <el-button type="primary" size="medium" @click="upload">上传</el-button>
      <el-divider content-position="left">viewer示例</el-divider>
      <div v-viewer>
        <img src="@/assets/404.gif" title="点击预览" width="300px" height="200px">&nbsp;&nbsp;&nbsp;&nbsp;<img
          src="@/assets/head.png" title="点击预览" width="200px" height="200px">&nbsp;&nbsp;&nbsp;&nbsp;<img
          src="@/assets/keywin.png" width="300px" title="点击预览" height="200px">&nbsp;&nbsp;&nbsp;&nbsp;<img
          src="@/assets/logo.png" title="点击预览" width="200px" height="200px">
      </div>
      <el-divider></el-divider>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import Bus from '@/common/event-hub/event-hub'
import KWCell from '@/components/cell/Cell.vue'
import KWText from '@/components/text/Text.vue'

@Component({
  components: {
    KWCell,
    KWText
  }
})
export default class Home extends Vue {
  message = 'welcome'

  upload(): void {
    // 打开文件选择框
    Bus.$emit('openUploader', {
      // 给服务端的额外参数
      params: {
        bizType: 'default'
      }
    })
  }
}
</script>
```