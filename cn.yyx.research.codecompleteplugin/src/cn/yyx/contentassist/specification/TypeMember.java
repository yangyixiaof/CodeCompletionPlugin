package cn.yyx.contentassist.specification;

import org.eclipse.jdt.internal.ui.text.java.LazyGenericTypeProposal;

@SuppressWarnings("restriction")
public class TypeMember {
	
	private String type = null;
	private Class<?> typeclass = null;
	
	public TypeMember(LazyGenericTypeProposal ltp) throws ClassNotFoundException{
		String display = ltp.getDisplayString();
		String[] dps = display.split("-");
		String rt = dps[0].trim();
		String pkg = dps[1].trim();
		type = pkg + "." + rt;
		try {
			typeclass = Class.forName(type);
		} catch (ClassNotFoundException e) {
			typeclass = Object.class;
			System.err.println("Unresolved class:"+type);
			throw e;
			// e.printStackTrace();
		}
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