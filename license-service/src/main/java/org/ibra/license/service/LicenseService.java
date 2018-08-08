package org.ibra.license.service;

import java.util.List;
import java.util.UUID;

import org.ibra.license.config.ServiceConfig;
import org.ibra.license.model.License;
import org.ibra.license.repository.LicenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
	
	private Logger serviceLogger = LoggerFactory.getLogger(LicenseService.class);
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private ServiceConfig serviceConfig;
	
	public List<License> findLicensesByOrgId(String orgId) {
		
		serviceLogger.info("Reading property from config : {}", serviceConfig.getTracerProperty());
		return licenseRepository.findByOrganizationId(orgId);
	}
	
	public void saveLicense(License license) {
		
		license.withId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}
}
