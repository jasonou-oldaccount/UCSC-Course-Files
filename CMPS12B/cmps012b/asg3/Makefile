########################################
# Name: Jason Ou
# Class: CMPS012B
# Date: November 19, 2014
# Filename: Makefile
# Description: Makefile used to manage class files
########################################

JAVASRC    = xref.java auxlib.java Tree.java Queue.java
SOURCES    = ${JAVASRC} Makefile
ALLSOURCES = ${SOURCES}
MAINCLASS  = xref
CLASSES    = ${patsubst %.java, %.class, ${JAVASRC}}

all: ${CLASSES}

%.class: %.java
	javac -Xlint $<

clean:
	rm -f *.class

test: all
	java xref test.txt
	
debug: all
	java xref -d test.txt

.PHONY: clean all test debug
