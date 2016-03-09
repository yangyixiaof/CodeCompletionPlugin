package cn.yyx.contentassist.codeutils;

import java.util.List;

public class classOrInterfaceType extends type{
	
	List<type> result = null;
	
	public classOrInterfaceType(List<type> result) {
		this.result = result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classOrInterfaceType)
		{
			// TODO
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		
		return 0;
	}

}
