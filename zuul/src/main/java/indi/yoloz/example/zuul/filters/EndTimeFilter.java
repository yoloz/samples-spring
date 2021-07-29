package indi.yoloz.example.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * <p>
 * 默认response filter 优先级是 1000，因此我们将它之前一个优先级的过滤器时间戳与最开始请求发起的时间戳相减即可:PreRequestFilter
 */

@Component
public class EndTimeFilter extends AbstractZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(EndTimeFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 从请求上下文中获取之前设置的开始时间戳
        Long startTime = (Long) request.getAttribute("startTime");
        log.info("{}==>{} request to {},cost time {}ms", this.getClass().getName(), request.getMethod(),
                request.getRequestURL(), System.currentTimeMillis() - startTime);
        return null;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}