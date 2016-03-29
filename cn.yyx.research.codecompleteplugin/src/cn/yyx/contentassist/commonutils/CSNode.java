package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CSNode {
	private CSNode prev = null;
	private CSNode next = null;
	private Map<String, TypeCheck> datas = new TreeMap<String, TypeCheck>();
	private int contenttype = -1;
	private String prefix = null;
	private String postfix = null;
	private boolean hashole = false;
	private boolean connect = false;
	private boolean maytypereplacer = false;
	private boolean used = true;
	
	public CSNode(int contenttype) {
		this.setContenttype(contenttype);
		if (contenttype == CSNodeType.SymbolMark || contenttype == CSNodeType.WholeStatement || contenttype == CSNodeType.HalfFullExpression)
		{
			this.connect = true;
		}
	}
	
	public void AddPossibleCandidates(String t, TypeCheck tc)
	{
		getDatas().put(t, tc);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		CSNode o = (CSNode) super.clone();
		o.setDatas(new TreeMap<String, TypeCheck>());
		Set<String> keys = getDatas().keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext())
		{
			String data = itr.next();
			TypeCheck tc = getDatas().get(data);
			o.getDatas().put(data, (TypeCheck)tc.clone());
		}
		return o;
	}
	
	public String GetFirstDataWithoutTypeCheck()
	{
		Iterator<String> itr = datas.keySet().iterator();
		String firstdata = itr.next();
		return (prefix != null ? prefix : "") + firstdata + (postfix != null ? postfix : "");
	}
	
	public TypeCheck GetFirstTypeCheck() {
		if (datas.size() == 0)
		{
			return null;
		}
		Iterator<String> itr = datas.keySet().iterator();
		String firstdata = itr.next();
		return datas.get(firstdata);
	}

	public CSNode getPrev() {
		return prev;
	}

	public void setPrev(CSNode prev) {
		this.prev = prev;
	}

	public CSNode getNext() {
		return next;
	}

	public void setNext(CSNode next) {
		this.next = next;
	}
	
	public void AddOneData(String code, TypeCheck tc)
	{
		datas.put(code, tc);
	}

	public Map<String, TypeCheck> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, TypeCheck> datas) {
		this.datas = datas;
	}

	public int getContenttype() {
		return contenttype;
	}

	public void setContenttype(int contenttype) {
		this.contenttype = contenttype;
	}

	public String getPrefix() {
		if (prefix == null)
		{
			prefix = "";
		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		if (this.prefix == null)
		{
			this.prefix = "";
		}
		this.prefix = prefix + this.prefix;
	}

	public String getPostfix() {
		if (postfix == null)
		{
			postfix = "";
		}
		return postfix;
	}
	
	public void setPostfix(String postfix) {
		if (this.postfix == null)
		{
			this.postfix = "";
		}
		this.postfix = this.postfix + postfix;
	}

	public boolean isHashole() {
		return hashole;
	}

	public void setHashole(boolean hashole) {
		this.hashole = hashole;
	}

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}

	public boolean isMaytypereplacer() {
		return maytypereplacer;
	}

	public void setMaytypereplacer(boolean maytypereplacer) {
		this.maytypereplacer = maytypereplacer;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
}