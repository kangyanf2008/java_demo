import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

//软件引用 设置JVM options  -Xmx20m
public class SoftReferenceTest {
    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.err.println(m.get());
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(m.get());

        byte[] b = new byte[1024*1024*15];
        System.err.println(m.get());
    }
    //非常适合缓存，类似 guava
}
