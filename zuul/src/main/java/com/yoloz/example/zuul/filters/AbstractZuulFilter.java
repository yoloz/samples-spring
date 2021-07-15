package com.yoloz.example.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author yoloz
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    RequestContext requestContext;

    /**
     * 标志，请求是否需要继续执行下一个过滤器
     */
    private final static String NEXT = "next";

    /**
     * 判断过滤器是否需要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        // 获取当前线程的请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 第一次请求经过过滤器，上下文中不存在自己设置的 NEXT 标识，需要默认返回 true，继续执行 run()
        // 之后则根据 NEXT 对应的 value 的实际值 true/false 决定是否执行
        return (boolean) context.getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {
        requestContext = RequestContext.getCurrentContext();
        return cusRun();
    }

    protected abstract Object cusRun();

    Object fail(int code, String msg) {
        requestContext.set(NEXT, false);
        requestContext.setSendZuulResponse(false); // zuul 响应
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseStatusCode(code);
        requestContext.setResponseBody(String.format("{\"result\": \"%s!\"}", msg));
        return null;
    }

    Object success() {
        requestContext.set(NEXT, true);
        return null;
    }
}
