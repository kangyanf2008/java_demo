import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixdThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        int count = 20;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i =0; i<count; i++ ) {
            es.execute(new ExecuteFun(i, countDownLatch));
        }
        countDownLatch.await();
        es.shutdown();
        System.out.println("exeit");
        System.exit(0);
    }

    static class ExecuteFun implements Runnable {
        private int i;
        private CountDownLatch countDownLatch;

        public ExecuteFun(int i, CountDownLatch countDownLatch) {
            this.i = i;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.i+"-begin线程开始"+Thread.currentThread().getId());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                System.out.println(this.i+"-end线程开始"+Thread.currentThread().getId());
            }
        }
    }
}
