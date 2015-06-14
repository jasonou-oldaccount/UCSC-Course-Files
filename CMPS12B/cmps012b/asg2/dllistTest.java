/**
* Name: Jason Ou
* Partner: Alisia Martinez "almimart (1366771)"
* CruzID: jaou (1385128)
* Class: CMPS012B
* Date: November 5, 2014
* Filename: dllistTest.java
* Description: This file contains the tests for Dllist.
*/

//---------------------------- IMPORTS ------------------------------------
import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.io.*;

///////////////////////////// DLLIST TEST CLASS ////////////////////////////////
public class dllistTest {


// Checks if the dllist is empty
    @Test
    public void startsEmptyTest() {
        dllist lst = new dllist();
        assertEquals(true, lst.isEmpty());
    }
    
// Insert and item into the list and check if it's empty. Should be FALSE
    @Test
    public void anotherEmptyTest() {
    	dllist test = new dllist();
    	test.insert("hello", dllist.position.LAST);
    	assertEquals(false, test.isEmpty());
    }
    
// Checks insert and getItem 
    @Test
    public void lastInsertGetItem() {
		dllist test = new dllist();
		test.insert("End", dllist.position.FOLLOWING);
		assertEquals("End", test.getItem());
		test.insert("2nd", dllist.position.FOLLOWING);
		test.insert("New", dllist.position.FOLLOWING);
		assertEquals("New", test.getItem());
    }

// Inserts items at the front of the list and checks if it's correct
	@Test
	public void frontInsert() {
		dllist test = new dllist();
		test.insert("3rd", dllist.position.PREVIOUS);
		test.insert("2nd", dllist.position.PREVIOUS);
		assertEquals("2nd", test.getItem());
		test.insert("1st", dllist.position.PREVIOUS);
		assertEquals("1st", test.getItem());
	}
	
// setPosition moves to the beginning of the list
	@Test
	public void setPositionFront() {
		dllist test = new dllist();
		test.insert("2nd", dllist.position.FOLLOWING);
		test.insert("Last", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.FIRST);
		assertEquals("2nd", test.getItem());
		
	}
	
// setPosition moves to the end of the list
	@Test
	public void setPositionEnd() {
		dllist test = new dllist();
		test.insert("2nd", dllist.position.PREVIOUS);
		test.insert("1st", dllist.position.PREVIOUS);
		test.setPosition(dllist.position.LAST);
		assertEquals("2nd", test.getItem());
	}	
	
// Builds a list inserting items at the end of the list
	@Test
	public void insertListFollowing() {
		dllist test = new dllist();
		test.insert("A", dllist.position.FOLLOWING);
		test.insert("B", dllist.position.FOLLOWING);
		test.insert("C", dllist.position.FOLLOWING);
		test.insert("D", dllist.position.PREVIOUS);	
		test.setPosition(dllist.position.LAST);
		assertEquals("C", test.getItem());	
	}
	
// Builds a list inserting items at the beginning of the list
	@Test
	public void insertListPrevious() {
		dllist test = new dllist();
		test.insert("A", dllist.position.PREVIOUS);
		test.insert("B", dllist.position.PREVIOUS);
		test.insert("C", dllist.position.PREVIOUS);
		test.insert("D", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.FIRST);
		assertEquals("C", test.getItem());		
	}
	
// Invented test that uses insert and setPosition.
// verifies that getItem returns the correct element in each position
	@Test
	public void inventedInsertList() {
		dllist test = new dllist();
		test.insert("First", dllist.position.PREVIOUS);
		test.insert("Second", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.FIRST);
		assertEquals("First", test.getItem());
		test.insert("NEWSecond", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.LAST);
		assertEquals("Second", test.getItem());
		test.insert("Third", dllist.position.FOLLOWING);
		test.insert("Fourth", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.LAST);
		assertEquals("Fourth", test.getItem());
	}
	
// Calls getPosition to verify if the position is correct
	@Test
	public void getPositionTest() {
		dllist test = new dllist();
		test.insert("First", dllist.position.PREVIOUS);
		test.insert("Second", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.FIRST);
		assertEquals(0, test.getPosition());
		test.insert("NEWSecond", dllist.position.FOLLOWING);
		test.setPosition(dllist.position.LAST);
		assertEquals(2, test.getPosition());
 		test.insert("Third", dllist.position.FOLLOWING);
 		test.insert("Fourth", dllist.position.FOLLOWING);
 		test.setPosition(dllist.position.LAST);
 		assertEquals(4, test.getPosition());
	}
	
	
// Test delete functionality
	@Test
	public void deleteTesting() {
		dllist test = new dllist();
		test.insert("No Delete", dllist.position.FOLLOWING);
		test.insert("Delete This", dllist.position.FOLLOWING);
		test.delete();
		assertEquals("No Delete", test.getItem());		
	}
	
// Test that cover error cases and handling of error cases
	@Test(expected=Exception.class)
	public void errorCaseTesting() {
		dllist test = new dllist();
		test.insert("Test", dllist.position.FOLLOWING);
		test.delete();
		test.delete();
	}		
}
///////////////////////////// END OF DLLIST TEST CLASS////////////////////////////////