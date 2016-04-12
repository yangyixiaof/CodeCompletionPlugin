package cn.yyx.contentassist.codesynthesis.data;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class CSVariableHolderExtraInfo implements CSSelfClosedMergable{
	
	private String varname = null;
	private Class<?> cls = null;
	
	private List<String> vars = null;
	private List<Class<?>> clss = null;
	
	public CSVariableHolderExtraInfo(String varname, Class<?> cls) {
		this.setVarname(varname);
		this.setCls(cls);
	}

	public String getVarname() {
		return varname;
	}

	private void setVarname(String varname) {
		this.varname = varname;
	}
	
	public Class<?> getCls() {
		return cls;
	}

	private void setCls(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public Object SelfClosedMerge(Object tv) throws CodeSynthesisException {
		if (!(tv instanceof CSVariableHolderExtraInfo))
		{
			throw new CodeSynthesisException("Not corresponding type, expect CSVariableHolderExtraInfo.");
		}
		
		CSVariableHolderExtraInfo newone = new CSVariableHolderExtraInfo(varname, cls);
		LinkedList<Class<?>> cs = new LinkedList<Class<?>>();
		cs.add(((CSVariableHolderExtraInfo)tv).cls);
		AddList(clss, cs);
		AddList(((CSVariableHolderExtraInfo)tv).clss, cs);
		newone.setClss(cs);
		
		LinkedList<String> ss = new LinkedList<String>();
		ss.add(((CSVariableHolderExtraInfo)tv).varname);
		AddList(vars, ss);
		AddList(((CSVariableHolderExtraInfo)tv).vars, ss);
		newone.setVars(ss);
		
		return newone;
	}
	
	private void AddList(List<Class<?>> ls, LinkedList<Class<?>> dest) {
		if (ls != null)
		{
			dest.addAll(ls);
		}
	}

	private void AddList(List<String> ls, List<String> dest)
	{
		if (ls != null)
		{
			dest.addAll(ls);
		}
	}

	public List<String> getVars() {
		return vars;
	}

	public void setVars(List<String> vars) {
		this.vars = vars;
	}

	public List<Class<?>> getClss() {
		return clss;
	}

	public void setClss(List<Class<?>> clss) {
		this.clss = clss;
	}
	
}