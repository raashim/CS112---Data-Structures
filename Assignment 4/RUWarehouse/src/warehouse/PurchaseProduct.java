package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile("purchaseproduct.in");
        StdOut.setFile("purchaseproduct.out");

	// Use this file to test purchaseProduct

    Warehouse w = new Warehouse(); 

    int size = StdIn.readInt(); 
    
    for(int i = 1; i < size + 1; i++) {

        String queryType = StdIn.readString(); 

        if(queryType.equals("add")) 
        {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            String name = StdIn.readString(); 
            int initialStock = StdIn.readInt(); 
            int intialDemand = StdIn.readInt(); 
            w.addProduct(productId, name, initialStock, day, intialDemand);
        } else if(queryType.equals("purchase")) {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            int amount = StdIn.readInt(); 
            w.purchaseProduct(productId, day, amount);
        }  
    }

    StdOut.println(w); 
    }
}
