package com.orm.v_1.RelationshipMapper.service.crud_servicies;

import java.sql.Connection;
import java.util.List;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;

public interface Reader {
	
	public <T> List<T> deepRead (Connection connection, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel);

}
