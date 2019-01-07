package com.orm.v_1.RelationshipMapper.model;

import java.lang.reflect.Field;

import com.orm.v_1.RelationshipMapper.annotations.SqlType;

public class ColumnMetadata {
	
	protected String nameInModel, nameInDatabase;
	
	/**
	 * length attribute in @Column annotation
	 * of default length value by type
	 */
	protected int length;
	
	protected boolean nullable, unique;
	
	/**
	 * reference to attribute in entity class
	 */
	protected Field fieldReference;
	
	protected Class<?> javaType;
	
	protected SqlType sqlType;
	
	protected TableMetadata tableMetadata;
	
	public ColumnMetadata(Field field) {
		super();
		this.fieldReference = field;
		this.nameInModel = field.getName();
		this.sqlType = SqlType.AUTO;
		this.javaType = field.getType();
		this.nullable = true;
		this.unique = false;
	}

	public String getNameInModel() {
		return nameInModel;
	}

	public void setNameInModel(String nameInModel) {
		this.nameInModel = nameInModel;
	}

	public String getNameInDatabase() {
		return nameInDatabase;
	}

	public void setNameInDatabase(String nameInDatabase) {
		this.nameInDatabase = nameInDatabase;
	}

	public Field getFieldReference() {
		return fieldReference;
	}

	public void setFieldReference(Field fieldReference) {
		this.fieldReference = fieldReference;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	
	public Class<?> getJavaType() {
		return javaType;
	}
	
	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}
	
	public SqlType getSqlType() {
		return sqlType;
	}
	
	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}
	
	public TableMetadata getTableMetadata() {
		return tableMetadata;
	}
	
	public void setTableMetadata(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
	}
	
	public String getNameForQuery() {
		return tableMetadata.getName() + "." + getNameInDatabase();
	}
	
	public boolean isId() {
		return false;
	}
	
	public boolean isForeignId() {
		return false;
	}

}
