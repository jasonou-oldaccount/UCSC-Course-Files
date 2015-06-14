// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// edgeList.h

/* edgeList.h
 * (what is the purpose of this file?)
 */

#ifndef CS101EdgeList
#define CS101EdgeList
/* Multiple typedefs for the same type are an error in C. */

typedef struct EdgeListNode * EdgeList;

typedef struct EdgeInfoStruct {
	int to;
	double wgt;
} EdgeInfo;

/** edgeNil denotes the empty EdgeList */
extern const EdgeList edgeNil;

/* Access functions
 * (what are the preconditions?)
 */

/** First
 *
 *	Precondition: There must exist an EdgeList with EdgeInfo value
 *  Postcondition: returns the EdgeInfo value of the EdgeList oldL
 */
EdgeInfo edgeFirst(EdgeList oldL);

/** Rest
 *
 *	Precondition: There must exist an EdgeList with EdgeList next
 *  Postcondition: returns the EdgeList next of the EdgeList oldL
 */
EdgeList edgeRest(EdgeList oldL);

/* Constructors
 * (what are the preconditions and postconditions?)
 */

/** Cons
 *
 *	Precondition: there must be an EdgeInfo newE and EdgeList oldL
 *  Postcondition: returns a new and updated EdgeList
 */
EdgeList edgeCons(EdgeInfo newE, EdgeList oldL);

#endif
