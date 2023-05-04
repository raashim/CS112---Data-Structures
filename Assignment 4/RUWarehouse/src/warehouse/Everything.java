package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile("everything.in");
        StdOut.setFile("everything.out");

	// Use this file to test all methods

    Warehouse w = new Warehouse(); 

    int numQueries = StdIn.readInt(); //gets the number of queries/input lines 

    for(int i = 0; i < numQueries; i++) {
        String queryType = StdIn.readString(); 
        
        if(queryType.equals("add")) {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            String name = StdIn.readString(); 
            int initialStock = StdIn.readInt(); 
            int intialDemand = StdIn.readInt(); 
            w.addProduct(productId, name, initialStock, day, intialDemand);
        }
        else if(queryType.equals("restock")) {
            int productID = StdIn.readInt(); 
            int restockAmt = StdIn.readInt(); 
            w.restockProduct(productID, restockAmt);
        }
        else if(queryType.equals("purchase")) {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            int amount = StdIn.readInt(); 
            w.purchaseProduct(productId, day, amount);
        } else if(queryType.equals("delete")) {
            int productID = StdIn.readInt(); 
            w.deleteProduct(productID);
        }
    }
    StdOut.print(w);
    
    }

}
