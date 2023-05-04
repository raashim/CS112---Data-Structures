package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addproduct.in");
        StdOut.setFile("addproduct.out");

	// Use this file to test addProduct
    Warehouse w = new Warehouse(); 

    int i = 0; 
    int numOfItems = StdIn.readInt(); //the first line tells you the number of products to add hence you can do the previous line  
    
    while(i < numOfItems) {
        int day = StdIn.readInt(); 
        int productID = StdIn.readInt(); 
        String productName = StdIn.readString(); 
        int initialStock = StdIn.readInt(); 
        int initialDemand = StdIn.readInt(); 
        w.addProduct(productID, productName, initialStock, day, initialDemand);
        i++;
    }

    StdOut.println(w); 
    }
}
