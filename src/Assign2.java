/**
 * Shwet Vasudevan
 * 6549422
 * November 13, 2020
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;

public class Assign2 {

    /**
     * Global variables used through out the class.
     * POP contains the population of chromosomes
     * choice hold the type of crossover
     * greatest is the final chromosome used
     */
    Node[] POP;
    int choice;
    int greatest;

    //Constructor

    public Assign2() throws FileNotFoundException {

        // File selector GUI
        Scanner scan = new Scanner(System.in);

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);


        //contain the shredded document

        char[][]shreddedDocument = FitnessCalculator.getShreddedDocument(chooser.getSelectedFile().getAbsolutePath());

        // Get population size from user
        System.out.println("Enter a Even Number for Population Size");
        int popSize = scan.nextInt();
        
        // Get number of generations from user
        System.out.println("Enter a Even Number for Number of Generations");
        int numOfGenerations = (scan.nextInt());
        
        // Get crossover rate from user
        System.out.println("Enter the Crossover rate as a decimal (i.e 0.1 = 10%)");
        double crossDec = scan.nextDouble();

        // Get mutation rate from user
        System.out.println("Enter Mutation rate as a decimal (i.e. 0.1 = 10%)");
        double muteDec = scan.nextDouble();

        // Get k value for tounoment selection from user
        System.out.println("Enter Reproduction Value (K value):");
        int kVal = scan.nextInt();
        
        // Get crossover type from user
        System.out.println("Choose Crossover: ");
        System.out.println("1. Ordered Crossover");
        System.out.println("2. Uniformed Ordered Crossover");
        choice = scan.nextInt();

        // Get seed from user
        System.out.println("Enter seed");
        long seed = scan.nextLong();
        
        // Set random using seed
        Random rand = new Random(seed);

        // Display values to user
        System.out.println("---------------------------------------------------");
        System.out.println("POPULATION:\t\t"+popSize);
        System.out.println("GENERATIONS:\t\t"+numOfGenerations);
        System.out.println("CROSSOVER RATE:\t\t"+crossDec);
        System.out.println("MUTATION RATE:\t\t"+muteDec);
        System.out.println("REPRODUCTION VALUE:\t"+kVal);
        if(choice == 1) System.out.println("CROSSOVER TYPE:\t\tOrdered Crossover");
        if(choice == 2) System.out.println("CROSSOVER TYPE:\t\tUniformed Ordered Crossover");
        System.out.println("SEED:\t\t\t"+seed);
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.println("Average\t\t\t\tBEST");
        System.out.println("---------------------------------------------------");

        // Set first generation using random generated of numbers
        POP = new Node[popSize];

        for (int i = 0; i < popSize; i++) {

            ArrayList<Integer> hold = new ArrayList<>();
            int[] chrom = new int[15];
            for (int j = 0; j < 15; j++) {
                hold.add(j);
            }
            Collections.shuffle(hold);
            for (int m = 0; m < 15; m++) {
                chrom [m] = hold.get(m);

            }
            Node c = new Node(chrom);
            POP[i] = c;


        }

        for (int i = 0; i < numOfGenerations; i++) { // every generation
            
            double[] fitnesses = new double[popSize];

            //calculate fitnesses for all chromosomes
            for (int j = 0; j < popSize; j++) { // every population
                fitnesses[j] = FitnessCalculator.fitness(shreddedDocument,POP[j].getChrom());
                
            }

            // get average
            double avg = getAvg(fitnesses);
            
            System.out.print(avg);

            //get k fittest from population
            int pos = 0;
            double Max = fitnesses[0];
            double[] fittest = new double[kVal];
            for (int k = 0; k < kVal; k++) {
                fittest[pos] = fitnesses[0];
                pos++;
            }

            
            // get fittest positions from fittest array
            pos = 0;
            int[] fittestPos = new int[kVal];
            for (int j = 0; j < kVal; j++) {

                for (int k = 0; k < popSize; k++) {
                    if(fittest[j] > fitnesses[k]){
                        fittest[j] = fitnesses[k];
                        fittestPos[j] = k;
                    }
                }
                pos++;
                fitnesses[fittestPos[j]] = Max;
            }

            // get fittest chromosome 
            double best = fittest[0];
            greatest =  fittestPos[0];

            System.out.println("\t\t"+best);

            // new Node array for next generation
            Node[] newGen = new Node[popSize];


            if(choice == 1){ // Ordered Crossover
                int poppos = 0;
                for (int j = 0; j < popSize; j+=2) {
                    
                    // random doubloe variable 
                    double fate = rand.nextDouble();

                    // check if random variable is lesser than crossover rate, if so do crossover
                    if(fate < crossDec){

                        // pick random fittest parent from fittest array and current position in POP array
                        
                        int parent1Pos = fittestPos[rand.nextInt(kVal-0)+0];
                        int[] parent1Array = POP[parent1Pos].getChrom();
                        int[] parent2Array = POP[poppos].getChrom();
                        poppos++;
                        
                        // set size variable
                        int size = parent1Array.length;

                        // get two random points in the array, set min point to start, set max point to end
                        int s = rand.nextInt(15-0)+0;
                        int e = rand.nextInt(15-0)+0;
                        int start = Math.min(s, e);
                        int end = Math.max(s, e);

                        //create two child arraylists to hold values visited
                        ArrayList<Integer> child1List = new ArrayList<>();
                        ArrayList<Integer> child2List = new ArrayList<>();

                        //two child arrays
                        int[] child1Array = new int[parent1Array.length];
                        int[] child2Array = new int[parent2Array.length];

                        for (int k = start; k < end; k++) {
                            child1Array[k] = parent1Array[k];
                            child2Array[k] = parent2Array[k];
                            child1List.add(parent1Array[k]);
                            child2List.add(parent2Array[k]);
                        }

                        
                        //left child 1 parent 2
                        for (int k = 0; k < start; k++) {
                            for (int k2 = 0; k2 < parent2Array.length; k2++) {
                                int hold = parent2Array[k2];
                                if (!child1List.contains(hold)) {
                                    child1Array[k] = hold;
                                    child1List.add(hold);
                                    break;
                                }
                            }
                        }
                        //right child 1 parent 2
                        for (int k = end; k < size; k++) {
                            for (int k2 = 0; k2 < parent2Array.length; k2++) {
                                int hold = parent2Array[k2];
                                if (!child1List.contains(hold)) {
                                    child1Array[k] = hold;
                                    child1List.add(hold);
                                    break;
                                }
                            }
                        }

                        //left child 2 parent 1
                        for (int k = 0; k < start; k++) {
                            for (int k2 = 0; k2 < parent1Array.length; k2++) {
                                int hold = parent1Array[k2];
                                if (!child2List.contains(hold)) {
                                    child2Array[k] = hold;
                                    child2List.add(hold);
                                    break;
                                }
                            }
                        }
                        //right child 2 parent 1
                        for (int k = end; k < size; k++) {
                            for (int k2 = 0; k2 < parent1Array.length; k2++) {
                                int hold = parent1Array[k2];
                                if (!child2List.contains(hold)) {
                                    child2Array[k] = hold;
                                    child2List.add(hold);
                                    break;
                                }
                            }
                        }

                        //mutate children and return mutated array or regular array
                        child1Array =  mutate(child1Array, rand, muteDec);
                        child2Array = mutate(child2Array, rand, muteDec);

                        //create nodes from the children array
                        Node c1 = new Node(child1Array);
                        Node c2 = new Node(child2Array);

                        //add new nodes to new generation array
                        newGen[j] = c1;
                        newGen[j+1] = c2;



                    }
                    else{
                        
                        // when crossover is not achived add fitest and a chromosome from previous generation to new population
                        int parent1Pos = fittestPos[rand.nextInt(kVal-0)+0];
                        int[] parent1Array = POP[parent1Pos].getChrom();
                        int[] parent2Array = POP[poppos].getChrom();
                        poppos++;

                        parent1Array = mutate(parent1Array, rand, muteDec);
                        parent2Array = mutate(parent2Array, rand, muteDec);

                        Node ch1 = new Node(parent1Array);
                        Node ch2 = new Node(parent2Array);

                        newGen[j] = ch1;
                        newGen[j+1] = ch2;
                    }
                    



                    
                }

                
            }
            
            else{ // Uniformed Ordered Crossover
                int poppos = 0;
                for (int j = 0; j < popSize; j+=2) {
                    //similar principle as the Ordered Crossovers
                    double fate = rand.nextDouble();
                    if(fate < crossDec){

                        //similar to the Ordered Crossover
                        int parent1Pos = fittestPos[rand.nextInt(kVal-0)+0];
                        int[] parent1Array = POP[parent1Pos].getChrom();
                        int[] parent2Array = POP[poppos].getChrom();
                        poppos++;

                        int size = parent1Array.length;

                        //create mask array used for Uniformed Ordered Crossover
                        int[] mask = new int[size];
                        for (int k = 0; k < mask.length; k++) {
                            int r = rand.nextInt(2-0)+0;
                            if(r == 1){
                                mask[k] = 1;
                            }
                            else{
                                mask[k] = 0;
                            }
                        }

                        ArrayList<Integer> child1List = new ArrayList<>();
                        ArrayList<Integer> child2List = new ArrayList<>();

                        int[] child1Array = new int[parent1Array.length];
                        int[] child2Array = new int[parent2Array.length];

                        for (int k = 0; k < mask.length; k++) {
                            if (mask[k] == 1) {
                                child1Array[k] = parent1Array[k];
                                child2Array[k] = parent2Array[k];
                                child1List.add(parent1Array[k]);
                                child2List.add(parent2Array[k]);
                            }
                        }


                        //child 1 parent 2
                        for (int k = 0; k < mask.length; k++) {
                            if (mask[k] == 0) {
                                for (int k2 = 0; k2 < parent2Array.length; k2++) {
                                    int hold = parent2Array[k2];
                                    if (!child1List.contains(hold)) {
                                        child1Array[k] = hold;
                                        child1List.add(hold);
                                        break;
                                    }
                                }
                            }
                        }

                        //child 2 parent 1
                        for (int k = 0; k < mask.length; k++) {
                            if (mask[k] == 0) {
                                for (int k2 = 0; k2 < parent1Array.length; k2++) {
                                    int hold = parent1Array[k2];
                                    if (!child2List.contains(hold)) {
                                        child2Array[k] = hold;
                                        child2List.add(hold);
                                        break;
                                    }
                                }
                            }
                        }


                        child1Array =  mutate(child1Array, rand, muteDec);
                        child2Array = mutate(child2Array, rand, muteDec);


                        Node c1 = new Node(child1Array);
                        Node c2 = new Node(child2Array);




                        newGen[j] = c1;
                        newGen[j+1] = c2;

                        

                    }
                    else{
                        int parent1Pos = fittestPos[rand.nextInt(kVal-0)+0];
                        int[] parent1Array = POP[parent1Pos].getChrom();
                        int[] parent2Array = POP[poppos].getChrom();
                        poppos++;
                        parent1Array = mutate(parent1Array, rand, muteDec);
                        parent2Array = mutate(parent2Array, rand, muteDec);

                        Node ch1 = new Node(parent1Array);
                        Node ch2 = new Node(parent2Array);


                        newGen[j] = ch1;
                        newGen[j+1] = ch2;
                    }
                }

            }


            for (int k = 0; k < popSize; k++) {
                POP[k] = newGen[k];
            }

        
        } // generation

        // print the fittest chromosome from the latest generation
        System.out.println();
        POP[greatest].printChrom();

        // print shredded document and unshredded document
        System.out.println("\n------SHREDDED DOCUMENT------\n");
        FitnessCalculator.prettyPrint(shreddedDocument);
        System.out.println();
        System.out.println("------UNSHREDDED DOCUMENT------\n");
        char[][] done = FitnessCalculator.unshred(shreddedDocument, POP[greatest].getChrom());
        FitnessCalculator.prettyPrint(done);
        scan.close();


    }

    /**
     * Get the average fitness of the population and return is back
     * to display to the user
     * @param fitness
     * @return
     */
    public double getAvg(double[] fitness){
        double avg = 0;
        for (double x : fitness) {
            avg = avg + x;
        }
        avg = avg / fitness.length;
        return avg;
    }

    /**
     * This method mutates the given chromosome and returns it back
     * if below mutaation rate
     * 
     * @param array
     * @param r
     * @param mr
     * @return
     */
    public int[] mutate(int[] array, Random r, double mr){
        double fate = r.nextDouble();
        if(fate < mr){
            int s = r.nextInt(15-0)+0;
            int e = r.nextInt(15-0)+0;
            int start = Math.min(s, e);
            int end = Math.max(s, e);
            int temp = array[start];
            int temp2 = array[end];
            array[start] = temp2;
            array[end] = temp;
            return array;
        }
        else{
            return array;
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        Assign2 a = new Assign2();

    }
}
