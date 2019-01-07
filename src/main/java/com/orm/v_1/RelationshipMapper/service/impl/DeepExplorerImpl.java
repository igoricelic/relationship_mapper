package com.orm.v_1.RelationshipMapper.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.service.BasicStringQueryProvider;
import com.orm.v_1.RelationshipMapper.service.DeepExplorer;
import com.orm.v_1.RelationshipMapper.service.ResultSetExtractor;

public class DeepExplorerImpl implements DeepExplorer {
	
	private static final Logger logger = Logger.getLogger(DeepExplorer.class.getName());
	
	private BasicStringQueryProvider queryProvider;
	
	private ResultSetExtractor rsExtractor;
	
	public DeepExplorerImpl() {
		this.queryProvider = new BasicStringQueryProviderImpl();
		this.rsExtractor = null;
	}

	@Override
	public <T> List<T> deepRead(Connection connection, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel) {
		try {
			String query = queryProvider.provideReadQuery(tableMetadata, null, maxDepthLevel);
			logger.info(query);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rsResultSet = preparedStatement.executeQuery();
			List<T> results = deepExtractor(rsResultSet, tableMetadata, currDepthLevel, maxDepthLevel);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private <T> List<T> deepExtractor (ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel) {
		try {
			List<T> results = new ArrayList<>();
			T object = null;
			
			while(resultSet.next()) {
				object = extractSingleObejct(resultSet, tableMetadata, currDepthLevel, maxDepthLevel);
				results.add(object);
			}
			
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private <T> T extractSingleObejct (ResultSet resultSet, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel) {
		try {
			T object = (T) tableMetadata.getEntityClass().newInstance();
			for(ColumnMetadata columnMetadata: tableMetadata.getColumns()) {
				Object value = null;
				if(columnMetadata.isForeignId() && currDepthLevel < maxDepthLevel) {
					value = extractSingleObejct(resultSet, ((ForeignKeyMetadata) columnMetadata).getRefTable(), (currDepthLevel+1), maxDepthLevel);
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

}
