package com.orm.v_1.RelationshipMapper.util;

public class StringUtilizator {
	
	public static String convertToSnakeCase (String text) {
		if(text == null) {
			return null;
		}
		String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        String textAsSnakeCase = text.replaceAll(regex, replacement)
                .toLowerCase();
        return textAsSnakeCase;
	}

}
