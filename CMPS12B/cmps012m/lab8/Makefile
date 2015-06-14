########################################
# Name: Jason Ou
# Class: CMPS012M
# Date: December 12, 2014
# Filename: Makefile
# Description: Makefile used to manage class files
########################################

MKFILE    = Makefile

GCC      = gcc -g -O0 -Wall -Wextra -std=gnu99

CSOURCE   = heap.c
OBJECTS   = ${CSOURCE:.c=.o}
EXECBIN   = heap
SOURCES   = ${CSOURCE} ${MKFILE}

all : ${EXECBIN}

${EXECBIN} : ${OBJECTS}
	${GCC} -o $@ ${OBJECTS}

%.o : %.c
	${GCC} -c $<

clean :
	rm -f ${OBJECTS} core

spotless : clean
	rm -f ${EXECBIN}

test : ${EXECBIN}
	./${EXECBIN} 10 test.txt

submit:
	submit cmps12b-nojw.f14 lab8 Makefile heap.c README
	
.PHONY: all clean spotless test submit
