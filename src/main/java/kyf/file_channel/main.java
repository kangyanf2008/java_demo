package kyf.file_channel;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class main {

    //使用缓冲区完成文件复制（内存映射文件）
    public static void main(String[] args) throws IOException {
        long beging = System.currentTimeMillis();
        testMappedByteBuffer();
        System.out.println("testMappedByteBuffer "+(System.currentTimeMillis() - beging));
        beging = System.currentTimeMillis();
        testMappedByteBuffer2();
        System.out.println("testMappedByteBuffer2 "+(System.currentTimeMillis() - beging));
    }

   public static void testMappedByteBuffer() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\工具\\vmware\\vmware-pro15.zip"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("ubuntu-19.10-desktop-amd64.iso11"), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ);

        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据请写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();
    }

    //性能是testMappedByteBuffer 一倍多
    public static void testMappedByteBuffer2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("ubuntu-19.10-desktop-amd64.iso11"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("ubuntu-19.10-desktop-amd64.iso22"), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ);

       outChannel.transferFrom(inChannel,0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }
}