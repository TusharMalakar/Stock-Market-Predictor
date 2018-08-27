
public class NodeMain {
	public static void main(String[] args) {
		Bar b1 = new Bar("01/03/2008, 20,21,19,20.5,10,1000000000000");
		Bar b2 = new Bar("01/04/2008, 21,22,20,21,21,100120");
		Bar b3 = new Bar("01/05/2008, 21.5,22,18.5,20.5,21,102340");
		
//		Node<Bar> n1 = new Node<Bar>(b1);
//		Node<Bar> n2 = new Node<Bar>(b2);
//		Node<Bar> n3 = new Node<Bar>(b3);
//		
//		n1.setNext(n2);
//		n2.setNext(n3);
		
//		Node<Bar> n3 = new Node<Bar>(b3);
//		Node<Bar> n2 = new Node<Bar>(b2, n3);
//		Node<Bar> n1 = new Node<Bar>(b1, n2);

		Node<Bar> n1 = new Node<Bar>(b3);
		n1 = new Node<Bar>(b2, n1);
		n1 = new Node<Bar>(b1, n1);
		
		Node<Bar> temp = n1;
		while(temp!= null) {
			temp.display();
			temp = temp.getNextNode();
		}
		
//		n1.display();
//		n1.getNextNode().display();
//		n1.getNextNode().getNextNode().display();
		
	}
	
}
