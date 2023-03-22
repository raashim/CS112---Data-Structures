package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

    /* Your code goes here */
    ArrayList<CharFreq> temp = new ArrayList<CharFreq>(); 
    int [] charArr  = new int[128]; 
    int count = 0; 
    //you have to keep track of the count because the probability of occurence of that letter 
    //is that specific character divided by the total number of characters in the file

    //reads all the characters from the file and uses char as an index to store them
    //it autmatically converts the char to the corresponding ASCII int value 
   while(StdIn.hasNextChar()) {
    charArr[StdIn.readChar()]++; //adding the current char (converted to ASCII) to the charArr 
    count++;  
   }

   //adds all the elements with frequency > 0 to the temp array
    for(char i = 0; i < charArr.length; i++) {
      //  System.out.println(i);
        if(charArr[i] != 0) {
         double prob = (double)charArr[i]/count; 
         CharFreq toAdd3 = new CharFreq(i, prob); 
         temp.add(toAdd3); }
    } 

    if(temp.size() == 1) { //there is only one character in the list
        //gets the next character from the list of ASCII by adding one to the index and 
        //moduling it by 128, which gets you the reminder, which is essentially the next character
        char oneUp = ((char)((temp.get(0).getCharacter()+ 1)%128));
        CharFreq toAdd = new CharFreq(oneUp, 0.0); 
        temp.add(toAdd); 
    }
 sortedCharFreqList = temp;
 Collections.sort(sortedCharFreqList); 
    }


    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

	/* Your code goes here */
    Queue<TreeNode> source = new Queue<TreeNode>(); 
    Queue<TreeNode> target = new Queue<TreeNode>(); 

    for(int i = 0; i < sortedCharFreqList.size(); i++) {
        TreeNode add = new TreeNode(sortedCharFreqList.get(i), null, null); 
        source.enqueue(add);
    }

     //TreeNode targetFirst; //first node in the target queue
   // TreeNode sourceFirst; //first node in the source queue
   TreeNode temp1 = new TreeNode(); 
  TreeNode temp2 = new TreeNode(); 
   
    
   while(!(source.isEmpty() && target.size()==1)) 
    { //while the source queue is not empty and target queue doesn't only contain one tree
      //  targetFirst = target.peek(); //gets the first node from the target queue
     // sourceFirst = source.peek(); //get the first node from the source queue 

     if(source.isEmpty() == false && (target.isEmpty() == true || (source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc())))
        {
            temp1 = source.dequeue(); 
        }
        else 
        {
            temp1 = target.dequeue(); 
        }

        if( (target.isEmpty() == false && source.isEmpty() == false) && source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc())  
        {
            temp2 = source.dequeue();
        }
        else if ( (!target.isEmpty() == true && source.isEmpty() == false) && source.peek().getData().getProbOcc() > target.peek().getData().getProbOcc()) 
        {
            temp2 = target.dequeue(); 
        }
        else if(source.isEmpty() == true && target.isEmpty() == false) 
        {
            temp2 = target.dequeue(); 
        }
        else if(target.isEmpty() == true && source.isEmpty() == false) 
        {
            temp2 = source.dequeue();
        }

        double prob1 = temp1.getData().getProbOcc(); 
        double prob2 = temp2.getData().getProbOcc(); 
        double combinedProb = prob1 + prob2; 

        CharFreq character = new CharFreq(null, combinedProb); 
        
        target.enqueue(new TreeNode(character, temp1, temp2));
    }
    
    huffmanRoot = target.dequeue(); 
} 

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

	/* Your code goes here */
    String[] characters = new String[128]; 
    String blank = new String(); 
    //you can start with any number because it doesn't matter the first time around
    //since its a recursive method, the value for leftRight will end up changing 
    //according to whether the node is towards the left or the right
    inOrderTraversal(huffmanRoot, 18, blank, characters); 
    encodings = characters; 
    
}
    //need a node cause thats what the tree is made up of, an empty string to store the 0/1 which needs to be added according to if we're going right or left 
    //need an Strings array to store the bitValue, and need a integer to figure out whether we went left or right 
    private void inOrderTraversal(TreeNode node, int leftRight, String empty, String[] storeBit ) { 
        if(leftRight == 0) {
            empty += 0; 
        }
        else if(leftRight == 1) {
            empty += 1; 
        }

        if(node == null) { //reached the end of the tree/null link/reached the last node
            return; 
        }
        else {
            if(node.getData().getCharacter() != null) {
                storeBit[node.getData().getCharacter()] = empty; 
                empty = null; //make the string null again so that it can store the next bit number correctly 
                return; 
            }
        }

        inOrderTraversal(node.getLeft(), 0, empty, storeBit); 
        inOrderTraversal(node.getRight(), 1, empty, storeBit); 
    }



    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	/* Your code goes here */
    String temp = ""; 

    while(StdIn.hasNextChar()) {
        char character = StdIn.readChar(); //reading the current letter/symbol from the file 
        String empty = encodings[character]; 
        temp += empty; 
    }
    writeBitString(encodedFile, temp);
}
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
       	/* Your code goes here */
        StdOut.setFile(decodedFile);

        String decode = readBitString(encodedFile); 
        TreeNode pointer = huffmanRoot;  
        String temp = new String();

        for(int i = 0; i < decode.length(); i++) 
        {
            if(decode.charAt(i) == '0') 
            {
                pointer = pointer.getLeft(); 
            }
            else if(decode.charAt(i) == '1')
            {
                pointer = pointer.getRight(); 
            }

            if(pointer.getData().getCharacter() != null) 
            {
                // System.out.print(pointer.getData().getCharacter());
                temp = temp + pointer.getData().getCharacter();
                pointer = huffmanRoot; //reset pointer to the original root so that it is in place to traverse the next character
            }
        }
       //System.out.print(temp);
       StdOut.print(temp);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
        
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
