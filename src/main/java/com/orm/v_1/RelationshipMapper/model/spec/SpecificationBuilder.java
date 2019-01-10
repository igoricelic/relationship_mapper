package com.orm.v_1.RelationshipMapper.model.spec;

import java.util.ArrayList;
import java.util.List;

import com.orm.v_1.RelationshipMapper.annotations.Column;
import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;

public class SpecificationBuilder<T> {
	
	private TableMetadata tableMetadata;
	
	private List<Criterion> criterions;
	
	public SpecificationBuilder(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
		this.criterions = new ArrayList<>();
	}
	
	public SpecificationBuilder<T> addCriterion (String field, Object value, Comparator comparator) {
		ColumnMetadata columnMetadata = this.tableMetadata.getColumns().stream().filter(column -> column.getNameInModel().equals(field)).findFirst().get();
		if(columnMetadata == null) return null;
		this.criterions.add(new Criterion(columnMetadata, value, comparator));
		return this;
	}
	
	public WhereSpecification<T> build () {
		StringBuilder sb = new StringBuilder(100);
		//this.criterions.forEach(criterion -> sb.append(criterion.getColumnMetadata().getNameForQuery()).append(" = ").append(criterion.getValue()).append(str));
		return null;
	}
	
	public static enum Comparator {
		OR,
		AND;
	}
	
	public static class Criterion {
		private ColumnMetadata columnMetadata;
		private Object value;
		private Comparator comparator;
		
		public Criterion(ColumnMetadata columnMetadata, Object value, Comparator comparator) {
			this.columnMetadata = columnMetadata;
			this.value = value;
			this.comparator = comparator;
		}
		
		public ColumnMetadata getColumnMetadata() {
			return columnMetadata;
		}
		public Object getValue() {
			return value;
		}
		public Comparator getComparator() {
			return comparator;
		}
		
	}

}
