package org.ibra.license.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "licenses")
public class License {
	
	@Id
	@Column(name = "license_id", nullable = false)
	private String id;
	@Column(name = "organization_id", nullable = false)
	private String organizationId;
	@Column(name = "product_name", nullable = false)
	private String productName;
	@Column(name = "license_type", nullable = false)
	private String licenseType;
	@Column(name = "license_max", nullable = false)
	private Integer licenseMax;
	@Column(name = "license_allocated", nullable = false)
	private Integer licenseAllocated;
	@Column(name="comment")
	private String comment;
	  
	@Transient
	private String orgName;
	@Transient
	private String orgPhone;
	@Transient
	private String orgMail;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public License withId(String id) {
		this.setId(id);
		return this;
	}

	public License withOrganizationId(String organizationId) {
		this.setOrganizationId(organizationId);
		return this;
	}

	public License withProductName(String productName) {
		this.setProductName(productName);
		return this;
	}

	public License withLicenseType(String licenseType) {
		this.setLicenseType(licenseType);
		return this;
	}

	public Integer getLicenseMax() {
		return licenseMax;
	}

	public void setLicenseMax(Integer licenseMax) {
		this.licenseMax = licenseMax;
	}

	public Integer getLicenseAllocated() {
		return licenseAllocated;
	}

	public void setLicenseAllocated(Integer licenseAllocated) {
		this.licenseAllocated = licenseAllocated;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public String getOrgMail() {
		return orgMail;
	}
	
	public License fillOrganizationInfo(Organization org) {
		
		this.orgName = org.getName();
		this.orgMail = org.getContactEmail();
		this.orgPhone = org.getContactPhone();
		
		return this;
	}
}
