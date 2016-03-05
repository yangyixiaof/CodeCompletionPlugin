package cn.yyx.contentassist.codeutils;

public class parameterizedType extends type{
	
	identifier id = null;
	typeList tlist = null;
	
	public parameterizedType(identifier id, typeList tlist) {
		this.id = id;
		this.tlist = tlist;
	}

}
