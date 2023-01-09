package kyf.list_test;

import java.util.ArrayList;
import java.util.List;

public class ListDelete {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable(){
            @Override
            public void run() {
                for (int j=0; j<10; j++){
                    long  begin = System.currentTimeMillis();
                    for (int s=0; s<1000000; s++) {
                        List<Object> aa = new ArrayList();
                        for(int i=0; i<100; i++){
                            aa.add(i);
                        }
                        //aa.clear();
                    }
                    System.out.println(System.currentTimeMillis() - begin);
                }
            }
        }).start();
        Thread.sleep(30000L);
    }
}
