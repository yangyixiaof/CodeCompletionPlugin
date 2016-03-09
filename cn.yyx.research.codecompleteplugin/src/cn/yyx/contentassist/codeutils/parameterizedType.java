package cn.yyx.contentassist.codeutils;

public class parameterizedType extends type{
	
	identifier id = null;
	typeList tlist = null;
	
	public parameterizedType(identifier id, typeList tlist) {
		this.id = id;
		this.tlist = tlist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof parameterizedType)
		{
			if (id.CouldThoughtSame(((parameterizedType) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof parameterizedType)
		{
			return 0.4 + 0.6*(id.Similarity(((parameterizedType) t).id));
		}
		return 0;
	}
	
}