package org.ibra.license.utils;

import java.util.Optional;

import org.springframework.util.Assert;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

    public static final UserContext getContext(){
    	
    	Optional<UserContext> optional = Optional.ofNullable(userContext.get());
    	
    	return optional.orElseGet(() -> {
    		UserContext freshContext = createEmptyContext();
            userContext.set(freshContext);
            
            return userContext.get();
    	});
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    public static final UserContext createEmptyContext(){
        return new UserContext();
    }
}
