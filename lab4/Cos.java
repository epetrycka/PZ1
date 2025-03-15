package lab4;

public class Cos extends Node{
    Node arg = null;

    Cos(double value){
        arg = new Constant(value);
    }

    Cos(Node arg){
        this.arg = arg;
    }

    Cos(){}

    @Override
    double evaluate(){
        return Math.cos(arg.evaluate());
    }

    int getArgumentsCount(){
        return 1;
    }

    @Override
    Node diff(Variable var){
        Sin new_sin = new Sin(arg);
        new_sin.minus();
        return new_sin;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("cos(");
        str.append(arg.toString());
        str.append(")");
        return str.toString();
    }

    @Override
    boolean isZero(Variable var){
        if (Math.abs(arg.evaluate()) == Math.PI/2) return true;
        else return false;
    }
}