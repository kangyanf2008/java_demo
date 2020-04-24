import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    volatile boolean running = true;
    void m(){
        System.err.println("m start");
        while (running){
        }
        System.err.println("m emd");
    }
    public static void main(String[] args) {
        VolatileDemo vd = new VolatileDemo();
        new Thread(vd::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vd.running = false;
    }
}
