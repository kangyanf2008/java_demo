package timer;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleAtFixedRate {
    public static void main(String[] args) throws IOException {
        //Timer timer = new Timer("my_timer", true);
        //timer.schedule(new MyTimerTask(timer), 10000);
        //timer.scheduleAtFixedRate(new MyTimerTask2(), 5000, 5000);
        HashMap<String, Map<String, String>> body = new HashMap<>();
        Map<String, String> notifySubInfo = body.computeIfAbsent("aaa", key -> new HashMap<>());
        notifySubInfo.put("111","aa");
        notifySubInfo = body.computeIfAbsent("aaa", key -> new HashMap<>());
        System.out.println("args = " + notifySubInfo);
        //Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new MyTimerTask2(), 10000L, 10000L, TimeUnit.MILLISECONDS);
        //System.in.read();
    }

    static class MyTimerTask extends TimerTask {
        Timer timer;

        public MyTimerTask(Timer timer) {
            this.timer = timer;
        }

        @SneakyThrows
        @Override
        public void run() {
            Thread.sleep(20000);
            System.out.println("timerEnd" + System.currentTimeMillis() / 1000);
            this.timer.schedule(new MyTimerTask(this.timer), 10000);
        }
    }

    static class MyTimerTask2 extends TimerTask {

        @SneakyThrows
        @Override
        public void run() {
            long time = 20000;
            Thread.sleep(time);
            System.out.println("2timerEnd耗时" + time + ", " + System.currentTimeMillis() / 1000);
        }
    }

}
