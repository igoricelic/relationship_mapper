package com.orm.v_1.RelationshipMapper.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import com.orm.v_1.RelationshipMapper.service.ConnectionPoolEndPoint;

public class ConnectionPoolEndPointImpl implements ConnectionPoolEndPoint {
	
	private Connection connection;
	
	public ConnectionPoolEndPointImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionPath = "jdbc:mysql://%s:%d/%s?user=%s&password=%s";
			this.connection = DriverManager
					.getConnection(String.format(connectionPath, "localhost", 3306, "user_registry", "root", "root"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	

}
