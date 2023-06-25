package me.tuhin47.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties(
		value = { "createdAt", "updatedAt" },
		allowGetters = true
)
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit extends Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
	@Column(name="created_at",nullable = false, updatable = false)
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at",nullable = false)
	private Instant updatedAt;

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, updatedAt, getId());
	}
}