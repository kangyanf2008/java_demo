
//CPU 重排序
public class CPUReSort {
    // static volatile int x,y,a,b;
    static int x, y, a, b;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(new Runnable() {
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread other = new Thread(new Runnable() {
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
            String result = "第" + i + "次 " + "(" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            }
        }
    }
}
