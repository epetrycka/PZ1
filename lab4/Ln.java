package lab4;

public class Ln extends Node{
    Node arg;

    Ln(double value){
        arg = new Constant(value);
    }

    Ln(Node arg){
        this.arg = arg;
    }

    Ln(){}

    @Override
    double evaluate(){
        return Math.log(arg.evaluate());
    }

    int getArgumentsCount(){
        return 1;
    }

    @Override
    Node diff(Variable var){
        Prod prod = new Prod();
        Power ln_diff = new Power(arg, -1);
        prod.mul(ln_diff);
        prod.mul(arg.diff(var));
        return prod;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("ln(");
        str.append(arg.toString());
        str.append(")");
        return str.toString();
    }

    @Override
    boolean isZero(Variable var){
        if (arg.evaluate() == 1) return true;
        else return false;
    }
}
