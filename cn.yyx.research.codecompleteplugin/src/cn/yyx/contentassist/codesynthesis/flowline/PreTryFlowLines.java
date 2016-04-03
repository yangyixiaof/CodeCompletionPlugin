package cn.yyx.contentassist.codesynthesis.flowline;

import java.util.LinkedList;
import java.util.List;

public class PreTryFlowLines<T> extends FlowLines<T> {
	
	private FlowLineNode<T> exactmatchtail = null;
	private FlowLineNode<T> tempexactmatchtail = null;
	
	private List<FlowLineNode<T>> overtails = new LinkedList<FlowLineNode<T>>();
	
	public void AddOverFlowLineNode(FlowLineNode<T> otail, FlowLineNode<T> prenode)
	{
		getOvertails().add(otail);
		otail.setPrev(prenode);
	}

	public FlowLineNode<T> getExactmatchtail() {
		return exactmatchtail;
	}

	public void setExactmatchtail(FlowLineNode<T> exactmatchtail) {
		this.exactmatchtail = exactmatchtail;
	}
	
	public void CompareAndSetTempExactMatchInfo(FlowLineNode<T> tempexactmatchtail)
	{
		if (this.tempexactmatchtail == null)
		{
			this.tempexactmatchtail = tempexactmatchtail;
		}
		else
		{
			if (this.tempexactmatchtail.getProbability() < tempexactmatchtail.getProbability())
			{
				this.tempexactmatchtail = tempexactmatchtail;
			}
		}
	}
	
	public void ClearExactMatch()
	{
		this.exactmatchtail = null;
	}
	
	@Override
	public void EndOperation()
	{
		super.EndOperation();
		exactmatchtail = tempexactmatchtail;
	}
	
	@Override
	public void InitialSeed(T t) {
		super.InitialSeed(t);
		exactmatchtail = getHeads();
	}

	public List<FlowLineNode<T>> getOvertails() {
		return overtails;
	}

	public int GetOveredSize() {
		return overtails.size();
	}
	
}