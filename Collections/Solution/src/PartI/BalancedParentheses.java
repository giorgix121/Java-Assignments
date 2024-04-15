package PartI;

public class BalancedParentheses {

	public static boolean isBalanced(String inString) {
		return false;
	}
	
	public static void main(String[] args) {
		boolean result = isBalanced("(()()()())");
		result = isBalanced("(((())))");
		result = isBalanced("((((((())");
	}
}
