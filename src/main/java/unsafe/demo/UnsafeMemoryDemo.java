package unsafe.demo;

import sun.misc.Unsafe;
import sun.misc.VM;

import java.lang.reflect.Field;
import java.util.Arrays;

public class UnsafeMemoryDemo {
    private static final Unsafe UNSAFE;
    private static final long TBASE;
    private static final int TSHIFT;
    static {
        int ts;
        try {
            UNSAFE = reflectGetUnsafe();
            TBASE = UNSAFE.arrayBaseOffset(int[].class);
            ts = UNSAFE.arrayIndexScale(int[].class);
        } catch (Exception e) {
            throw new Error(e);
        }
        TSHIFT = 31 - Integer.numberOfLeadingZeros(ts);
    }

    public static void main(String[] args) {
        System.out.println("isDirectMemoryPageAligned = " + VM.isDirectMemoryPageAligned());
        System.out.printf("maxDirectMemory = %dM\n", VM.maxDirectMemory()/1024/1024);
        System.out.println("allowArraySyntax = " + VM.allowArraySyntax());
        System.out.println("getPeakFinalRefCount = " + VM.getPeakFinalRefCount());
        System.out.println("getFinalRefCount = " + VM.getFinalRefCount());

        long memorySize = 16;
        //在堆外开辟指定byte空间，返回内存地址
        long memoryAddress = UNSAFE.allocateMemory(memorySize);
        System.out.println("memoryAddress = " + memoryAddress);
        UNSAFE.setMemory(memoryAddress, memorySize, (byte) 0);

        int num = 100000;
        UNSAFE.putInt(memoryAddress, num);
        int readNum = UNSAFE.getIntVolatile(null, memoryAddress);
        System.out.println("readNum = " + readNum);


        UNSAFE.freeMemory(memoryAddress);
        UNSAFE.putInt(memoryAddress, num);
        int readNum2 = UNSAFE.getIntVolatile(null, memoryAddress);
        System.out.println("readNum2 = " + readNum2);
//        UNSAFE.putObject( (byte)0, memoryAddress + 4, num);
//        int readNum2 = UNSAFE.getIntVolatile(null, memoryAddress + 4);
//        System.out.println("readNum2 = " + readNum2);


    }

    static int entryAt(int[] tab, int i) {
        //return (Integer) UNSAFE.getObjectVolatile(tab, ((long)i << TSHIFT) + TBASE);
        return (int) UNSAFE.getInt(tab, ((long)i << TSHIFT) + TBASE);
    }

    static  Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
