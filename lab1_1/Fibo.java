package lab1_1;
import java.util.Scanner;
import java.util.Locale;

public class Fibo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number between 1 and 45: ");
        int number = scan.nextInt();
        if (number < 1 || number > 45)
        {
            System.out.print("Invalid number");
            return;
        }

        int[] tab = new int[number];
        tab[0] = 1;
        if (number > 1) {tab[1] = 1;}

        for(int i=2; i<number; i++)
        {
            tab[i] = tab[i-1] + tab[i-2];
        }

        System.out.println("Fibo Values: ");
        for(int i=0; i<number; i++)
        {
            System.out.print(tab[i] + ", ");
        }
    }
}
