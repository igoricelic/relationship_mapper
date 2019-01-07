package com.orm.v_1.RelationshipMapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {
	
	Column column() default @Column;
	
	String mappedBy() default "";
	
	JoinType joinType() default JoinType.INNER;

}
