# spring-cloud-demo

## eureka-server

port: 8000

## helloworld

port: 9090

request:
* /hello
* /say?name=xx

## zuul

port: 9000

* add loginFilter need token;
* add rateLimit, 2 tokens per second;
* filter duration time;

request:
* /hello?token=xx
* /say?name=xx&token=xx

