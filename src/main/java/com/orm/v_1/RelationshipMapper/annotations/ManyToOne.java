package com.orm.v_1.RelationshipMapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {

	Column column();

	JoinType joinType() default JoinType.INNER;

}
