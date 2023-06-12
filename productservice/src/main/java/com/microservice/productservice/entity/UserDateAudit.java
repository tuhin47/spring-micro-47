package com.microservice.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
		value = { "createdBY", "updatedBy" },
		allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(name = "created_by",updatable = false)
	private Long createdBy;

	@LastModifiedBy
	@Column(name = "updated_by")
	private Long updatedBy;
}