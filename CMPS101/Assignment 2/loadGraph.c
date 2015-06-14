// Jason Ou (jaou - 1385128)
// Date: 7 May 2015
// CS101 Spring 2015
// loadGraph.c

#include <stdio.h>
#include <stdlib.h>

#include "intList.h"

/*
 * The transpose graph has edges in the opposite direction of the original graph,
 * in one-to-one correspondence.
 *
 * The transposeGraph prototype takes two parameters, the original graph that you want to transpose
 * and the size of the graph that you are transposing.
 *
 * The prototype will then return the transpose of that graph to where it was called.
 */
 
IntList* transposeGraph(IntList originalVertex[], int n) {
	// Creates a new IntList Array to put the transposed values into
	IntList *transposedArray = (IntList*)malloc((n+1) *sizeof(IntList));
	
	// Initiates all of the values in the array as 0.
	int nullInc = 0;
	for(; nullInc <= n; ++nullInc) {
		transposedArray[nullInc] = 0;
	}
	
	// Transpose operations, loops through originalVertex[] and swaps the vertex with the edge
	int startVertex = 1, arrayIndex = 0;
	for(; startVertex <= n; ++startVertex){
	
		IntList tempGraph = originalVertex[startVertex];
		while(tempGraph){
		
			arrayIndex = intFirst(tempGraph);
			
			if(transposedArray[arrayIndex] == 0) {
				transposedArray[arrayIndex] = intCons(startVertex, 0);
			} else {
				IntList tempGraph = intCons(intFirst(transposedArray[arrayIndex]),intRest(transposedArray[arrayIndex]));
				transposedArray[arrayIndex] = intCons(startVertex, tempGraph);	
			}
			
			tempGraph = intRest(tempGraph);
			
		}
	}
	return transposedArray;
}
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
 
int hasCycle(IntList* origGraph, int n) {

	int currIndexVert = 0, arrayIndex = 0;
	
	for(; arrayIndex <= n; ++arrayIndex) {
	
		IntList tempGraph = origGraph[arrayIndex];
		
		while(tempGraph){
			currIndexVert = hasCycleLen(origGraph, n, 1, intFirst(tempGraph));
			
			if(currIndexVert != 0){
				return currIndexVert;
			}
			
			tempGraph = intRest(tempGraph);
		}
	}
	
	return 0;
}

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
 
int hasCycleLen(IntList* origGraph, int n, int sofar, int v) {

	int vertexIndex, nextVertex = 0;
	
	if(sofar == n)return v;
	
	IntList tempGraph = origGraph[v];
	
	while(tempGraph) {
		nextVertex = intFirst(tempGraph);
		vertexIndex = hasCycleLen(origGraph, n, sofar + 1, nextVertex);
		tempGraph = intRest(tempGraph);
	}
	
	if(vertexIndex != 0) {
		return vertexIndex;
	} else {
		return 0;
	}
}