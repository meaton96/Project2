public class LargeNumberTest {

    public static void main(String[] args) {


        UnboundedInt int1 = new UnboundedInt("509");
        UnboundedInt int2 = new UnboundedInt("500");
       // UnboundedInt int3 = new UnboundedInt();
        int int3 = 998001;
        System.out.println(int1.equals(int2));
    }
}
