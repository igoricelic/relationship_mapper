package com.orm.v_1.RelationshipMapper.service;

import com.orm.v_1.RelationshipMapper.model.DatabaseMetadata;

public interface IndigoConfigurator {
	
	public <T> BasicCrudDaoRepository<T> provideDaoAccessPoint (Class<?> entityClass);
	
	public DatabaseMetadata getDbMetadata();

}
