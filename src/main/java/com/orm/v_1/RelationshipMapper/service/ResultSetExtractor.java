package com.orm.v_1.RelationshipMapper.service;

import java.sql.ResultSet;
import java.util.List;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;

public interface ResultSetExtractor {
	
	public <T> T extractSingleObject (ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel);
	
	public <T> List<T> extractList (ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel);

}
