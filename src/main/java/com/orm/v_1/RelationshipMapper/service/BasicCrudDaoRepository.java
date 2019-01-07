package com.orm.v_1.RelationshipMapper.service;

import java.util.List;

public interface BasicCrudDaoRepository<T> {
	
	public T create (T object);
	
	public List<T> readAll();
	
	public T update (T object);
	
	public boolean delete (T object);

}
