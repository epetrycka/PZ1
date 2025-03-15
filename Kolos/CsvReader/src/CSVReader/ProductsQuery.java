package CSVReader;

import java.util.Comparator;
import java.util.function.Predicate;

public class ProductsQuery {
    ProductsList src;
    Predicate<Product> p = a->true;
    Comparator<Product> cmp;
    int limit = Integer.MAX_VALUE;
    int offset = 0;

    ProductsQuery selectFrom(ProductsList src){
        this.src = src;
        return this;
    }

    ProductsQuery where(Predicate<Product> pred){
        this.p = pred;
        return this;
    }

    ProductsQuery and(Predicate<Product> pred){
        this.p = pred.and(p);
        return this;
    }

    ProductsQuery or(Predicate<Product> pred){
        this.p = p.or(pred);
        return this;
    }

    ProductsQuery sort(Comparator<Product> cmp){
        this.cmp = cmp;
        return this;
    }

    ProductsQuery limit(int limit){
        this.limit = limit;
        return this;
    }

    ProductsQuery offset(int offset){
        this.offset = offset;
        return this;
    }

    ProductsList execute(){
        ProductsList result = src.filter(p);
        if (cmp != null) {
            result.sortInplace(cmp);
        }
        return result.filter(a -> true, offset, limit);
    }
}
