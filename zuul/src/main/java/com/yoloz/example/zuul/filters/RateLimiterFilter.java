package com.yoloz.example.zuul.filters;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * guava 中提供了限流工具类 RateLimiter，根据令牌桶算法实现
 */

@Component
public class RateLimiterFilter extends AbstractPreZuulFilter{

    private static final Logger log = LoggerFactory.getLogger(RateLimiterFilter.class);
    /**
     * 限流器，2.0 表示每秒获取两个令牌
     */
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    protected Object cusRun() {
        HttpServletRequest request = requestContext.getRequest();
        // 尝试获取令牌
        if (rateLimiter.tryAcquire()) {
            log.info("get rate token success");
            return success();
        } else {
            log.error("rate limit: {}", request.getRequestURL());
            return fail(402, "error: rate limit");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}

