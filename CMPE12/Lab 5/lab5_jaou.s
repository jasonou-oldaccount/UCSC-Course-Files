/*
* JASON OU (1385128)
* jaou@ucsc.edu
* Partner: Vanessa Putnam
* Section: 6
* TA: Daphene Gorman
* 16 March 2015
* lab5_jaou.s
*/

#include <WProgram.h>

/* define all global symbols here */
.global getDelay
.global delayScalar
.global t1_setup
.global milliseconds
.global counter
.global helloWorld

.text
.set noreorder

/*********************************************************************
 * Setup getDelay
 ********************************************************************/
.ent getDelay
getDelay:
    /* Loads a0 with 'Hello Word! %d\n' and loads a1 with the counter */
    la $a0, helloWorld
    lw $s1, 0($sp) /* loads stack into $s1 */
    lw $a1, counter

    /* Pop */
    addi $sp, $sp, -4 
    sw $ra, 0($sp)

    /* Prints out "Hello World! with the counter */
    jal printf
    nop

    /* increment counter by one then stores it back into itself */
    lw  $s0, counter
    addi $s0, $s0, 1
    sw  $s0, counter

    /* Loads the stack and pushes the stack then stores into the stack*/
    lw $ra, 0($sp)
    addi $sp, $sp, 4
    sw $s1, 0($sp)

    /* Reads from PORTD to determine the value of the user
    switches by using a bitmask and shifting to the right eight times
    to get the value of the switches */
    la $t1, PORTD /* Reads from the input of the switches */
    lw $t4, 0($t1)
    li $t2, 0x0F00  /* Bit Mask 0000 1111 0000 0000 */
    and $t4, $t2, $t4
    srl $t4, $t4, 8

    /* Depending on the value of the switch, that value will be multiplied
    by one second to create the delay for the LEDs*/
    la $s5, delayScalar
    lw $s3, 0($s5)
    mul $t0, $t4, $s3

    addi $v0, $t0, 0

    jr $ra
    nop
.end getDelay

/*********************************************************************
 * Timer of 0.5 second delay/interrupt setup
 ********************************************************************/
.ent t1_setup
t1_setup:
    /* Clear T1CON - put it in known state */
    la $t1, T1CON
    li $t2, 0xFFFF 
    sw $t2, 4($t1)

    /* Set T1CKPS - set the prescalar for T1CON */
    la $t1, T1CON
    li $t2, 0x30 
    sw $t2, 8($t1)

    /* Clear TMR1 value - clear the cunt if it was used */
    la $t1, TMR1
    li $t2, 0xFFFF 
    sw $t2, 4($t1)

    /* Set PR1 value - Set to half a second */
    la $t1, PR1
    li $t2, 0x9897 
    sw $t2, 8($t1)

    /* Set T1IP - set the interrupt priority to 4 */
    la $t1, IPC1
    li $t2, 0x4 
    sw $t2, 8($t1)

    /* Clear T1IF - clear any prior interrupt */
    la $t1, IFS0
    li $t2, 0x10 
    sw $t2, 4($t1)

    /* Enable T1IE - enable the interrupts */
    la $t1, IEC0
    sw $t2, 8($t1) 

    /* Set T1 ON - turns on the timer */
    la $t1, T1CON
    li $t2, 0x8000 
    sw $t2, 8($t1)

    jr $ra
    nop
.end t1_setup

/*********************************************************************
 * This is your ISR implementation. It is called from the vector table jump.
 ********************************************************************/
Lab5_ISR:
     /* Toggles on and off the LED depending on if it was already on or off
     by doing the inverse of the current value of all of the LEDs */
    la $t1, TRISE
    li $t2, 0xFF
    sw $t2, 12($t1)

    /* Clears the interrupt flag */
    la $t1, IFS0
    li $t2, 0x0010 
    sw $t2, 4($t1)
eret
nop

/*********************************************************************
 * This is the actual interrupt handler that gets installed
 * in the interrupt vector table. It jumps to the Lab5
 * interrupt handler function.
 ********************************************************************/
.section .vector_4, code
	j Lab5_ISR
	nop

.data
helloWorld:     .ascii "Hello world! %d\n"
counter:        .word 0
delayScalar:    .word 0x100000
milliseconds:   .word 0x80000