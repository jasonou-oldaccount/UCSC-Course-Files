// Jason Ou (1385128)
// Date: 15 April 2015
// CS101 Spring 2015
// intList.h

/* intList.h
 * - The header declares what a class will do.
 * - Reduces dependencies so that code that uses the header doesn't need to
 * 	 	know all the details of the implementation and any other classes/headers needed only for that.
 * 	- Reduces compilation times and also the amount of recompilation needed when something in the implementation
 *    	changes.
 */

#ifndef C101IntList
#define C101IntList
/* Multiple typedefs for the same type are an error in C. */

typedef struct IntListNode *IntList;

/** intNil denotes the empty IntList */
extern const IntList intNil;

/* Access functions
 * Accessors are a way to grab information from intList.
 * Precondition for intFirst() is IntList oldL must exist and have a value.
 * Precondition for intRest() is IntList oldL must exist and point to another IntList.
 */

/** first
 */
int intFirst(IntList oldL);

/** rest
 */
IntList intRest(IntList oldL);

/* Constructors
 * Precondition for intCons() is that there must be an int to be added in and also an IntList oldL must exist.
 * Postcondition for intCons() is that it must return a new IntList.
 */

/** cons
 */
IntList intCons(int newE, IntList oldL);

#endif