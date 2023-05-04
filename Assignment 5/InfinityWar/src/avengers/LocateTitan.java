package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE

        //read file names from the command line 
        String locateTitanInputFile = args[0]; 
        String locateTitanOutputFile = args[1]; 

        //Set the input file 
        StdIn.setFile(locateTitanInputFile); 

        //Step 1 
        int numRows = StdIn.readInt(); //num of rows/col because its a square matrix

        double[] functionality = new double[numRows]; //functionality matrix
        
        //populate the functionality array 
        for(int i = 0; i < numRows; i++) {
            int index = StdIn.readInt(); 
            double value = StdIn.readDouble(); 
            functionality[index] = value; 
        }
        
        int[][] matrix = new int[numRows][numRows]; //adjacency matrix 

        //populate the 2D array with r rows and c col with input file 
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numRows; j++) {
                matrix[i][j] = StdIn.readInt(); 
            }
        }

        //Step 2 
        //traverse matrix, for each element: do matrix[i][j] = matrix[i][j] / functionality[row] 
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numRows; j++) {
                matrix[i][j] = (int) (matrix[i][j] / (functionality[i]*functionality[j])) ; 
            }
        }

        //Step 3 
        //Dijkstra algorithm 
        //used the pseudocode provided in the instructions for the assignment
        int [] minCost = new int[numRows]; 
        boolean [] DijkstraSet = new boolean[numRows]; 

        int pointer = 0; 
        while(pointer < numRows) {
            if(pointer == 0) {
                minCost[pointer] = 0; 
            } else {
                minCost[pointer] = Integer.MAX_VALUE; 
            }
            pointer++; 
        }

        int currentSource; 
        for(int i = 0; i < numRows; i++) {
            currentSource = getMinCostNode(minCost, DijkstraSet); 
            DijkstraSet[currentSource] = true; 

            for(int w = 0; w < numRows; w++) {
                if((DijkstraSet[w] == false) && 
                (matrix[currentSource][w] != 0) &&
                (minCost[w] > (minCost[currentSource] + matrix[currentSource][w]))) {

                minCost[w] = minCost[currentSource] + matrix[currentSource][w];

                }
        }

        //Set the Output File 
        StdOut.setFile(locateTitanOutputFile); 

        //Code to output to the output file
       StdOut.print(minCost[minCost.length - 1]);
     
     /*  for(int c = 0; c < matrix.length; c++) {
        for(int j = 0; j < matrix[c].length; j++) {
            System.out.print(matrix[c][j]); 
        }
       } */ 
    }
}

    private static int getMinCostNode(int [] minCost, boolean [] DijkstraSet) {
        //we accept that minCost array as a parameter because that stores the values
        //we accept the boolean array because we want to make sure that we do not recheck the nodes that we've already checked 

        int minNum = Integer.MAX_VALUE; //make this the max number for an int because then basically everything 
                                              //would be smaller than this value, which means that it would immediately 
                                              //get replaced with the first number/the minimum of the minCost array
        int nodeMin = -5; 
        
        for(int i = 0; i < minCost.length; i++) {
            if( (minCost[i] < minNum) && (DijkstraSet[i] == false)) {
                nodeMin = i; //set the node value to the node that currently has the current minimum value 
                minNum = minCost[i]; //set the minimum value to the current minimum value from the minCost array 
            }
        }
        return nodeMin; 
    }

}
