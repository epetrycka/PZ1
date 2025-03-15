//package choinki;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Bucket implements XmasShape {
//    List<XmasShape> shapes = new ArrayList<>();
//    int x;
//    int y;
//    Bucket(int x,int y){
//        this.x=x;
//        this.y=y;
//        Random r = new Random();
//        for(int i=0;i<10;i++){
//            Bubble b = new Bubble();
//            b.x=r.nextInt(100);
//            b.y=r.nextInt(100);
//            b.scale=r.nextDouble()*0.2;
//            b.fillColor=new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
//            b.lineColor=new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
//            shapes.add(b);
//        }
//    }
//    @Override
//    public void transform(Graphics2D g2d) {
//        g2d.translate(x,y);
//    }
//
//    @Override
//    public void render(Graphics2D g2d) {
//        g2d.setColor(new Color(255, 90, 173));
//        g2d.fillRect(0,0,100,100);
//        for(var b:shapes)b.draw(g2d);
//    }
//}
