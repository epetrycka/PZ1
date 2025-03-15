package lab4;

public class Power extends Node {
    Node arg;
    double power;

    Power(Node arg, double power) {
        this.arg = arg;
        this.power = power;
    }

    int getArgumentsCount(){
    return 1;
    }

    @Override
    double evaluate(){
        return Math.pow(arg.evaluate(), power);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.sign == -1){
            s.append("-");
        }
        boolean useBrackets = false;
        if (arg.sign < 0 || arg.sign > 1){useBrackets = true;}
        if (useBrackets){s.append("(");}
        s.append(arg.toString());
        if (useBrackets){s.append(")");}
        s.append("^");
        s.append(power);
        return s.toString();
    }

    @Override
    Node diff(Variable var) {
        Prod r =  new Prod(sign*power,new Power(arg,power-1));
        r.mul(arg.diff(var));
        return r.simplify();
    }

    @Override
    boolean isZero(Variable var){
        return arg.isZero(var);
    }
}
