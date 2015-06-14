// Jason Ou (jaou - 1385128)
// Date: 7 May 2015
// CS101 Spring 2015
// loadGraph.h

#ifndef C101LoadGraph
#define C101LoadGraph

/*
 * The transpose graph has edges in the opposite direction of the original graph,
 * in one-to-one correspondence.
 *
 * The transposeGraph prototype takes two parameters, the original graph that you want to transpose
 * and the size of the graph that you are transposing.
 *
 * The prototype will then return the transpose of that graph to where it was called.
 */
IntList* transposeGraph(IntList* origGraph, int n);

/*
 * A function used to determine if a given graph has a cycle.
 * Takes in two parameters, the original graph which you're checking for a cycle, and the size of the graph
 * A cycle is a path that follows edges and ends where it began.
 *
 * This function should return 0 if there is NO cycle.
 * If it finds a cycle, print ONE vertex that MIGHT BE in that cycle.
 * The vertex you print is on a path that went around in a cycle
 * but it might have gone further by the time your program realizes there
 * is a cycle.
 *
 * Your hasCycle() function needs to call a helper function that is
 * recursive, or else the problem will get too complicated.
 *
 * hasCycle() needs to try starting a cycle at each vertex in origGraph.
 */
int hasCycle(IntList* origGraph, int n);

/*
 * hasCycleLen is the recursive prototype that will be called from hasCycle.
 * 
 * The idea is that each recursive call increases 'sofar' by 1, so if this
 * function is called with sofar >= n it must have gone around in a cycle
 * by now (why?), so it should return v as one of its base cases.
 * If it cannot extend the path further from v in any new way, that is, it
 * tried extending with all edges leaving v and all those recursive calls
 * returned 0, then it should also return 0.
 *
 * hasCycleLen() has to try extending the path by using every edge adjacent to v.
 */
int hasCycleLen(IntList* origGraph, int n, int sofar, int v);

#endif