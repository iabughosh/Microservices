package org.ibra.orgservice.controller;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.ibra.orgservice.model.Organization;
import org.ibra.orgservice.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    
	private static Logger log = LoggerFactory.getLogger(OrganizationServiceController.class);
	
	@Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
    	
    	if(RandomUtils.nextInt(4) == 3) {
			log.info("Sleeping for 30 seconds");
			try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				log.error("Sleeping thread has been awakened", e);
			}
		}
    	
    	Optional<Organization> organization = orgService.getOrg(organizationId);
        return organization.isPresent() ? organization.get() : null;
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.updateOrg(org);
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg(org);
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId,  @RequestBody Organization org) {
        orgService.deleteOrg(org);
    }
}
