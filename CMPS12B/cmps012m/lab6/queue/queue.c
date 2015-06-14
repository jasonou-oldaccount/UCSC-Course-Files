/**
* Name: Jason Ou
* Class: CMPS012M
* Date: November 24, 2014
* Filename: queue.c
* Description: Implementation of queue in C
*/

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "queue.h"

#define STUBPRINTF(...) fprintf(stderr, __VA_ARGS__);

/* Internal implementation definitions */
struct queue_node {
   queue_item_t item;
   struct queue_node *link;
};

typedef struct queue_node queue_node;

struct queue {
   queue_node *front;
   queue_node *rear;
};

/* Functions */

queue *queue_new(void) {
   struct queue *newQueue = malloc(sizeof(struct queue));
   assert(newQueue != NULL);
   newQueue->front = NULL;
   newQueue->rear = newQueue->front;
   return newQueue;
}

void queue_free(queue *this) {
   assert(queue_isempty(this));
   free(this);
}

void queue_insert(queue *this, queue_item_t item) {
   struct queue_node *newNode = malloc(sizeof(struct queue_node));
   assert(newNode != NULL);
   newNode->item = item;
   newNode->link = NULL;
   if(!queue_isempty(this)) {
   	this->rear->link = newNode;
   	this->rear = newNode;
   } else {
   	this->front = newNode;
   	this->rear = newNode;
   }
}

queue_item_t queue_remove(queue *this) {
	if(queue_isempty(this)){
		queue_free(this);
		return NULL;
	} else {
		queue_node *variable = this->front;
		queue_item_t ivari = variable->item;
		this->front = this->front->link;
		free(variable);
		return ivari;
	}
}

bool queue_isempty(queue *this) {
   return this->front == NULL;
}
