package com.orm.v_1.RelationshipMapper.model;

import java.util.HashMap;
import java.util.Map;

public class DatabaseMetadata {
	
	private String name;
	
	private Map<Class<?>, TableMetadata> tablesMap;
	
	public DatabaseMetadata(String name) {
		this.name = name;
		this.tablesMap = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<Class<?>, TableMetadata> getTablesMap() {
		return tablesMap;
	}
	
	public void setTablesMap(Map<Class<?>, TableMetadata> tablesMap) {
		this.tablesMap = tablesMap;
	}
	
	public boolean addTable (Class<?> entityClazz, TableMetadata tableMetadata) {
		if(entityClazz == null || tableMetadata == null) return false;
		this.tablesMap.put(entityClazz, tableMetadata);
		tableMetadata.setDatabase(this);
		return true;
	}
	
	public boolean update () {
		this.tablesMap.values().stream().forEach(tm -> tm.update());
		return true;
	}

}
