package org.ibra.zuulsrv.utils;

import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

@Component
public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
    	
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(UserContext.CORRELATION_ID) !=null) {
            return ctx.getRequest().getHeader(UserContext.CORRELATION_ID);
        }
        else{
            return ctx.getZuulRequestHeaders().get(UserContext.CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId) {
        
    	RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(UserContext.CORRELATION_ID, correlationId);
    }

    public final String getOrgId() {
        
    	RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(UserContext.ORG_ID) != null) {
            return ctx.getRequest().getHeader(UserContext.ORG_ID);
        } else{
            return ctx.getZuulRequestHeaders().get(UserContext.ORG_ID);
        }
    }

    public void setOrgId(String orgId) {
        
    	RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(UserContext.ORG_ID, orgId);
    }

    public final String getUserId() {
    	
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(UserContext.USER_ID) !=null) {
            return ctx.getRequest().getHeader(UserContext.USER_ID);
        } else{
            return ctx.getZuulRequestHeaders().get(UserContext.USER_ID);
        }
    }

    public void setUserId(String userId) {
        
    	RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(UserContext.USER_ID, userId);
    }

    public final String getAuthToken() {
    	
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(UserContext.AUTH_TOKEN);
    }

    public String getServiceId() {
    	
        RequestContext ctx = RequestContext.getCurrentContext();

        //We might not have a service id if we are using a static, non-eureka route.
        return (ctx.get("serviceId") == null) ? 
        		"" : ctx.get("serviceId").toString();

    }
}
