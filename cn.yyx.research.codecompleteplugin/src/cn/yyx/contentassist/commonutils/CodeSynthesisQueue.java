package cn.yyx.contentassist.commonutils;

/**
 * @author Skip
 * @version 1.0
 */
public class CodeSynthesisQueue<T> {
	// node class
	protected static class Node<T> {
		Node<T> perv = null;
		Node<T> next = null;
		T data = null;
		
		public Node() {
		}

		public Node(T t) {
			this.data = t;
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
			other.perv = last;
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

	public T getFirst() {
		return head.data;
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

}