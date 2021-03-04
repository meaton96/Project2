public class LargeNumberTest {

    public static void main(String[] args) {


        UnboundedInt int1 = new UnboundedInt("1000");
        UnboundedInt int2 = new UnboundedInt("2500");
       // UnboundedInt int3 = new UnboundedInt();
        int int3 = 998001;
        System.out.println(int1.add(int2));
    }
}
