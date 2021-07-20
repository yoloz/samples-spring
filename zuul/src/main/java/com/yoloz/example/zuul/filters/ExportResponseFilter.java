package com.yoloz.example.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * @author yoloz
 */
@Component
public class ExportResponseFilter extends AbstractZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        InputStream inputStream = requestContext.getResponseDataStream();
        if (requestContext.getResponseGZipped()) {
            // If origin tell it's GZipped but the content is ZERO bytes,
            // don't try to uncompress
            final Long len = requestContext.getOriginContentLength();
            if (len == null || len > 0) {
                try {
                    inputStream = new GZIPInputStream(requestContext.getResponseDataStream());
                }catch (IOException ex) {
                  ex.printStackTrace();
                }
            }
        }
        System.out.println(requestContext.getOriginContentLength());
        String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        System.out.println(response);
        return null;
    }
}
