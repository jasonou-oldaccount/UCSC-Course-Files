########################################
# Name: Jason Ou
# Class: CMPS012M
# Date: October 16, 2014
# Filename: Makefile
# Description: Makefile used to manage class files
########################################

JAVASRC = greet.java greetings.java
SOURCES = README makefile ${JAVASRC}
CLASSES = greet.class greetings.class

runGreet: ${JAVASRC}
	javac -Xlint ${JAVASRC}
	java greet

runGreetings: ${JAVASRC}
	javac -Xlint ${JAVASRC}
	java greetings

clean:
	rm ${CLASSES}

.PHONY: clean runGreet runGreetings
