########################################
# Name: Jason Ou
# Class: CMPS012B
# Date: December 8, 2014
# Filename: Makefile
# Description: Makefile used to manage class files
########################################

JAVASRC    = cyoa.java
SOURCES    = ${JAVASRC} Makefile
ALLSOURCES = ${SOURCES}
MAINCLASS  = cyoa
CLASSES    = ${patsubst %.java, %.class, ${JAVASRC}}

all: ${CLASSES}

%.class: %.java
	javac -Xlint $<

clean:
	rm -f *.class

test: all
	java cyoa demo.adventure

fail:
	clear
	java cyoa hello
	java cyoa hello hello
	java cyoa
	java cyoa empty.adventure
	java cyoa two.adventure
	
submit:
	submit cmps12b-nojw.f14 asg4 README design.txt Makefile demo.adventure cyoa.java
	
.PHONY: clean all test fail submit