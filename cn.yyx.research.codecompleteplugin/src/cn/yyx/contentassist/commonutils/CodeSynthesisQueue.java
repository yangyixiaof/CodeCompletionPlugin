package cn.yyx.contentassist.commonutils;

/**
 * @author Skip
 * @version 1.0
 */
public class CodeSynthesisQueue<T> {
	// node class
	protected static class Node<T> {
		Node<T> prev = null;
		Node<T> next = null;
		T data = null;
		boolean hasHole = false;
		boolean connect = false;
		int addinfo = -1;
		
		public Node() {
		}

		public Node(T t) {
			this.data = t;
		}
		
		public Node(T t, boolean hasHole) {
			this.data = t;
			this.hasHole = hasHole;
		}
		
		public Node(T t, boolean connect, int extra) {
			this.data = t;
			this.connect = connect;
		}
		
		public Node(T t, int addinfo) {
			this.data = t;
			this.addinfo = addinfo;
		}
		
		public Node(Node<T> t) {
			this.data = t.data;
		}
		
	}
	
	@Override
	public Object clone() {
		CodeSynthesisQueue<T> o = new CodeSynthesisQueue<T>();
		o.length = length;
		Node<T> temp = head;
		while (temp != null)
		{
			o.add(temp.data);
			temp = temp.next;
		}
		return o;
	}

	protected Node<T> head = null;
	protected Node<T> last = null;
	protected int length;

	public CodeSynthesisQueue() {
		length = 0;
	}

	public CodeSynthesisQueue(T data) {
		head = new Node<T>(data);
		last = head;
		length = 1;
	}

	public void add(T data) {
		if (isEmpty()) {
			head = new Node<T>(data);
			last = head;
			length++;
		} else {
			// tail insert method
			Node<T> other = new Node<T>(data);
			other.prev = last;
			last.next = other;
			last = other;
			length++;
		}
	}
	
	public void add(T data, boolean hasHole) {
		if (isEmpty()) {
			head = new Node<T>(data, hasHole);
			last = head;
			length++;
		} else {
			// tail insert method
			Node<T> other = new Node<T>(data, hasHole);
			other.prev = last;
			last.next = other;
			last = other;
			length++;
		}
	}
	
	public void add(T data, boolean connect, int extra) {
		if (isEmpty()) {
			head = new Node<T>(data, connect, -1);
			last = head;
			length++;
		} else {
			// tail insert method
			Node<T> other = new Node<T>(data, connect, -1);
			other.prev = last;
			last.next = other;
			last = other;
			length++;
		}
	}
	
	public void add(T data, int addinfo) {
		if (isEmpty()) {
			head = new Node<T>(data, addinfo);
			last = head;
			length++;
		} else {
			// tail insert method
			Node<T> other = new Node<T>(data, addinfo);
			other.prev = last;
			last.next = other;
			last = other;
			length++;
		}
	}

	public T get(int index) {
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException("Index out of boud:" + index);
		}
		Node<T> other = head;
		for (int i = 0; i < index; i++) {
			other = other.next;
		}
		return other.data;
	}
	
	public boolean hasHole(int index) {
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException("Index out of boud:" + index);
		}
		Node<T> other = head;
		for (int i = 0; i < index; i++) {
			other = other.next;
		}
		return other.hasHole;
	}

	public boolean contains(T data) {
		Node<T> other = head;
		while (other != null) {
			if (other.data.equals(data)) {
				return true;
			}
			other = other.next;
		}
		return false;
	}

	public T getLast() {
		return last.data;
	}
	
	public boolean hasHoleLast() {
		return last.hasHole;
	}

	public T getFirst() {
		return head.data;
	}
	
	public boolean hasHoleFirst() {
		return head.hasHole;
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
			Node<T> other = head;
			for (int i = 0; i < length; i++) {
				System.out.print(other.data + " ");
				other = other.next;
			}
			System.out.println();
		}
	}

	public void SetLast(T cnt) {
		last.data = cnt;
	}

	public void SetLastHasHole(boolean hole) {
		last.hasHole = hole;
	}
	
	public T GetLastButOne()
	{
		return last.prev.data;
	}

	public boolean GetLastHoleButOne()
	{
		return last.prev.hasHole;
	}
	
	public boolean CanBeMerged()
	{
		if (head == last)
		{
			return false;
		}
		return true;
	}
	
	public void MergeLast(T merge) {
		last.prev.data = merge;
		last.prev.hasHole = false;
		last.prev.next = null;
		last = last.prev;
	}

	public void MergeLast() {
		// skip prev data.
		last.prev.data = last.data;
		last.prev.hasHole = false;
		last.prev.next = null;
		last = last.prev;
	}
	
}