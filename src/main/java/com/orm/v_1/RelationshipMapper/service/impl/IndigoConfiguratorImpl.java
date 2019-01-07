package com.orm.v_1.RelationshipMapper.service.impl;

import java.util.List;

import com.orm.v_1.RelationshipMapper.model.DatabaseMetadata;
import com.orm.v_1.RelationshipMapper.service.BasicCrudDaoRepository;
import com.orm.v_1.RelationshipMapper.service.ConnectionPoolEndPoint;
import com.orm.v_1.RelationshipMapper.service.IndigoConfigurator;
import com.orm.v_1.RelationshipMapper.service.ModelScenner;

public class IndigoConfiguratorImpl implements IndigoConfigurator {
	
	private DatabaseMetadata databaseMetadata;
	
	private ConnectionPoolEndPoint connectionPoolEndPoint;
	
	private int maxFetchDepth = 2;
	
	public IndigoConfiguratorImpl(List<Class<?>> entities) {
		ModelScenner scenner = new ModelScennerImpl();
		this.connectionPoolEndPoint = new ConnectionPoolEndPointImpl();
		databaseMetadata = scenner.scennEntities(entities);
		databaseMetadata.update();
	}

	@Override
	public <T> BasicCrudDaoRepository<T> provideDaoAccessPoint(Class<?> entityClass) {
		return new CrudDaoRepositoryRelationDbImpl<>(databaseMetadata.getTablesMap().get(entityClass), connectionPoolEndPoint, maxFetchDepth);
	}

	@Override
	public DatabaseMetadata getDbMetadata() {
		return databaseMetadata;
	}

}
