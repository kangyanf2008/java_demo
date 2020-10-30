// 必须定义 `ShowMeBug` 入口类和 `public static void main(String[] args)` 入口方法
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ShowMeBug {
    private static volatile int i = 1;
    private static Lock lock = new ReentrantLock();
    private static Condition c1 = lock.newCondition();
    private static Condition c2 = lock.newCondition();
    private static CountDownLatch count =new CountDownLatch (2);
    public static void main(String[] args) throws InterruptedException {

        Thread t1=new Thread(()->{
            try{
                while(i<=99){
                    System.out.println("threadA--"+i);
                    i++;
                    c1.notify();
                    c2.wait();
                }
            }catch(Exception e){
            }finally{
                count.countDown();
            }
        });
        Thread t2=new Thread(()->{
            try{
                while(i<=100){
                    System.out.println("threadB");
                    c1.wait();
                    System.out.println("threadB--"+i);
                    c2.notify();
                    System.out.println("threadB--"+i);
                    i++;
                }
            }catch(Exception e){
            }finally{
                count.countDown();
            }
        });
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        t1.join();
        t2.join();

        Thread.sleep(5000L);
    }
}
