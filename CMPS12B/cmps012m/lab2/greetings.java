/**
* Name: Jason Ou
* Class: CMPS012M
* Date: October 16, 2014
* Filename: greetings.java
* Description: Greets everyone listed in directory.txt
*/

import static java.lang.System.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

class greetings {
	public static void main( String[] args ) throws IOException {
		BufferedReader in = new BufferedReader(
			new FileReader("directory.txt"));
		while(true) {
			String name = in.readLine();
			String[] matches = name.split(",");
			if (name == null) break;
			System.out.println("Hello, " + matches[0] + ".");
		}
		in.close();
	}
}
