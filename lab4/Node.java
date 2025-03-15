package lab4;

abstract public class Node {
    int sign = 1;

    Node minus(){
        this.sign = -1;
        return this;
    }

    Node plus(){
        this.sign = 1;
        return this;
    }

    int getSign(){
        return this.sign;
    }

    abstract double evaluate();

    public String toString(){return "";}

    int getArgumentsCount(){return 0;}

    abstract Node diff(Variable var);

    abstract boolean isZero(Variable var);
}
