import java.util.Scanner;
import Calculate.Calculate;

public class Main {

    private static String input() {
        System.out.print('>');
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void main(String[] args){
        Calculate calculate = new Calculate();
        while (true){
            String equ = input();
            if (equ.equals("q")) break;
            System.out.println(calculate.calculate(equ));
        }
    }
}
