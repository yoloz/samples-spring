# spring-service-demo

## eureka-server

port: 8000

## helloworld

port: 9090

request:
* /hello
* /say?name=xx

## zuul

https://github.com/Netflix/zuul.git

port: 9000

* add loginFilter check token;
* add rateLimit, 2 tokens per second;
* filter duration time;

request:
* /hello\[?token=xx]
* /say?name=xx\[&token=xx]
* /baidu

### 单独zuul服务
1. 注释pom.xml中的eureka-client依赖;
2. 注释yml配置中的eureka-client配置;

## http认证

http-basic-auth port: 8001

Basic模式认证过程如下：
1. 浏览器发送http报文请求一个受保护的资源。
2. 服务端的web容器将http响应报文的响应码设为401，响应头部加入WWW-Authenticate: Basic realm=”xxx”。
```txt
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=E5A8D3C16B65A0A007CFAACAEEE6916B; Path=/spring-security-mvc-basic-auth/; HttpOnly
WWW-Authenticate: Basic realm="Spring Security Application"
Content-Type: text/html;charset=utf-8
Content-Length: 1061
Date: Wed, 29 May 2018 15:14:08 GMT
```   
3. 浏览器会弹出默认对话框让用户输入用户名和密码，并用Base64进行编码，实际是用户名+冒号+密码进行Base64编码，即Base64(username:password)，然后浏览器就会在HTTP报文头部加入这个编码，形如：Authorization: Basic YWRtaW46YWRtaW4=。
4. 服务端web容器获取HTTP报文头部相关认证信息，匹配此用户名与密码是否正确，是否有相应资源的权限，如果认证成功则返回相关资源，否则再执行②，重新进行认证。
5. 以后每次访问都要带上认证头部。

Basic认证的优点是基本上所有流行的网页浏览器都支持，一般被用在受信赖的或安全性要求不高的系统中（如路由器配置页面的认证，tomcat管理界面认证）

缺点是用户名密码基本等于是明文传输带来很大风险(结合https使用)，并且没有注销认证信息的手段，只能依靠关闭浏览器退出认证。

## 表单认证

http-form-login port:8002

http-form-Login模式的三要素：
* 登录验证逻辑
* 资源访问控制规则，如：资源权限、角色权限
* 用户信息 

一般来说，使用权限认证框架的的业务系统登录验证逻辑是固定的，而资源访问控制规则和用户信息是从数据库或其他存储介质灵活加载的。

## oauth2-sso-auth-server

port:8003

### 授权码服务器验证
1. 访问`http://192.168.90.122:8003/oauth/authorize?client_id=test&response_type=code&redirect_uri=http://www.baidu.com`
2. 跳转到SpringSecurity默认的登录页面,输入用户名/密码：test/test，点击登录后跳转到确认授权页面
3. 至少选中一个，然后点击Authorize按钮，跳转到`https://www.baidu.com/?code=tg0GDq`
4. 通过授权码code申请token`http://192.168.90.122:8003/oauth/token?grant_type=authorization_code&client_id=test&client_secret=test&code=4AwoG0&redirect_uri=http://www.baidu.com`

## oauth2-sso-client-usermsg

port:8004

访问`http://192.168.90.122:8004/client1`

### 凭证式验证
1. 访问`http://192.168.90.122:8003/oauth/token?grant_type=client_credentials&client_id=client1&client_secret=client1-secret`
2. 访问`http://192.168.90.122:8004/client1/api`获取数据，HEADER需添加`Authorization: Bearer ACCESS_TOKEN`

## oauth2-sso-client-updw

上传下载 port:8005
访问`http://192.168.90.122:8005/client2`

## caution

2021-07:
* 使用spring-cloud版本2020.3的时候,spring-cloud-starter-netflix-zuul兼容有点问题
`Caused by: java.lang.NoSuchMethodError: org.springframework.boot.web.servlet.error.ErrorController.getErrorPath()Ljava/lang/String;`
依赖spring-boot版本2.3.12.RELEASE中的spring-boot-autoconfigure,对应的cloud版本是Hoxton.SR12

* http-basic-auth使用cloud版本2020.3会找不到spring-cloud-starter-security,改用版本Hoxton.SR12

* sso-client需要配置servlet-context，否则认证后被拦截继续认证，然后又拦截认证