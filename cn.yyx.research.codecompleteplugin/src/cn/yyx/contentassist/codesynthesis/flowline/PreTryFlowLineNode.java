package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class PreTryFlowLineNode<T> extends FlowLineNode<T> {
	
	double seqencesimilarity = -1;
	private FlowLineNode<T> parent = null;
	
	public PreTryFlowLineNode(T t, double prob, double seqsimilarity, FlowLineNode<T> parent) {
		super(t, prob);
		this.seqencesimilarity = seqsimilarity;
		this.setParent(parent);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compareTo(FlowLineNode<T> o) {
		if (o instanceof PreTryFlowLineNode)
		{
			if (!SimilarityHelper.CouldThoughtTwoDoubleSame(seqencesimilarity, ((PreTryFlowLineNode) o).seqencesimilarity))
			{
				return ((Double)(-seqencesimilarity)).compareTo((Double)(-((PreTryFlowLineNode) o).seqencesimilarity));
			}
		}
		return ((Double)(-probability)).compareTo((Double)(-o.probability));
	}

	public FlowLineNode<T> getParent() {
		return parent;
	}

	public void setParent(FlowLineNode<T> parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "data:" + data.toString() + ";prob:" + probability + ";seqsim:" + seqencesimilarity;
	}
	
}