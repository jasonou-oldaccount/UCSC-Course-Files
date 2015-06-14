/**
* Name: Jason Ou
* Class: CMPS012B
* Date: December 8, 2014
* Filename: cyoa.java
* Description: Code for main application
*/

import java.util.Scanner;
import static java.lang.System.*;
import java.io.*;
import java.util.*;

class cyoa {
	
	//----- Global declarations of variables to be used -----
	public static int cols = 0; //Keeps track of the number of rooms
	public static String[][] data = new String[1000][1000]; //2D array size 1000x1000
	public static int currRoom = 0; //Keeps track of current room
	public static int startRoom = 0; //Keeps track of the first room

	public static int roomOptions = 0; //Keeps track of the number of options in the room
	
	public static String[] userOptions = new String[1000]; //Keeps track of the options the user has made
	public static int userOmade = -1; //Keeps track of the number of options the user has made
	public static int currOpt = 0; //Keeps track of the user's current option
	
	public static String[] orderData; //Keeps track of the rooms and tags
	
	public static String[] roomChecker = new String[100]; //Keeps track of all the rooms in the game
	
	public static boolean konami = false; //CHEAT CODE
	
	//----- Inputs the file into a 2D Array -----
	public static void input_file(String file) throws IOException {
		String line;
		int rows = 0;
		BufferedReader in = new BufferedReader(new FileReader(file));
		
		//----- Inputs data into the 2D array -----
		while( (line = in.readLine()) != null ) {
			if(line.trim().equals("")) {
				cols++;
				rows = 0;
			} else {
				data[cols][rows] = line;
				rows++;
			}
		}
		orderData = new String[cols+1];
		in.close();
		
		if(rows == 0) {
			System.out.println("\033[31mFile is Empty, game cannot be created.\033[0m");
			System.exit(0);
		}
	}
	
	//----- Checks for invalid tags -----
	public static void check_rooms() {
		String[] tagsChecker = new String[1000];
		int numRoom = -1;
		int numTag = -1;
		
		//----- Separates the rooms and tags -----
		for(int i = 0; i < 100; i++) {
			if (data[i][0] != null) {
				for(int j = 0; j < 10; j++) {
					if (data[i][j] == null) {
						continue;
					} else {
						if(data[i][j].charAt(0) == 'r') {
							roomChecker[++numRoom] = data[i][j].substring(2, data[i][j].length());
						}
						if(data[i][j].charAt(0) == 't') {
							tagsChecker[++numTag] = data[i][j].substring(2, data[i][j].length());
						}
					}
				}
			}
		}
		
		//----- Checks the tags to make sure there are no invalid tags -----
		int validTags = 0;
		
		for(int i = 0; i < numTag + 1; i++) {
			for(int j = 0; j < numRoom + 1; j++) {
				if(tagsChecker[i].compareTo(roomChecker[j]) == 0) validTags++;
			}
		}
		
		//----- If there is an invalid tag, then the game will not load -----
		if(validTags != numTag + 1) {
			System.out.print('\u000C');
			System.out.println("\033[31mOne or more of your tags is invalid, adventure game file is corrupt.\033[0m");
			System.exit(0);
		}
	}
	
	//----- Prints out the room the user is in -----
	public static void printRoom() {
		if (konami == true) System.out.println("\033[1;32m[HACK ON -- YOU CAN NOW ENTER ANY ROOM YOU CHOOSE]\033[0m");
		System.out.println();
		for(int j = 0; j < 100; j++) {
			if(data[currRoom][0].charAt(0) == '#') {
				currRoom++;
				startRoom++;
			} else if(data[currRoom][j] == null) {
				continue;
			} else if(data[currRoom][j].charAt(0) == 'd') {
				System.out.println("\033[1;32m" + data[currRoom][j].substring(2, data[currRoom][j].length()) + "\033[0m");
				System.out.println();
			}
		}
		if(userOmade == -1) optionTracker(data[startRoom][0].substring(2, data[startRoom][0].length()));
		printOptions();
	}
	
	//----- Prints out the options for the room the user is in -----
	public static void printOptions() {
		int charNum = 96;
		if(konami == false) {
			for(int j = 0; j < 100; j++) {
				if(data[currRoom][j] == null) {
					continue;
				} else if(data[currRoom][j].charAt(0) == 'o') {
					System.out.println("\033[31m" + (char)++charNum + " -- " + data[currRoom][j].substring(2, data[currRoom][j].length()));
				}
			}
		} else {
			for(int i = 0; i < 100; i++) {
				if(roomChecker[i] == null) {
					continue;
				} else {
					System.out.println("\033[31m" + (char)++charNum + " -- " + roomChecker[i]);
				}
			}
		}
		roomOptions = ++charNum - 96;
		if (roomOptions != 1) {
			System.out.println((char)charNum + " -- Random move." + "\033[0m");
			System.out.println();
		}
	}
	
	//----- Prints out all the info for all rooms -----
	public static void printRoomInfo() {
		System.out.print('\u000C');
		System.out.println("\033[1;33m[information]");
		System.out.println();
		
		//----- Finds the rooms and corresponding tags and puts them into an array -----
		String roomTag = "";
		for(int i = 0; i < 100; i++) {
			roomTag = "";
			if (data[i][0] != null) {
				for(int j = 0; j < 10; j++) {
					if (data[i][j] == null || data[i][0].charAt(0) == '#') {
						continue;
					} else {
						if(data[i][j].charAt(0) == 'r') {
							roomTag += data[i][j].substring(2, data[i][j].length()) + " : ";
						}
						if(data[i][j].charAt(0) == 't') {
							roomTag += data[i][j].substring(2, data[i][j].length()) + " ";
						}
					}
				}
			orderData[i] = roomTag;
			}
		}
		
		//----- Sorts the rooms of the data plus its tags-----
		Arrays.sort(orderData);
		
		//----- Prints out the sorted rooms of the data plus its tags----
		for(int i = 0; i < cols+1; i++) {
			System.out.println(orderData[i]);
		}
		
		System.out.print("\033[0m");
		
		printRoom();
	}
	
	//----- used to choose the option a user wants to do -----
	public static void chooseOption() {
		int optionScan = 0;
		if (roomOptions < currOpt || currOpt < 0) {
			System.out.println("\033[31mOption not valid\033[0m");
		} else if (konami == true) {
			optionTracker(roomChecker[currOpt-1]);
			System.out.print('\u000C');
			System.out.println("YOU HAVE ENTERED ROOM [" + roomChecker[currOpt-1] + "]");
			changeRoom(roomChecker[currOpt-1]);
		} else {
			for(int j = 0; j < 100; j++) {
				if((data[currRoom][j] == null) || (optionScan == currOpt)) {
					return;
				} else {
					if(data[currRoom][j].charAt(0) == 't') {
						optionScan++;
						if(optionScan == currOpt) {
							String nameRoom = data[currRoom][j].substring(2, data[currRoom][j].length());
							optionTracker(nameRoom);
							System.out.print('\u000C');
							System.out.println("[" + data[currRoom][j-1].substring(2, data[currRoom][j-1].length()) + "]");
							changeRoom(nameRoom);
						}
					}
				}
			}
		}
	}
	
	//----- Used to change to room a user is currently in to another -----
	public static void changeRoom(String name) {
		for(int i = 0; i < 50 + 1; i++) {
			if (data[i][0] == null) {
				continue;
			} else {
				String room = data[i][0].substring(2, data[i][0].length());
				if(room.compareTo(name) == 0) {
					currRoom = i;
				}
			}
		}
		printRoom();
	}
	
	//----- Keeps track of the options the user makes -----
	public static void optionTracker(String name) {
		userOptions[++userOmade] = name;
	}
	
	//----- Undo the options the user makes -----
	public static void undoOption() {
		if (userOmade == 0) {
			System.out.println("\033[31mNo moves to undo\033[0m");
		} else {
			System.out.print('\u000C');
			changeRoom(userOptions[--userOmade]);
		}
	}
	
	//----- Generates a random option for the user -----
	public static void randomOption() {
		if (roomOptions == 1) {
			System.out.println("\033[31mOption not valid\033[0m");
			return;
		} else {
			Random rand = new Random();
			currOpt = rand.nextInt(roomOptions-1) + 1;
		}
	}
	
	//Cheat code to access any room
	public static void konamiCode(String input) {
		if(input.compareTo("^^VV<><>BA") == 0) {
			konami = true;
			System.out.print('\u000C');
			printRoom();
		} else {
			konami = false;
			System.out.println("\033[31mINVALID CODE\033[0m");
		}
	}
	
	
	//----- Main method where game is uploaded and played -----
	public static void main (String[] args) throws IOException {
	
		//----- Exits if user uploads game incorrectly -----
		if (args.length != 1) {
			System.out.println("\033[31mUsage: cyoa [adventureFile]\033[0m");
			System.exit(0);
		}
		
		//----- Tests to see if the game file entered is valid -----
		try {
			input_file(args[0]);
		} catch (IOException error) {
			System.out.println("\033[31mFile not found: " + args[0] + "\033[0m");
			System.exit(0);
		}
		
		//----- checks to make sure there are no invalid tags -----
		check_rooms();
		
		//----- Let the game begin! -----
		System.out.print('\u000C');
		printRoom();
		Scanner stdin = new Scanner(System.in);
		for(;;) {
			String inputline = stdin.nextLine();
			if (inputline.length() == 0 || inputline.matches("^\\s*$")) {
				System.out.println("\033[31mPlease enter an option.\033[0m");
				continue;
			}
			
			switch (inputline.charAt(0)) {
				case '^':
					konamiCode(inputline);
					break;
				case 'z':
					undoOption();
					break;
				case 'y':
					printRoomInfo();
					break;
				case 'r':
					System.out.print('\u000C');
					currRoom = 0;
					userOmade = -1;
					printRoom();
					break;
				case 'q':
					System.out.println("\033[1;34m[quit]\033[0m");
					System.exit(0);
					break;
				default:
					int ascii = (int)inputline.charAt(0);
					currOpt = ascii - 96;
					if (currOpt == roomOptions) randomOption();
					chooseOption();
					break;
			}
		}
	}
}