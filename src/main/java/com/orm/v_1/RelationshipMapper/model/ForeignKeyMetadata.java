package com.orm.v_1.RelationshipMapper.model;

import java.lang.reflect.Field;

import com.orm.v_1.RelationshipMapper.annotations.JoinType;
import com.orm.v_1.RelationshipMapper.enums.RelationType;

public class ForeignKeyMetadata extends ColumnMetadata {
	
	private String foreignKeyName;
	
	private JoinType joinType;
	
	private TableMetadata refTable;
	
	private RelationType relationType;
	
	private String mappedBy;
	
	public ForeignKeyMetadata(Field field) {
		super(field);
		joinType = JoinType.INNER;
	}
	
	public String getForeignKeyName() {
		return foreignKeyName;
	}
	
	public void setForeignKeyName(String foreignKeyName) {
		this.foreignKeyName = foreignKeyName;
	}
	
	public JoinType getJoinType() {
		return joinType;
	}
	
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	
	public TableMetadata getRefTable() {
		return refTable;
	}
	
	public void setRefTable(TableMetadata refTable) {
		this.refTable = refTable;
	}
	
	public RelationType getRelationType() {
		return relationType;
	}
	
	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}
	
	public String getMappedBy() {
		return mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}
	
	@Override
	public boolean isForeignId() {
		return true;
	}
	
	public boolean update () {
		this.refTable = this.getTableMetadata().getDatabase().getTablesMap().get(getJavaType());
		return true;
	}

}
