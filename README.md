# spring-cloud-demo

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

## caution
* eureka-server,helloworld的spring-boot-starter-parent的version是2.5.2，而zuul使用同样版本会有兼容问题
`Caused by: java.lang.NoSuchMethodError: org.springframework.boot.web.servlet.error.ErrorController.getErrorPath()Ljava/lang/String;`
需要spring-boot 2.3.12.RELEASE spring-boot-autoconfigure。

