package com.orm.v_1.RelationshipMapper.service.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.orm.v_1.RelationshipMapper.annotations.JoinType;
import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.model.spec.WhereSpecification;
import com.orm.v_1.RelationshipMapper.service.BasicStringQueryProvider;

public class BasicStringQueryProviderImpl implements BasicStringQueryProvider {
	
	private static final Integer MAX_DEPTH_LEVEL = 3;

	@Override
	public String provideCreateQuery(TableMetadata tableMetadata) {
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
	public <T> String provideReadQuery(TableMetadata tableMetadata, WhereSpecification<T> whereClause, int maxDepthLevel) {
		String query = "SELECT " + columnsAsSubQuery(tableMetadata, maxDepthLevel) + relationsAsSubQuery(tableMetadata, maxDepthLevel);
		return query;
	}
	
	private String columnsAsSubQuery (TableMetadata tableMetadata, int maxDepthLevel) {
		Queue<Struct> dataToProcessing = new LinkedList<>();
		int currDepthLevel = 0;
		dataToProcessing.add(new Struct(currDepthLevel, tableMetadata));
		StringBuilder sbColumns = new StringBuilder(500);
		ColumnMetadata columnMetadata = null;
		while(!dataToProcessing.isEmpty()) {
			if(dataToProcessing.peek().level > currDepthLevel) currDepthLevel++;
			if(currDepthLevel > 0) sbColumns.append(", ");
			tableMetadata = dataToProcessing.poll().getTableMetadata();
			Iterator<ColumnMetadata> columnsIterator = tableMetadata.getColumns().iterator();
			while(columnsIterator.hasNext()) {
				columnMetadata = columnsIterator.next();
				sbColumns.append(columnMetadata.getNameForQuery());
				if(columnsIterator.hasNext()) sbColumns.append(", ");
				if(columnMetadata.isForeignId() && currDepthLevel < maxDepthLevel) dataToProcessing.add(new Struct(currDepthLevel+1, ((ForeignKeyMetadata)columnMetadata).getRefTable()));
			}
		}
		System.out.println(">>> "+sbColumns.toString());
		return sbColumns.toString();
	}
	
	private String relationsAsSubQuery (TableMetadata tableMetadata, int maxDepthLevel) {
		Queue<Struct> dataToProcessing = new LinkedList<>();
		int currDepthLevel = 0;
		dataToProcessing.add(new Struct(currDepthLevel, tableMetadata));
		StringBuilder stringBuilder = new StringBuilder(500);
		stringBuilder.append(" FROM ").append(tableMetadata.getName());
		ColumnMetadata columnMetadata = null;
		while(!dataToProcessing.isEmpty()) {
			if(dataToProcessing.peek().level > currDepthLevel) currDepthLevel++;
			tableMetadata = dataToProcessing.poll().getTableMetadata();
			Iterator<ColumnMetadata> columnsIterator = tableMetadata.getColumns().iterator();
			while(columnsIterator.hasNext()) {
				columnMetadata = columnsIterator.next();
				if(columnMetadata.isForeignId()) {
					TableMetadata fkTableMetadata = ((ForeignKeyMetadata) columnMetadata).getRefTable();
					JoinType joinType = ((ForeignKeyMetadata) columnMetadata).getJoinType();
					stringBuilder.append(joinType.getStringRepr())
						.append(" JOIN ")
						.append(fkTableMetadata.getName())
						.append(" ON ")
						.append(columnMetadata.getNameForQuery())
						.append(" = ")
						.append(fkTableMetadata.getPrimaryKeyMetadata().getNameForQuery());
					if(currDepthLevel < maxDepthLevel) dataToProcessing.add(new Struct(currDepthLevel+1, fkTableMetadata));
				}
			}
		}
		System.out.println(">>> "+stringBuilder.toString());
		return stringBuilder.toString();
	}
	
	private String deepColumnsAsStringQueryFormatter (String base, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel) {
		StringBuilder sbColumns = (base != null) ? new StringBuilder(base) : new StringBuilder(500);
		Iterator<ColumnMetadata> columnsIterator =  tableMetadata.getColumns().iterator();
		while(columnsIterator.hasNext()) {
			ColumnMetadata columnMetadata = columnsIterator.next();
			sbColumns.append(columnMetadata.getNameForQuery()).append(", ");
			//if(columnsIterator.hasNext()) sbColumns.append(", ");
			if(columnMetadata.isForeignId() && currDepthLevel < maxDepthLevel) {
				TableMetadata fkTableMetadata = ((ForeignKeyMetadata) columnMetadata).getRefTable();
				sbColumns = new StringBuilder(deepColumnsAsStringQueryFormatter(sbColumns.toString(), fkTableMetadata, (currDepthLevel+1), maxDepthLevel));
			}
		}
		return sbColumns.toString();
	}
	
	// u prvoj rundi za tabelu iz nivoa pa posle za druge -> BFS umesto DFS
	private String deepJoinTableQueryFromater(String base, TableMetadata tableMetadata, int currDepthLevel, int maxDepthLevel) {
		StringBuilder sbFrom = new StringBuilder(base);
		Iterator<ColumnMetadata> columnsIterator =  tableMetadata.getColumns().iterator();
		while(columnsIterator.hasNext()) {
			ColumnMetadata columnMetadata = columnsIterator.next();
			if(columnMetadata.isForeignId() && currDepthLevel < maxDepthLevel) {
				TableMetadata fkTableMetadata = ((ForeignKeyMetadata) columnMetadata).getRefTable();
				JoinType joinType = ((ForeignKeyMetadata) columnMetadata).getJoinType();
				sbFrom.append(joinType.getStringRepr())
					.append(" JOIN ")
					.append(fkTableMetadata.getName())
					.append(" ON ")
					.append(columnMetadata.getNameForQuery())
					.append(" = ")
					.append(fkTableMetadata.getPrimaryKeyMetadata().getNameForQuery());
				sbFrom = new StringBuilder(deepJoinTableQueryFromater(sbFrom.toString(), fkTableMetadata, (currDepthLevel+1), maxDepthLevel));
			}
		}
		return sbFrom.toString();
	}
	
	public StringBuilder[] provideDeepReadQuery (StringBuilder[] builders, TableMetadata tableMetadata, int currDeapthLevel, int maxDepthLevel) {
		Iterator<ColumnMetadata> columnsIterator =  tableMetadata.getColumns().iterator();
		StringBuilder sbColumns = builders[0];
		StringBuilder sbFroms = builders[1];
		while(columnsIterator.hasNext()) {
			ColumnMetadata columnMetadata = columnsIterator.next();
			if(columnMetadata.isForeignId() && currDeapthLevel < maxDepthLevel) {
				TableMetadata fkTableMetadata = ((ForeignKeyMetadata) columnMetadata).getRefTable();
				sbFroms.append(" JOIN ")
					.append(fkTableMetadata.getName())
					.append(" ON ")
					.append(columnMetadata.getNameForQuery())
					.append(" = ")
					.append(fkTableMetadata.getPrimaryKeyMetadata().getNameForQuery());
				StringBuilder[] builders2 = provideDeepReadQuery(new StringBuilder[]{sbColumns, sbFroms}, fkTableMetadata, currDeapthLevel+1, maxDepthLevel);
				sbColumns = builders2[0];
				sbFroms = builders2[1];
				sbColumns.append(", ");
			}
			sbColumns.append(columnMetadata.getNameForQuery());
			if(columnsIterator.hasNext()) sbColumns.append(", ");
		}
		return new StringBuilder[]{sbColumns, sbFroms};
	}
	
	@Override
	public <T> String provideCountQuery(TableMetadata tableMetadata, WhereSpecification<T> whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> String provideExistsQuery(TableMetadata tableMetadata, WhereSpecification<T> whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String provideUpdateQuery(TableMetadata tableMetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String provideDeleteQuery(TableMetadata tableMetadata) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static class Struct {
		private int level;
		private TableMetadata tableMetadata;
		
		public Struct(int level, TableMetadata tableMetadata) {
			this.level = level;
			this.tableMetadata = tableMetadata;
		}
		
		public int getLevel() {
			return level;
		}
		public TableMetadata getTableMetadata() {
			return tableMetadata;
		}
		
	}

}
