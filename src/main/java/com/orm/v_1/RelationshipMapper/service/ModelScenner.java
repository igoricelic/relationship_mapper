package com.orm.v_1.RelationshipMapper.service;

import java.util.List;

import com.orm.v_1.RelationshipMapper.model.DatabaseMetadata;

public interface ModelScenner {
	
	public DatabaseMetadata scennEntities (List<Class<?>> entities);

}
