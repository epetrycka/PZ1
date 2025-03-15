package lab4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiffTests {
    @Test
    void diffPoly() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7);
        System.out.print("exp=");
        System.out.println(exp.toString());

        Node d = exp.diff(x);
        System.out.print("d(exp)/dx=");
        System.out.println(d.toString());

    }

    @Test
    void diffCircle() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.print("f(x,y)=");
        System.out.println(circle.toString());

        Node dx = circle.diff(x);
        System.out.print("d f(x,y)/dx=");
        System.out.println(dx.toString());
        System.out.print("d f(x,y)/dy=");
        Node dy = circle.diff(y);
        System.out.println(dy.toString());
    }

    @Test
    void simplifyTest() {
        Variable x = new Variable("x");
        Prod prodtest =  new Prod(x);
        prodtest.mul(new Constant(5))
                .mul(new Variable("y"))
                .mul(new Constant(4));
        System.out.println(prodtest.simplify().toString());

        Prod prodtest2 =  new Prod();
        prodtest2.mul(new Constant(3))
                .mul(new Variable("y"))
                .mul(new Power(x,2))
                .mul(new Constant(1))
                .mul(new Constant(2));
        System.out.println(prodtest2.simplify().toString());

    }
}