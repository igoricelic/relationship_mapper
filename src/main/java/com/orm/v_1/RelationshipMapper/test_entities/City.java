package com.orm.v_1.RelationshipMapper.test_entities;

import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.Table;

@Entity
@Table(name = "city")
public class City {
	
	@PrimaryKey(autoIncrement = true)
	private Long id;
	
	private String name;
	
	private String postCode;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", postCode=" + postCode + "]";
	}

}
