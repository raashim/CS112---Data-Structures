package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE
         //read file names from the command line 
         String PredictThanosSnapInputFile = args[0]; 
         String PredictThanosSnapOutputFile = args[1]; 
 
         //Set the input file 
         StdIn.setFile(PredictThanosSnapInputFile); 
         StdOut.setFile(PredictThanosSnapOutputFile);
    
        //Step 1
        long randomNumGen = StdIn.readLong(); 

        int numPeople = StdIn.readInt(); //number of people 

        int[][] matrix = new int[numPeople][numPeople]; 

        //ajacency matrix for an undirected graph 
        for(int i = 0; i < numPeople; i++) {
            for(int j = 0; j < numPeople; j++) {
                matrix[i][j] = StdIn.readInt(); 
            }
        }

        //Step 2
        StdRandom.setSeed(randomNumGen); 
        
        int pointer = 0;
        int counter = numPeople; //you need this because when you want to delete a vertex, you need to reduce the total number of vertices present in the graph
       /* while(pointer < numPeople) {

            double randomNum = StdRandom.uniform(); 

            if (randomNum <= 0.5) 
            {
                counter--; //decrease the total number of vertices
                for(int i = 0; i < counter; i++) 
                {
                    matrix[pointer][i] = 0; 
                    matrix[i][pointer] = 0; 
                    //dfs(matrix, i);
                }
            }
        } */

        for(int i = 0; i < numPeople; i++) {
            double randomNum = StdRandom.uniform(); 

            if (randomNum <= 0.5) 
            {
                counter--; //decrease the total number of vertices
                for(int j = 0; j < counter; j++) 
                {
                    matrix[i][j] = 0; 
                    matrix[j][i] = 0; 
                    //dfs(matrix, i);
                }
            }
        }

        //check to see if the graph is still connected after deleting random vertices 
        int edges = 0;

        for (int r = 0; r < numPeople; r++) {
            for (int c = 0; c < numPeople; c++) {
                if (matrix[r][c] == 1) {
                    edges++;
                }
            }
        }

        edges /= 2; 
        //since it is an undirected graph, the edges go both ways 

        StdOut.print(numPeople - edges <= 1);
    }

  /*  private static void dfs(boolean [] marked, int [][]matrix, int vertex) {
        marked[vertex] = true; 

        for(int w = 0; w < matrix.length; w++) {
            if(matrix[vertex][w]!= 0 && marked[w] == false) {
                dfs(marked, matrix, w);
            }
        }
    } */

  /* private boolean[] marked;     

    private void dfs(int [][] matrix, int v) {
        marked[v] = true; 
        for(int i = 0; i < matrix.length; i++) {
            if(!marked[v]) {
                dfs(matrix, v); 
            }
        }
    }
    
    private boolean visited(int v) {
        return marked[v];
    } */ 

}
