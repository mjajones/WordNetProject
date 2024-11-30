WordNet Project - CSC513 Algorithms

:::::::::::::Overview::::::::::::::::::::

This project implements a WordNet - based application using Java and includes the following:

1. WordNet construction using synsets and hypernyms files.
2. Shortest ancestral path (SAP) computations.
3. Outcast detection using WordNet nouns.

This readme file provides step-by-step instructions to compile and run the program.

:::::::::::Software Environments:::::::::::::::

1. Java Development Kit (JDK) 
--Version JDK 8 or later
--Make sure java and javac commands are added to your system's PATH

2. Libraries Required
--algs4.jar - From Princeton's Algorithms Library https://algs4.cs.princeton.edu/code/
--stdlib.jar - Standard input/output library

3. Development Environment
--Command Line or IDE (Visual Studio Code is what I used for IDE)

:::::::::::::::Directory Structure:::::::::::::::::

1. Create project directory first called WordNetProject/

2. Create first sub folder src/
--Place WordNet.java inside
--Place SAP.java inside
--Place Outcast.java inside

2. Create sub folder lib/
--Place algs4.jar inside
--Place stdlib.jar inside 

3. Create sub folder data/
--Place synsets.txt inside
--Place hypernyms.txt inside 
--Place outcast1.txt inside
--Place (all other test files) inside folder

::::::::::::::::Step to Compile::::::::::::::::::::

1. Open a terminal or command prompt.
2. Navigate to the project root directory WordNetProject/
3. Run the following command to compile all .java files under the src/ folder

javac -cp "src;lib/algs4.jar;lib/stdlib.jar" src/*.java

:::::::::::::::Test Java Files::::::::::::::::::::

1. Test WordNet.java

java -cp "src;lib/algs4.jar;lib/stdlib.jar" WordNet data/synsets.txt data/hypernyms.txt

2. Test SAP.java

java -cp "src;lib/algs4.jar;lib/stdlib.jar" SAP data/digraph1.txt

3. Test Outcast.java

java -cp "src;lib/algs4.jar;lib/stdlib.jar" Outcast data/synsets.txt data/hypernyms.txt data/outcast5.txt

:::::::::::::::Start Running Examples::::::::::::

1. SAP Example

java -cp "src;lib/algs4.jar;lib/stdlib.jar" SAP data/digraph2.txt

Enter your vertices pairs to get length and ancestor.

2. Outcast Example

java -cp "src;lib/algs4.jar;lib/stdlib.jar" Outcast data/synsets.txt data/hypernyms.txt data/outcast5.txt

Result for this should be "Outcast:  table"
