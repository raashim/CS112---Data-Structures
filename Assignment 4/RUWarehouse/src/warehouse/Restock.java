package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile("restock.in");
        StdOut.setFile("restock.out");

	// Uset his file to test restock
    Warehouse w = new Warehouse(); 
    
    int numQueries = StdIn.readInt(); 
    

    for(int i = 0; i < numQueries; i++) {
        String queryType = StdIn.readString(); 
        if(queryType.equals("add")) 
        {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            String name = StdIn.readString(); 
            int initialStock = StdIn.readInt(); 
            int intialDemand = StdIn.readInt(); 
            w.addProduct(productId, name, initialStock, day, intialDemand);
        }
        else if(queryType.equals("restock")) 
        {
            int productID = StdIn.readInt(); 
            int restockAmt = StdIn.readInt(); 
            w.restockProduct(productID, restockAmt);
        }
    }

    StdOut.println(w);
    }      
}

