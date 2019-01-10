package com.orm.v_1.RelationshipMapper.model.spec;

public class WhereSpecificationImpl<T> implements WhereSpecification<T> {

	private String clause;
	
	public WhereSpecificationImpl(String clause) {
		this.clause = clause;
	}
	
	@Override
	public String getWhereClause() {
		return clause;
	}

}
