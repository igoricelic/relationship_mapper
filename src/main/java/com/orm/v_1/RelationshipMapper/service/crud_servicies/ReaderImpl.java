package com.orm.v_1.RelationshipMapper.service.crud_servicies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.orm.v_1.RelationshipMapper.enums.RelationType;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.model.spec.WhereSpecificationImpl;
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
			List<ForeignKeyMetadata> foreignKeys = tableMetadata.getForeignKeys();
			if(foreignKeys != null && currDepthLevel < maxDepthLevel) {
				for(ForeignKeyMetadata foreignKey: foreignKeys) {
					String nameForQuery = foreignKey.getMappedColumnMetadata().getNameForQuery();
					query = queryProvider.provideReadQuery(foreignKey.getRefTable(), new WhereSpecificationImpl<>(nameForQuery + " = ?"), maxDepthLevel);
					logger.info(query);
					tableMetadata.getPrimaryKeyMetadata().getFieldReference().setAccessible(true);
					foreignKey.getMappedColumnMetadata().getFieldReference().setAccessible(true);
					foreignKey.getFieldReference().setAccessible(true);
					for(T result: results) {
						Object idValue = tableMetadata.getPrimaryKeyMetadata().getFieldReference().get(result);
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setObject(1, idValue);
						ResultSet rs = preparedStatement.executeQuery();
						
						if(foreignKey.getRelationType() == RelationType.OneToOne) {
							if(rs.next()) {
								T otherObject = rsExtractor.extractSingleObject(rs, foreignKey.getRefTable(), 0, maxDepthLevel);
								foreignKey.getMappedColumnMetadata().getFieldReference().set(otherObject, result);
								foreignKey.getFieldReference().set(result, otherObject);
							}
						}
						else if(foreignKey.getRelationType() == RelationType.OneToMany) {
							List<T> otherObjects = rsExtractor.extractList(rs, foreignKey.getRefTable(), 0, maxDepthLevel);
							for(T otherObject: otherObjects) {
								List<T> values = (List<T>) foreignKey.getFieldReference().get(result);
								if(values == null) values = new ArrayList<>();
								values.add(otherObject);
								foreignKey.getFieldReference().set(result, values);
								foreignKey.getMappedColumnMetadata().getFieldReference().set(otherObject, result);
							}
						}
						
					}
				}
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
