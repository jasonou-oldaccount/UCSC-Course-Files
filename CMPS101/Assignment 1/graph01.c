// Jason Ou (1385128)
// Date: 15 April 2015
// CS101 Spring 2015
// graph01.c

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>

#include "intList.h"

int main(int argc, char *argv[]) {
	
	// Variable Declarations:
	// 	number of vertices, start edges, end edge, input line, edge counter, file line counter
	int vertices, startE, endE, eCounter = 0, lineCounter = 1;
	char line[5];
	
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
	} else {
		// file doesn't exist
		printf("File %s does not exists.\n", argv[1]);
		exit(1);
	}
	
	// Reads in the first line of the file to find out the number of vertices
	fgets(line, 10, fr);
	sscanf(line, "%d", &vertices);
	
	// Creates an array with the same number of elements as the number of vertices
	IntList verticesArray[vertices];
	int nullInc = 0;
	// Sets all the values in the array to be 0(NULL).
	for(; nullInc < vertices ; ++nullInc){
		verticesArray[nullInc] = 0;
	}
	
	// Scans in the entire file and assigns the values in the file a location in the graph
	while(fgets(line, 10, fr) != NULL) {
		sscanf(line, "%d %d", &startE, &endE);
		++lineCounter;
		// Checks for invalid file line input and if a value is trying to make an edge with itself
		if((startE > vertices) || (endE > vertices) || (startE == 0) || (endE == 0) || (startE == endE)) {
			continue;
		}
		bool repeatChecker = false;
		// Inital Insert of the first item into the verticesArray
		if(verticesArray[startE-1] == 0) {
			verticesArray[startE-1] = intCons(endE, 0);
		} else {
			// If item already exists in the IntList, then it will create a new IntListNode attached to the old
			// 	IntList
			IntList tempList = intCons(intFirst(verticesArray[startE-1]),intRest(verticesArray[startE-1]));
			
			// Checks for repeats
			IntList tempCheckRepeat[vertices];
			tempCheckRepeat[startE-1] = intCons(endE, tempList);
			IntList tempChecker = intRest(tempCheckRepeat[startE-1]);
			while(tempChecker) {
				if((intFirst(tempChecker) == endE)) { 
					repeatChecker = true; 
				}
				tempChecker = intRest(tempChecker);
			}
			if(repeatChecker == true) continue;
			verticesArray[startE-1] = intCons(endE, tempList);
		}
		// If the line reads in successfully, then it will increment the edges
		if(repeatChecker == true) continue;
		++eCounter;
	}
	
	// Prints out the number of vertices and number of edges
	printf("\nNumber of vertices: %d.", vertices);
	printf("\nNumber of edges: %d.\n\n", eCounter);
	
	// Prints out the entire graph
	int printCounter = 0;
	for(; printCounter < vertices ; ++ printCounter) {
		// Checks for if there is an edges in verticesArray[printCounter] if there isn't, then
		// 	it will print NULL.
		if(verticesArray[printCounter] == 0) {
			printf("%d\t[NULL", printCounter+1);
		} else {
			// If there are edges associated with the vertice, then it will scan through the entire
			// 	vertice at printCounter and print out all the edges assocatiated at that vertice.
			printf("%d\t[%d", printCounter+1, intFirst(verticesArray[printCounter]));
			IntList tempList = intRest(verticesArray[printCounter]);
			while(tempList) {
				printf(", %d", intFirst(tempList));
				tempList = intRest(tempList);
			}
		}
		// End of the vertice, will go to the next vertice if there exists one
		printf("]\n");
	}
	
	// Closes the file then exits the program
	printf("\n");
	fclose(fr);
	
	return 0;
}
