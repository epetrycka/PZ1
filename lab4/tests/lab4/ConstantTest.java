package lab4;

import org.junit.jupiter.api.Test;

import java.util.*;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

public class ConstantTest {

    @Test
    void buildAndPrint(){
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7);
        System.out.println(exp.toString());
    }

    double bisection(Node exp, Variable x, double a, double b, double eps){
        x.setValue(a);
        double f_a = exp.evaluate();
        x.setValue(b);
        double f_b = exp.evaluate();
        double c = 0, f_c;
        if (f_a*f_b>0){
            throw new RuntimeException("Function not solvable by this method");
        }
        c = (a+b)/2;
        while (abs(a-b)>eps){
            x.setValue(c);
            f_c = exp.evaluate();
            if (f_a*f_c<0){
                b = c;
            }
            if (f_b*f_c<0){
                a = c;
            }
            x.setValue(a);
            f_a = exp.evaluate();
            x.setValue(b);
            f_b = exp.evaluate();
            c = (a+b)/2;
        }
        return c;
    }

    @Test
    void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        for (double v = -5.0; v < 5.0; v += 0.1) {
            x.setValue(v);
            System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
        }

        double x0 = bisection(exp, x, -5.0, 5.0, 0.1);
        x.setValue((double) round(x0));
        double result = exp.evaluate();
        assertEquals(0.0, result, 0.1);
    }

    public static Map<String, Double> extractCoefficients(String expression){
        Map<String,Double> coefficients = new HashMap<>();

        Pattern pattern = Pattern.compile("(-?\\d*\\.?\\d+)\\s*\\*\\s*([xy])");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()){
            double coefficient = Double.parseDouble(matcher.group(1));
            String variable = matcher.group(2);
            coefficients.put(variable, coefficient);
        }
        System.out.println(coefficients);
        return coefficients;
    }

    public static Double extractConstantTerm(String expression){
        Double constant = 0.0;
        Pattern pattern = Pattern.compile("(-?\\d*\\.?\\d+)$");
        Matcher matcher = pattern.matcher(expression);

        if(matcher.find()){
            constant = Double.parseDouble(matcher.group());
        }
        System.out.println(constant);
        return constant;
    }

    @Test
    public void extractCoefTest() {
        String expression = "x^2.0 + y^2.0 + 8 * x + 4 * y + 16";
        Map<String, Double> coefficients = extractCoefficients(expression);

        System.out.println(coefficients); // Expected: {x=8.0, y=4.0}
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        String expression2 = circle.toString();
        Map<String, Double> coefficients2 = extractCoefficients(expression);
        System.out.println(coefficients2); // Expected: {x=8.0, y=4.0}
        Double constractTerm = extractConstantTerm(expression2);
        System.out.println(constractTerm);
    }

    List<SimpleEntry<Double, Double>> pointsInCircle(Node circle, Variable x, Variable y){
        String expression = circle.toString();
        Map<String, Double> center = extractCoefficients(expression);
        System.out.println(center);
        Double c = extractConstantTerm(expression);
        List<SimpleEntry<Double, Double>> points = new ArrayList<>();

        double a = center.get("x")/(-2);
        double b = center.get("y")/(-2);
        double r = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - c);

        double vx, vy;
        for (int i = 0; i < 100; i++) {
            vx = a + (2 * r * (Math.random() - 0.5));
            vy = b + (2 * r * (Math.random() - 0.5));
            x.setValue(vx);
            y.setValue(vy);
            points.add(new SimpleEntry<>(vx, vy));
            System.out.printf("Punkt %d: (%.2f, %.2f)\n", i, vx, vy);
        }
        return points;
    }

    @Test
    void defineCircle(){
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.println(circle.toString());

        double xv = 100*(Math.random()-0.5);
        double yv = 100*(Math.random()-0.5);
        x.setValue(xv);
        y.setValue(yv);
        double fv = circle.evaluate();
        System.out.print(String.format("Punkt (%f,%f) leży %s koła %s",xv,yv,(fv<0?"wewnątrz":"na zewnątrz"),circle.toString()));
        //List<SimpleEntry<Double, Double>> points = pointsInCircle(circle, x, y); // nie zawsze działa
        List<SimpleEntry<Double, Double>> points =new ArrayList<>();
        while (points.size()<100){
            xv = 100*(Math.random()-0.5);
            yv = 100*(Math.random()-0.5);
            x.setValue(xv);
            y.setValue(yv);
            fv = circle.evaluate();
            if (fv<0){
                points.add(new SimpleEntry<>(xv,yv));
                System.out.print(new SimpleEntry<>(xv,yv) + "\n");
            }
        }
    }
}