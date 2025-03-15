package histogram;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class histogram {

    static int[] array;

    static void initArray(int size){
        array = new int[size];
        var random = new Random();
        for(int i=0;i<size;i++){
            array[i]= random.nextInt(256);
        }
    }

    static BlockingQueue<double[]> queue = new ArrayBlockingQueue<>(10);

    static class HistogramThread extends Thread{
        private final int start;
        private final int end;
        private double mean = 0;

        HistogramThread(int start, int end){
            this.start = start;
            this.end=end;
        }

        public void run(){

        }
    }
}
