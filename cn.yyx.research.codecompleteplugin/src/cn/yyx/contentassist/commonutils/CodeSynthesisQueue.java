package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Skip
 * @version 1.0
 */
public class CodeSynthesisQueue {
	
	protected CSNode head = null;
	protected CSNode last = null;
	protected int length;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		CodeSynthesisQueue o = new CodeSynthesisQueue();
		o.length = length;
		CSNode temp = head;
		while (temp != null)
		{
			o.add((CSNode)temp.clone());
			temp = temp.getNext();
		}
		return o;
	}
	
	public CodeSynthesisQueue() {
		length = 0;
	}
	
	public void add(CSNode data) {
		if (isEmpty()) {
			head = data;
			last = head;
			length++;
		} else {
			data.setPrev(last);
			last.setNext(data);
			last = data;
			length++;
		}
	}
	
	public CSNode get(int index) {
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException("Index out of boud:" + index);
		}
		CSNode other = head;
		for (int i = 0; i < index; i++) {
			other = other.getNext();
		}
		return other;
	}
	
	public CSNode getLast() {
		return last;
	}

	public CSNode getFirst() {
		return head;
	}
	
	public int getSize() {
		return length;
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public void clear() {
		head = null;
		length = 0;
	}
	
	public void printList() {
		if (isEmpty()) {
			System.out.println("empty list");
		} else {
			CSNode other = head;
			for (int i = 0; i < length; i++) {
				System.out.print(other.getContenttype() + " ");
				other = other.getNext();
			}
			System.out.println();
		}
	}
	
	public boolean CanBeMerged()
	{
		if (head == last)
		{
			return false;
		}
		return true;
	}

	public boolean MergeBackwardAsFarAsItCan() {
		while (CanBeMerged())
		{
			CSNode sndlast = last.getPrev();
			boolean lasthandled = false;
			if (last.isConnect())
			{
				boolean conflict = MergeLastTwo();
				if (conflict)
				{
					return true;
				}
				lasthandled = true;
			}
			if (sndlast.isHashole())
			{
				if (!lasthandled)
				{
					boolean conflict = MergeLastTwo();
					if (conflict)
					{
						return true;
					}
				}
			}
			else
			{
				if (sndlast.isConnect())
				{
					if (!lasthandled)
					{
						boolean conflict = MergeLastTwo();
						if (conflict)
						{
							return true;
						}
					}
					boolean conflict = MergeLastTwo();
					if (conflict)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean MergeLastTwo() {
		// TODO Auto-generated method stub
		CSNode res = new CSNode(CSNodeType.TempMergeUnknown);
		CSNode sndlast = last.getPrev();
		if (sndlast.isMaytypereplacer())
		{
			Map<String, TypeCheck> po = last.getDatas();
			Set<String> codes = po.keySet();
			Iterator<String> citr = codes.iterator();
			while (citr.hasNext())
			{
				String code = citr.next();
				TypeCheck tc = po.get(code);
				String rt = tc.getExpreturntype();
				res.AddOneData(sndlast.getPrefix() + rt + sndlast.getPostfix() + last.getPrefix() + code + last.getPostfix(), tc);
			}
		}
		else
		{
			Map<String, TypeCheck> pores = CSNodeHelper.ConcatTwoNodesDatasWithTypeChecking(sndlast, last, -1);
			if (pores.size() == 0)
			{
				return true;
			}
			res.setDatas(pores);
		}
		res.setPrev(sndlast.getPrev());
		sndlast.getPrev().setNext(res);
		add(res);
		return false;
	}
	
	/*public void MergeLast(T merge) {
		last.getPrev().data = merge;
		last.getPrev().hasHole = false;
		last.getPrev().setNext(null);
		last = last.getPrev();
	}

	public void MergeLast() {
		// skip prev data.
		last.getPrev().data = last.data;
		last.getPrev().hasHole = false;
		last.getPrev().setNext(null);
		last = last.getPrev();
	}*/
	
}