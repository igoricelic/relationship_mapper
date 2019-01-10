package com.orm.v_1.RelationshipMapper.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orm.v_1.RelationshipMapper.enums.RelationType;

public class TableMetadata {
	
	private Class<?> entityClass;
	
	private String name;
	
	private PrimaryKeyMetadata primaryKeyMetadata;
	
	private List<ColumnMetadata> columns;
	
	private List<ForeignKeyMetadata> foreignKeys;
	
	private Map<RelationType, List<ForeignKeyMetadata>> foreignKeyMap;
	
	private DatabaseMetadata database;
	
	public TableMetadata(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
		this.columns = new ArrayList<>();
		this.foreignKeys = new ArrayList<>();
		this.foreignKeyMap = new HashMap<>();
	}
	
	public TableMetadata(String name) {
		this.name = name;
		this.columns = new ArrayList<>();
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnMetadata> getColumns() {
		return columns;
	}
	
	public void setColumns(List<ColumnMetadata> columns) {
		this.columns = columns;
	}
	
	public DatabaseMetadata getDatabase() {
		return database;
	}
	
	public void setDatabase(DatabaseMetadata database) {
		this.database = database;
	}
	
	public PrimaryKeyMetadata getPrimaryKeyMetadata() {
		return primaryKeyMetadata;
	}
	
	public void setPrimaryKeyMetadata(PrimaryKeyMetadata primaryKeyMetadata) {
		if(primaryKeyMetadata == null) return;
		this.addColumn(primaryKeyMetadata);
		this.primaryKeyMetadata = primaryKeyMetadata;
	}
	
	public Map<RelationType, List<ForeignKeyMetadata>> getForeignKeyMap() {
		return foreignKeyMap;
	}
	
	public void setForeignKeyMap(Map<RelationType, List<ForeignKeyMetadata>> foreignKeyMap) {
		this.foreignKeyMap = foreignKeyMap;
	}
	
	public List<ForeignKeyMetadata> getForeignKeys() {
		return foreignKeys;
	}
	
	public void setForeignKeys(List<ForeignKeyMetadata> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}
	
	public boolean addColumn (ColumnMetadata mColumn) {
		if(mColumn == null) return false;
		this.columns.add(mColumn);
		mColumn.setTableMetadata(this);
		return true;
	}
	
	public boolean addForeignKey (ForeignKeyMetadata fkMetadata) {
		if(fkMetadata == null) return false;
		List<ForeignKeyMetadata> fKeyMetadatas = this.foreignKeyMap.get(fkMetadata.getRelationType());
		if(fKeyMetadatas == null) {
			fKeyMetadatas = new ArrayList<>();
			this.foreignKeyMap.put(fkMetadata.getRelationType(), fKeyMetadatas);
		}
		if(fkMetadata.getRelationType() == RelationType.ManyToOne) this.addColumn(fkMetadata);
		if(fkMetadata.getRelationType() == RelationType.OneToMany) {
			this.foreignKeys.add(fkMetadata);
		}
		if(fkMetadata.getRelationType() == RelationType.OneToOne && (fkMetadata.getMappedBy() == null || fkMetadata.getMappedBy().equals(""))) this.addColumn(fkMetadata);
		else if(fkMetadata.getRelationType() == RelationType.OneToOne) this.foreignKeys.add(fkMetadata);
		fKeyMetadatas.add(fkMetadata);
		fkMetadata.setTableMetadata(this);
		return true;
	}
	
	public boolean update() {
		this.columns.forEach(column -> {
			if(column.isForeignId()) {
				((ForeignKeyMetadata) column).update();
			}
		});
		for(List<ForeignKeyMetadata> lFks: foreignKeyMap.values()) {
			lFks.stream().forEach(fkmd -> fkmd.update());
		}
		return true;
	}

}
