package com.orm.v_1.RelationshipMapper.service;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.model.spec.WhereSpecification;

public interface BasicStringQueryProvider {
	
	public String provideCreateQuery (TableMetadata tableMetadata);
	
	public <T> String provideReadQuery (TableMetadata tableMetadata, WhereSpecification<T> whereClause, int maxDepthLevel);
	
	public <T> String provideCountQuery (TableMetadata tableMetadata, WhereSpecification<T> whereClause);
	
	public <T> String provideExistsQuery (TableMetadata tableMetadata, WhereSpecification<T> whereClause);
	
	public String provideUpdateQuery (TableMetadata tableMetadata);
	
	public String provideDeleteQuery (TableMetadata tableMetadata);

}
