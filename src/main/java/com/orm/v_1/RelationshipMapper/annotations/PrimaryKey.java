package com.orm.v_1.RelationshipMapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
	
	boolean autoIncrement() default false;

}
