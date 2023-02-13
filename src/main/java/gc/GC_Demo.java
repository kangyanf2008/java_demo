package gc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GC_Demo extends ArrayList implements List {

    public String aa;
    private List bb;
    protected static Object cc;
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        String strA = new String("11");
        lock.unlock();
        System.out.println(strA);

        /**
         * -Xms30m -Xmx30m -XX:NewRatio=2 -XX:SurvivorRatio=9 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC
         */
        /*
        Runtime rt = Runtime.getRuntime();
        System.out.println("1总"+rt.totalMemory()/1024/1204);
        System.out.println("1空闲"+rt.freeMemory()/1024/1204);
        int _M = 1024 * 1024;
        byte[] array1, array2, array3, array4;

        System.out.println("第一等待");
        Thread.sleep(25000L);
        array1 = new byte[16 * _M];
        System.out.println("2总"+rt.totalMemory()/1024/1204);
        System.out.println("2空闲"+rt.freeMemory()/1024/1204);
        System.out.println("第二等待");
        Thread.sleep(10000L);
        array2 = new byte[5 * _M];
        System.out.println("3总"+rt.totalMemory()/1024/1204);
        System.out.println("3空闲"+rt.freeMemory()/1024/1204);
        System.out.println("第三等待");

        Thread.sleep(10000L);
        array3 = new byte[2 * _M];
        System.out.println("4总"+rt.totalMemory()/1024/1204);
        System.out.println("4空闲"+rt.freeMemory()/1024/1204);

        Thread.sleep(10000L);
        array4 = new byte[2 * _M];
        System.out.println("5总"+rt.totalMemory()/1024/1204);
        System.out.println("5空闲"+rt.freeMemory()/1024/1204);


        Thread.sleep(10000L);
        //array4 = new byte[2 * _M];
        System.out.println("6总"+rt.totalMemory()/1024/1204);
        System.out.println("6空闲"+rt.freeMemory()/1024/1204);


        //System.out.println("==========="+PESFaceSDKImageFormat.BGR);
        Thread.sleep(1000000L);
        System.out.println(array1.length);
        System.out.println(array2.length);
        System.out.println(array3.length);
        System.out.println(array4.length);
         */
        Object o = new Object();
    }
}



