package lab4;

import java.util.List;
import java.util.ArrayList;

public class Prod extends Node {
    List<Node> args = new ArrayList<Node>();
    Constant constant = null;

    Prod (List<Node> args){
        this.args = args;
    }

    Prod (double val, Node n){
        args.add(new Constant(val));
        args.add(n);
    }

    Prod(){}

    Prod(Node n){
        args.add(n);
    }

    Prod(double val){
        args.add(new Constant(val));
    }

    Prod (Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }

    Prod mul(double val){
        args.add(new Constant(val));
        return this;
    }

    Prod mul(Node n){
        args.add(n);
        return this;
    }

    Prod changeValue(Prod prod2){
        this.args = prod2.args;
        return this;
    }

    int getArgumentsCount(){return args.size();}


    Prod simplify(){
        Prod simplified = new Prod();
        double result = 1;
        if (this.constant != null){
            result = this.constant.evaluate();
        }
        if (!args.isEmpty()){
            for (Node n: args) {
                if (n instanceof Constant) {
                    result *= n.evaluate();
                }
                else if (n instanceof Prod) {
                    Prod in_prod = ((Prod) n).simplify();
                    if (in_prod.constant != null) {
                        result *= in_prod.constant.evaluate();
                    }
                    simplified.mul(new Prod(in_prod.args));
                }
                else {
                    simplified.mul(n);
                }
            }
        }
        if (result != 1) {
            simplified.constant = new Constant(result);
        }
        return simplified;
    }


    @Override
    double evaluate(){
        double result = 1.0;
        for(Node n : args){
            result *= n.evaluate();
        }
        if (constant != null){
            result *= constant.evaluate();
        }
        return result;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        if (sign<0){s.append("-(");}
        if (constant != null){
            s.append(constant);
            if (!args.isEmpty()){
                s.append("*");
            }
        }
        if (!args.isEmpty()){
            for (int i=0; i<args.size()-1; i++){
                s.append(args.get(i).toString());
                s.append("*");
            }
            s.append(args.get(args.size()-1).toString());
        }
        if(sign<0){s.append(")");}
        return s.toString();
    }

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        boolean isZero = false;
        for (int i = 0; i < args.size(); i++) {
            Prod m = new Prod();
            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);
                if (j == i) {
                    m.mul(f.diff(var));
                    isZero = f.isZero(var);
                }
                else m.mul(f);
            }
            if (!isZero) {
                r.add(m.simplify());
            }
        }
        return r;
    }

    @Override
    boolean isZero(Variable var) {
        boolean isZero = false;
        for (int i = 0; i < args.size(); i++) {
            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);
                if (j == i) {
                    isZero = f.isZero(var);
                }
            }
        }
        return isZero;
    }
}
