import java.lang.ref.WeakReference;

//弱引用
public class WeekReferenceTest {
    public static void main(String[] args) {
        WeakReference<T> t = new WeakReference<>(new T("ttttt"));
        System.err.println("t="+t.get());
        System.gc();
        System.err.println("t="+t.get()); //拉圾回收之后就为空

        ThreadLocal<T> tl = new ThreadLocal<>();
        new Thread(()->{
            tl.set(new T("inner thread"));
            System.err.println("subthread tl="+tl.get().getT1());
        }).start();

        tl.set(new T("out thread"));
        System.err.println("main thread tl="+tl.get().getT1());
      /*  tl.remove();
        System.err.println("tl="+tl.get());*/
    }

    static class T{
        private String t1;

        public String getT1() {
            return t1;
        }

        public T(String t1) {
            this.t1 = t1;
        }
    }
}
