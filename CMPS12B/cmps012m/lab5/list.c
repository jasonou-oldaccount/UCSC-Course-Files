/**
* Name: Jason Ou
* Class: CMPS012M
* Date: November 16, 2014
* Filename: list.c
* Description: Implementation of Singly Linked List in C
*/

#include <stdio.h>
#include <stdlib.h>

// A node in a singly-linked list
struct node {
    int value;
    struct node *next;
} *head = NULL;

// Insert a value into linked list
void list_insert(int v) {
    struct node *n;
    n = (struct node *)malloc(sizeof(struct node));
    n->value = v;
    n->next = head;
    head = n;
}

// Insert two values at once into linked list
void list_insert2(int a, int b) {
    struct node *u, *v;
	u = (struct node *)malloc(sizeof(struct node));
	v = (struct node *)malloc(sizeof(struct node));
	u->value = a;
	u->next = v;
	v->value = b;
	v->next = head;
	
	head = u;
}

// Remove an element from linked list
void list_remove(int v) {
    struct node *n = head;
	
    while(n && n->next && n->next->value != v) {
        n = n->next;
    }
    if(n && n->next && n->next->value == v) {
		struct node *temp = n->next;
        n->next = n->next->next;
		free(temp);
    }
}

// Print out all values in linked list
void list_printall(void) {
    struct node *p = head;
	
    while(p) {
        printf("%d ", p->value);
        p = p->next;
    }
	printf("\n");
}

// Deallocate all memory used in linked list
void list_destroy(void) {
	struct node *temp = head;

	while(temp) {
		head = head->next;
		free(temp);
		temp = head;
	}
}

int main(int argc, char *argv[]) {
    printf("Test Linked Lists\n");
    list_printall();
	list_insert(42);
	list_insert2(17, 10);
	list_insert(18);
	list_remove(10);
	list_printall();
	list_destroy();
    return 0;
}