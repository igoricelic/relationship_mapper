package com.orm.v_1.RelationshipMapper;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Iterable<Field> iterable = Arrays.asList(Types.class.getDeclaredFields());
    	Iterator<Field> iterator = iterable.iterator();
    	while(iterator.hasNext()) {
    		System.out.print(iterator.next().getName());
    		if(iterator.hasNext()) System.out.println(",");
    		else System.out.println(";");
    	}
    	
    	//fNames.stream().filter(f -> !f2Names.contains(f)).forEach(System.out::print);
    }
}
