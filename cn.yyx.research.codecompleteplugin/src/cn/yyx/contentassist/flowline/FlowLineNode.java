package cn.yyx.contentassist.flowline;

public class FlowLineNode<T> {
	
	private T data = null;
	private double probability = 0;
	private FlowLineNode<T> prev = null;
	private FlowLineNode<T> next = null;
	private FlowLineNode<T> silbprev = null;
	private FlowLineNode<T> silbnext = null;
	
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
	
}