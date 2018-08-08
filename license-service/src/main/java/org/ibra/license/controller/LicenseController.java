package org.ibra.license.controller;

import java.util.List;

import org.ibra.license.model.License;
import org.ibra.license.service.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/org/{orgId}/licenses")
public class LicenseController {

	private Logger log = LoggerFactory.getLogger(LicenseController.class);
	
	@Autowired
	private LicenseService licenseService;
	
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses( @PathVariable("orgId") String organizationId,
                                @PathVariable("licenseId") String licenseId) {

        return new License()
            .withId(licenseId)
            .withOrganizationId(organizationId)
            .withProductName("Teleco")
            .withLicenseType("Seat");
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public List<License> findLicensesByOrgId(@PathVariable("orgId") String orgId) {
		
		return licenseService.findLicensesByOrgId(orgId);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveLicense(@RequestBody License license) {
		
		licenseService.saveLicense(license);
		log.info("License with id {}, has been saved successfully ...", license.getId());
	}
}
