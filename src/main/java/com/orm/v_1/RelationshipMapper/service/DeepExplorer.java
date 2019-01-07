package com.orm.v_1.RelationshipMapper.service;

import java.sql.Connection;
import java.util.List;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;

public interface DeepExplorer {
	
	public <T> List<T> deepRead (Connection connection, TableMetadata tableMetadata, int level, int maxDepth);

}
