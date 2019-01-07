package com.orm.v_1.RelationshipMapper.tests;

import java.util.Arrays;
import java.util.List;

import com.orm.v_1.RelationshipMapper.service.BasicCrudDaoRepository;
import com.orm.v_1.RelationshipMapper.service.IndigoConfigurator;
import com.orm.v_1.RelationshipMapper.service.impl.IndigoConfiguratorImpl;
import com.orm.v_1.RelationshipMapper.test_entities.Address;
import com.orm.v_1.RelationshipMapper.test_entities.Book;
import com.orm.v_1.RelationshipMapper.test_entities.City;
import com.orm.v_1.RelationshipMapper.test_entities.TaxNumber;
import com.orm.v_1.RelationshipMapper.test_entities.User;

public class Test1 {
	
	public static void main(String[] args) {
		try {
			IndigoConfigurator indigoConfigurator = new IndigoConfiguratorImpl(Arrays.asList(User.class, Book.class, Address.class, City.class, TaxNumber.class));
			
//			DatabaseMetadata dbMetadata = indigoConfigurator.getDbMetadata();
//			BasicStringQueryProvider basicStringQueryProvider = new BasicStringQueryProviderImpl();
//			
//			TableMetadata addressMd = dbMetadata.getTablesMap().get(Address.class);
//			TableMetadata usetMd = dbMetadata.getTablesMap().get(User.class);
//			TableMetadata bookMd = dbMetadata.getTablesMap().get(Book.class);
			
			BasicCrudDaoRepository<TaxNumber> taxNumberDao = indigoConfigurator.provideDaoAccessPoint(TaxNumber.class);
			BasicCrudDaoRepository<City> cityDao = indigoConfigurator.provideDaoAccessPoint(City.class);
			BasicCrudDaoRepository<Address> addressDao = indigoConfigurator.provideDaoAccessPoint(Address.class);
			BasicCrudDaoRepository<User> userDao = indigoConfigurator.provideDaoAccessPoint(User.class);
			BasicCrudDaoRepository<Book> bookDao = indigoConfigurator.provideDaoAccessPoint(Book.class);
			
			List<TaxNumber> taxNumbers = taxNumberDao.readAll();
			for(TaxNumber taxNumber: taxNumbers) {
				System.out.println(taxNumber);
			}
			
			List<City> cities = cityDao.readAll();
			for(City city: cities) {
				System.out.println(city);
			}
			
			List<Address> addresses = addressDao.readAll();
			for(Address address: addresses) {
				System.out.println(address.toString());
			}
			
			List<User> users = userDao.readAll();
			for(User user: users) {
				System.out.println(user.toString());
			}
			
			List<Book> books = bookDao.readAll();
			for(Book book: books) {
				System.out.println(book.toString());
			}
			
//			QueryProvider<User> userQueryProvider = indigoConfigurator.getProviderByEntityClass(User.class);
//			QueryProvider<Book> bookQueryProvider = indigoConfigurator.getProviderByEntityClass(Book.class);
//			
//			System.out.println(userQueryProvider.getSelectQuery());
//			
//			System.out.println(bookQueryProvider.getSelectQuery());
			
//			
//			System.out.println(userQueryProvider.getInsertQuery());
//			System.out.println(bookQueryProvider.getInsertQuery());
//			System.out.println(sectorQueryProvider.getInsertQuery());
//			
//			System.out.println(userQueryProvider.getSelectQuery());
//			System.out.println(bookQueryProvider.getSelectQuery());
//			System.out.println(sectorQueryProvider.getSelectQuery());
			
//			List<String> names = Arrays.asList("username", "dateOfRegister", "userProfile", "string_tokenizer", "iLoveCoffe");
//			names.stream().map(StringUtilizator::convertToSnakeCase).forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
