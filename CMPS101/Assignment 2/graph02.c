// Jason Ou (jaou - 1385128)
// Date: 7 May 2015
// CS101 Spring 2015
// graph01.c

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>

#include "intList.h"
#include "loadGraph.h"

// Pre-Declaration
void printGraph(IntList verticesArray[], int vertices);

int main(int argc, char *argv[]) {
	
	// Variable Declarations:
	// 	number of vertices, start edges, end edge, input line, edge counter, file line counter
	int vertices, startE, endE, eCounter = 0, lineCounter = 1;
	char line[10];
	
	// File pointer
	FILE *fr;
	
	// Checks for if there is a valid file to be opened
	if (argv[1] == NULL) {
		printf("\nUsage: ./graph01 [input file]\n\n");
		exit(1);
	}
	
	// Opens the file and checks if the file exists
	fr = fopen(argv[1], "r");
	if( access( argv[1], F_OK ) != -1 ) {
		// file exists
		printf("\nOpened \"%s\" for input.\n\n", argv[1]);
	} else {
		// file doesn't exist
		printf("File %s does not exists.\n\n", argv[1]);
		exit(1);
	}
	
	// Reads in the first line of the file to find out the number of vertices
	fgets(line, 10, fr);
	sscanf(line, "%d", &vertices);
	
	// Creates an array with the same number of elements as the number of vertices
	IntList verticesArray[vertices+1];
	int nullInc = 0;
	// Sets all the values in the array to be 0(NULL).
	for(; nullInc <= vertices ; ++nullInc){
		verticesArray[nullInc] = 0;
	}
	
	// Scans in the entire file and assigns the values in the file a location in the graph
	while(fgets(line, 10, fr) != NULL) {
		sscanf(line, "%d %d", &startE, &endE);
		++lineCounter;
		// Checks for invalid file line input and if a value is trying to make an edge with itself
		if((startE > vertices) || (endE > vertices) || (startE == 0) || (endE == 0) || (startE == endE)) {
			printf("Invalid Input: %d [%d]\n", startE, endE);
			continue;
		}
		bool repeatChecker = false;
		// Initial Insert of the first item into the verticesArray
		if(verticesArray[startE] == 0) {
			verticesArray[startE] = intCons(endE, 0);
		} else {
			// If item already exists in the IntList, then it will create a new IntListNode attached to the old
			// 	IntList
			IntList tempList = intCons(intFirst(verticesArray[startE]),intRest(verticesArray[startE]));
			
			// Checks for repeats
			IntList tempCheckRepeat = verticesArray[startE];
			while(tempCheckRepeat) {
				if((intFirst(tempCheckRepeat) == endE)) { 
					repeatChecker = true; 
				}
				tempCheckRepeat = intRest(tempCheckRepeat);
			}
			if(repeatChecker == true) continue;
			verticesArray[startE] = intCons(endE, tempList);
		}
		// If the line reads in successfully, then it will increment the edges
		if(repeatChecker == true) continue;
		++eCounter;
	}
	
	// Prints out the number of vertices and number of edges
	printf("\nNumber of vertices: %d.", vertices);
	printf("\nNumber of edges: %d.\n\n", eCounter);
	
	// Prints out the entire graph
	printf("Original Graph.\n");
	printGraph(verticesArray, vertices);
	
	// Checks if the graph has any cycles
	int cycleValidate = hasCycle(verticesArray, vertices);
	// If no cycle, print no cycle, else prints where the cycle starts
	(cycleValidate == 0) ? printf("\nGraph contains no cycles.\n") : printf("\nGraph contains a cycle starting at vertex: %d.\n", cycleValidate);
	
	// Closes the file then exits the program
	printf("\n");
	fclose(fr);
	
	// How many times a user wants to transpose the graph
	int numberOfTranspose;
	// Whether the user wants to transpose the graph or not
	char transposeIt;
	
	// Asks if the user wants to transpose the graph
	printf("Do you want to transpose the graph (y | n) ? ");
	scanf(" %c", &transposeIt);
	
	
	// Checks if the user wants to transpose the graph
	if(transposeIt == 'n' || transposeIt == 'N') {
		numberOfTranspose = 0;
	} else {
		// If the user wants to transpose the graph, asks the user how many times he wants to transpose it
		printf("How many times do you want to transpose the graph : ");
		scanf(" %d", &numberOfTranspose);
	}
	
	// If the user wants to transpose the graph, then it will transpose it as many times as the user wants
	while(numberOfTranspose-- != 0) {
	
		// Initialize an temp empty array for the values in verticesArray to be transposed into
		IntList *transposeArray;
		// Transposes the verticesArray then returns it back into transposeArray
		transposeArray = transposeGraph(verticesArray, vertices);
		
		// Replaces verticesArray with the transposedArray to be used for later
		for(nullInc = 0; nullInc <= vertices ; ++nullInc){
			verticesArray[nullInc] = transposeArray[nullInc];
		}
		
		// Prints out the entire transposed graph
		printf("\nTranspose of previous Graph.\n");
		printGraph(verticesArray, vertices);
		
		// Checks if the graph has any cycles
		int cycleValidate = hasCycle(verticesArray, vertices);
		// If no cycle, print no cycle, else prints where the cycle starts
		if(verticesArray[cycleValidate] == 0) cycleValidate = 0;
		(cycleValidate == 0) ? printf("\nGraph contains no cycles.\n") : printf("\nGraph contains a cycle starting at vertex : %d.\n", cycleValidate);

	}
		
	printf("\n");
	
	return 0;
}

void printGraph(IntList verticesArray[], int vertices) {
	// Prints out the entire graph
	int printCounter = 1;
	for(; printCounter <= vertices ; ++ printCounter) {
		// Checks for if there is an edges in verticesArray[printCounter] if there isn't, then
		// 	it will print NULL.
		if(verticesArray[printCounter] == 0) {
			printf("%d\t[NULL", printCounter);
		} else {
			// If there are edges associated with the vertex, then it will scan through the entire
			// 	vertex at printCounter and print out all the edges associated at that vertex.
			printf("%d\t[%d", printCounter, intFirst(verticesArray[printCounter]));
			IntList tempList = intRest(verticesArray[printCounter]);
			while(tempList) {
				printf(", %d", intFirst(tempList));
				tempList = intRest(tempList);
			}
		}
		// End of the vertex, will go to the next vertex if there exists one
		printf("]\n");
	}
}
