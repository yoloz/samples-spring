package com.yoloz.example.zuul.filters;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author yoloz
 */
public abstract class AbstractPreZuulFilter extends AbstractZuulFilter{
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
}
