package lab4;

public class Sin extends Node{
    Node arg = null;

    Sin(double value){
        arg = new Constant(value);
    }

    Sin(Node arg){
        this.arg = arg;
    }

    Sin(){}

    @Override
    double evaluate(){
        return Math.sin(arg.evaluate());
    }

    int getArgumentsCount(){
        return 1;
    }

    @Override
    Node diff(Variable var){
        Cos new_cos = new Cos(arg);
        return new_cos;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("sin(");
        str.append(arg.toString());
        str.append(")");
        return str.toString();
    }

    @Override
    boolean isZero(Variable var){
        if (arg.evaluate() == 0) return true;
        else return false;
    }
}
