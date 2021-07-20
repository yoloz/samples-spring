package com.yoloz.example.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * 身份验证，需要在请求路由前判断，因此使用 pre filter
 */

@Component
public class TokenFilter extends AbstractZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("{}==>{} request to {},token={}", this.getClass().getName(), request.getMethod(),
                request.getRequestURL(), request.getParameter("token"));
        return null;
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
