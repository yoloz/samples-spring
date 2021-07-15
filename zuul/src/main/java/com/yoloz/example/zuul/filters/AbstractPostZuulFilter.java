package com.yoloz.example.zuul.filters;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author yoloz
 */
public abstract class AbstractPostZuulFilter extends AbstractZuulFilter{
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }
}
