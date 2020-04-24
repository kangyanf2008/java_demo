import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用
 */
public class PhantomReferenceTest {

    //设置JVM options  -Xmx20m
    private static final ReferenceQueue<T> QUEUE = new ReferenceQueue<>();
    private static final List<Object> LIST = new LinkedList<>();

    public static void main(String[] args) {
        PhantomReference<T> phantomReference = new PhantomReference<>(new T(), QUEUE);
        new Thread(()->{
            while(true){
                LIST.add(new byte[1024*2014]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while (true) {
                Reference<? extends T>  poll = QUEUE.poll();
                if (poll != null) {
                    System.err.println("--虚引用对象被jvm回收了--"+poll);
                }
            }
        }).start();

    }

    static class T{
        private String t1;
    }
}
