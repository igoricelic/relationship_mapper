package com.orm.v_1.RelationshipMapper.service;

public interface QueryProvider<T> {
	
	public String getInsertQuery();
	
	public String getSelectQuery();

}
