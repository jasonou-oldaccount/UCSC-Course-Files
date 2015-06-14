// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// edgeList.c

// Include Libraries
#include <stdlib.h>

// Include Header Files
#include "edgeList.h"

struct EdgeListNode {
	EdgeInfo value;
	EdgeList next;
};

const EdgeList edgeNil = NULL;

EdgeInfo edgeFirst(EdgeList oldL){
	return oldL -> value;
}

EdgeList edgeRest(EdgeList oldL){
	return oldL -> next;
}

EdgeList edgeCons(EdgeInfo newE, EdgeList oldL){
	EdgeList newEdgeList = malloc(sizeof(EdgeList));
	newEdgeList -> value = newE;
	newEdgeList -> next = oldL;
	return newEdgeList;
}
