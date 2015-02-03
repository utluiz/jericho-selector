package br.com.starcode.jerichoselector;

import br.com.starcode.parccser.model.AttributeOperator;

public class Comparator {
	
	public static boolean compare(AttributeOperator operator, String attributeValue, String selectorValue) {
		
		switch (operator) {
		case EQUALS:
			return attributeValue.equals(selectorValue);
			
	    case INCLUDES:
    		String[] values = attributeValue.split("\\s");
    		for (String v : values) {
				if (v.equals(selectorValue)) {
					return true;
				}
			}
    		return false;
    		
	    case DASH_MATCH:
    		String[] values2 = attributeValue.split("\\s");
    		String[] valuesSelector = selectorValue.split("\\s");
    		if (valuesSelector.length <= values2.length) {
    			for (int i = 0; i < valuesSelector.length; i++) {
					if (values2[i].equals(valuesSelector[i])) {
						return false;
					}
				}
    			return true;
    		}
    		return false;
    		
	    case PREFIX_MATCH:
	    	return attributeValue.startsWith(selectorValue);
	    	
	    case SUFFIX_MATCH:
	    	return attributeValue.endsWith(selectorValue);
	    	
	    case SUBSTRING_MATCH:
	    	return attributeValue.contains(selectorValue);
	    	
		}
		
		throw new RuntimeException("Comparator not implemmented for " + operator + "!"); 
		
	}
	    
}
