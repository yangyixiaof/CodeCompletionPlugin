package cn.yyx.contentassist.codesynthesis.data;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;

public class CSVariableHolderExtraInfo implements CSSelfClosedMergable{
	
	private String varname = null;
	private CCType cls = null;
	
	private List<String> vars = null;
	private List<CCType> clss = null;
	
	public CSVariableHolderExtraInfo(String varname, CCType cls) {
		this.setVarname(varname);
		this.setCls(cls);
	}

	public String getVarname() {
		return varname;
	}

	private void setVarname(String varname) {
		this.varname = varname;
	}
	
	public CCType getCls() {
		return cls;
	}

	private void setCls(CCType cls) {
		this.cls = cls;
	}

	@Override
	public Object SelfClosedMerge(Object tv) throws CodeSynthesisException {
		if (!(ClassInstanceOfUtil.ObjectInstanceOf(tv, CSVariableHolderExtraInfo.class)))
		{
			throw new CodeSynthesisException("Not corresponding type, expect CSVariableHolderExtraInfo.");
		}
		
		CSVariableHolderExtraInfo newone = new CSVariableHolderExtraInfo(varname, cls);
		LinkedList<CCType> cs = new LinkedList<CCType>();
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
	
	private void AddList(List<CCType> ls, LinkedList<CCType> dest) {
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

	public List<CCType> getClss() {
		return clss;
	}

	public void setClss(List<CCType> clss) {
		this.clss = clss;
	}
	
	@Override
	public String toString() {
		return "varname:" + varname + ";cls:" + cls + ";vars:" + vars + ";clss:" + clss;
	}
	
}