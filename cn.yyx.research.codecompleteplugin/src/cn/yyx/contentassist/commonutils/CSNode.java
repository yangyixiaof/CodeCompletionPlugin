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
	
	public CSNode() {
	}
	
	public CSNode(int contenttype) {
		this.setContenttype(contenttype);
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
		return firstdata;
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
	
}