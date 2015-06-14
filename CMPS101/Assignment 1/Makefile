# ===================================================================
# Jason Ou (1385128)
# Date: 15 April 2015
# CS101 Spring 2015
# Makefile

# Makefile starter for pa01.

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
#       make graph01 >& make.log
# to create the executable file graph01.
#
# ===================================================================

CC     = gcc
CFLAGS = -g -Wall -O0

intList.o:	intList.c  intList.h
	${CC}  -c  ${CFLAGS}  intList.c

graph01.o:	graph01.c  intList.h
	${CC}  -c  ${CFLAGS}  graph01.c

graph01:	graph01.o  intList.o
	${CC}  -o  graph01  graph01.o intList.o

testall:
	$ ./graph01 testEmpty.in
	$ ./graph01 testMultiNodes.in
	$ ./graph01 testOneNode.in
	$ ./graph01 testRepeatNumbers.in
	$ ./graph01 testZero.in
	$ ./graph01 testInvalidEdges.in

clean:
	$ rm graph01 graph01.o intList.o
	
submit:
	$ submit cmps101-avg.s15 pa01 graph01.c intList.c intList.h Makefile README testEmpty.in testMultiNodes.in testOneNode.in testInvalidEdges.in testRepeatNumbers.in testZero.in