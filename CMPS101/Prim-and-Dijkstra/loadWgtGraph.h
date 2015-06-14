// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// loadWgtGraph.h

#ifndef CS101LoadWgtGraph
#define CS101LoadWgtGraph

/** Precondition: there must be an adjInfo, a FILE to read from for stdin or file input, size of the graph,  and task to specify either option P or D
 *  Postcondition: returns the number of edges found from either file input or stdin
 */
void loadEdges(EdgeList adjInfo[], FILE *fp, int size, char task);

/** Precondition: There must be an adjInfo, size of graph, and task to specify either option P or D
 *  Postcondition: none
 */
void printGraph(EdgeList adjInfo[], int size, char task);

#endif
