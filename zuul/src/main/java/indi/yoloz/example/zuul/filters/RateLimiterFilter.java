package indi.yoloz.example.zuul.filters;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * guava 中提供了限流工具类 RateLimiter，根据令牌桶算法实现
 */

@Component
public class RateLimiterFilter extends AbstractZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 限流器，2.0 表示每秒获取两个令牌
     */
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("{}==>{} request to {}", this.getClass().getName(), request.getMethod(), request.getRequestURL());
        // 尝试获取令牌
        if (rateLimiter.tryAcquire()) {
            return null;
        } else {
            return fail(402, "error: rate limit");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}

