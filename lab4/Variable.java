package lab4;

public class Variable extends Node {
    String name;
    Double value = null;

    Variable (String name){
        this.name = name;
    }

    Variable (String name,Double value){
        this.name = name;
        this.sign = value<0?-1:1;
        this.value = value<0?-value:value;
    }

    public void setValue(Double value){
        this.sign = value<0?-1:1;
        this.value = value<0?-value:value;
    }

    @Override
    double evaluate(){
        return sign*value;
    }

    @Override
    public String toString(){
        String sgn = sign<0?"(-":"";
        return sgn+name+(sign<0?")":"");
    }

    @Override
    Node diff(Variable var) {
        if(var.name.equals(name))return new Constant(sign);
        else return new Constant(0);
    }

    @Override
    boolean isZero(Variable var){
        if(var.name.equals(name))return false;
        else return true;
    }
}
