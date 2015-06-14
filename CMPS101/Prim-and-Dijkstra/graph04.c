// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// graph04.c

// Include Libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>

// Include Header Files
#include "edgeList.h"
#include "loadWgtGraph.h"
#include "minPQ.h"

// keeps track of the number of vertices
static int vertices;

// Keeps track of whether the user input -P or -D option
static char task;

// Keeps track of the starting vertex
static int startVertex;

// update fringe for either case Prim or Dijkstra
void updateFringe(MinPQ pq, EdgeList adjInfoOfV, int v) {
	// Case Prim
	if(task == 'P') {
		EdgeList remAdj = adjInfoOfV;
		while(remAdj != edgeNil) {
			EdgeInfo wInfo = edgeFirst(remAdj);
			int w = wInfo.to;
			double newWgt = wInfo.wgt;
			
			if(getStatus(pq, w) == UNSEEN) {
				insertPQ(pq, w, newWgt, v);
			} else if(getStatus(pq, w) == FRINGE) {
				if(newWgt < getPriority(pq, w)) decreaseKey(pq, w, newWgt, v);
			}
			remAdj = edgeRest(remAdj);
		}
	// Case Dijkstra uses a distance counter
	} else {
		double distance = getPriority(pq, v);
		EdgeList remAdj = adjInfoOfV;
		while(remAdj != edgeNil) {
			EdgeInfo wInfo = edgeFirst(remAdj);
			int w = wInfo.to;
			double updateDistance = distance + wInfo.wgt;
			
			if(getStatus(pq, w) == UNSEEN) {
				insertPQ(pq, w, updateDistance, v);
			} else if(getStatus(pq, w) == FRINGE) {
				if(updateDistance < getPriority(pq, w)) decreaseKey(pq, w, updateDistance, v);
			}
			remAdj = edgeRest(remAdj);
		}
	}
}

// Greedy Tree implementation, does not get checked for either case Prim or Dijkstra until updateFringe is called
void greedyTree(EdgeList *adjInfo, int *status, int *parent, double *fringeWgt) {
	// Creates the priority queue and inserts the starting vertex with a weight of 0 and parent of -1
	MinPQ pq = createPQ(vertices, status, fringeWgt, parent);
	insertPQ(pq, startVertex, 0, -1);
	
	// Implements either Prim and Dijkstra depending on the value of task
	while(!isEmptyPQ(pq)) {

		int v = getMin(pq);
		delMin(pq);

		updateFringe(pq, adjInfo[v], v);
	}
}

// Prints the output. 
// In the case that the priority is not discovered, priority is set as INFINITY. 
// In the case that there is no parent and not discovered, parent is set as null
void printOutput(int *status, int *parent, double *fringeWgt) {
	// Prints out whether the user is using Prim or Dijkstra
	(task == 'P') ? printf("\nPrim MST starting at vertex %d : \n", startVertex) : printf("\nDijkstra SSSP starting at vertex %d : \n", startVertex);
	
	// Prints out the each vertex and its status, fringeWgt, and parent
	printf("Vertex\t| Status\t| Priority\t| Parent\n====================================================\n");
	int inc = 0;
	for( ; ++inc <= vertices ; ) {
		double wgtTest = fringeWgt[inc];
		int parTest = parent[inc];
		// if the wgt was not discovered, wgt will equal infinity
		if(wgtTest < -1) { wgtTest = INFINITY; }
		// if the parent was not discovered, parent will equal null
		if(parTest < -1) { printf("%d\t| %c\t\t| %.2lf\t\t| null\n", inc, status[inc], wgtTest); }
		else { printf("%d\t| %c\t\t| %.2lf\t\t| %d\n", inc, status[inc], wgtTest, parTest); }
		
	}
}

// Main
int main(int argc, char *argv[]) {
	
	// Buffer for using fgets
	char buff[1080];
	
	// Checks for valid input from user command line
	if(argc < 4 || argc > 4) {
		printf("Usage: ./graph04 [-P / -D] [# start vertex] [input.in / -]\n");
		return EXIT_FAILURE;
	}
	
	// File Pointer: Original Graph
	FILE *fp;
	
	// Checks for either option -P or -D, if option is invalid, will prompt the user
	if(strcmp(argv[1], "-P") == 0) {
		task = 'P';
	} else if(strcmp(argv[1], "-D") == 0) {
		task = 'D';
	} else {
		printf("\"%s\" is an invalid command.\n", argv[1]);
		printf("Usage: ./graph04 [-P / -D] [# start vertex] [input.in / -]\n");
		return EXIT_FAILURE;
	}
	
	// Stores argv[2] into as starting vertex
	startVertex = atoi(argv[2]);
	
	// Checks if user wants to input from stdin
	if(strcmp(argv[3], "-") == 0) {
		// - Standard Input Option
		printf("Standard Input: \n");
		fp = stdin;
	} else {
		fp = fopen(argv[3], "r");
		if( access( argv[3], F_OK ) != -1 ) {
			// file exists
			printf("\nOpened \"%s\" for input.\n\n", argv[3]);
		} else {
			// file doesn't exist
			printf("File \"%s\" does not exists.\n", argv[3]);
			return EXIT_FAILURE;
		}
	}
	
	// Gets the first line of the file or stdin and stores that into number of vertices
	fgets(buff, 1080, fp);
	sscanf(buff," %d ", &vertices);
	if(startVertex > vertices || startVertex < 1) { printf("Invalid Starting Vertex : [%d] Out of Range.\n", startVertex); return EXIT_FAILURE; }
	
	// Creates EdgeList array and initializes it as edgeNil
	EdgeList *adjInfo = malloc(sizeof(EdgeList) *(vertices + 1));
	// Creates status, parent, and fringeWgt arrays and initializes it as -2
	int *status = malloc(sizeof(char) *(vertices + 1)),
		*parent = malloc(sizeof(int) *(vertices + 1));
	double *fringeWgt = malloc(sizeof(double) *(vertices + 1));
	
	// Initializes adjInfo as edgeNil, and status, parent, and fringeWgt as -2
	int i = 0;
	for( ; ++i <= vertices ; ) {
		adjInfo[i] = edgeNil;
		status[i] = UNSEEN;
		parent[i] = -2;
		fringeWgt[i] = -2;
	}
	
	// Loads the edge from user input or file input and prints out the weighted graph
	loadEdges(adjInfo, fp, vertices, task);
	
	// Closes the file if there was a file opened
	if(strcmp(argv[3],"-") != 0) fclose(fp);
	
	// Calls the greedy tree to perform either Prims or Dijkstra
	greedyTree(adjInfo, status, parent, fringeWgt);
	
	// Prints out the output of either Prim or Dijkstra
	printOutput(status, parent, fringeWgt);
	
	// Prints new line and exits the program with EXIT_SUCCESS
	printf("\n");
	return EXIT_SUCCESS;
}
