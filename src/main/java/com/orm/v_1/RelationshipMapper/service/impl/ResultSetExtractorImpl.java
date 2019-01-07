package com.orm.v_1.RelationshipMapper.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.service.ResultSetExtractor;

public class ResultSetExtractorImpl implements ResultSetExtractor {

	@Override
	public <T> T extractSingleObject(ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel,
			int maxDepthLevel) {
		try {
			T object = (T) tableMetadata.getEntityClass().newInstance();
			for(ColumnMetadata columnMetadata: tableMetadata.getColumns()) {
				Object value = null;
				if(columnMetadata.isForeignId()) {
					if(currDepthLevel < maxDepthLevel) value = extractSingleObject(resultSet, ((ForeignKeyMetadata) columnMetadata).getRefTable(), (currDepthLevel+1), maxDepthLevel);
					else continue;
				} else {
					value = resultSet.getObject(columnMetadata.getNameForQuery());
				}
				columnMetadata.getFieldReference().setAccessible(true);
				columnMetadata.getFieldReference().set(object, value);
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public <T> List<T> extractList(ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel,
			int maxDepthLevel) {
		try {
			List<T> results = new ArrayList<>();
			T object = null;
			
			while(resultSet.next()) {
				object = extractSingleObject(resultSet, tableMetadata, currDepthLevel, maxDepthLevel);
				results.add(object);
			}
			
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
