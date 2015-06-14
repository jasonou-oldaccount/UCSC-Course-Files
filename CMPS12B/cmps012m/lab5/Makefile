########################################
# Name: Jason Ou
# Class: CMPS012M
# Date: November 16, 2014
# Filename: Makefile
# Description: Makefile used to manage class files
########################################

CC = gcc
CFLAGS = -std=c99 -Wall -Werror -Wfatal-errors -g
OBJS = list.o
OUTPUT = list

${OUTPUT}: ${OBJS}
	${CC} $< -o ${OUTPUT}

clean:
	rm -f list

spotless:
	rm -f ${OUTPUT} ${OBJS}

test: ${OUTPUT}
	valgrind --leak-check=full ./${OUTPUT}

submit:
	submit cmps12b-nojw.f14 lab5 README BUGS Makefile list.c
	
.PHONY: clean spotless test submit