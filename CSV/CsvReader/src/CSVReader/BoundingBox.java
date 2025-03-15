package CSVReader;

import java.util.Locale;

public class BoundingBox {
    private double xmin = 0;
    private double ymin = 0;
    private double xmax = 0;
    private double ymax = 0;
    private boolean empty = true;

    public BoundingBox(){
        this.empty = true;
    }

    public String getWKT() {
        if (isEmpty()) {
            return "GEOMETRYCOLLECTION EMPTY";
        }

        return String.format(
                Locale.US,
                "POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f))\n",
                xmin, ymin,
                xmin, ymax,
                xmax, ymax,
                xmax, ymin,
                xmin, ymin
        );
    }


    void addPoint(Double x, Double y){
        if (x == null || y == null){
            return;
        }
        if (isEmpty()) {
            xmin = xmax = x;
            ymin = ymax = y;
            empty = false;
        } else {
            xmin = Math.min(xmin, x);
            ymin = Math.min(ymin, y);
            xmax = Math.max(xmax, x);
            ymax = Math.max(ymax, y);
        }
    }

    boolean isEmpty(){
        return empty;
    }

    boolean contains(double x, double y){
        if (isEmpty()) return false;
        return x >= xmin && x <= xmax && y >= ymin && y <= ymax;
    }

    boolean contains(BoundingBox bb){
        if (this.empty || bb.isEmpty()) return false;
        return bb.xmin >= this.xmin && bb.xmax <= this.xmax &&
                bb.ymin >= this.ymin && bb.ymax <= this.ymax;
    }

    boolean intersects(BoundingBox bb){
        if (this.empty || bb.isEmpty()) return false;
        return this.xmax >= bb.xmin && this.xmin <= bb.xmax &&
                this.ymax >= bb.ymin && this.ymin <= bb.ymax;
    }

    BoundingBox add(BoundingBox bb){
        if (bb.isEmpty()) return this;
        if (isEmpty()) {
            this.xmin = bb.xmin;
            this.ymin = bb.ymin;
            this.xmax = bb.xmax;
            this.ymax = bb.ymax;
            this.empty = false;
        } else {
            this.xmin = Math.min(this.xmin, bb.xmin);
            this.ymin = Math.min(this.ymin, bb.ymin);
            this.xmax = Math.max(this.xmax, bb.xmax);
            this.ymax = Math.max(this.ymax, bb.ymax);
        }
        return this;
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox check = (BoundingBox) o;
        if (this.empty && check.empty) return true;
        return Double.compare(check.xmin, xmin) == 0 &&
                Double.compare(check.ymin, ymin) == 0 &&
                Double.compare(check.xmax, xmax) == 0 &&
                Double.compare(check.ymax, ymax) == 0;
    }

    Double getCenterX(){
        if (isEmpty()) throw new IllegalStateException("BoundingBox is empty");
        return (xmin + xmax) / 2;
    }

    Double getCenterY(){
        if (isEmpty()) throw new IllegalStateException("BoundingBox is empty");
        return (ymin + ymax) / 2;
    }

    double distanceTo(BoundingBox bbx){
        if (this.isEmpty() || bbx.isEmpty()) throw new IllegalStateException("BoundingBox is empty");

        final double R = 6371e3;
        double lat1 = Math.toRadians(this.getCenterY());
        double lon1 = Math.toRadians(this.getCenterX());
        double lat2 = Math.toRadians(bbx.getCenterY());
        double lon2 = Math.toRadians(bbx.getCenterX());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    static BoundingBox copy (BoundingBox box){
        BoundingBox copy = new BoundingBox();
        copy.xmin = box.xmin;
        copy.xmax = box.xmax;
        copy.ymin = box.ymin;
        copy.ymax = box.ymax;
        copy.empty = box.empty;
        return copy;
    }

    // funkcja przyjmuje distance czyli o ile kilometrów od środka box, box ma się zwiększyć swój rozmiar(zakres)
    static BoundingBox resize(BoundingBox box, double distance) {
        if (box == null || box.isEmpty()) {
            throw new IllegalArgumentException("BoundingBox is null or empty");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must be non-negative");
        }

        BoundingBox resized = BoundingBox.copy(box);

        final double stopienSzerokosciGeog = 111.32;
        final double srodekSzerokosci = (box.ymin + box.ymax) / 2;

        double przesuniecieSzerokosci = distance / stopienSzerokosciGeog;
        double stopienDlugosciGeog = stopienSzerokosciGeog * Math.cos(Math.toRadians(srodekSzerokosci));
        double przesuniecieDlugosci = distance / stopienDlugosciGeog;

        resized.xmin -= przesuniecieDlugosci;
        resized.xmax += przesuniecieDlugosci;
        resized.ymin -= przesuniecieSzerokosci;
        resized.ymax += przesuniecieSzerokosci;

        return resized;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("xmin: " + xmin + "\n");
        sb.append("ymin: " + ymin + "\n");
        sb.append("xmax: " + xmax + "\n");
        sb.append("ymax: " + ymax + "\n");
        return sb.toString();
    }
}