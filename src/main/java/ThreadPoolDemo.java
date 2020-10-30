import java.util.concurrent.*;


public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8,
                6000,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            pool.execute(new ParamRunable(i, "pool"));
        }
        pool.shutdown();
        System.out.println("����ʱ�� "+(System.currentTimeMillis()-begin));

        ThreadPoolExecutor pools = new ThreadPoolExecutor(1,
                6000,
                10L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(1000, true),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        long begin2 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            pools.execute(new ParamRunable(i, "pools"));
        }
        pools.shutdown();
        System.out.println("����ʱ�� "+(System.currentTimeMillis()-begin2));
        String[] aaaaaaaa = {""};
        System.out.println(aaaaaaaa.length);
    }

}

class ParamRunable implements Runnable {
    private int i;
    private String pool;

    public ParamRunable() {
    }

    public ParamRunable(int i, String pool) {
        this.i = i;
        this.pool = pool;
    }

    @Override
    public void run() {
        //System.out.println(this.pool + "_" + Thread.currentThread().getId() + "_" + this.i);
    }
}