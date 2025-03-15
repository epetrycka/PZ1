package lab4;

public class Exp extends Node{
    Node arg;

    Exp(){
        this.arg = new Constant(0);
    }

    Exp(Node n){
        this.arg = n;
    }

    int getArgumentsCount(){return 1;}

    @Override
    double evaluate(){
        return Math.exp(arg.evaluate());
    }

    @Override
    Node diff(Variable var){
        return new Exp(arg.diff(var));
    }

    @Override
    boolean isZero(Variable var){
        return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("e^");
        s.append(arg.toString());
        return s.toString();
    }
}
