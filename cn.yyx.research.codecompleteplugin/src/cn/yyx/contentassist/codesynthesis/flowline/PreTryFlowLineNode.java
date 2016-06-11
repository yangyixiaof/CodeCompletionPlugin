package cn.yyx.contentassist.codesynthesis.flowline;

import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class PreTryFlowLineNode<T> extends FlowLineNode<T> {
	
	private double seqencesimilarity = -1;
	private PreTryFlowLineNode<T> parent = null;
	private String key = null;
	private String wholekey = null;
	private int keylen = 0;
	private boolean isexactsame = false;
	
	public PreTryFlowLineNode(T t, double prob, double seqsimilarity, PreTryFlowLineNode<T> parent, String key, String wholekey, int keylen) {
		super(t, prob);
		this.setSeqencesimilarity(seqsimilarity);
		this.setParent(parent);
		this.setKey(key);
		this.setWholekey(wholekey);
		this.setKeylen(keylen);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compareTo(FlowLineNode<T> o) {
		if (ClassInstanceOfUtil.ObjectInstanceOf(o, PreTryFlowLineNode.class))
		{
			if (!SimilarityHelper.CouldThoughtTwoDoubleSame(getSeqencesimilarity(), ((PreTryFlowLineNode) o).getSeqencesimilarity()))
			{
				return ((Double)(-getSeqencesimilarity())).compareTo((Double)(-((PreTryFlowLineNode) o).getSeqencesimilarity()));
			}
			int kcmp = ((Integer)keylen).compareTo(((PreTryFlowLineNode)o).getKeylen());
			if (kcmp == 0)
			{
				if (SimilarityHelper.CouldThoughtTwoDoubleSame(getProbability(), ((PreTryFlowLineNode) o).getProbability()))
				{
					return wholekey.compareTo(((PreTryFlowLineNode)o).getWholekey());
				}
			}
			return kcmp;
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
		return "prev " + (getPrev() != null ? getPrev().rawString() : " data is null") + "#" + rawString(); // "data:" + data.toString() + ";prob:" + probability + ";seqsim:" + getSeqencesimilarity()
	}
	
	public String rawString() {
		return super.rawString() + ";keylen:" + keylen + ";seqsim:" + getSeqencesimilarity() + ";wholekey:" + wholekey + ";key:" + key;
	}

	public double getSeqencesimilarity() {
		return seqencesimilarity;
	}

	public void setSeqencesimilarity(double seqencesimilarity) {
		this.seqencesimilarity = seqencesimilarity;
	}

	public int getKeylen() {
		return keylen;
	}

	public void setKeylen(int keylen) {
		this.keylen = keylen;
	}

	public String getWholekey() {
		return wholekey;
	}

	private void setWholekey(String wholekey) {
		this.wholekey = wholekey;
	}

	public boolean isIsexactsame() {
		return isexactsame;
	}

	public void setIsexactsame(boolean isexactsame) {
		this.isexactsame = isexactsame;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}