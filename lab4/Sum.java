package lab4;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {
    List<Node> args = new ArrayList<Node>();

    Sum(){};

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }

    Sum add(Node n){
        args.add(n);
        return this;
    }

    Sum add(double val){
        args.add(new Constant(val));
        return this;
    }

    Sum add(double val, Node n){
        Node mul = new Prod(val,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate(){
        double result = 0;
        for (Node n : args){
            result += n.evaluate();
        }
        return sign*result;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        if(sign<0){s.append("-(");}
        for (int i=0; i<args.size()-1; i++){
            s.append(args.get(i).toString());
            s.append(" + ");
        }
        if (args.size()>0){
            s.append(args.get(args.size()-1).toString());
        }
        if(sign<0){s.append(")");}
        return s.toString();
    }

    int getArgumentsCount(){return args.size();}

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        for(Node n:args){
            if (!n.isZero(var)){
                r.add(n.diff(var));
            }
        }
        return r;
    }

    @Override
    boolean isZero(Variable var) {
        return false;
    }
}
