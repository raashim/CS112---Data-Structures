package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */
public class Warehouse {
    private Sector[] sectors;

    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }

    /**
     * Provided method, code the parts to add their behavior
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD

        // creating a product with the given parameters
        Product newProduct = new Product(id, name, stock, day, demand);

        // getting the last value from the stock to figure out what sector the product
        // belongs in
        int correctSectorID = id % 10;

        // finds the correct sector from the Sectors object array
        Sector correctSector = sectors[correctSectorID];

        // adding each product from the input file to the warehouse
        correctSector.add(newProduct);
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * 
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        // IMPLEMENT THIS METHOD

        // find the correct sector using the id of the product
        int correctSectorID = id % 10;

        // get the Sector in which the product is to be added into
        Sector correctSector = sectors[correctSectorID];

        // size gives you the last index in which the product is being added
        // so by using .getSize(), you are able to get the last index in which
        // the product is being inserted into
        // and then you would call swim on that index to move it into place for the min heap structure

        int lastIndex = correctSector.getSize();

        correctSector.swim(lastIndex); 
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5
     * while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the
     * Sector class
     * 
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        // IMPLEMENT THIS METHOD

        // get the last digit of the id to find the correct sector
        int correctSectorID = id % 10;

        // find the correct sector from the array of Sector objects
        Sector correctSector = sectors[correctSectorID];

        // since this is a min PQ, the item with the least popularity would be the root
        // of the tree hence, if you swap the root and the last item, 
        //you would end up with the item with the least popularity
        // being the last item on the tree
        //you can then call deleteLast which will delete the last element on the tree which after the 
        //swap is the item with the least popularity 
        //afterwards, to get the tree back into heap order, you need to call sink for the "newRoot", which is out of order
        //because it was swapped with the last item

        if (correctSector.getSize() == 5) {
            correctSector.swap(1,5); //swap root and last element
            correctSector.deleteLast(); //delete the last element, which is now the root (least popular)
            correctSector.sink(1); //this is done to put the tree back into heap order 
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * 
     * @param id     The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        // IMPLEMENT THIS METHOD

        // get the last digit of the id to find the correct sector
        int correctSectorID = id % 10;

        //find the correct sector from the Sectors object array
        Sector correctSector = sectors[correctSectorID];

        //find the size of the current sector 
        int size = correctSector.getSize(); 

       /* for(int i = 0; i < size; i++) {
            if(correctSector.get(i) != null) {
                if(correctSector.get(i).getId() == id) {
                    correctSector.get(i).updateStock(amount);
                }
            }
        } */

        //you have to create a Product object because otherwise, the instance variable is not initliazed, 
        //therefore, you will be able to use the updateStock method among the other methods in the Product class
        //will not work as intended

        //the for loop needs to start at one because in a priority queue, we do not use the index value of 0
        for(int i = 1; i < size + 1; i++) {
           Product product = correctSector.get(i); 
            if(product != null) { //product is not null
                if(product.getId() == id) { //product matches the input
                    product.updateStock(amount); //update the stock
                }
            }
        }
    }

    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(),
     * .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * 
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        // IMPLEMENT THIS METHOD

         // get the last digit of the id to find the correct sector
         int correctSectorID = id % 10;

         //find the correct sector from the Sectors object array
         Sector correctSector = sectors[correctSectorID];
 
         //find the size of the current sector 
         int size = correctSector.getSize(); 

         //iterate through the sector to find the correct product 
       for(int i = 1; i < size + 1; i++) {
            
            Product productToTemp = correctSector.get(i); //find the product in that Sector at that index value in the priority queue
            
            if(productToTemp != null) { //product is not null
                if(productToTemp.getId() == id) { //product matches the id
                    correctSector.swap(i, size); //swap the found element and last element 
                    correctSector.deleteLast(); //deleting the last node that is now the 
                 // size -= 1; //reduce size
                    correctSector.sink(i); //put it back into heap order by reorganizing the tree from "i" down using the sink function
                }
            }
        }  
    } 

    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector
     * class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(),
     * updateStock(), updateDemand() methods
     * 
     * @param id     The id of the purchased product
     * @param day    The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD

        // get the last digit of the id to find the correct sector
        int correctSectorID = id % 10;

        //find the correct sector from the Sectors object array
        Sector correctSector = sectors[correctSectorID];

        //find the size of the current sector 
        int size = correctSector.getSize(); 


        for(int i = 1; i < size + 1; i++) {

            Product product = correctSector.get(i); //find the current product in the CORRECT sector 
            int prevStock = product.getStock(); //find the existing stock for the product
            int prevDemand = product.getDemand(); //find the existing demand for the product 

            if(product.getId() == id) { //if the product matches the id
                if(amount > prevStock) { //if the amount to purchase is greater than the existing stock, 
                    return;             //we just return because otherwise it would go into the negatives and that doesn't really make any sense in a Warehouse
                }
                else {
                    product.setLastPurchaseDay(day); //update the purchase day to the current day 
                    product.setStock(prevStock - amount); //decrease stock by amount purchased
                    product.setDemand(prevDemand + amount); //increase the demand by amount purchased
                    correctSector.sink(i); //maintain heap structure by moving the product down the tree using the sink function since the popularity has increased
                }
            }
        }
    }

    /**
     * Construct a better scheme to add a product, where empty spaces are always
     * filled
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }

        return warehouseString + "]";
    } 

    /*
     * Do not remove this method, it is used by Autolab
     */
    public Sector[] getSectors() {
        return sectors;
    }
}
