package com.orm.v_1.RelationshipMapper.test_entities;

import java.util.Date;

import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.Table;

@Entity
@Table(name = "tax_number")
public class TaxNumber {
	
	@PrimaryKey(autoIncrement = true)
	private Long id;
	
	private Date createdAt;
	
	private String number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "TaxNumber [id=" + id + ", createdAt=" + createdAt + ", number=" + number + "]";
	}

}
