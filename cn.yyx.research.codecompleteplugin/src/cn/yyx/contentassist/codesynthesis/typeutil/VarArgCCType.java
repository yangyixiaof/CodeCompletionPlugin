package cn.yyx.contentassist.codesynthesis.typeutil;

public class VarArgCCType extends CCType {
	
	private Class<?> compocls = null;
	
	public VarArgCCType(CCType cct) {
		super(cct.getCls(), cct.getClstr());
		this.setClstr(cct.getClstr() + "...");
		if (cct.getCls() != null)
		{
			setCompocls(cct.getCls());
			CCType acct = TypeComputer.CreateArrayType(this, 1, "[]");
			this.setCls(acct.getCls());
		}
	}

	public Class<?> getCompocls() {
		return compocls;
	}

	public void setCompocls(Class<?> compocls) {
		this.compocls = compocls;
	}
	
}