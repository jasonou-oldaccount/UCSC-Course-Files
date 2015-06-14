# ===================================================================
# Jason Ou (jaou - 1385128)
# Date: 7 May 2015
# CS101 Spring 2015
# Makefile

# Makefile starter for pa02.

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
#       make graph02 >& make.log
# to create the executable file graph02.
#
# ===================================================================

CC     = gcc
CFLAGS = -g -Wall -O0
	
intList.o:	intList.c  intList.h
	${CC}  -c  ${CFLAGS}  intList.c

graph02.o:	graph02.c  intList.h
	${CC}  -c  ${CFLAGS}  graph02.c

loadGraph.o:	loadGraph.c  intList.h
	${CC}  -c  ${CFLAGS} loadGraph.c

graph02:	graph02.o  intList.o loadGraph.o
	${CC}  -o  graph02  graph02.o intList.o loadGraph.o

testTransposeAndCycle:
	$ ./graph02 testTransposeAndCycle.in

testall:
	$ ./graph02 testEdgeItself.in
	$ ./graph02 testEmpty.in
	$ ./graph02 testMultiNodes.in
	$ ./graph02 testOneNode.in
	$ ./graph02 testRepeatNumbers.in
	$ ./graph02 testZero.in
	$ ./graph02 testInvalidEdges.in
	$ ./graph02 testTransposeAndCycle.in

clean:
	$ rm graph02 graph02.o intList.o loadGraph.o
	
submit:
	$ submit cmps101-avg.s15 pa02 graph02.c intList.h intList.c loadGraph.c loadGraph.h Makefile README testEmpty.in testMultiNodes.in testOneNode.in testInvalidEdges.in testRepeatNumbers.in testZero.in testEdgeItself.in testTransposeAndCycle.in