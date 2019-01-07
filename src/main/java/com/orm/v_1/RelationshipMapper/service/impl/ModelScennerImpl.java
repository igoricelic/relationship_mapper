package com.orm.v_1.RelationshipMapper.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.orm.v_1.RelationshipMapper.annotations.Column;
import com.orm.v_1.RelationshipMapper.annotations.ForeignKey;
import com.orm.v_1.RelationshipMapper.annotations.ManyToOne;
import com.orm.v_1.RelationshipMapper.annotations.OneToMany;
import com.orm.v_1.RelationshipMapper.annotations.OneToOne;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.Table;
import com.orm.v_1.RelationshipMapper.enums.RelationType;
import com.orm.v_1.RelationshipMapper.model.ColumnMetadata;
import com.orm.v_1.RelationshipMapper.model.DatabaseMetadata;
import com.orm.v_1.RelationshipMapper.model.ForeignKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.PrimaryKeyMetadata;
import com.orm.v_1.RelationshipMapper.model.TableMetadata;
import com.orm.v_1.RelationshipMapper.service.ModelScenner;
import com.orm.v_1.RelationshipMapper.util.StringUtilizator;

public class ModelScennerImpl implements ModelScenner {

	@Override
	public DatabaseMetadata scennEntities(List<Class<?>> entities) {
		DatabaseMetadata mDatabase = new DatabaseMetadata("test_db");
		entities.stream().forEach(entityClazz -> mDatabase.addTable(entityClazz, scannEntity(entityClazz)));
		return mDatabase;
	}
	
	private TableMetadata scannEntity (Class<?> entity) {
		TableMetadata mTable = new TableMetadata(entity);
		for(Annotation entityAnnotation: entity.getDeclaredAnnotations()) {
			if(entityAnnotation instanceof Table) {
				Table tableAnnotation = (Table) entityAnnotation;
				mTable.setName(tableAnnotation.name());
			}
		}
		if(mTable.getName() == null) mTable.setName(StringUtilizator.convertToSnakeCase(entity.getSimpleName()));
		List<Field> fields = Arrays.asList(entity.getDeclaredFields());
		fields.forEach(field -> mTable.addColumn(scannColumnMetadata(field)));
		fields.forEach(field -> mTable.setPrimaryKeyMetadata(scannPrimaryKeyMetadata(field)));
		fields.forEach(field -> mTable.addForeignKey(scannForeignKeyMetadata(field)));
		return mTable;
	}
	
	// ova za kolone
	private ColumnMetadata scannColumnMetadata (Field field) {
		ColumnMetadata mColumn = null;
		Column cAnnotation = null;
		for(Annotation annotation: field.getDeclaredAnnotations()) {
			if(annotation instanceof Column) {
				cAnnotation = (Column) annotation;
			}
			else if(annotation instanceof PrimaryKey) {
				return null;
			}
			else if(annotation instanceof ForeignKey) {
				return null;
			}
			else if(annotation instanceof OneToMany || annotation instanceof OneToOne) {
				return null;
			}
		}
		if(mColumn == null) mColumn = new ColumnMetadata(field);
		if(cAnnotation != null) {
			mColumn.setNameInDatabase(cAnnotation.name());
			mColumn.setSqlType(cAnnotation.type());
			mColumn.setNullable(cAnnotation.nullable());
			mColumn.setUnique(cAnnotation.unique());
			mColumn.setLength(cAnnotation.length());
		}
		if(mColumn.getNameInDatabase() == null || mColumn.getNameInDatabase().isEmpty()) {
			mColumn.setNameInDatabase(StringUtilizator.convertToSnakeCase(field.getName()));
		}
		return mColumn;
	}
	
	// ova za pk
	private PrimaryKeyMetadata scannPrimaryKeyMetadata (Field field) {
		PrimaryKeyMetadata pkMetadata = null;
		Column cAnnotation = null;
		for(Annotation annotation: field.getDeclaredAnnotations()) {
			if(annotation instanceof Column) {
				cAnnotation = (Column) annotation;
			}
			else if(annotation instanceof PrimaryKey) {
				PrimaryKey pkAnnotation = (PrimaryKey) annotation;
				pkMetadata = new PrimaryKeyMetadata(field, pkAnnotation.autoIncrement());
			}
			else if(annotation instanceof ForeignKey) {
				return null;
			}
			else if(annotation instanceof OneToMany) {
				return null;
			}
		}
		if(pkMetadata == null) return null;
		if(cAnnotation != null) {
			pkMetadata.setNameInDatabase(cAnnotation.name());
			pkMetadata.setSqlType(cAnnotation.type());
			pkMetadata.setNullable(cAnnotation.nullable());
			pkMetadata.setUnique(cAnnotation.unique());
			pkMetadata.setLength(cAnnotation.length());
		}
		if(pkMetadata.getNameInDatabase() == null || pkMetadata.getNameInDatabase().isEmpty()) {
			pkMetadata.setNameInDatabase(StringUtilizator.convertToSnakeCase(field.getName()));
		}
		return pkMetadata;
	}
	
	// ovo za fk
	private ForeignKeyMetadata scannForeignKeyMetadata (Field field) {
		ForeignKeyMetadata fkMetadata = null;
		Column cAnnotation = null;
		for(Annotation annotation: field.getDeclaredAnnotations()) {
			if(annotation instanceof Column) {
				cAnnotation = (Column) annotation;
			}
			else if(annotation instanceof ForeignKey) {
				if(fkMetadata == null) fkMetadata = new ForeignKeyMetadata(field);
				ForeignKey fkAnnotation = (ForeignKey) annotation;
				fkMetadata.setForeignKeyName(fkAnnotation.name());
			}
			else if(annotation instanceof ManyToOne) {
				if(fkMetadata == null) fkMetadata = new ForeignKeyMetadata(field);
				ManyToOne manyToOneAnnotation = (ManyToOne) annotation;
				cAnnotation = manyToOneAnnotation.column();
				fkMetadata.setJoinType(manyToOneAnnotation.joinType());
				fkMetadata.setRelationType(RelationType.ManyToOne);
			}
			else if(annotation instanceof OneToOne) {
				if(fkMetadata == null) fkMetadata = new ForeignKeyMetadata(field);
				OneToOne oneToOneAnnotation = (OneToOne) annotation;
				if(oneToOneAnnotation.column() != null) {
					cAnnotation = oneToOneAnnotation.column();
				} else {
					/**
					 * Ako postoji mappedBy polje, to znaci da je polje koje referencira
					 * obaj objekat u drugoj tabeli, i da ovaj objekat ne treba dodavati u kolone
					 * tabele prilikom pravljenja podela, nego opaliti dodatni upit
					 */
					fkMetadata.setMappedBy(oneToOneAnnotation.mappedBy());
				}
				fkMetadata.setJoinType(oneToOneAnnotation.joinType());
				fkMetadata.setRelationType(RelationType.OneToOne);
			}
		}
		if(fkMetadata == null) return null;
		if(cAnnotation != null) {
			fkMetadata.setNameInDatabase(cAnnotation.name());
			fkMetadata.setSqlType(cAnnotation.type());
			fkMetadata.setNullable(cAnnotation.nullable());
			fkMetadata.setUnique(cAnnotation.unique());
			fkMetadata.setLength(cAnnotation.length());
		}
		if(fkMetadata.getNameInDatabase() == null || fkMetadata.getNameInDatabase().isEmpty()) {
			fkMetadata.setNameInDatabase(StringUtilizator.convertToSnakeCase(field.getName()));
		}
		return fkMetadata;
	}
	
}
