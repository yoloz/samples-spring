package com.yoloz.example.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * <p>
 * 访问日志中记录请求地址、请求时间，需要pre filter 和 post filter 结合
 * 在过滤器中存储客户端发起请求的时间戳：优先级设置最高
 */
@Component
public class StartTimeFilter extends AbstractZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(StartTimeFilter.class);

    /**
     * filterType：返回一个字符串代表过滤器的类型，数字越低等级越高
     * pre: 在路由转发之前起作用,主要是用来给 route 型过滤器设置所需信息的,如添加参数等
     * routing: 是用来发送请求给其他的服务用的，在这里它的大部分工作是转换请求和响应数据给前台客户端，修改url必须写在这里！！！
     * post: 在把结果返回给浏览器时起作用，Post 类型的 filters 就是对响应体的操作
     * error: 在整个路由阶段，出现异常时起作用
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("{}==>{} request to {}", this.getClass().getName(), request.getMethod(), request.getRequestURL());
        request.setAttribute("startTime", System.currentTimeMillis());
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
