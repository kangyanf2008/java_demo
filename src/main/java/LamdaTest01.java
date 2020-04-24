import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class LamdaTest01 {
    volatile List<Demo> list = new LinkedList<Demo>();
    public static void main(String[] args) {
/*        Interface01 interface01 = ()->{
            System.err.println("你好");
        };
        interface01.print();
        System.err.println(interface01.add(1,2));
        System.err.println(Interface01.add(1,2, 4));

        Interface02 interface02 = (int x, int y)->{
            return x + y;
        };
        System.err.println(interface02.add(3,4));

        System.err.println("=========forEach=================");
        List<Demo>   list = new LinkedList<Demo>();
        for(int i=0; i<100000; i++){
            list.add(new Demo(""+i));
        }
        new Thread(()->{
            list.forEach((x)->{
                x.name = "ttt";
                //System.err.println(x);
            });
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            list.forEach((x)->{
                System.err.println(x.name);
            });
        }).start();

        list.forEach(System.out::println);*/

/*

        List<String> listString = new Vector<>();
        for (int i=1; i<1000; i++) {
            new Thread(()->{
                listString.add(Thread.currentThread().getName());
            }).start();
        }
        //listString.forEach(System.out::println);
        System.err.println(listString.parallelStream().count());

        //排序打印
        listString.stream().sorted((x,y)->{
            String[] threadString = x.split("-");
            String[] thread2String = y.split("-");
            return Integer.parseInt(threadString[1])>Integer.parseInt(threadString[1])?1:0;
        }).forEach(System.out::println);
*/

        //生产数据
        List<Integer> sortList1 = new ArrayList();
        for(int i=100000; i > 0; i--) {
            sortList1.add((int)(Math.random()*100000));
        }
        System.err.println("sortList1.size"+sortList1.size());

        List<Integer> sortList2 = new ArrayList<>();
        for(int i=100000; i > 0; i--) {
            sortList2.add((int)(Math.random()*100000));
        }
        System.err.println("sortList2.size="+sortList1.size());

        //对比流式排序和并发流式排序
        long beging1 = System.currentTimeMillis();
        sortList1.stream().sorted((x,y)->{
            //return x>y?1:0;
            return x.compareTo(y);
        });
        //sortList1.forEach(System.out::println);
        System.err.println(System.currentTimeMillis() - beging1);

        long beging2 = System.currentTimeMillis();
        sortList2.parallelStream().sorted((x,y)->{
           // return x>y?1:0;
            return x.compareTo(y);
        });
        sortList2.forEach(System.out::println);
        System.err.println(System.currentTimeMillis() - beging2);

        List<Integer> cowal = new CopyOnWriteArrayList<Integer>();
        cowal.add(11);

    }
}

@FunctionalInterface
interface Interface01 {
    void print();
    default int add(int x, int y){
        return x + y;
    }
    static int add(int x, int y, int z){
        return x + y + z;
    }
}

interface Interface02 {
    int add(int x, int y);
}

class Demo {
    String name;

    public Demo(String name) {
        this.name = name;
    }
}