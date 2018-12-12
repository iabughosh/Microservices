package org.ibra.zuulsrv.filters;

import org.ibra.zuulsrv.utils.FilterUtils;
import org.ibra.zuulsrv.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class TrackingFilter extends ZuulFilter{
    
	private static final int FILTER_ORDER =  1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent(){
      
    	return (filterUtils.getCorrelationId() != null);
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public Object run() {

        if (isCorrelationIdPresent()) {
           logger.debug("{} found in tracking filter: {}. ", UserContext.CORRELATION_ID, filterUtils.getCorrelationId());
        } else{
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("{} generated in tracking filter: {}.", UserContext.CORRELATION_ID, filterUtils.getCorrelationId());
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
        
        return null;
    }
}
