package unsafe.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeMain {
    private static final sun.misc.Unsafe UNSAFE;
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
        int[] tab = new int[]{1,222,7000000};

        System.out.println(TBASE);
        System.out.println(TSHIFT);


        System.out.println(entryAt(tab,2));

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
