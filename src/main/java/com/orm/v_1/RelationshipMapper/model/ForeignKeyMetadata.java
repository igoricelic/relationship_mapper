package com.orm.v_1.RelationshipMapper.model;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.orm.v_1.RelationshipMapper.annotations.JoinType;
import com.orm.v_1.RelationshipMapper.enums.RelationType;

public class ForeignKeyMetadata extends ColumnMetadata {
	
	private String foreignKeyName;
	
	private JoinType joinType;
	
	private TableMetadata refTable;
	
	private RelationType relationType;
	
	private String mappedBy;
	
	private ForeignKeyMetadata mappedColumnMetadata;
	
	public ForeignKeyMetadata(Field field) {
		super(field);
		joinType = JoinType.INNER;
		if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) field.getGenericType();
            Type[] argTypes = paramType.getActualTypeArguments();
            if (argTypes.length > 0) {
            	System.out.println(argTypes[0]);
               this.javaType = (Class<?>) argTypes[0];
            }
		}
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
	
	public ForeignKeyMetadata getMappedColumnMetadata() {
		return mappedColumnMetadata;
	}
	
	public void setMappedColumnMetadata(ForeignKeyMetadata mappedColumnMetadata) {
		this.mappedColumnMetadata = mappedColumnMetadata;
	}
	
	@Override
	public boolean isForeignId() {
		return true;
	}
	
	public boolean update () {
		this.refTable = this.getTableMetadata().getDatabase().getTablesMap().get(getJavaType());
		if(mappedBy != null && !mappedBy.equals("")) {
			System.out.println(mappedBy);
			this.refTable.getColumns().stream().forEach(column -> {
				if(column.getNameInModel().equals(mappedBy)) {
					this.mappedColumnMetadata = (ForeignKeyMetadata) column;
					return;
				}
			});
		}
		return true;
	}

}
