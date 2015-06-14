/**
* Name: Jason Ou
* Class: CMPS012M
* Date: October 27, 2014
* File name: Calc.java
* Description: RPN Calculator
*/

public class Calc {

	private double[] stackArray;
	private int top;
	private int tracker;	

	// Constructor
	public Calc() {
		stackArray = new double[100];
		top = -1;
		tracker = 0;
	}
    
	// Push a number
	public void push(double x) {
		stackArray[++top] = x;
		tracker++;
	}
    
	// Pop top number (removes)
	public double pop() {
		if(top < 0){ throw new RuntimeException(); }
		tracker--;
		return stackArray[top--];
	}
    
	// Peek at top number (does not remove)
	public double peek() {
		if(top < 0){ throw new RuntimeException(); }
		return stackArray[top];
	}
    
	// Add top two numbers
	public void add() {
		if(top < 1){ throw new RuntimeException(); }
		stackArray[top - 1] = stackArray[top - 1] + stackArray[top];
		top--;
		tracker--;
	}
    
	// Subtract top two numbers (top on right side)
	public void subtract() {
		if(top < 1){ throw new RuntimeException(); }
		stackArray[top - 1] = stackArray[top - 1] - stackArray[top];
		top--;
		tracker--;
	}

	// Multiply top two numbers
	public void multiply() {
		if(top < 1){ throw new RuntimeException(); }
		stackArray[top - 1] = stackArray[top - 1] * stackArray[top];
		top--;
		tracker--;
	}
    
	// Divide top two numbers (top on bottom)
	public void divide() {
		if(top < 1){ throw new RuntimeException(); }
		stackArray[top - 1] = stackArray[top - 1] / stackArray[top];
		top--;
		tracker--;
	}
	
	// Transforms the number on top of the stack to its reciprocal
	public void reciprocal() {
		if(top < 0){ throw new RuntimeException(); }
		stackArray[top] = 1/stackArray[top];
	}
    
	// Return how many numbers are in the stack
	public int depth() {
		return tracker;
	}
}
