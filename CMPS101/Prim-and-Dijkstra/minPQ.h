// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// minPQ.h

/* minPQ.h (what is the purpose of this file?)
*/

#ifndef CS101MinPQ
#define CS101MinPQ
/* Multiple typedefs for the same type are an error in C. */

typedef struct MinPQNode * MinPQ;

#define UNSEEN ('u')
#define FRINGE ('f')
#define INTREE ('t')

/* Access functions (what are the preconditions?)
*/

/** isEmptyPQ
 *
 *	Precondition: none
 *  Postcondition: returns 1 if it is empty, and returns 0 if it is not empty
 */
int isEmptyPQ(MinPQ pq);

/** getMin
 *
 *	Precondition: isEmptyPQ must be false since you cannot get min if there is nothing in the priority queue
 *  Postcondition: returns min of the priority queue
 */
int getMin(MinPQ pq);

/** getStatus
 *
 *	Precondition: pq must exist and id must be a number
 *  Postcondition: returns the status of the priority queue at the id indicated
 */
int getStatus(MinPQ pq, int id);

/** getParent
 *
 *	Precondition: pq must exist and id must be a number
 *  Postcondition: returns the parent of the priority queue at the id indicated
 */
int getParent(MinPQ pq, int id);

/** getPriority
 *
 *	Precondition: pq must exist and id must be a number
 *  Postcondition: returns the priority of the pq at the id indicated
 */
double getPriority(MinPQ pq, int id);

/* Manipulation procedures (what are the preconditions and postconditions?)
*/

/** delMin
 *
 *	Precondition: pq must exist
 *  Postcondition: none
 */
void delMin(MinPQ pq);

/** insertPQ
 *
 *	Precondition: pq must exist and id must be a number, priority must be a number, par must be a number
 *  Postcondition: none
 */
void insertPQ(MinPQ pq, int id, double priority, int par);

/** decreaseKey
 *
 *	Precondition: pq must exist id must be a number, priority must be a number, par must be a number
 *  Postcondition: none
 */
void decreaseKey(MinPQ pq, int id, double priority, int par);

/* Constructors (what are the preconditions and postconditions?)
*/

/** createPQ
 *
 *	Precondition: n must be a number, status must be an int array, priority must be a double array, parent must be an int array
 *  Postcondition: after creating a MinPQ pq, it will return a MinPQ pq that was created
 */
MinPQ createPQ(int n, int status[], double priority[], int parent[]);

#endif
