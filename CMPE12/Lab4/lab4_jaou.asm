; JASON OU (1385128)
; jaou@ucsc.edu
; Partner: Calvin Pham
; Section: 6
; TA: Daphene Gorman
; 23 FEB 2015
; lab4_jaou.asm

; This program converts two two-digit numbers into 
; half-precision floating-point numbers and multiplies them


; The code will begin in memory at the address
; specified by .orig <number>.

	.ORIG   x3000

START:

; Clear all registers that we may use

	AND	R0, R0, 0
	AND	R1, R0, 0
	AND	R2, R0, 0
	AND	R3, R0, 0
	AND	R4, R0, 0
	AND	R5, R0, 0
	AND	R6, R0, 0

REINITIALIZE	.FILL	1
NEGATIVE1	.FILL	1
NEGATIVE2	.FILL	1

	LD	R1, REINITIALIZE		;Reinitialize labels NEGATIVE1 and NEGATIVE2 
	ST	R1, NEGATIVE1			;so they can determine whether negative inputs
	ST	R1, NEGATIVE2			;were entered

FIRSTGREET	.FILL	x3123			;Address of first character in first string
SECONDGREET	.FILL	x313E			;Address of second character in second string

; Print out the first greeting

	LD	R0, FIRSTGREET			;Load R0 with the address of the first character of first greeting so the string can be printed
	PUTS

; Get ten's place digit of first user input
; Echo it back right away
 
GETIN1:
	
	GETC					;R0 now holds either the ASCII value of the "-" key or the ten's place digit of the first user input
	PUTC
	LD	R1, NEGCHECK			;Load R1 with -45 to check if a "-" was entered	
	LD	R2, NEGOFFSET			;Load R2 with -3 to finish fixing the ASCII offset if the "-" key was not entered
	ADD	R0, R0, R1			;Add -45 to the value in R0 to perform the check for a "-" key
	BRp	SKIPOV1				;If the result is positive, a "-" key was not entered, branch to SKIPOV1
	ST	R0, NEGATIVE1			;If the result is not positive (i.e 0), store the "fact" that a "-" key was entered at location labeled NEGATIVE1
	
	BRz	GETIN1				;If a "-" was indeed the first character entered, loop back to GETIN1 to get the ten's 
						;place digit of the first user input

SKIPOV1:

	ADD	R0, R2, R0			;If a digit was entered, add the value currently in R0 to -3 to finish fixing 
						;the ASCII offset since it was previously added to -45
	
	ST	R0, TENSPLACE1			;Store the ten's place digit of the first input in the location labeled TENSPLACE1

; Get one's digit of the first user input
; Echo it back right away 

	GETC					;R0 now holds the one's place digit of the first user input
	PUTC
	LD	R1, OFFSET			;Load R1 with -48 to fix the ASCII offset
	ADD	R0, R0, R1			;Add value in R0 to -48 to obtain actual integer value
	ST	R0, ONESPLACE1			;Store the one's place digit of the first user input in the location labeled ONESPLACE1

; Print out the second greeting

	LD	R0, SECONDGREET			;Load R0 with the address of the first character of second greeting so the string can be printed
	PUTS

; Get ten's place digit of first user input
; Echo it back right away
GETIN2:

	GETC					;R0 now holds either the ASCII value of the "-" key or the ten's place digit of the second user input
	PUTC
	LD	R1, NEGCHECK			;Load R1 with -45 to check if a "-" was entered		
	LD	R2, NEGOFFSET			;Load R2 with -3 to finish fixing the ASCII offset if the "-" key was not entered
	ADD	R0, R0, R1			;Add -45 to the value in R0 to perform the check for a "-" key
	BRp	SKIPOV2				;If the result is positive, a "-" key was not entered, branch to SKIPOV1
	ST	R0, NEGATIVE2			;If the result is not positive (i.e 0), store the "fact" that a "-" key was entered at location labeled NEGATIVE1

	BRz	GETIN2				;If a "-" was indeed the first character entered, loop back to GETIN1 to get the ten's place digit of the first user input
						;the ASCII offset since it was previously added to -45


SKIPOV2:	
	
	ADD	R0, R2, R0			;If a digit was entered, add the value currently in R0 to -3 to finish fixing
						;the ASCII offset since it was previously added to -45
						
	ST	R0, TENSPLACE2			;Store the ten's place digit of the second input in the location labeled TENSPLACE2

; Get one's digit of the first user input
; Echo it back right away 

	GETC					;R0 now holds the one's place digit of the first user input
	PUTC
	LD	R1, OFFSET			;Load R1 with -48 to fix the ASCII offset
	ADD	R0, R0, R1			;Add value in R0 to -48 to obtain actual integer value
	ST	R0, ONESPLACE2			;Store the one's place digit of the first user input in the location labeled ONESPLACE1

; Jump to subroutines to convert operands and product into half-precision floating point
	
	JSR	DETERMINEFIRSTINPUT		;Jump to the subroutine that will determine the integer value of the first input
	JSR	DETERMINESECONDINPUT		;Jump to the subroutine that will determine the integer value of the second input

	LD	R1, USERINPUT1			;Test to see if any of the two integers are zero
	BRz	INPUTISZERO			;If so, branch to DOZERO
	LD	R1, USERINPUT2
	BRz	INPUTISZERO
	BRp	NOTZERO

INPUTISZERO:
						;If any of the inputs are zero, unconditionally branch to DONE, and skip all subsequent JSR commands
	JSR	ZERO
	BRnzp	DONE

NOTZERO:

	JSR	CONVERTINPUT1			;Convert first input into half-precision floating point
	JSR	CONVERTINPUT2			;Convert second input into half-precision floating point	
	JSR 	CONPROSIGN			;Convert sign bit for the product of input1 and input2
	JSR	CONPROEXPO			;Convert exponents for the product of input1 and input2
	JSR	CONMANT				;Convert mantissas for the product of input1 and input2
	JSR	FINALPRODUCT			;Calculates the final product by adding the product conversions

DONE:

; Stop the processor
	HALT

;
; SUBROUTINES AND DATA DECLARATIONS FOLLOW BELOW
;

;This subroutine will store zero in location x3200 if any of the two inputs is zero

ZERO:
	AND 	R1, R0, 0
	STI	R1, STOREPRODUCT
	RET

;Subroutine to determine the integer value of the first input

DETERMINEFIRSTINPUT:	
		
	AND	R3, R0, 0						

	LD	R1, TENSPLACE1			;Load the ten's place digit of the first input into R1
	LD	R2, TENS			;Load the value +10 into R2 so it can be multiplied to the tens place digit
	LD	R4, ONESPLACE1			;Load the one's place digit of the first input into R4

	ADDAGAIN1:
	
	ADD	R3, R3, R1			;Add the ten's place digit to itself 10 times
	ADD	R2, R2, #-1			;Decrement 10 each time we do so, so we know how many times to repeat instruction
	BRp	ADDAGAIN1			;Execute the last two instructions as long as R2 is still positive
	ADD	R3, R3, R4			;After multiplying the ten's place digit by 10, add it to the one's place digit to obtain the integer value of the first input
	ST	R3, USERINPUT1			;Store the first input in the location labeled USERINPUT1
	LD	R1, NEGATIVE1			;Load R1 with NEGATIVE1 to test if the first input is negative
	BRz	INVERT1				;If the first input is negative, branch to INVERT1
	BRp	DONECONVERT1			;If the first input is positive, branch to DONECONVERT1 so its value can be stored in USERDEC1
	
	INVERT1:

	NOT	R3, R3				;INVERT1 will obtain the 2's complement of the integer before storing it
	ADD	R3, R3, 1
	ST	R3, USERDEC1
	RET

	DONECONVERT1:
	ST	R3, USERDEC1
	RET


; Subroutine to determine the integer value of the second input

DETERMINESECONDINPUT:

	AND	R3, R0, 0	

	LD	R1, TENSPLACE2			;Load the ten's place digit of the second input into R1	
	LD	R2, TENS			;Load the value +10 into R2 so it can be multiplied to the tens place digit
	LD	R4, ONESPLACE2			;Load the one's place digit of the second input into R4

ADDAGAIN2:
	ADD	R3, R3, R1			;Add the ten's place digit to itself 10 times
	ADD	R2, R2, #-1			;Decrement 10 each time we do so, so we know how many times to repeat instruction
	BRp	ADDAGAIN2			;Execute the last two instructions as long as R2 is still positive
	ADD	R3, R3, R4			;After multiplying the ten's place digit by 10, add it to the one's place digit to obtain the integer value of the second input
	ST	R3, USERINPUT2			;Store the first input in the location labeled USERINPUT1
	LD	R1, NEGATIVE2			;Load R1 with NEGATIVE2 to test if the first input is negative
	BRz	INVERT2				;If the first input is negative, branch to INVERT2
	BRp	DONECONVERT2			;If the first input is positive, branch to DONECONVERT2 so its value can be stored in USERDEC2
	
	INVERT2:

	NOT	R3, R3				;INVERT2 will obtain the 2's complement of the integer before storing it
	ADD	R3, R3, 1
	ST	R3, USERDEC2
	RET

	DONECONVERT2:
	ST	R3, USERDEC2
	RET

; Subroutine to convert the first input into half-precision floating point

CONVERTINPUT1:
	
	AND	R3, R0, 0		
	AND	R4, R0, 0			

	LD 	R1, USERINPUT1			;Load integer value of first user input into R1
	LD	R2, BITMASK1			;Load bit mask with decimal value 1024 into R2, it will be used to shift the mantissa into the correct position 
	LD	R5, FIXERUPPER			;Load the decimal value 25 into R5, will be used to find the exponent

LOOP1:
	ADD 	R1, R1, R1			;Shift the mantissa over one bit position to the left
	ADD	R3, R3, 1			;Increment R3 every time we perform a bit shift
	AND	R4, R1, R2			;AND the current bit pattern with the bit mask to see when we should stop shifting
	BRz	LOOP1				;As long as the AND operation produces a zero, we still need to shift the mantissa bits to the left
	
	ST	R1, USERMANT1			;Stores the input1's mantissa with leading 1

	NOT	R2, R2				;Once the mantissa is in the correct position, invert the bit mask
	ADD	R2, R2, 1			;and add it to the current bit pattern to eliminate the 
	ADD 	R1, R1, R2			;leading "1" that will not be a part of the mantissa
	
	AND	R2, R0, 0			;Clear R2 and R4 so they can be used in the operations to come				
	AND 	R4, R0, 0
			
	LD	R2, BITMASK2			;Load the bit mask with the decimal value 16384 into R2, will be be used to shift the exponent into the correct position
	NOT	R3, R3				;Obtain the value of the exponent by subtracting the number of times we shifted the mantissa(R3) to the left from 25(R5)
	ADD	R3, R3, 1
	ADD	R3, R5, R3

LOOP2:
	ADD 	R3, R3, R3			;Shift the value of the exponent over one bit position to the left
	AND	R4, R3, R2			;AND the bit pattern in R3 with the bit mask in R2 to see when we should stop shifting
	BRz	LOOP2				;As long as the AND operation produces a zero, we still need to shift the exponent bits to the left


	ADD	R1, R1, R3			;Once the exponent bits are in the correct position, add them to R1, which has the mantissa of the first input 
						;already in the correct position
	
	LD	R3, BITMASK3			;Load the bit mask with the decimal value 32768 into R3, it will be used to add a sign bit if one is needed
	LD	R2, NEGATIVE1			;Test to see if a "-" key was entered
	BRp	STOREINPUT1			;If a "-" key was not entered, branch to STORE1
	ADD	R1, R3, R1			;If a "-" key was entered, indicating a negative number, a sign bit of 1 will be assigned to the current bit pattern

STOREINPUT1:
	LD	R5, BITMASK4
	AND	R5, R1, R5
	ST	R5, USER1EXPO
	AND	R4, R1, R3
	ST	R4, USER1SIGNBIT
	ST	R1, USERINPUT1			;Store the half-precision floating point representation of the first input in location w/ label USERINPUT1
	RET


; Subroutine to convert the second input into half-precision floating point

CONVERTINPUT2:
	
	AND	R3, R0, 0	
	AND	R4, R0, 0			

	LD 	R1, USERINPUT2			;Load integer value of second user input into R1
	LD	R2, BITMASK1			;Load bit mask with decimal value 1024 into R2, it will be used to shift the mantissa into the correct position
	LD	R5, FIXERUPPER			;Load the decimal value 25 into R5, will be used to find the exponent

LOOP3:
	ADD 	R1, R1, R1			;Shift the mantissa over one bit position to the left
	ADD	R3, R3, 1			;Increment R3 every time we perform a bit shift
	AND	R4, R1, R2			;AND the current bit pattern with the bit mask to see when we should stop shifting
	BRz	LOOP3				;As long as the AND operation produces a zero, we still need to shift the mantissa bits to the left
	
	ST	R1, USERMANT2			;Stores the input2's mantissa with leading 1
	
	NOT	R2, R2				;Once the mantissa is in the correct position, invert the bit mask
	ADD	R2, R2, 1			;and add it to the current bit pattern to eliminate the 
	ADD 	R1, R1, R2			;leading "1" that will not be a part of the mantissa
						
	AND	R2, R0, 0			;Clear R2 and R4 so they can be used in the operations to come
	AND 	R4, R0, 0			
	
	LD	R2, BITMASK2			;Load the bit mask with the decimal value 16384 into R2, will be be used to shift the exponent into the correct position
	NOT	R3, R3				;Obtain the value of the exponent by subtracting the number of times we shifted the mantissa(R3) to the left from 25(R5)	
	ADD	R3, R3, 1
	ADD	R3, R5, R3
	
LOOP4:
	ADD 	R3, R3, R3			;Shift the value of the exponent over one bit position to the left
	AND	R4, R3, R2			;AND the bit pattern in R3 with the bit mask in R2 to see when we should stop shifting
	BRz	LOOP4				;As long as the AND operation produces a zero, we still need to shift the exponent bits to the left

	ADD	R1, R1, R3			;Once the exponent bits are in the correct position, add them to R1, which has the mantissa of the second input
						;already in the correct position			
	AND	R2, R0, 0			;Clear R2 and R3 so they can be used in the operations to come
	AND	R3, R0, 0
	LD	R3, BITMASK3			;Load the bit mask with the decimal value 32768 into R3, it will be used to add a sign bit if one is needed
	LD	R2, NEGATIVE2			;Test to see if a "-" key was entered
	BRp	STOREINPUT2			;If a "-" key was not entered, branch to STORE1
	ADD	R1, R3, R1			;If a "-" key was entered, indicating a negative number, a sign bit of 1 will be assigned to the current bit pattern

STOREINPUT2:
	LD	R5, BITMASK4
	AND	R5, R1, R5
	ST	R5, USER2EXPO
	AND	R4, R1, R3
	ST	R4, USER2SIGNBIT
	ST	R1, USERINPUT2			;Store the half-precision floating point representation of the second input in location w/ label USERINPUT2
	RET

;
; THE FOLLOWING SUBROUTINES WILL BE USED TO OBTAIN THE PRODUCT OF THE HPFP REPRESENTATION OF THE TWO INPUTS
;


;CONPROSIGN will calculate the sign bit of the 
;HPFP representation of the product

CONPROSIGN:					
	AND	R3, R0, 0

	LD	R1, USER1SIGNBIT		;Load R1 and R2 with the sign bit of the first and second user inputs respectively
	LD	R2, USER2SIGNBIT

	ADD	R3, R1, R2			;Add the sign bits of the two inputs
	ST	R3, PROSIGNBIT			;Store the result in the memory location labeled PROSIGNBIT
	
	RET

;CONPROEXPO will calculate the exponent bits of the 
;HPFP representation of the product

CONPROEXPO:					
	AND	R4, R0, 0			;Clear R4 so it can be used	

	LD	R1, USER1EXPO			;Load R1, R2 with the exponent bits from the first and second inputs
	LD	R2, USER2EXPO
	LD	R3, BITMASK5			;Load R3 with BITMASK5, it will be used to calculate the exponent bits of the product

	ADD	R4, R1, R2			;Add the exponent bits from the two inputs
	ADD	R4, R4, R3			;Add the result with BITMASK5 to obtain the exponent bits of the product, this operation takes care of possible overflow

	ST	R4, PROUSEREXPO			;Store the exponent bits of the product in the location labeled PROUSEREXPO
	
	RET

;CONMANT will calculate the mantissa bits of the 
;HPFP representation of the product

CONMANT:					
	AND	R3, R0, 0			;Clears R3	
	AND	R4, R0, 0			;Clears R4

	LD	R1, USERMANT1			;Loads User mantissa 1
	LD	R2, DIVBY2			;Loads the number -2
	LD	R5, DIVCOUNTER			;Loads the division counter (-4)
	
DIVLOOP1:

	ADD	R3, R3, 1			;This subroutine shifts the mantissa of the first input to the right 4 times
	ADD	R1, R1, R2
	BRzp	DIVLOOP1
	ADD	R3, R3, -1
	ST	R3, USERMANT1
	AND	R1, R3, R3
	AND 	R3, R0, 0
	ADD	R5, R5, 1
	BRn	DIVLOOP1

	LD	R1, USERMANT2
	LD	R5, DIVCOUNTER
	
DIVLOOP2:

	ADD	R3, R3, 1			;This subroutine shifts the mantissa of the second input to the right 4 times
	ADD	R1, R1, R2
	BRzp	DIVLOOP2
	ADD	R3, R3, -1
	ST	R3, USERMANT2
	AND	R1, R3, R3
	AND 	R3, R0, 0
	ADD	R5, R5, 1
	BRn	DIVLOOP2

	LD	R1, USERMANT1
	LD	R2, USERMANT2	
	AND	R3, R3, 0

MULTLOOP:

	ADD	R3, R3, R1			;R3 will hold the product of the mantissas from the two inputs
	ADD 	R2, R2, -1			;Decrement the second user input so we know how many times to add first user input to itself		
	BRp	MULTLOOP			;As long as R4 is still positive, repeat the last two instructions

	AND	R5, R0, 0
	AND	R6, R0, 0
	LD	R1, BITMASK6

	AND	R5, R3, R1			;AND result with X2000
	BRz	SHIFTBACK

	ST	R3, USERMANTPRO			;When R4 is no longer positive, store the value in R2 to x3101
	AND	R3, R0, 0
	
	LD	R1, PROUSEREXPO			;ADD 1 TO EXPONENT if X2000 does not equal 0 else skipt to SHIFTBACK
	LD	R2, BITMASK1
	ADD	R1, R1, R2
	ST	R1, PROUSEREXPO

SHIFTBACK:
					
	LD	R1, USERMANTPRO			;Shifts the mantissa of the product all the way back to the correct position
	LD	R2, DIVBY2
	LD	R5, REINITIALIZE
	
DIVLOOP3:

	ADD	R3, R3, 1			;After multiplying the two shifted mantissas, shift the result back all the way to the right
	ADD	R1, R1, R2
	BRzp	DIVLOOP3			
	ADD	R3, R3, -1
	ST	R3, USERMANTPRO			;R3 will contain the the product of the the two mantissas shifted all the way to the right
	AND	R1, R3, R3			
	AND 	R3, R0, 0
	AND	R6, R5, R1
	BRz	DIVLOOP3


						
	SHIFTLEFT:

	LD	R2, BITMASK1			;Load bit mask with decimal value 1024 into R2, it will be used to shift the mantissa into the correct position
	AND	R5, R1, R2
	BRp	FINISH				;Once ANDing the current bit pattern with the bit mask produces a positive output, branch to FINISH
	ADD 	R1, R1, R1		
	
	AND	R4, R1, R2			;AND the current bit pattern with the bit mask to see when we should stop shifting
	BRz	SHIFTLEFT			;As long as the AND operation produces a zero, we still need to

	FINISH:

	NOT	R2, R2				;Once the mantissa is in the correct position, invert the bit mask
	ADD	R2, R2, 1			;and add it to the current bit pattern to eliminate the 
	ADD 	R1, R1, R2			;leading "1" that will not be a part of the mantissa

	ST	R1, USERMANTPRO			;Stores the product of the two mantissas

	RET

;FINALPRODUCT will calculate the HPFP of the 
;product by adding the sign bit, exponent bits,
;and mantissa bits all together to get the final 
;answer

FINALPRODUCT:					
	LD	R1, PROSIGNBIT			;Load R1 with the sign bit of the HPFP of the product
	LD	R2, PROUSEREXPO			;Load R2 with the exponent bits of the HPFP of the product
	LD	R3, USERMANTPRO			;Load R3 with the mantissa bits, in their correct positions, of the HPFP of the product

	ADD	R1, R1, R2			;Add all these components together, the result will be in R1
	ADD	R1, R3, R1

	STI	R1, STOREPRODUCT		;Stores the final product in HPFP in location x3200

	RET

; Declaring and initializing labels that will be used

USERDEC1	.FILL	0			;Stores the first user input as decimal form
USERDEC2	.FILL	0			;Stores the second user input as decimal form
USERINPUT1	.FILL	0			;Stores converted first user input
USERINPUT2	.FILL	0			;Stores converted second user input
STOREPRODUCT	.FILL	X3200			;Contains the memory location to store the HPFP of the product
TENSPLACE1	.FILL	0			;Stores ten's place of user input 1
ONESPLACE1	.FILL	0			;Stores one's place of user input 1
TENSPLACE2	.FILL	0			;Stores ten's place of user input 2
ONESPLACE2	.FILL	0			;Stores one's place of user input 2
OFFSET		.FILL	-48			;ASCII offset
NEGOFFSET	.FILL	-3			;Will be used to finish fixing the ASCII offset after check for "-" key
NEGCHECK	.FILL	-45			;Will be used to check if a "-" key was entered
BITMASK1	.FILL	1024			;2^10
BITMASK2	.FILL	16384			;2^14
BITMASK3	.FILL	32768			;2^15
BITMASK4	.FILL	31744			;0111110000000000 --> Bit mask for exponent
BITMASK5	.FILL	-15360			;0011110000000000 --> Bit mask for product of exponents
BITMASK6	.FILL	8192			;X2000
DIVBY2		.FILL	-2			;Used to divide by 2
DIVCOUNTER	.FILL	-4			;Used to shift 4 times
FIXERUPPER	.FILL	25			;Used to obtain the exponent bits of the HPFP representation of the inputs after shifting the mantissas
TENS		.FILL	10			;First digit entered will be multiplied by this number 
USER1SIGNBIT	.FILL	0			;Sign bit for userinput 1 to be multiplied later
USER2SIGNBIT	.FILL	0			;Sign bit for userinput 2 to be multiplied later
PROSIGNBIT	.FILL	0			;Product of the sign bit of userinput 1 and 2
USER1EXPO	.FILL	0			;Exponent for userinput 1 to be multiplied later
USER2EXPO	.FILL	0			;Exponent for userinput 2 to be multiplied later
PROUSEREXPO	.FILL	0			;Product of the user exponents
USERMANT1	.FILL	0			;Mantissa for userinput1 with leading 1
USERMANT2	.FILL	0			;Mantissa for userinput1 with leading 2
USERMANTPRO	.FILL	0			;Product of the mantissa for userinput1 and 2

; Strings
GREETING1	.STRINGZ	"Enter a two digit number: "
GREETING2	.STRINGZ	"\nEnter a two digit number: "


; End of code
	.END