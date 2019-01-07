package com.orm.v_1.RelationshipMapper.service;

import java.util.Iterator;

import com.orm.v_1.RelationshipMapper.enums.RelationType;
import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;

public class QueryProviderImpl<T> implements QueryProvider<T> {
	
	private TableMetadata tableMetadata;
	
	public QueryProviderImpl(TableMetadata mTable) {
		this.tableMetadata = mTable;
	}

	@Override
	public String getInsertQuery() {
		Iterator<ColumnMetadata> columnsIterator =  tableMetadata.getColumns().iterator();
		String insertQuery = "INSERT INTO %s (%s) VALUES (%s)";
		StringBuilder sbColumns = new StringBuilder(30);
		StringBuilder sbQuestionnaire = new StringBuilder(30);
		while(columnsIterator.hasNext()) {
			sbColumns.append(columnsIterator.next().getNameInDatabase());
			sbQuestionnaire.append("?");
			if(columnsIterator.hasNext()) {
				sbColumns.append(", ");
				sbQuestionnaire.append(", ");
			}
		}
		return String.format(insertQuery, tableMetadata.getName(), sbColumns.toString(), sbQuestionnaire.toString());
	}

	@Override
	public String getSelectQuery() {
		Iterator<ColumnMetadata> columnsIterator =  tableMetadata.getColumns().iterator();
		StringBuilder sbSelectQuery = new StringBuilder("SELECT ");
		while(columnsIterator.hasNext()) {
			ColumnMetadata columnMetadata = columnsIterator.next();
			sbSelectQuery.append(columnMetadata.getNameForQuery());
			if(columnMetadata.isForeignId()) {
				Iterator<ColumnMetadata> subEntityColumnsIterator = ((ForeignKeyMetadata) columnMetadata).getRefTable().getColumns().iterator();
				while(subEntityColumnsIterator.hasNext()) {
					if(subEntityColumnsIterator.hasNext()) sbSelectQuery.append(", ");
					sbSelectQuery.append(subEntityColumnsIterator.next().getNameForQuery());
				}
			}
			if(columnsIterator.hasNext()) sbSelectQuery.append(", ");
		}
		sbSelectQuery.append(" FROM ").append(tableMetadata.getName());
		if(tableMetadata.getForeignKeyMap().get(RelationType.ManyToOne) != null) {
			Iterator<ForeignKeyMetadata> fkManyToOne = tableMetadata.getForeignKeyMap().get(RelationType.ManyToOne).iterator();
			while(fkManyToOne.hasNext()) {
				ForeignKeyMetadata fk = fkManyToOne.next();
				sbSelectQuery.append(" JOIN ")
					.append(fk.getRefTable().getName())
					.append(" ON ")
					.append(fk.getNameForQuery())
					.append(" = ")
					.append(fk.getRefTable().getPrimaryKeyMetadata().getNameForQuery());
			}
		}
		sbSelectQuery.append(";");
		return sbSelectQuery.toString();
	}

}
