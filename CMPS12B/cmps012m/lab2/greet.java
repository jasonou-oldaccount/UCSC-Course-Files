/**
* Name: Jason Ou
* Class: CMPS012M
* Date: October 16, 2014
* Filename: greet.java
* Description: Infinite loop, asking your name and greeting you.
*/

import static java.lang.System.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

class greet {
	public static void main( String[] args ) {
		BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
		try {
			for(int i = 1; i !=0; i++) {
				System.out.println("What is your name?");
				String name = in.readLine();
				System.out.println("Hello, " + name + ".");
			}
		} catch(IOException io) {
			io.printStackTrace();
		}
	}
}
