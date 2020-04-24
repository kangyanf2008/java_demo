public class Demo2 {
    public static volatile int i=0;
    public static void main(String[] args) {
        for (int i=0; i < 1000000; i++) {
           // System.err.print(i);
            m();
            n();
        }
        //System.err.print(i);
    }

    public static synchronized void m(){}
    public static void n() {
        //System.err.println(i);
        i = 1;
    }

}
