public class UnboundedInt implements Cloneable{

    private int manyNodes;
    private IntNode head;
    private IntNode back;
    private IntNode cursor;

    public UnboundedInt(String s) {

        manyNodes = 0;
        head = createNodes(s);
        cursor = head;
        if (head != null)
            while (hasLink())
                cursor = cursor.getLink();
        back = cursor;
        cursor = head;

    }
    public UnboundedInt() {
        head = null;
    }

    
    private IntNode createNodes(String s) {

        if (s.length() == 0)
            return null;
        if (s.length() >= 3) {
            manyNodes++;
            try {
                return new IntNode(
                        Integer.parseInt(s.substring(s.length() - 3)),
                        createNodes(s.substring(0, s.length() - 3)));
            }
            catch (NumberFormatException e) {
                System.out.println("String is not numbers!");
            }
        }
        manyNodes++;
        return new IntNode(Integer.parseInt(s), null);

    }
    public UnboundedInt add(UnboundedInt other) {
        
        
        if (equals(other)) {
            //
        }
        UnboundedInt answer = new UnboundedInt();
        int temp = 0, carryOver = 0;
        start();
        other.start();
        
        //check many nodes to see which number is bigger and loop through that one
        
       /* while (cursor != null && other.cursor != null) {
            temp = getNodeValue() + other.getNodeValue() + carryOver;
            if (temp > 999) {
                carryOver = 1;
                temp -= 1000;
            } else {
                carryOver = 0;
            }
            answer.addEnd(temp);
            advance();
            other.advance();
        }
        while (cursor != null || other.cursor != null) {
            if (cursor != null) {
                answer.addEnd(getNodeValue());
                advance();
            }
            else {
                answer.addEnd(other.getNodeValue());
                other.advance();
            }
        }*/

        int curNode = 0;
        while (curNode < Math.max(manyNodes, other.manyNodes)) {
            temp = 0;
            if (cursor != null) {
                temp += getNodeValue();
                advance();
            }
            if (other.cursor != null) {
                temp += other.getNodeValue();
                other.advance();
            }
            temp += carryOver;
            if (temp < 999) {
                temp -= 1000;
                carryOver = 1;
            } else {
                carryOver = 0;
            }
            answer.addEnd(temp);
            curNode++;
        }
        
        
        return answer;

    }
    public UnboundedInt multiply(UnboundedInt other) {

        if (head == null || other.head == null)
            throw new IllegalStateException("Head is null");
        
        if (manyNodes == 1 || other.manyNodes == 1) {
            if (head.getData() == 0 || other.head.getData() == 0)
                return new UnboundedInt();
        }
        
        UnboundedInt answer = new UnboundedInt();
        
        
        start();
        other.start();

        int place = 0;
        

        return answer;
    }
    public void addEnd(int i) {
        if (head == null) {
            head = new IntNode(i, null);
            back = head;
            cursor = head;
            return;
        }
        back.setLink(new IntNode(i, null));
        back = back.getLink();
    }

    public UnboundedInt clone() {
        return null;
    }
    public boolean equals(Object o) {
        return false;
    }
    public String toString() {
        if (head == null)
            return "0";
        StringBuilder s = new StringBuilder(toStringNoCommas());
        s = s.reverse();
        int x = 3;
        while (x < s.length()) {
            s.insert(x, ',');
            x += 4;
        }
        return s.reverse().toString();
    }
    public void start() {
        cursor = head;
    };
    public void advance() {
        cursor = cursor.getLink();
    };
    public int getNodeValue() {
        return cursor.getData();
    };
    private boolean hasLink() {
        return cursor.getLink() != null;
    }
    public String toStringNoCommas() {
        if (head == null)
            return "0";
        StringBuilder s = new StringBuilder();
        IntNode cur = head;
        while (cur != null) {
            int num = cur.getData();
            if (num < 10)
                s.insert(0, "00").insert(2, num);
            else if (num < 100)
                s.insert(0, "0").insert(1, num);
            else
                s.insert(0, num);
            cur = cur.getLink();
        }
        while (s.charAt(0) == '0')
            s = s.deleteCharAt(0);
        return s.toString();
    };


}
