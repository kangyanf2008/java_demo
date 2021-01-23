import java.util.HashMap;
import java.util.Map;

//CPU 重排序
public class CPUReSort {
    public volatile static int x = 0, y = 0, a = 0, b = 0;
    public static void main(String[] args) throws InterruptedException {
        reSort1();
        //reSort2();
    }


    private static void reSort1() throws InterruptedException {
        for (int i=0; ; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one =  new Thread(()->{
                a = x;
                y = 1;
            });
            Thread two =  new Thread(()->{
                b = y;
                x = 1;
            });

            one.start();
            two.start();
            one.join();
            two.join();

            if (a == 1 && b == 1) {
                System.out.println("i=" + i + ",x=" + x + ",y=" + y);
                break;
            }
        }
    }
    private static void reSort2() throws InterruptedException  {
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;true;i++){
            x=0;
            y=0;
            Thread one = new Thread(()->{
                int a = y;
                x = 1;
                map.put("a",a);
            });
            Thread two = new Thread(()->{
                int b = x;
                y = 1;
                map.put("b",b);
            });
            one.start();
            two.start();
            one.join();
            two.join();
            if (map.get("a") == 1 && map.get("b") == 1) {
                System.out.println("i=" + i + ",a=" + map.get("a") + ", " + "b=" + map.get("b"));
                break;
            }
        }
    }
}