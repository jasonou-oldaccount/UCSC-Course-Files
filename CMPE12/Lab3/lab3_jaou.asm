; JASON OU (1385128)
; jaou@ucsc.edu
; Partner: Andy Wang
; Section: 6
; TA: Daphene Gorman
; 9 FEB 2015
; lab3_jaou.asm
;
; This program takes in two user inputs and does calculations with it.

; The code will begin in memory at the address
; specified by .orig <number>.

	.ORIG   x3000

START:

;clear all registers that we may use
	AND	R0, R0, 0 ;Clears R0
	AND	R1, R0, 0 ;Clears R1
	AND	R2, R0, 0 ;Clears R2
	AND	R3, R0, 0 ;Clears R3
	AND	R4, R0, 0 ;Clears R4

;print out a greeting
	LEA	R0, GREETING
	PUTS

;prompts user for first input
	LEA	R0, ENTERNUMBER
	PUTS

;get a user-entered character (result in R0)
;echo it back right away (otherwise it isn't visible)
	GETC
	PUTC

;store entered string (otherwise it may be overwritten) then converts it from ascii to decimal
	ST	R0, USERINPUT
	LD	R1, USERINPUT
	LD	R2, ASCII
	ADD	R3, R1, R2
	ST	R3, USERINPUT

;prompts user for second input and prints it back out
	LEA	R0, ENTERNUMBER2
	PUTS

;get a user-entered character (result in R0)
;echo it back right away (otherwise it isn't visible)
	GETC
	PUTC

;store entered string (otherwise it may be overwritten) then converts it from ascii to decimal
	ST	R0, USERINPUT2
	LD	R1, USERINPUT2
	LD	R2, ASCII
	ADD	R3, R1, R2
	ST	R3, USERINPUT2

;CALLS SUBTRACTION SUBROUTINE
	JSR	SUBTRACT

;CALLS MULTIPLICATION SUBROUTINE
	JSR	MULTIPLICATION

;CALLS DIVISION SUBROUTINE
	JSR	DIVISION

;reached end of code, tells user the calculations are complete
	LEA	R0, CALCFINISHED
	PUTS

;stop the processor
	HALT

;Subtraction subroutine

SUBTRACT
	AND	R1, R1, 0 ;resets R0
	AND	R2, R2, 0 ;resets R1
	AND	R3, R3, 0 ;resets R3
	LD	R1, USERINPUT ;loads first user input
	LD	R2, USERINPUT2 ;loads second user input
	NOT	R3, R2 ;does the not of R2 and stores in R3
	ADD	R3, R3, #1 ;adds one to R3 to get the 2s comp of user input two
	ADD	R3, R1, R3 ;adds user input one and twos comp of user input two
	STI	R3, SUBTRACTED ;stores the output into the variable SUBTRACTED
	RET ;End of subroutine

;Multiplication subroutine

MULTIPLICATION
	AND	R1, R0, 0 ;resets R0
	AND	R2, R0, 0 ;resets R1
	AND	R3, R0, 0 ;resets R3
	LD	R1, USERINPUT ;loads first user input
	LD	R2, USERINPUT2 ;loads second user input
ITSELF	ADD	R3, R3, R1 ;Adds R1 to itself
	ADD	R2, R2, #-1 ;subtracts one from user input two each time R1 is added to itself
	BRp	ITSELF ;if the previous number is positive, loops back to ITSELF
	STI	R3, MULTIPLIED ;stores final value into the variable MULTIPLIED
	RET ;End of subroutine

;DIVISION subroutine

DIVISION
	AND	R1, R0, 0 ;resets R0
	AND	R2, R0, 0 ;resets R1
	AND	R3, R0, 0 ;resets R3
	LD	R1, USERINPUT ;loads first user input
	LD	R2, USERINPUT2 ;loads second user input
	NOT	R3, R2 ;does the not of R2 and stores in R3
	ADD	R3, R3, #1 ;adds one to R3 to get the 2s comp of user input two
SUBA	ADD	R4, R4, #1 ;increments each time subtract is looped
	ADD	R1, R1, R3 ;subtracts R1 from R3 and saves value to R3
	BRp	SUBA ;if the previous number is positive, loops back to SUBA (subtract again)
	BRz	DONE ;if the previous number is zero, goes to DONE and stores value
	ADD	R4, R4, #-1 
	ADD	R1, R1, R2 ;if the previous value is negative, this calculates the remainder
DONE	STI	R4, DIVIDED ;stores the remainder into the variable DIVIDED
	STI	R1, REMAINDER ;stores the remainder into the variable REMAINDER
	RET ;End of subroutine

;data declarations follow below

;strings
GREETING	.STRINGZ	"\nWelcome to the Jason Ou's LC-3 calculator."
ENTERNUMBER	.STRINGZ	"\nPlease enter a single digit number : "
ENTERNUMBER2	.STRINGZ	"\nPlease enter another single digit number : "
CALCFINISHED	.STRINGZ	"\nCalculations Complete."

;variables
USERINPUT	.FILL	0
USERINPUT2	.FILL	0
SUBTRACTED	.FILL	x3100 ;stores SUBTRACTED numbers in x3100
MULTIPLIED	.FILL	x3101 ;stores MULTIPLIED numbers in x3101
DIVIDED		.FILL	x3102 ;stores DIVIDED numbers in x3102
REMAINDER	.FILL	x3103 ;stores REMAINDER numbers in x3103
ASCII		.FILL	#-48

;end of code
	.END