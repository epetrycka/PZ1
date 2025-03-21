package zegar;

import java.time.LocalTime;

public class Clock extends Thread{
    @Override
    public void run() {
        while(true){
            LocalTime time = LocalTime.now();
            System.out.printf("%02d:%02d:%02d\n",
                    time.getHour(),
                    time.getMinute(),
                    time.getSecond());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
