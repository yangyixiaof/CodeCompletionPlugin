package cn.yyx.contentassist.specification;

public class TypeMember {
	
	private String type = null;
	private Class<?> typeclass = null;
	
	public TypeMember(String tp, Class<?> tpcls){
		this.type = tp;
		this.typeclass = tpcls;
	}

	public Class<?> getTypeclass() {
		return typeclass;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
}