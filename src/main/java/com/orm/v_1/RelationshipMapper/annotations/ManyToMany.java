package com.orm.v_1.RelationshipMapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToMany {
	
	String tableName() default "";
	
	String columnName() default "";
	
	String inverseColumnName() default "";
	
	String mappedBy() default "";

}
