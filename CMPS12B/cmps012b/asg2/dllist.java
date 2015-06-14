/**
* Name: Jason Ou
* Partner: Alisia Martinez "almimart (1366771)"
* CruzID: jaou (1385128)
* Class: CMPS012B
* Date: November 5, 2014
* Filename: dllist.java
* The dllist class contains methods that navigate the doubly linked list such
* as setPosition, insert, delete, getPosition, read_file, write_file, etc.
*/

//---------------------------- IMPORTS ------------------------------------
import static java.lang.System.*;
import java.util.*;
import java.io.*;

///////////////////////////// DLLIST CLASS ///////////////////////////////
public class dllist {

	public enum position {FIRST, PREVIOUS, FOLLOWING, LAST};
	
	public static int num_lines_read = 0;		// Counts number of lines read from read_file
	public static int num_lines_written = 0;	// Counts number of lines written from write_file

	private class node {
		String item;	// String item
		node prev;		// previous node in list
		node next;		// next node in list
	}

	private node first = null;
	private node current = null;
	private node last = null;
	private int currentPosition = 0;

//---------------------------- setPosition ------------------------------------
// Changes the current position to be one of the places specified by the enum argument. 
// This method is used to move the current position to the first, last, previous, or following string. 
// Attempts to move the current position before the first position or following the last position are silently ignored
	
	public void setPosition (position pos) {	

		switch (pos) {
			case FIRST:
				current = first; 
				break;

			case PREVIOUS:
				if (current == first) break;
				current = current.prev;
				break;

			case FOLLOWING:
				if (current == last) break;
				current = current.next;
				break;

			case LAST:
				current = last;
				break;

			default:
				throw new IllegalArgumentException();
		}
	}

//------------------------------- isEmpty ----------------------------------
// Returns true if the list is empty, false otherwise

	public boolean isEmpty () {	 
		return first == null; 
	}

//------------------------------- getItem ------------------------------------
// Returns the string at the current position without changing anything in the list. 
// Throws the exception java.util.NoSuchElementException if there are no elements in the list

	public String getItem () {	
		if (isEmpty()) throw new NoSuchElementException(); 
		return current.item;
	}

//------------------------------- getPosition ------------------------------------------
// Returns the relative numerical position of the current element in the list. 
// The first position in the list is position 0. 
// Throws java.util.NoSuchElementException if there are no elements in the list.

	public int getPosition () {			
		int elem = 0; 	// can move outside and use for setPosition // Start elements 

		if (current == null) throw new NoSuchElementException();
		node position = first;

		while (position != current) {
			position = position.next;
			elem++;
		}
		currentPosition = elem;
		return currentPosition; 
	}
//------------------------------- delete --------------------------------------
// Deletes the string at the current position in the list and makes the following string the current position. 
// If the last string in the list is deleted then the current position becomes the new last string. 
// Throws java.util.NoSuchElementException if there are no elements in the list.

	public void delete () {		
		if (isEmpty()) throw new NoSuchElementException();
		else if (first == last) {
			first = null;
			last = null;
			current = null;
		}
		else if (current == first) {
			current = first.next;
			first = current;
			current.prev = null;
		}
		else if (current == last) {
			current = last.prev;
			last = current;
			current.next = null;
		}
		else {
			node after_curr = current.next;
			node before_curr = current.prev;
			before_curr.next = after_curr;
			after_curr.next = before_curr;
			current = after_curr;
		}
	}

//-------------------------------- insert ----------------------------------
// Inserts a new string into the list at the specified position. 
// The new item can be inserted as the first element, last element, 
// immediately before the current position, or immediately after the current position. 
// The element just inserted becomes the new current element. 
// Throws IllegalArgumentException if the position argument does not make sense for the current string

	public void insert (String item, position pos) {
		node newNode = new node ();
		newNode.item = item;

		if (isEmpty()){
			newNode.prev = null;
			newNode.next = null;
			current = newNode;
			first = current;
			last = current;
		return;
		}

		switch (pos) {
			case PREVIOUS:
				if (current == first) {
				newNode.prev = null;
				newNode.next = current;
				current = newNode;
				first = current;
				} else {
				node prevNode = current.prev;
				newNode.next = current;
				newNode.prev = prevNode;
				prevNode.next = newNode;
				current.prev = newNode;
				current = newNode;
				} 
				break;

			case FOLLOWING:
				if (current == last) {
					newNode.prev = current;
					newNode.next = null;
					current.next = newNode;
					current = newNode;
					last = current;
				} else {
					node afterNode = current.next;
					newNode.prev = current;
					newNode.next = afterNode;
					current.next = newNode;
					afterNode.prev = newNode;
					current = newNode;
				} 
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

//------------------------------- read_file ------------------------------------	
// The contents of filename are read and all the lines are inserted after the current line. 
// The current line becomes the last line inserted. An error is printed if the file cannot be read. 
// If the operation succeeds, the number of new inserted lines is displayed.

	public static void read_file(String file_name, dllist lines) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file_name));
			
			String curr_line;
			num_lines_read = 0;
			
			while((curr_line = in.readLine()) != null) {
				num_lines_read++;
				lines.insert(curr_line, dllist.position.FOLLOWING);
			}
			out.printf("%d lines read from %s%n",num_lines_read,file_name);
			in.close();
			lines = null;
		} catch (Exception e) {
			num_lines_read = 0;
			auxlib.warn(e.getMessage());
		}
	}
	
//------------------------------ write_file --------------------------------
// All of the lines are written to filename. On success the number of lines written is displayed. 
// An error message is displayed if the file cannot be created or written.

	public static void write_file (String file_name, dllist lines) {
		try {
			BufferedWriter outW = new BufferedWriter(new FileWriter(file_name));
			num_lines_written = 0;
			
			if (!lines.isEmpty()) {
				lines.setPosition(dllist.position.LAST);
				int last = lines.getPosition();
				lines.setPosition(dllist.position.FIRST);
				
				for (int argi = 0; argi <= last; argi++) {
					++num_lines_written;
					outW.write(lines.getItem()+'\n');
					lines.setPosition(dllist.position.FOLLOWING);
				}
			}
			out.printf("%d lines written to %s%n",num_lines_written,file_name);
			outW.close();
			lines = null;
		} catch (Exception e) {
			num_lines_written = 0;
			auxlib.warn(e.getMessage());
		}
	}

//------------------------------ print_lines ---------------------------------
// Method that scans and prints all the lines

	public void print_lines() {
		node position = first;
		
		while (position != null) {
			out.printf("%s%n",position.item);
			position = position.next;
		}
	}
}
////////////////////////////// END OF DLLIST CLASS ////////////////////////////