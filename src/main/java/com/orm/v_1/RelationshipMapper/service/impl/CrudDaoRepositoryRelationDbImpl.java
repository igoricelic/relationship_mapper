package com.orm.v_1.RelationshipMapper.service.impl;

import java.util.List;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.service.BasicCrudDaoRepository;
import com.orm.v_1.RelationshipMapper.service.ConnectionPoolEndPoint;
import com.orm.v_1.RelationshipMapper.service.crud_servicies.Reader;
import com.orm.v_1.RelationshipMapper.service.crud_servicies.ReaderImpl;

public class CrudDaoRepositoryRelationDbImpl<T> implements BasicCrudDaoRepository<T> {
	
	private TableMetadata tableMetadata;
	
	private ConnectionPoolEndPoint connectionPoolEndPoint;
	
	private Reader reader;
	
	private int maxFetchDepth;
	
	public CrudDaoRepositoryRelationDbImpl(TableMetadata tableMetadata, ConnectionPoolEndPoint connectionPoolEndPoint, int maxFetchDepth) {
		this.tableMetadata = tableMetadata;
		this.connectionPoolEndPoint = connectionPoolEndPoint;
		this.reader = new ReaderImpl();
		this.maxFetchDepth = maxFetchDepth;
	}

	@Override
	public T create(T object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> readAll() {
		return reader.deepRead(connectionPoolEndPoint.getConnection(), tableMetadata, 0, maxFetchDepth);
	}

	@Override
	public T update(T object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(T object) {
		// TODO Auto-generated method stub
		return false;
	}

}
