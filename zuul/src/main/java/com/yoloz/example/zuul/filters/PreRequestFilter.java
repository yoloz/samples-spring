package com.yoloz.example.zuul.filters;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 *
 * 访问日志中记录请求地址、请求时间，需要pre filter 和 post filter 结合
 * 在过滤器中存储客户端发起请求的时间戳：优先级设置最高
 */
@Component
public class PreRequestFilter extends AbstractPreZuulFilter{

    @Override
    protected Object cusRun() {
        HttpServletRequest request = requestContext.getRequest();
        request.setAttribute("startTime", System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
