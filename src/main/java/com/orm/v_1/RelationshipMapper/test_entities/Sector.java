package com.orm.v_1.RelationshipMapper.test_entities;

import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;

@Entity
public class Sector {
	
	@PrimaryKey(autoIncrement = true)
	private Long id;
	
	private String name;

}
