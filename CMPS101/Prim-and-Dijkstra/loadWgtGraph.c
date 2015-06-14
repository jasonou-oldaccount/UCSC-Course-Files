// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// loadWgtGraph.c

// Include Libraries
#include <stdlib.h>
#include <stdio.h>

// Include Header Files
#include "edgeList.h"
#include "loadWgtGraph.h"

void loadEdges(EdgeList adjInfo[], FILE *fp, int size, char task) {
	// Keeps track of the total amount of edges and the from edge of each input
	int totalEdges = 0, fromEdge;
	
	// Checks for invalid input
	char checkJunk;
	
	// Buffer for fgets
	char buff[1080];
	
	while(fgets(buff, 1080, fp) != NULL) {
		EdgeInfo edge;
		// Scans in the user input or file input and has an extra variable, check, to make sure that the user isnt inputting more than 3 inputs
		int userInput = sscanf(buff, " %d %d %lf %c ", &fromEdge, &(edge.to), &(edge.wgt), &checkJunk);
		
		// Checks for out of bound edges
		if(fromEdge > size || edge.to > size || fromEdge == 0 || edge.to == 0) { printf("Invalid Vertex or Edge : [%d, %d] Out of Range\n", fromEdge, *&(edge.to)); continue; }
		
		// Checks if the user input/file input is valid
		if(userInput == 3) {
			// If task is P, then it stores the graph as an undirected graph
			if(task == 'P') {
				adjInfo[fromEdge] = edgeCons(edge, adjInfo[fromEdge]);
				EdgeInfo tempEdge;
				tempEdge.to = fromEdge;
				tempEdge.wgt = edge.wgt;
				adjInfo[edge.to] = edgeCons(tempEdge, adjInfo[edge.to]);
				++totalEdges;
			// Else if it is option D, it is a directed graph and gets stored accordingly
			} else {
				adjInfo[fromEdge] = edgeCons(edge, adjInfo[fromEdge]);
				++totalEdges;
			}
		// If the user inputs more than 3 or less than 3 inputs, then it is an error
		} else {
			printf("Invalid Input Format, Usage: [int] [int] [double]\n");
		}
	}
	
	// Prints out the number of vertices and edges then prints out the graph itself
	printf("\nNumber of Vertices : %d\n", size);
	(task == 'P') ? printf("Number of Undirected Edges : %d\n\n", totalEdges) : printf("Number of Directed Edges : %d\n\n", totalEdges);
	printGraph(adjInfo, size, task);
}

void printGraph(EdgeList adjInfo[], int size, char task) {
	
	// Prompts the user whether the graph is a directed or undirected graph then prints out the graph
	(task == 'P') ? printf("Undirected Graph: \n") : printf("Directed Graph: \n");
	printf("Vertex\t| Edge : Weight\n========================\n");
	
	// Goes through adjInfo and prints out all of the vertices and its corresponding edges their weights
	int inc = 0;
	for( ; ++inc <= size ; ){
	
		EdgeList edgeVal = adjInfo[inc];
		printf("%d\t| ",inc);
		
		// If vertex is NULL, prints out NULL : NULL for Edge : Weight
		if(edgeVal == edgeNil) printf("[NULL : NULL]");
		
		// While it is not NULL, it will continue printing out the Edge : Weight until nothing left to print out
		while(edgeVal != edgeNil) {
			EdgeInfo tempE = edgeFirst(edgeVal);
			printf("[%d : %.2lf] ", tempE.to, tempE.wgt);
			edgeVal = edgeRest(edgeVal);
		}
		
		printf("\n");
	}
	printf("\n");
}
