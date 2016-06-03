package cn.yyx.contentassist.codesynthesis.typeutil;

public class VarArgCCType extends CCType {
	
	public VarArgCCType(Class<?> cls, String clstr) {
		super(cls, clstr);
		this.setClstr(clstr + "...");
		if (cls != null)
		{
			CCType cct = TypeComputer.CreateArrayType(this, 1, "[]");
			this.setCls(cct.getCls());
		}
	}
	
}