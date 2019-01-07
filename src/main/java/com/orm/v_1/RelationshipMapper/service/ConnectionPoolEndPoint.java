package com.orm.v_1.RelationshipMapper.service;

import java.sql.Connection;

public interface ConnectionPoolEndPoint {
	
	public Connection getConnection();

}
