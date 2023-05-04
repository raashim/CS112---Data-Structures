package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("deleteproduct.in");
        StdOut.setFile("deleteproduct.out");

        //create a new Warehouse object 
        Warehouse w = new Warehouse(); 

	// Use this file to test deleteProduct
    int size = StdIn.readInt(); //first number is the size 

    for(int i = 0; i < size; i++) {
        String queryType = StdIn.readString(); 
        
        if(queryType.equals("add")) 
        {
            int day = StdIn.readInt(); 
            int productId = StdIn.readInt(); 
            String name = StdIn.readString(); 
            int initialStock = StdIn.readInt(); 
            int intialDemand = StdIn.readInt(); 
            w.addProduct(productId, name, initialStock, day, intialDemand);
        } else if(queryType.equals("delete")) {
            int productID = StdIn.readInt(); 
            w.deleteProduct(productID);
        }
    }

    StdOut.println(w);

    }
}
