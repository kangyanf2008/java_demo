package kyf.bytebuffer;

import java.nio.ByteBuffer;

public class main {
    public static void main(String[] args) {
        //allocate();
        allocateDirect();
    }
    //直接缓冲区
    private static void allocateDirect() {
        String testStr = "abc";
        ByteBuffer bb = ByteBuffer.allocateDirect(1024);
System.out.println(bb.isDirect());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());

        System.out.println("==put 1");
        bb.put(testStr.getBytes());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end put 1");

        System.out.println("==put 2");
        bb.put(testStr.getBytes());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end put 2");

        bb.flip();

        System.out.println("==get 1");
        byte[] getByte = new byte[bb.limit()];
        bb.get(getByte, 0, bb.limit());
        System.out.println(new String(getByte));
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 1");

        bb.rewind();

        System.out.println("==get 2");
        byte[] getByte2 = new byte[bb.limit()];
        bb.get(getByte2, 0, bb.limit());
        System.out.println(new String(getByte2));
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 2");


        System.out.println("==get 3");
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 3");
        bb.rewind();

        bb.clear();
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
    }


    //非直接缓冲区
    private static void allocate() {
        String testStr = "abc";
        ByteBuffer bb = ByteBuffer.allocate(1024);
System.out.println(bb.isDirect());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());

        System.out.println("==put 1");
        bb.put(testStr.getBytes());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end put 1");

        System.out.println("==put 2");
        bb.put(testStr.getBytes());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end put 2");

        bb.flip();

        System.out.println("==get 1");
        byte[] getByte = new byte[bb.limit()];
        bb.get(getByte, 0, bb.limit());
        System.out.println(new String(getByte));
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 1");

        bb.rewind();

        System.out.println("==get 2");
        byte[] getByte2 = new byte[bb.limit()];
        bb.get(getByte2, 0, bb.limit());
        System.out.println(new String(getByte2));
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 2");


        System.out.println("==get 3");
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
        System.out.println("==end get 3");
        bb.rewind();

        bb.clear();
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
        System.out.println(bb.position());
    }

}
