package com.orm.v_1.RelationshipMapper.service.crud_servicies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.service.BasicStringQueryProvider;
import com.orm.v_1.RelationshipMapper.service.DeepExplorer;
import com.orm.v_1.RelationshipMapper.service.ResultSetExtractor;
import com.orm.v_1.RelationshipMapper.service.impl.BasicStringQueryProviderImpl;
import com.orm.v_1.RelationshipMapper.service.impl.ResultSetExtractorImpl;

public class ReaderImpl implements Reader {

	private static final Logger logger = Logger.getLogger(DeepExplorer.class.getName());

	private BasicStringQueryProvider queryProvider;

	private ResultSetExtractor rsExtractor;

	public ReaderImpl() {
		this.queryProvider = new BasicStringQueryProviderImpl();
		this.rsExtractor = new ResultSetExtractorImpl();
	}
	
	@Override
	public <T> List<T> deepRead(Connection connection, TableMetadata tableMetadata, int currDepthLevel,
			int maxDepthLevel) {
		try {
			String query = queryProvider.provideReadQuery(tableMetadata, null, maxDepthLevel);
			logger.info(query);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rsResultSet = preparedStatement.executeQuery();
			List<T> results = rsExtractor.extractList(rsResultSet, tableMetadata, currDepthLevel, maxDepthLevel);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
