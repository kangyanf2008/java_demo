package kyf.socket_channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public void start(String ip, int port) {
        //创建channel
        try (SocketChannel socketChannel = SocketChannel.open()){
            //连接服务器
            socketChannel.connect(new InetSocketAddress(ip, port));
            //发送消息数量
            int sendCount = 0;
            //ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            //while (sendCount < 10) {
            while (true) {
                buffer.clear();

                buffer.put(("currentTime:" + System.currentTimeMillis()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").getBytes());
                //读取模式
                buffer.flip();
                socketChannel.write(buffer);  //向服务端发送消息
                buffer.clear();
                //从服务端读取消息
                int readLenth = socketChannel.read(buffer);
                //读取模式
                buffer.flip();
                byte[] bytes = new byte[readLenth];
                buffer.get(bytes);
//System.out.println("return"+new String(bytes, "UTF-8"));
                buffer.clear();

                sendCount++;
                if (sendCount % 10000 == 0) {
                    System.out.println(System.currentTimeMillis()/1000+", sendCount = " + sendCount);
                }
              /*  try {
                   // Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }// end while()
           // socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
