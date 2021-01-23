package unsafe.demo;

import java.util.concurrent.locks.LockSupport;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(20000L);
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i<10000000; i++) {
                    if (i==1000000) {
                        System.out.println("线程将阻塞");
                        LockSupport.park();
                 /*       try {
                            //Thread.sleep(20000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        System.out.println("线程解开阻塞");
                    }
                    System.out.println(i);
                }
            }
        });
        s.start();

        try {

            //LockSupport.parkUntil(s, 1000);
            Thread.sleep(20000L);
            System.out.println("解开阻塞");
            LockSupport.unpark(s);
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
