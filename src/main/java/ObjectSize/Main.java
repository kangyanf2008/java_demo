package ObjectSize;

import com.kyf.agent.MyAgent;

public class Main {
    public static void main(String[] args) {
        Object a = new Object();
        String b = new String();
        int[] c = new int[]{};
        int e = 0;
        String f = "";
        char value[] = new char[]{'a'};
        System.out.println(MyAgent.sizeof(a));
        System.out.println(MyAgent.sizeof(b));
        System.out.println(MyAgent.sizeof(c));
        System.out.println(MyAgent.sizeof(e));
        System.out.println(MyAgent.sizeof(new P()));
        System.out.println(MyAgent.sizeof(f));
        System.out.println(MyAgent.sizeof(value));
        //-XX:-UseCompressedClassPointers -XX:-UseCompressedOops  //16 32 24
        //-XX:+UseCompressedClassPointers -XX:+UseCompressedOops  //16 24 16
    }


    private static class P {
        int id;
        String name;
        int age;

        byte b1;
        byte b2;

        Object o;
    }

}
