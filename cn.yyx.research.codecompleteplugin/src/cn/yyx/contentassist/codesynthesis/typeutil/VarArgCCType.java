package cn.yyx.contentassist.codesynthesis.typeutil;

public class VarArgCCType extends CCType {
	
	public VarArgCCType(CCType cct) {
		super(cct.getCls(), cct.getClstr());
		this.setClstr(cct.getClstr() + "...");
		if (cct.getCls() != null)
		{
			CCType acct = TypeComputer.CreateArrayType(this, 1, "[]");
			this.setCls(acct.getCls());
		}
	}
	
}