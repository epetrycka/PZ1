package lab4;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node {
    double value;

    Constant(double value) {
        this.sign = value<0?-1:1;
        this.value = value<0?-value:value;
    }

    Constant setValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    double evaluate(){
        return sign*value;
    }

    @Override
    public String toString(){
        StringBuilder sgn = new StringBuilder();
        sgn.append(sign<0?"(-":"");
        DecimalFormat decimal = new DecimalFormat("0.####", new DecimalFormatSymbols(Locale.US));
        sgn.append(decimal.format(value));
        sgn.append(sign<0?")":"");
        return sgn.toString();
    }

    @Override
    Node diff(Variable var) {
        return new Constant(0);
    }

    @Override
    boolean isZero(Variable var){
        return true;
    }


}