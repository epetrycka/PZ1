package lab1_1;
import java.util.Scanner;
import java.util.Arrays;

public class Problem115A {
    public static void main(String[] args)
    {
        System.out.println("Enter the number of employees: ");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        System.out.println("Enter the integers pi: ");
        int[] array = new int[n];
        for(int i=0; i<n; i++)
        {
            int superior = scan.nextInt();
            if (superior > 0){array[superior]++;}
        }
        int maxValue = Arrays.stream(array).max().orElseThrow();
        maxValue++;
        System.out.println("The number of groups: " + maxValue);
    }
}
