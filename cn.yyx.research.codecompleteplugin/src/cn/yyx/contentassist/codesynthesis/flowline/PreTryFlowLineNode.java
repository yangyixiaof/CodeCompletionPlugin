package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class PreTryFlowLineNode<T> extends FlowLineNode<T> {
	
	private double seqencesimilarity = -1;
	private PreTryFlowLineNode<T> parent = null;
	
	public PreTryFlowLineNode(T t, double prob, double seqsimilarity, PreTryFlowLineNode<T> parent) {
		super(t, prob);
		this.setSeqencesimilarity(seqsimilarity);
		this.setParent(parent);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compareTo(FlowLineNode<T> o) {
		if (o instanceof PreTryFlowLineNode)
		{
			if (!SimilarityHelper.CouldThoughtTwoDoubleSame(getSeqencesimilarity(), ((PreTryFlowLineNode) o).getSeqencesimilarity()))
			{
				return ((Double)(-getSeqencesimilarity())).compareTo((Double)(-((PreTryFlowLineNode) o).getSeqencesimilarity()));
			}
		}
		return ((Double)(-probability)).compareTo((Double)(-o.probability));
	}

	public PreTryFlowLineNode<T> getParent() {
		return parent;
	}

	public void setParent(PreTryFlowLineNode<T> parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "prev " + getPrev() + "#data:" + data.toString() + ";prob:" + probability + ";seqsim:" + getSeqencesimilarity();
	}

	public double getSeqencesimilarity() {
		return seqencesimilarity;
	}

	public void setSeqencesimilarity(double seqencesimilarity) {
		this.seqencesimilarity = seqencesimilarity;
	}
	
}