
public class Node<T> {
	private T bar;
	private Node<T> next;
	
	public Node(T bar, Node<T> next) {
		this.bar = bar;
		this.next = next;
	}
	
//	public Node(Node next) {
//		this.next = next;
//		this.bar = new Bar();
//	}
	
	public Node(T bar) {
		this.bar = bar;
		this.next = null;
	}
	
	public void setBar(T bar) {
		this.bar = bar;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public T getBar() {
		return bar;
	}
	
	public Node<T> getNextNode() {
		return next;
	}
	
	public T getNextBar() {
		return next.getBar();
	}
	
	public Node<T> getNode() {
		return next;
	}
	
	public String toString() {
		return bar.toString();
	}
	
	public void display() {
		System.out.println(this.toString());
	}
	
}
