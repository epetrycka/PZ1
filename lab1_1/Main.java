import java.util.Locale;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        scan.useLocale(Locale.US);
        String s = scan.next();
        int i = scan.nextInt();
        double d = scan.nextDouble();
        System.out.printf(Locale.US, "Wczytano %s , %d , %f", s, i, d);
    }
}
