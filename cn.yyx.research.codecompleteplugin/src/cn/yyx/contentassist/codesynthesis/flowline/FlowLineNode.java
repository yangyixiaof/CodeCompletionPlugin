package cn.yyx.contentassist.codesynthesis.flowline;

public class FlowLineNode<T> implements Comparable<FlowLineNode<T>>{
	
	protected T data = null;
	protected double probability = 0;// probability of the data T. Used in all the execution flow of the framework. So put it in here not in data T.
	protected FlowLineNode<T> prev = null;
	protected FlowLineNode<T> next = null;
	protected FlowLineNode<T> silbprev = null;
	protected FlowLineNode<T> silbnext = null;
	
	// this integer variable is set by framework. represents the length to the head including the head and itself.
	protected int length = 0;
	// this boolean variable could only be used by the framework.
	protected boolean couldextend = true;
	
	public FlowLineNode(T t, double prob) {
		this.data = t;
		this.probability = prob;
	}

	public FlowLineNode<T> getPrev() {
		return prev;
	}

	public void setPrev(FlowLineNode<T> prev) {
		this.prev = prev;
	}

	public FlowLineNode<T> getNext() {
		return next;
	}

	public void setNext(FlowLineNode<T> next) {
		this.next = next;
	}

	public FlowLineNode<T> getSilbprev() {
		return silbprev;
	}

	public void setSilbprev(FlowLineNode<T> silbprev) {
		this.silbprev = silbprev;
	}

	public FlowLineNode<T> getSilbnext() {
		return silbnext;
	}

	public void setSilbnext(FlowLineNode<T> silbnext) {
		this.silbnext = silbnext;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean HasPrev() {
		if (prev != null)
		{
			return true;
		}
		return false;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public boolean isCouldextend() {
		return couldextend;
	}

	public void setCouldextend(boolean couldextend) {
		this.couldextend = couldextend;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int compareTo(FlowLineNode<T> o) {
		return ((Double)(-probability)).compareTo((Double)(-o.probability));
	}
	
	@Override
	public String toString() {
		return "prev " + (getPrev() != null ? getPrev().rawString() : " data is null") + "#data:" + data.toString() + ";prob:" + probability;
	}
	
	public String rawString() {
		return "data:" + data.toString() + ";prob:" + probability;
	}
	
}