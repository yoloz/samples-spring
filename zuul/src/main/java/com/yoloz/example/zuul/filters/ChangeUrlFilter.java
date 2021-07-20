package com.yoloz.example.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 */
@Component
public class ChangeUrlFilter extends AbstractZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(ChangeUrlFilter.class);

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("{}==>{} request to {},routeHost={},requestPath={}", this.getClass().getName(), request.getMethod(), request.getRequestURL(),
                requestContext.getRouteHost(), requestContext.get(FilterConstants.REQUEST_URI_KEY));
//        try {
//            //修改路由的url后缀，注意与yml配置文件的stripPrefix配合使用
//            requestContext.put(FilterConstants.REQUEST_URI_KEY, "/aaa");
//            //修改路由的Host(ip+port)，注意与yml配置文件的stripPrefix配合使用
//            requestContext.setRouteHost(new URL("http://localhost:1111/test"));
//        } catch (MalformedURLException e) {
//            return fail(402, e.getMessage());
//        }
        return null;
    }

    /**
     * filterType：返回一个字符串代表过滤器的类型，数字越低等级越高
     * pre: 在路由转发之前起作用,主要是用来给 route 型过滤器设置所需信息的,如添加参数等
     * routing: 是用来发送请求给其他的服务用的，在这里它的大部分工作是转换请求和响应数据给前台客户端，修改url必须写在这里！！！
     * post: 在把结果返回给浏览器时起作用，Post 类型的 filters 就是对响应体的操作
     * error: 在整个路由阶段，出现异常时起作用
     */
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
    }
}
