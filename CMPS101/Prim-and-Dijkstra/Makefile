# ===================================================================
# Jason Ou (jaou - 1385128)
# Date: 3 June 2015
# CS101 Spring 2015
# Makefile

# Makefile for pa04.

# See Cmake/Makefile for a complete commented sample Makefile,
# illustrating separate compilation and dependencies.

# Running "make" can generate many error messages.  Do
#	make intList.o >& make.log
# to compile intList.c and cause the errors to go into the file make.log.
# When "make" finishes you can read the file with "view", "more", or "less".
#
# Many later error messages can be meaningless because they were caused
# by an earlier error.  Always try to correct errors in order.
#
# Do 
#       make graph04 >& make.log
# to create the executable file graph04.
#
# ===================================================================

CC     = gcc
CFLAGS = -g -std=c99 -Wall -O0

graph04:  graph04.o  edgeList.o  loadWgtGraph.o  minPQ.o
	${CC}  -o  graph04  graph04.o  edgeList.o  loadWgtGraph.o  minPQ.o

graph04.o:  graph04.c
	${CC}  -c  ${CFLAGS}  graph04.c

edgeList.o:  edgeList.c  edgeList.h
	${CC}  -c  ${CFLAGS}  edgeList.c

loadWgtGraph.o:  loadWgtGraph.c  loadWgtGraph.h
	${CC}  -c  ${CFLAGS}  loadWgtGraph.c
	
minPQ.o:  minPQ.c  minPQ.h
	${CC}  -c  ${CFLAGS}  minPQ.c
	
testp:
	graph04 -P 1 input3.in
	
testd:
	graph04 -D 1 input3.in
	
clean:
	rm  graph04  edgeList.o  graph04.o  loadWgtGraph.o  minPQ.o

submit:
	submit cmps101-avg.s15 pa04 graph04.c edgeList.c edgeList.h Makefile README minPQ.h minPQ.c loadWgtGraph.h loadWgtGraph.c input1.in input2.in input3.in
