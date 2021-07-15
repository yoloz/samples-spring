package com.yoloz.example.zuul.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @author yoloz
 * <p>
 * 默认response filter 优先级是 1000，因此我们将它之前一个优先级的过滤器时间戳与最开始请求发起的时间戳相减即可:PreRequestFilter
 */

@Component
public class AccessLogFilter extends AbstractPostZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(AccessLogFilter.class);

    @Override
    protected Object cusRun() {
        HttpServletRequest request = requestContext.getRequest();
        // 从请求上下文中获取之前设置的开始时间戳
        Long startTime = (Long) request.getAttribute("startTime");
        String requestURI = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;
        log.info("url: {}, duration: {}", requestURI, duration);
        return success();
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}