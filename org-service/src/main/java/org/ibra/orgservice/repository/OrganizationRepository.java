package org.ibra.orgservice.repository;

import java.util.Optional;

import org.ibra.orgservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    Optional<Organization> findById(String organizationId);
}
