package mapdemo;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MapDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Duration.ofMillis(100));
        Map<Long,Long> aa = new HashMap<>();
        for(long i=0; i< 100000; i++) {
            aa.put(i, i);
        }
        Thread.sleep(200000L);
    }
}
