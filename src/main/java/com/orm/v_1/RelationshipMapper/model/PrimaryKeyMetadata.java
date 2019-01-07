package com.orm.v_1.RelationshipMapper.model;

import java.lang.reflect.Field;

public class PrimaryKeyMetadata extends ColumnMetadata {
	
	private boolean autoIncrement;

	public PrimaryKeyMetadata(Field field) {
		super(field);
		super.nullable = false;
		super.unique = true;
	}
	
	public PrimaryKeyMetadata(Field field, boolean isAutoincrement) {
		this(field);
		this.autoIncrement = isAutoincrement;
	}
	
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	
	@Override
	public boolean isId() {
		return true;
	}

}
