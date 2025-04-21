# Genetic-Algorithms
Development, Implementation, and Experimentation of Genetic Algorithms


This program was developed using Java 8 in Intellij and can be run under the Assign2.java file which is found in the src folder.
When running the program, a file select window will be displayed.(CAUTION: There are instances when the file select window does 
not appear when using Intellij however using Visual Studio Code seems to mitigate this issue. If this occurs rerun the program.
If no file is selected it will result in the program crashing.)

Navigate from the home directory to the folder that holds the the shredded documents, which is found in the Examples folder. 
Once selected you will be prompted to enter the population size,the number of generations, the crossover rate as a decimal value,
the mutation rate as a decimal value, the k value for tournoment select, you will then be asked which type of crossover you would 
like to use, enter 1 for ordered crossover, enter 2 for uniformed ordered crossover, and finally you will be asked for the seed 
for this run of the program.

When the seed is entered, the program will display your parameters on the console. The the genetic algorithm will produce the
average fitnesses of each generation on the left column, and the best fitness of each generation on the right column. Once the
final generation is made the fittest chromosome will be displayed. Then the shredded version of the document will be shown,
followed up by the unshredded version.

If you wish to try new parameters, rerun the Assign2.java file.

A demonstrated trial can be found under the Results folder to show how the console will look once the program is done running.
