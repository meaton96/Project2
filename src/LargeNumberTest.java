import java.util.Scanner;

public class LargeNumberTest {

    private static UnboundedInt int1 = null, int2 = null;


    public static void main(String[] args) {

        UnboundedInt cloneObject;
        readInNewInts();
        int menuChoice = 0;
        while (menuChoice != 7) {
            menuChoice = displayMenu();
            switch (menuChoice) {
                case 1 : System.out.println(int1 + "\n" + int2);
                break;
                case 2 : readInNewInts();
                break;
                case 3 : System.out.println(int1 + "=" + int2 + "\nis " + int1.equals(int2));
                break;
                case 4 : System.out.println("The sum of the numbers = " + int1.add(int2));
                break;
                case 5 : System.out.println("The product of the numbers = " + int1.multiply(int2));
                break;
                case 6 : cloneObject = int1.clone();
                         System.out.println(cloneObject);
                break;
                default :
                break;
            }
            System.out.println("____________________________________________________");
        }
    }

    public static int displayMenu() {
        System.out.println("1. Display both integers" +
                "\n2. Enter new numbers" +
                "\n3. Check if the numbers are equal" +
                "\n4. Get the sum of the numbers" +
                "\n5. Get the product of the numbers" +
                "\n6. Clone the first number" +
                "\n7. Quit");
        return new Scanner(System.in).nextInt();
    }

    public static void readInNewInts() {
        int1 = null;
        int2 = null;
        Scanner sc = new Scanner(System.in);

        String firstLast = "first";
        while (int1 == null || int2 == null) {

            System.out.printf("Enter %s value: ", firstLast);
            String num = sc.next();
            try {
                if (int1 == null) {
                    int1 = new UnboundedInt(num);
                    firstLast = "second";
                } else {
                    int2 = new UnboundedInt(num);
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
}
