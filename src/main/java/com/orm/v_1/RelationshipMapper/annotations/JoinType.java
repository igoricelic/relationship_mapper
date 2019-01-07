package com.orm.v_1.RelationshipMapper.annotations;

public enum JoinType {
	
	LEFT,
	
	RIGHT,
	
	INNER;
	
	public String getStringRepr() {
		return " " + this;
	}

}
