# Pascal in Truffle

This project implements a subset of Pascal language by Niklaus Wirth from the Swiss Federal Institute of Technology in Zürich (aka ETH Zürich).

It uses Oracle's [Truffle](http://www.oracle.com/technetwork/oracle-labs/program-languages/overview/index-2301583.html) to achieve higher performance by allowing just-in-time compilation of the interpreted Pascal code.

## Features

The language (should) support:

- only the "main" procedure (no user-defined functions or procedures)
- integer, real, boolean and string variables
- static 1-D arrays of integers, real, boolean and string defined by `[from..to]` range.
- write and writeln (no read, no readln)!!!
- +, -, *, /, div
- if, while, for ... to and for ... downto
- and, or, not
- <, >, <=, >=, <>, =

## Dependencies

- [GraalVM JDK 0.20](http://www.oracle.com/technetwork/oracle-labs/program-languages/downloads/index.html)

GraalVM JDK must be installed in the `graalvm` directory under the root of the project.

## Build

Run mvn package in the pascal-truffle directory.

JDK_HOME must be set to graalvm, then run mvn package.
	
	mvn package

## Run

	./run-pascal benchmark/bubble.pas -time
	
The code above runs bubble sort on 10000 numbers and prints the time needed to execute the code (without time needed to start the Java Virtual Machine).  
There is one another benchmark in the directory. 

Code can be entered using the standard input as well.

## Performance Tests

During the development, some of the Truffle features were incrementally added. 
The respective versions are [tagged](https://github.com/chovanecm/pascal-truffle/releases) and described in the repository.

### Versions
- FreePascal: program compiled using fpc
- v0.2 - pascal-truffle with the Truffle API and no optimisations. Often uses "slow operations" (interpreter mode)
- v0.3 - pascal-truffle - avoiding slow operations by preallocating FrameSlots
- v0.4 - pascal-truffle - uses RepeatingNode to speed loops up.

The following table shows the time needed to execute respective programs.

| Program name   | FreePascal (s) | pascal-truffle v0.2(.1)  (s)| pascal-truffle v0.3  (s) | pascal-truffle v0.4 (s) |
|---|---|---|---|---|
| Collatz  | 2,1  | 168  | 90  | 3,15  |
| Bubble  | 0,225  | 30  | 19,5  | 5,04  |

### About the Programs

#### Collatz
Collatz computes the Collatz sequence of given natural number *n*. The successor of *n* is *n/2* for even numbers and *3n+1* for odd numbers until reaching *1*. This is repeated many times to heat the virtual machine up.

#### Bubble
Bubble is a bubble sort implementation running on 10 000 numbers in an array. It requires many memory access operations, including access to the array. Unlike *Collatz*, it doesn't repeat the program itself many times. Therefore, the virtual machine is not as heated up as in the previous program. When repeating the program 10 times, the sorting is in average about 25 % faster than the signle-pass version.

## Acknowledgements
- A big thanks to Karel Rank, who allowed me to use his older work, [Pascal Parser](https://github.com/karl82/pascal-interpreter), as my starting point.
- [Jakub Podlešák](https://github.com/japod) from Oracle for his tutorial session at the Czech Technical University and providing me with further materials on Truffle
