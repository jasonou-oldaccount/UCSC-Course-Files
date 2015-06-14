/**
* Name: Jason Ou
* Class: CMPS012B
* Date: November 19, 2014
* Filename: xref.java
* Description: Code for main application
*/

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class xref {

	static boolean debug_opt = false;
	static boolean opt_in = false;

    static void processFile(String filename, boolean debug) throws IOException {
        Scanner scan = new Scanner (new File(filename));
        Tree tree = new Tree();

        for (int linenr = 1; scan.hasNextLine (); ++linenr) {
            for (String word: scan.nextLine().split ("\\W+")) {
				if (word.matches ("^\\d*$")) continue;
                tree.insert(word, linenr);
            }
        }
        scan.close();
        if (debug) {
            tree.debug();
        } else {
            tree.output();
        }
    }

	static void checkOpt(String opt) {
		if(opt.equals("-d")) {
			debug_opt = true;
		} else {
			opt_in = true;
			auxlib.warn("invalid command", opt);
		}
	}
	
    public static void main(String[] args) {
		if((args.length != 0) && (args[0].charAt(0) == '-')) {
			checkOpt(args[0]);
		}
		
		if(args.length == 0 || ((debug_opt || opt_in) && args.length < 2)) {
			System.out.println("Usage: xref [-d] filename");
			auxlib.exit();
		}
		
		String filename;
		
		int inc = 0;
		
		if(debug_opt || opt_in) {
			inc = 1;
		}
		else {
			inc = 0;
		}
		
		for(; inc < args.length; ++inc) {
			filename = args[inc];
			try {
				processFile(filename, debug_opt);
			}catch (IOException error) {
				auxlib.warn (error.getMessage());
			}
		}
		auxlib.exit();
    }

}

