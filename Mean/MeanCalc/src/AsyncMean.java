import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncMean {
    public static final int SIZE = 100_000_000;
    static double[] array;
    static void initArray(int size) {
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * size / (i + 1);
        }
    }

    static class MeanCalcSupplier implements Supplier<Double> {
        private int start;
        private int end;
        MeanCalcSupplier(int start, int end){
            this.end = end;
            this.start = start;
        }
        @Override
        public Double get() {
            double mean=0;
            for (int i=start; i<=end; i++){
                mean += array[i];
            }
            mean /= (end-start);
            //System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
            return mean;
        }
    }

    public static void asyncMeanv1() {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        int n = 10;
        List<CompletableFuture<Double>> partialResults = new ArrayList<>();
        int batch_size = SIZE/n;
        double t1 = System.nanoTime()/1e6;
        for(int i=0;i<n;i++){
            CompletableFuture<Double> partialMean = CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i*batch_size,(i+1)*batch_size-1),executor);
            partialResults.add(partialMean);
        }
        double mean=0;
        double t2 = System.nanoTime()/1e6;
        for(var pr:partialResults){
           mean += pr.join();
        }
        mean /= n;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=10 >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                t2-t1,
                t3-t1,
                mean);
        //System.out.printf(Locale.US,"mean=%f\n",mean);

        executor.shutdown();
    }

    static void asyncMeanv2() {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        int n=10;
        int batch_size = SIZE/n;
        BlockingQueue<Double> queue = new ArrayBlockingQueue<>(n);
        double t1 = System.nanoTime()/1e6;
        for (int i = 0; i < n; i++) {
            CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i*batch_size,(i+1)*batch_size-1), executor)
            .thenApply(d -> queue.offer(d));
        }
        double mean=0;
        double t2 = System.nanoTime()/1e6;
        for (int i = 0; i < n; i++) {
            try {
                mean += queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        mean /= n;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=10 >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                t2-t1,
                t3-t1,
                mean);
//        System.out.printf(Locale.US,"mean=%f\n", mean);

        executor.shutdown();
    }

    public static void main(String[] args) {
        initArray(SIZE);
        asyncMeanv1();
        asyncMeanv2();
    }
}
