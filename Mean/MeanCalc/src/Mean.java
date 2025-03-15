import java.util.Locale;
import java.util.concurrent.*;

public class Mean {
    public static final int SIZE = 128000000;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);
    static double[] array;
    static void initArray(int size) {
        array = new double[size];
        for (int i=0; i<size; i++){
            array[i] = Math.random()*size/(i+1);
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end=end;
        }

        public void run(){
            for (int i=start; i<=end; i++){
                mean += array[i];
            }
            mean /= (end-start);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
        }
    }

    /**
     * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
     * Wypisuje czasy operacji
     * @param cnt - liczba wątków
     */
    static void parallelMean(int cnt) throws InterruptedException {
        MeanCalc threads[] = new MeanCalc[cnt];
        int frame = SIZE/cnt;
        for (int i=0; i<cnt; i++){
            threads[i] = new MeanCalc(i*frame,(i+1)*frame-1);
        }
        double mean = 0;
        double t1 = System.nanoTime()/1e6;
        for(MeanCalc mc:threads) {
            mc.run();
            mc.join();
        }
        double t2 = System.nanoTime()/1e6;
        for(MeanCalc mc:threads) {
            mean += mc.mean;
        }
        mean /= cnt;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }

    static void parallelMeanV2(int cnt) throws InterruptedException {
        MeanCalc threads[] = new MeanCalc[cnt];
        int frame = SIZE/cnt;
        for (int i=0; i<cnt; i++){
            threads[i] = new MeanCalc(i*frame,(i+1)*frame-1);
        }
        double mean = 0;
        double t1 = System.nanoTime()/1e6;
        for(MeanCalc mc:threads) {
            mc.run();
        }
        double t2 = System.nanoTime()/1e6;
        for (int i=0; i<cnt; i++){
            mean += results.take();
        }
        mean /= cnt;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }

    static void parallelMeanV3(int cnt) throws InterruptedException {
        MeanCalc threads[] = new MeanCalc[cnt];
        ExecutorService executor = Executors.newFixedThreadPool(cnt);
        int frame = SIZE / cnt;
        for (int i = 0; i < cnt; i++) {
            threads[i] = new MeanCalc(i * frame, (i + 1) * frame - 1);
        }
        double mean = 0;
        double t1 = System.nanoTime() / 1e6;
        for (MeanCalc mc : threads) {
            executor.execute(mc);
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        double t2 = System.nanoTime() / 1e6;
        for (int i = 0; i < cnt; i++) {
            mean += results.take();
        }
        mean /= cnt;
        double t3 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }


    public static void main(String[] args) throws InterruptedException {
        initArray(SIZE);
        for(int cnt:new int[]{1,2,4,8,16,32}){
            parallelMean(cnt);
        }
        for(int cnt:new int[]{1,2,4,8,16,32}){
            parallelMeanV2(cnt);
        }
        for(int cnt:new int[]{1,2,4,8,16,32}){
            parallelMeanV3(cnt);
        }
    }
}
