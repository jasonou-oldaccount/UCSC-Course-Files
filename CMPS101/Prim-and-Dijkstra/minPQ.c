// Jason Ou (jaou - 1385128)
// Date: 3 June 2015
// CS101 Spring 2015
// minPQ.c

// Include Libraries
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

// Include Header Files
#include "minPQ.h"

struct MinPQNode {
	int numVertices, numPQ, minVertex, *status, *parent;
	double *fringeWgt, oo;
};

int isEmptyPQ(MinPQ pq) {
	if(pq -> numPQ == 0) return 1;
	return 0;
}

int getMin(MinPQ pq){
	if(pq -> minVertex == -1) {
		double minWgt;
		minWgt = pq -> oo;
		
		int inc = 0;
		for( ; ++inc <= pq -> numVertices ; ) {
			if(pq -> status[inc] == FRINGE){
				if(pq -> fringeWgt[inc] < minWgt){
					pq -> minVertex = inc;
					minWgt = pq -> fringeWgt[inc];
				}
			}
		}
	}
	
	return pq -> minVertex;
}

int getStatus(MinPQ pq, int id) {
	return pq -> status[id];
}

int getParent(MinPQ pq, int id){
	return pq -> parent[id];
}

double getPriority(MinPQ pq, int id){
	return pq -> fringeWgt[id];
}

void delMin(MinPQ pq) {
	if(!isEmptyPQ(pq)) {
		int oldMin = getMin(pq);
		pq -> status[oldMin] = INTREE;
		pq -> minVertex = -1;
		pq -> numPQ--;
	}
}

void insertPQ(MinPQ pq, int id, double priority, int par) {
	pq -> parent[id] = par;
	pq -> fringeWgt[id] = priority;
	pq -> status[id] = FRINGE;
	pq -> minVertex = -1;
	pq -> numPQ++;
}

void decreaseKey(MinPQ pq, int id, double priority, int par) {
	pq -> parent[id] = par;
	pq -> fringeWgt[id] = priority;
	pq -> minVertex = -1;
}

MinPQ createPQ(int n, int status[], double priority[], int parent[]) {
	MinPQ pq = malloc(sizeof(MinPQ));
	pq -> parent = parent;
	pq -> fringeWgt = priority;
	pq -> numVertices = n;
	pq -> numPQ = 0;
	pq -> minVertex = -1;
	pq -> oo = INFINITY;
	pq -> status = status;
	
	int inc = 0;
	for( ; ++inc <= n ; ) pq -> status[inc] = UNSEEN;
	
	return pq;
}
