public class UnboundedInt implements Cloneable{

    private int manyNodes;
    private IntNode head;
    private IntNode back;
    private IntNode cursor;

    public UnboundedInt(String s) {

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
        if (s.length() >= 3)
            return new IntNode(
                    Integer.parseInt(s.substring(s.length() - 3)),
                    createNodes(s.substring(0, s.length() - 3)));

        return new IntNode(Integer.parseInt(s), null);

    }
    public UnboundedInt add(UnboundedInt other) {
        UnboundedInt answer = new UnboundedInt();
        int temp = 0, carryOver = 0;
        start();
        other.start();
        while (cursor != null && other.cursor != null) {
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
        }

        return answer;

    }
    public UnboundedInt multiply(UnboundedInt other) {

        UnboundedInt answer = new UnboundedInt();

        int temp = 0, carryOver = 0;
        start();
        other.start();

        while (cursor != null && other.cursor != null) {
            temp = getNodeValue() * other.getNodeValue();
            if (temp > 1000) {
                //TO FINISH
            }
        }

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
