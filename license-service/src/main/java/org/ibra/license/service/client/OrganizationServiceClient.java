package org.ibra.license.service.client;

import org.ibra.license.model.Organization;
import org.ibra.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class OrganizationServiceClient {
	
	private static Logger logger = LoggerFactory.getLogger(OrganizationServiceClient.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "fetchDefaultOrgInfo", threadPoolKey = "orgSrvClient")
	//Set command property in default licenseservice configuration
			/*commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", 
								value = "3000")})*/
	public Organization getOrganization(String organizationId) {
		
		logger.info("OrganizationServiceClient Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		
		ResponseEntity<Organization> response =
				restTemplate.exchange(
				"http://orgservice/v1/organizations/{organizationId}",
				HttpMethod.GET,
				null, Organization.class, organizationId);
		
		return response.getBody();
	}
	
	protected Organization fetchDefaultOrgInfo(String organizationId) {
		
		Organization org = new Organization();
		org.setId(organizationId);
		org.setName("Information is not available at this moment");
		org.setContactEmail("Information is not available at this moment");
		org.setContactName("Information is not available at this moment");
		org.setContactPhone("Information is not available at this moment");
		
		return org;
	}
}
