import java.util.*;

public class InClassAssignment2 {
 public static void main(String[] args) {
	 String expression = "[(3*6)-4*(5-(6*3-2)))";
	 System.out.println("Is the Math expression " + expression +
			 " correct? " + isMathematicallyCorrect(expression));
 }
 
 public static boolean isMathematicallyCorrect(String expression) {
	 List<String> parenthesis = Arrays.asList(new String[] {"(", ")", "[", "]", "{", "}"});
	 Stack stack = new Stack();
	 for(int i = 0; i < expression.length(); i++) {
		 String temp = expression.substring(i, i+1);
		 if (parenthesis.contains(temp)){
			 if(parenthesis.indexOf(temp) % 2 == 0) {
				 stack.push(temp);
			 }else {
				 if (parenthesis.get(parenthesis.indexOf(temp) - 1).equals(stack.peek())) {
					 stack.pop();
				 } else {
					 System.out.println("Wrong use of closing parenthesis. " + temp );
					 System.out.println("Should use " + parenthesis.get(parenthesis.indexOf(stack.peek()) + 1) + " instead.");
				 }
			 }
		 }
	 }
	 
	 if(stack.isEmpty()) {
		 return true;
	 }
	 
	 return false;
 }
}
