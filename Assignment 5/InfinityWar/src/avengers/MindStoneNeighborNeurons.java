package avengers;

import java.nio.file.LinkPermission;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
    	// WRITE YOUR CODE HERE

        //read file names from the command line 
        String msnnInputFile = args[0]; 
        String msnnOutputFile = args[1]; 

        //Set the input file 
        StdIn.setFile(msnnInputFile); 
        StdOut.setFile(msnnOutputFile);

        //Step 1 
        int numNeurons = StdIn.readInt(); //number of vertices in the graph 
        
        String [] neuronName = new String[numNeurons]; 

        //taking input and adding the names to the array
        for(int i = 0; i < numNeurons; i++) {
            neuronName[i] = StdIn.readString(); 
        }

        int numSynapse = StdIn.readInt(); //number of edges in the graph 

        String [][] edges = new String[numSynapse][2]; 
        //there are numSynapses number of rows as that's how the input file is structured 
        //there are 2 strings in each line, hence there are two columns 
        //this is why "edges" is a 2D array 

       // int rowPointer = 0; 
       // int colPointer = 0; 
        
        /*while(rowPointer < edges.length) {
            while(colPointer < 2) {
                edges[rowPointer][colPointer] = StdIn.readString(); 
                colPointer++; 
            }
            rowPointer++; 
        } */ 

        for(int i = 0; i < edges.length; i++) {
            for(int j = 0; j < edges[i].length; j++) {
                edges[i][j] = StdIn.readString();
        }
    }

        //identify the mindStone 
        int pointer1 = 0; 
        int pointer2 = 0; 
        String mindStone = ""; 
        
     /*  while(pointer1 < numNeurons) {
            while(pointer2 < edges.length) {
                if(neuronName[pointer1].equals(edges[pointer2][0])) {
                    
                } else {
                    mindStone = neuronName[pointer1]; 
                }
            }
            pointer1++; 
        }  */

        for(int i = 0; i < numNeurons; i++) {
            for(int j = 0; j < edges.length; j++) {
                if(neuronName[i].equals(edges[j][0])) {

                } else {
                    mindStone = neuronName[i];
                }
            }
        } 

        //to identity the vertices that connect to the MindStone vertex and print those out to the output file 
        while(pointer1 < edges.length) {
            if(edges[pointer1][1].equals(mindStone)) {
                StdOut.println(edges[pointer1][0]);
               }
               pointer1++; 
        }
        
      /*  for(int i = 0; i < edges.length; i++) {
           if(edges[i][1].equals(mindStone)) {
            StdOut.println(edges[i][0]);
           }
        } */

    }
}
