package org.ibra.license.repository;

import java.util.List;

import org.ibra.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

	List<License> findByOrganizationId(String organizationId);
	License findByOrganizationIdAndId(String organizationId, String id);
}
