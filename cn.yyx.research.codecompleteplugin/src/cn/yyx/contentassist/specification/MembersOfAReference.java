package cn.yyx.contentassist.specification;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.commonutils.ConstantVariable;

public class MembersOfAReference {
	
	private List<FieldMember> fmlist = new LinkedList<FieldMember>();
	private List<MethodMember> mmlist = new LinkedList<MethodMember>();
	
	public MembersOfAReference() {
	}
	
	public void AddFieldMember(FieldMember fm)
	{
		getFmlist().add(fm);
	}
	
	public void AddMethodMember(MethodMember mm)
	{
		getMmlist().add(mm);
	}
	
	public Iterator<FieldMember> GetFieldMemberIterator()
	{
		return fmlist.iterator();
	}
	
	public Iterator<MethodMember> GetMethodMemberIterator()
	{
		return mmlist.iterator();
	}
	
	public List<FieldMember> getFmlist() {
		return fmlist;
	}

	public void setFmlist(List<FieldMember> fmlist) {
		this.fmlist = fmlist;
	}

	public List<MethodMember> getMmlist() {
		return mmlist;
	}

	public void setMmlist(List<MethodMember> mmlist) {
		this.mmlist = mmlist;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Field:" + ConstantVariable.LineSeperator);
		int len = fmlist.size();
		for (int i=0;i<len;i++)
		{
			sb.append(fmlist.get(i) + ConstantVariable.LineSeperator);
		}
		sb.append("Method:" + ConstantVariable.LineSeperator);
		len = mmlist.size();
		for (int i=0;i<len;i++)
		{
			sb.append(mmlist.get(i) + ConstantVariable.LineSeperator);
		}
		return sb.toString();
	}
	
}