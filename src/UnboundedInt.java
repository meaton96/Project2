public class UnboundedInt implements Cloneable {
    
    private int manyNodes;              //number of nodes in the number
    private IntNode head;               //first node
    private IntNode back;               //last node
    private IntNode cursor;             //current node for iterating all nodes
    
    /**
     * constructor
     * @precondition s is a string of numbers, manyNodes = 0, head, back, and cursor are null
     * @postcondition s has been turned into a collection of nodes representing 3 places of an integer
     * head points to the first node, back to the last node, cursor to the first node, head begins at the 0s place of the number
     * constructor, creates a new unbound from the supplied string
     * @param s a string of digits to make the integer from
     * @throws IllegalArgumentException if given string s is not a number
     */
    public UnboundedInt(String s) {
        
        manyNodes = 0;
        head = createNodes(s);
        cursor = head;
        if (head != null)                       //iterate the list to move the back to the end of the list
            while (hasLink())
                cursor = cursor.getLink();
        back = cursor;
        cursor = head;
        
    }
    
    /**
     * @precondition manyNodes = 0, head, back, and cursor are null
     * @postcondition head is null
     */
    public UnboundedInt() {
        head = null;
    }
    
    /**
     * creates int nodes from the given string of numbers
     * @param s the string of numbers to create the nodes from
     * @precondition manyNodes is 0, head is null, cursor is null, back is null
     * @postcondition head contains the first 3 digits of the number and points to the next node
     * cursor points to head, back points to the last number of the list
     * @throws IllegalArgumentException if the String passed in isn't a string of digits
     * @return returns in IntNode object that contains up to 3 digits of the number and a pointer to the next node in the list
     */
    private IntNode createNodes(String s) {
        
        if (s.length() == 0)                                                                //if s is empty just set head to null and finish
            return null;
        if (s.length() >= 3) {                                                              //if the length is higher than 3
            manyNodes++;                                                                    //increment many nodes since we are adding a node
            try {
                return new IntNode(                                                         //try creating a new node by parsing the int and
                        Integer.parseInt(s.substring(s.length() - 3)),                      //sending the string, less 3 digits, to this method to continue
                        createNodes(s.substring(0, s.length() - 3)));                       //creating more nodes
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Given string is not a number!");        //if there was an error parsing the string throw an error
            }
        }
        manyNodes++;                                                                        //the string is 1 or 2 digits long, so we are just adding 1 final node
        try {
            return new IntNode(Integer.parseInt(s), null);                                  //try adding a node by parsing int throw error if it doesnt work
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Given string is not a number!");
        }
        
    }
    
    /**
     * adds 2 UnboundInts together
     * @precondition other and this are not null
     * @postcondition a new UnboundInt has been created and returned as the sum
     * @param other the 2nd UnboundInt to add to this one
     * @return a new UnboundInt that is the sum of the two other UnboundInts
     */
    public UnboundedInt add(UnboundedInt other) {
        if (manyNodes == 0)                                                     //if one of the UnboundInts are empty just return the other one as the sum
            return other;
        if (other.manyNodes == 0)
            return this;
        
        UnboundedInt answer = new UnboundedInt();                               //create an answer UnboundInt and set both cursors to head node of respective Ints
        int curSum, carryOver = 0;
        start();
        other.start();
        
        int curNode = 0;
        int maxNodeSize = Math.max(manyNodes, other.manyNodes);                //set the node size to the same as the largest node size of the ints being added
        while (curNode < maxNodeSize) {                                        //iterate until you get to the end of the larger number being added
            curSum = 0;
            if (cursor != null) {                                              //if this int has a node then add its value to the current sum
                curSum += getNodeValue();
                advance();                                                      //and advance the cursor to the next node
            }
            if (other.cursor != null) {                                         //if the other int has a node then do the same
                curSum += other.getNodeValue();
                other.advance();
            }
            curSum += carryOver;                                                //add the carry over value if the previous sum was over 1000
            if (curSum > 999) {                                                 //set carry over if the current sum is over 1000
                curSum -= 1000;
                carryOver = 1;
            } else {
                carryOver = 0;
            }
            
            answer.addEnd(curSum);                                              //add the current sum to the end of the answer UnboundInt
            curNode++;                                                          //increment which node we are adding
        }
        if (carryOver == 1)                                                     //if there was a carry over from the final sum then add it to the end
            answer.addEnd(1);
        
        return answer;
    }
    
    /**
     * multiplies two UnboundInts together and returns the product
     * @param other the other integer to multiply by
     * @precondition other and this are not null
     * @postcondition a new UnboundInt has been created and returned as the product
     * @throws IllegalStateException if one of the ints has a null head node
     * @return the product of the two UnboundInts
     */
    public UnboundedInt multiply(UnboundedInt other) {
        if (head == null || other.head == null)                     //throw an error if one of the numbers has a null head node
            throw new IllegalStateException("Head is null");
        
        if (manyNodes == 1 || other.manyNodes == 1) {               //check if one of the numbers is 0
            if (head.getData() == 0 || other.head.getData() == 0)
                return new UnboundedInt();
        }
        start();
        other.start();
        
        UnboundedInt answer = new UnboundedInt();                               //create a new UnboundInt for the product
        for (int x = 0; x < manyNodes; x++) {                                   //iterate this numbers nodes and use the value of each to multiply the other number by that node value
            answer = answer.add(
                    scalarMultiply(other,
                            (int) (getNodeValue() * Math.pow(10, x * 3))));
            advance();                                                          //add the scalar product to the answer UnboundInt and advance the cursor
        }
        return answer;
    }
    
    /**
     * multiply an UnboundInt by a scalar value
     * @param multiplicand the UnboundInt to multiply
     * @param multiplier the value to multiply by
     * @return a new UnboundInt that is the product of the multiplicand and the multiplier
     */
    public static UnboundedInt scalarMultiply(UnboundedInt multiplicand, int multiplier) {
        UnboundedInt answer = new UnboundedInt();
        
        int currentProduct;
        int carryOver = 0;
        multiplicand.start();                                                   //set multiplicand cursor to the head node
        for (int x = 0 ; x < multiplicand.manyNodes; x++) {                     //iterate through all of the multiplicand's nodes
            currentProduct = multiplier * multiplicand.getNodeValue();          //multiply it by the scalar value
            
            currentProduct += carryOver;                                        //add the previous carry over value if there was one
            
            if (currentProduct > 1000) {                                        //if the current product is over 1000 then set the carry over to the value above 1000
                carryOver = currentProduct / 1000;                              //and set the current product to the remainder
                currentProduct %= 1000;
            } else {
                carryOver = 0;
            }
            
            answer.addEnd(currentProduct);                                      //add the current product to the end of the product
            multiplicand.advance();                                             //advance the cursor
            
        }
        if (carryOver > 0)                                                      //add the final carry over if there is one
            answer.addEnd(carryOver);
        
        return answer;
    }
    
    /**
     * add the integer value to the end of the of the int
     * @postcondition head is not null
     * @precondition a new IntNode has been added the end of the list with the value i and a pointer to null
     * @throws IllegalArgumentException if i is greater than 1000
     * @param i the integer to add to the end of the list
     */
    public void addEnd(int i) {
        if (i > 1000)
            throw new IllegalArgumentException("i is greater than max value able to store");
        
        if (head == null) {                         //if head is null add the value as a new head node
            head = new IntNode(i, null);
            back = head;
            cursor = head;
            return;
        }
        back.setLink(new IntNode(i, null));         //otherwise add the value as a new node to the end and set back to it
        back = back.getLink();
    }
    
    /**
     * clones the current string and returns it as a copy of the current UnboundInt
     * @return a copy of this class
     */
    public UnboundedInt clone() {
        return new UnboundedInt(this.toStringNoCommas());
    }
    
    /**
     * checks if two UnboundInts are equal
     * @param o the object to check if it is equal to this UnboundInt
     * @precondition o and this are not null
     * @postcondition
     * @throws IllegalStateException if o is null or if the head node of this int are null
     * @return true if they are equal false otherwise
     */
    public boolean equals(Object o) {
        if (head == null || o == null) {
            throw new IllegalStateException("One of the objects is null!");
        }
        if (o instanceof UnboundedInt) {                                    //o is an UnboundInt Object
            if (manyNodes == ((UnboundedInt) o).manyNodes) {                //the two UnboundInts have the same amount of nodes
                start();
                ((UnboundedInt) o).start();
                for (int x = 0; x < manyNodes; x++) {                               //iterate the nodes of both and check if their values are equal
                    if (cursor.getData() != ((UnboundedInt) o).cursor.getData())
                        return false;                                               //something wasn't equal
                }
                return true;                                                        //all values were equal
            }
            return false;                                                           //different number of nodes
        }
        return false;                                                               //o wasn't an UnboundInt object
    }
    
    /**
     * creates a string from the UnboundInt for printing, contains commas separating the values
     * @precondition head is not null
     * @postcondition a new string has been created nothing in the class has been changed
     * @return a string of numbers representing the values from this class
     */
    public String toString() {
        if (head == null)
            return "0";
        
        StringBuilder s = new StringBuilder(toStringNoCommas());
        s = s.reverse();                            //get a string from toStringNoCommas() and reverse it
        int x = 3;                                  //iterate it and add a comma every 3 places
        while (x < s.length()) {
            s.insert(x, ',');
            x += 4;
        }
        return s.reverse().toString();              //then reverse it back and return
    }
    
    /**
     * moves the cursor to the start of the list
     * @precondition head is not null
     * @postcondition cursor has been moved to the start of the number and is now equal to head
     */
    public void start() {
        cursor = head;
    }
    
    
    /**
     * advances the cursor node 1 node
     * @precondition cursor is not null
     * @postcondition cursor has been advanced by 1 place in the number
     */
    public void advance() {
        if (cursor == null) {
            cursor = head;
        }
        cursor = cursor.getLink();
    }
    
    
    /**
     * method to fetch node value of current cursor node
     * @precondition cursor is not null
     * @throws IllegalStateException if cursor is null
     * @return the integer value of the current cursor node
     */
    public int getNodeValue() {
        if (cursor == null)
            throw new IllegalStateException("Cursor is null");
        return cursor.getData();
    }
    
    
    /**
     * checks if the cursor has a link
     * @precondition cursor is not null
     * @throws IllegalStateException if cursor is null
     * @return true if cursor has a valid link false otherwise (cursor is at the end of the list)
     */
    private boolean hasLink() {
        if (cursor == null)
            throw new IllegalStateException("Cursor is null");
        return cursor.getLink() != null;
    }
    
    /**
     * creates a string of digits from the list of nodes without commas
     * @precondition head is not null
     * @return a string of digits from the nodes of this UnboundInt
     */
    public String toStringNoCommas() {
        if (head == null)                               //if head is null the number is 0
            return "0";
        StringBuilder s = new StringBuilder();
        IntNode cur = head;
        while (cur != null) {                           //iterate the list
            int num = cur.getData();
            if (num < 10)                               //depending on how many places are in the number
                s.insert(0, "00").insert(2, num);       //add it to the start of the String number with a
            else if (num < 100)                         //corresponding amount of 0s
                s.insert(0, "0").insert(1, num);
            else
                s.insert(0, num);
            cur = cur.getLink();                        //advance the current node
        }
        while (s.charAt(0) == '0') {                    //remove and 0s from the beginning of the number
            s.deleteCharAt(0);
        }
        return s.toString();
    }
}
