// Jason Ou (1385128)
// Date: 15 April 2015
// CS101 Spring 2015
// intList.c

#include "intList.h"
#include <stdlib.h>
#include <stdio.h>

// a node with int value and IntList next
typedef struct IntListNode {
	int value;
	IntList next;
} IntListNode;

// Returns the value of oldL
int intFirst(IntList oldL) {
	return oldL->value;
}

// returns the IntList next to oldL
IntList intRest(IntList oldL) {
	return oldL->next;
}

// Creates a new list connected to the old list
IntList intCons(int newE, IntList oldL) {
	IntList newL = malloc(sizeof(IntList));
	newL->value = newE;
	newL->next = oldL;
	
	return newL;
}
