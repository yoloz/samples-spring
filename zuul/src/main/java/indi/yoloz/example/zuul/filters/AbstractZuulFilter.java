package indi.yoloz.example.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author yoloz
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    //请求是否需要继续执行下一个过滤器,如果其中一个过滤器出错，后续过滤器无需再执行
    //请求第一个过滤器的时候返回true,之后过滤器执行依据前一个过滤器是否出错
//    private static final ThreadLocal<Boolean> shouldFilter = ThreadLocal.withInitial(() -> true);
    private static final String shouldFilter = "shouldFilter";

    /**
     * 判断过滤器是否需要执行
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
//        return shouldFilter.get();
        return (boolean) requestContext.getOrDefault(shouldFilter, true);

    }

    @SuppressWarnings("all")
    Object fail(int code, String msg) {
        RequestContext requestContext = RequestContext.getCurrentContext();
//        shouldFilter.set(false);
        requestContext.set(shouldFilter, false);
        /**
         * 从
         * {@link com.netflix.zuul.filters.ZuulServletFilter}
         * 的doFilter中可见setSendZuulResponse设置后，routing和postRouting不执行了
         */
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseStatusCode(code);
        requestContext.setResponseBody(String.format("{\"result\": \"%s!\"}", msg));
        return null;
    }

}
