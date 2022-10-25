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
            
##系统功能或模块的使用说明
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
