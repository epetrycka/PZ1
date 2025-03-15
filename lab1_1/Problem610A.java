package lab1_1;
import java.util.Scanner;
import java.util.Locale;

public class Problem610A {
    public static void main(String[] args) {
        System.out.println("Enter the length of the stick: ");
        Scanner scan = new Scanner(System.in);
        int length, possibilites=0;

        if (scan.hasNextInt())
        {
            length = scan.nextInt();
        } else {System.out.println("Invalid length"); return;}

        if(length%2 != 0 || length<4)
        {
            System.out.println("Too small length or length is it's an odd number");
            return;
        }

        for(int i=1; i<(int)Math.ceil((double) length/4); i++)
        {
            if ((length-(2*i))%2==0)
            {
                possibilites++;
            }
        }

        System.out.println("Possibilites: " + possibilites);
    }
}
