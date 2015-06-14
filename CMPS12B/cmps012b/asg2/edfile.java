/**
* Name: Jason Ou
* Partner: Alisia Martinez "almimart (1366771)"
* CruzID: jaou (1385128)
* Class: CMPS012B
* Date: November 5, 2014
* Filename: edfile.java
* Description: Contains the main class which reads in the names of files.
* Also contains the cases for editing text.
*/

//-------------------------IMPORTS-----------------------------------                                                                                                   
import java.util.Scanner;
import static java.lang.System.*;
import java.io.*;

///////////////////////////// EDFILE CLASS ////////////////////////////////

class edfile{
	
	public static boolean fileFound = false;

//------------------------------- print_curr ----------------------------------

	public static void print_curr(String item) {	// Prints current line
		out.printf("%s%n", item);
	}

//------------------------------- input_file ----------------------------------	

	public static void input_file(String fileName, dllist lines) {	// Reads in files 
		String line;
		try {
			BufferedReader in = new BufferedReader ( new FileReader (fileName) );
			while ( (line = in.readLine()) != null ) {
				lines.insert(line, dllist.position.FOLLOWING);
			}
			in.close();
		} catch (Exception e) {
			auxlib.warn(e.getMessage());
			fileFound = false;
		}
	}

//---------------------------------- MAIN ---------------------------------------
	public static void main (String[] args) throws IOException {
	
		String fileName = null;
		dllist lines = new dllist ();
		boolean want_echo = false;
		
		for(int inc = 0; inc < args.length; ++inc){
			if(args[inc].equals("-e")){
				want_echo = true;
				fileFound = true;
				continue;
			}
			input_file(args[inc], lines);
		}
		if (fileFound == false) System.out.println("File not found, initiating empty list.");
		System.out.println("Welcome. This is a text editor.");	// Welcome message
		
//------------------------------- Switch Cases and For loop ----------------------------------
		Scanner stdin = new Scanner(System.in);
		for (;;) {
			if (! stdin.hasNextLine()) break;
			String inputline = stdin.nextLine();
			if (want_echo) out.printf ("%s%n", inputline);
			if (inputline.length() == 0) continue;
			if (inputline.matches("^\\s*$")) continue;
			
			String command = inputline.substring(1);
			switch (inputline.charAt(0)) {
				case '#': 
					break;
				case '$': 
					lines.setPosition (dllist.position.LAST);
					print_curr(lines.getItem());
					break;
				case '*': 
					lines.print_lines();
					lines.setPosition(dllist.position.LAST);
					break;
				case '.': 
					print_curr(lines.getItem()); 
					break;
				case '0':
					lines.setPosition(dllist.position.FIRST);
					print_curr(lines.getItem());
					break;
				case '<':
					lines.setPosition(dllist.position.PREVIOUS);
					print_curr(lines.getItem());
					break;
				case '>':
					lines.setPosition(dllist.position.FOLLOWING);
					print_curr(lines.getItem());
					break;
				case 'a':
					lines.insert(command, dllist.position.FOLLOWING);
					print_curr(lines.getItem());
					break;
				case 'd': 
					try {
						lines.delete(); 
					} catch (Exception e) {
						auxlib.warn("Nothing to Delete, Empty List.");
					}
					break;
				case 'i':
					lines.insert(command, dllist.position.PREVIOUS); 
					print_curr(lines.getItem());
					break;
				case 'r': 
					dllist.read_file(command,lines); 
					break;
				case 'w': 
					dllist.write_file(command,lines);
					break;
				default : 
					auxlib.warn("Invalid command: " + command); 
					break;
			}
			System.out.println(""); // New line
		}
		auxlib.die("Reached End of File"); 	// END OF FILE 
	} 
//-------------------------END OF MAIN-------------------------------
} 
/////////////////////////////// END OF EDFILE CLASS ////////////////////////////////