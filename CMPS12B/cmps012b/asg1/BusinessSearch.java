/**
* Name: Jason Ou
* Class: CMPS012B
* Date: October 22, 2014
* Filename: BusinessSearch.java
* Description: Using Merge Sort and Binary Search, this program looks for businesses and outputs the phone number of that business
*/

//----------------------Imports---------------------------

import static java.lang.System.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

//----------------------Main Class---------------------------

class BusinessSearch {
	
	//-------------Initializing Businesses Array Along With Array Size-------------
	private static String[] businesses; // businesses array
	private static int arraySize; // businesses array size/number of elements
	
	//----------------------Main Function---------------------------
	
	public static void main(String args[]) throws IOException {
		
		//------------If No Database Input----------------------
		
		if (args.length == 0) { // If No DB Input, Ends The Program
			System.out.println("Usage: BusinessSearch businessDB");
			System.exit(0);
		}
		
		//---------------Reads in Database Input---------------------
		
		BufferedReader in = new BufferedReader(new FileReader(args[0])); // Reads In The File

		arraySize = Integer.parseInt(in.readLine()); // Reads In The First Line Of The "file".txt
		businesses = new String[arraySize]; // Sets The Size Of the Businesses Array To The First Number Of "file".txt
		
		for (int i = 0; i < arraySize; i++) { // Creates An Array Using the DB Input
			String dbLine = in.readLine();
			if (dbLine == null) break;
			businesses[i] = dbLine;
		}
		in.close();
		
		//------------Runs Businesses Array Through Merge Sort-------------
		
		businesses = mergeSort(businesses);
		
		//--------Keeps Track Of Queries, Not Found, Index of Company-----------
		
		int totalQueries = 0;
		int notFound = 0;
		int index = 0;
		
		//------------Loop For User Input And Finds Businesses Until User Enters Nothing-------------
		
		System.out.println("What Company Phone Number Do You Want?");
		
		while(index != -2) {
		
			Scanner userInput = new Scanner(System.in);
		
			String nameSearch = userInput.nextLine();
		
			index = find(nameSearch);
		
			if ( index == -2) { // If User Input == Blank, Ends Program
				System.out.println(totalQueries + " total queries, " + notFound + " results not found.");
			} else if ( index >= 0 ) { // If User Input == Found, Prints Out The Phone Number Of The Company
				String [] splitBusinesses = businesses[index].split(",");
				System.out.println(splitBusinesses[1]);
				totalQueries += 1;
			} else { // If User Input == Not Found, Tells The User That Company Not Found
				System.out.println("Business Not Found");
				totalQueries += 1;
				notFound += 1;
			}
		}
	} // End Main Function
	
	/**
	* Binary Search Code Reference
	* Source: CMPS 012B Ecommons, ExamplePrograms.ZIP -- ReadFiles/Chap06/binarySearch
	*/
	
	//----------------------Find Function---------------------------
	
	public static int find(String nameSearch) {
		if (nameSearch.length() != 0) {
			return recFind(nameSearch, 0, arraySize-1);
		} else {
			return -2;
		}
	} // End find()
	
	//----------------------Rec Find Function---------------------------
	
	private static int recFind(String nameSearch, int lowerBound, int upperBound) {

		int curIn = (lowerBound + upperBound ) / 2;
		String businessName = businesses[curIn].substring(0,(businesses[curIn].indexOf(",")));
		
			if(businessName.equals(nameSearch)) {
				return curIn; // Found It
			} else if(lowerBound > upperBound) {
				return -1; // Can't Find It
			} else { //Divide Range
				if(businessName.compareTo(nameSearch) < 0) // It's Upper Half
					return recFind(nameSearch, curIn+1, upperBound);
				else // It's Lower Half
					return recFind(nameSearch, lowerBound, curIn-1);
			} // End Else Divide Range
	} // End recFind()
	
	/**
	* Merge Sort Code Reference
	* Source: http://javahungry.blogspot.com/2013/06/java-sorting-program-code-merge-sort.html
	*/
	
	//----------------------Merge Sort Function---------------------------
	
	public static String [] mergeSort(String [] businesses) {
	
		if (businesses.length <= 1) {
			return businesses;
		}
		
		String[] firstHalf = new String[businesses.length / 2];
		String[] secondHalf = new String[businesses.length - firstHalf.length];
		System.arraycopy(businesses, 0, firstHalf, 0, firstHalf.length);
		System.arraycopy(businesses, firstHalf.length, secondHalf, 0, secondHalf.length);
		
		mergeSort(firstHalf);
		mergeSort(secondHalf);
		
		merge(firstHalf, secondHalf, businesses);
		
		return businesses;
	} // End mergeSort()
	
	//----------------------Merge Function---------------------------
	
	public static void merge(String[] first, String[] second, String[] result) {
	
		int firstCheck = 0;
		int secondCheck = 0;
		int incr = 0;
		
		while (firstCheck < first.length && secondCheck < second.length) {
			if (first[firstCheck].compareTo(second[secondCheck]) < 0) {
				result[incr] = first[firstCheck];
				firstCheck++;
			} else {
				result[incr] = second[secondCheck];
				secondCheck++;
			}
			incr++;
		}
		
		System.arraycopy(first, firstCheck, result, incr, first.length - firstCheck);
        	System.arraycopy(second, secondCheck, result, incr, second.length - secondCheck);
	} // End merge()
	
} // End BusinessSearch Class
