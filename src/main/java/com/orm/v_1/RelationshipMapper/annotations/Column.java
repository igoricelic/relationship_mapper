package com.orm.v_1.RelationshipMapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	String name() default "";
	
	SqlType type() default SqlType.AUTO;
	
	int length() default 0;
	
	boolean nullable() default true;
	
	boolean unique() default false;

}
