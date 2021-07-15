package com.yoloz.example.zuul.filters;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yoloz
 * 身份验证，需要在请求路由前判断，因此使用 pre filter
 */

@Component
public class LoginFilter extends AbstractPreZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    protected Object cusRun() {
        // requestContext 是 AbstractZuulFilter 类中初始化完成的 =》RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            log.error("error: token is empty");
            return fail(401, "error: token is empty");
        }
        // TODO token 具体校验略
        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
