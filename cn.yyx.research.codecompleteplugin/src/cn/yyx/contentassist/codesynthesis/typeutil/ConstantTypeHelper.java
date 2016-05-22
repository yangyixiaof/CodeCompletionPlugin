package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.LinkedList;
import java.util.List;

public class ConstantTypeHelper {
	
	private static List<CCType> PostfixPossibleTypes = new LinkedList<CCType>();
	private static List<CCType> PrefixPossibleTypes = new LinkedList<CCType>();
	
	static {
		// pre fix
		getPrefixPossibleTypes().add(new CCType(int.class, "int"));
		getPrefixPossibleTypes().add(new CCType(boolean.class, "boolean"));
		getPrefixPossibleTypes().add(new CCType(double.class, "double"));
		getPrefixPossibleTypes().add(new CCType(float.class, "float"));
		getPrefixPossibleTypes().add(new CCType(long.class, "long"));
		getPrefixPossibleTypes().add(new CCType(byte.class, "byte"));
		getPrefixPossibleTypes().add(new CCType(short.class, "short"));
		
		getPrefixPossibleTypes().add(new CCType(Integer.class, "Integer"));
		getPrefixPossibleTypes().add(new CCType(Boolean.class, "Boolean"));
		getPrefixPossibleTypes().add(new CCType(Double.class, "Double"));
		getPrefixPossibleTypes().add(new CCType(Float.class, "Float"));
		getPrefixPossibleTypes().add(new CCType(Long.class, "Long"));
		getPrefixPossibleTypes().add(new CCType(Byte.class, "Byte"));
		getPrefixPossibleTypes().add(new CCType(Short.class, "Short"));
		
		
		
		// post fix
		getPostfixPossibleTypes().add(new CCType(int.class, "int"));
		getPostfixPossibleTypes().add(new CCType(double.class, "double"));
		getPostfixPossibleTypes().add(new CCType(float.class, "float"));
		getPostfixPossibleTypes().add(new CCType(long.class, "long"));
		getPostfixPossibleTypes().add(new CCType(byte.class, "byte"));
		getPostfixPossibleTypes().add(new CCType(short.class, "short"));
		getPostfixPossibleTypes().add(new CCType(char.class, "char"));
		
		getPostfixPossibleTypes().add(new CCType(Integer.class, "Integer"));
		getPostfixPossibleTypes().add(new CCType(Double.class, "Double"));
		getPostfixPossibleTypes().add(new CCType(Float.class, "Float"));
		getPostfixPossibleTypes().add(new CCType(Long.class, "Long"));
		getPostfixPossibleTypes().add(new CCType(Byte.class, "Byte"));
		getPostfixPossibleTypes().add(new CCType(Short.class, "Short"));
		getPostfixPossibleTypes().add(new CCType(Character.class, "Character"));
	}

	public static List<CCType> getPostfixPossibleTypes() {
		return PostfixPossibleTypes;
	}

	public static void setPostfixPossibleTypes(List<CCType> postfixPossibleTypes) {
		PostfixPossibleTypes = postfixPossibleTypes;
	}

	public static List<CCType> getPrefixPossibleTypes() {
		return PrefixPossibleTypes;
	}

	public static void setPrefixPossibleTypes(List<CCType> prefixPossibleTypes) {
		PrefixPossibleTypes = prefixPossibleTypes;
	}
	
}