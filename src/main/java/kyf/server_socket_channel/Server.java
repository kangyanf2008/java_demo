package kyf.server_socket_channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Server {

    private volatile byte isRun = 1;
    public void stop (){
        isRun = -1;
    }
    public void start(int port){

        int localPort = port;
        if (localPort <  0) {
            localPort = 8888;
        }
        //创建serverSocketChannel,监听端口
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            //监听本地端口
            serverSocketChannel.bind(new InetSocketAddress(localPort));
System.out.println("bind port:"+localPort+" success");
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //为serverSocketChannel注册selector
            Selector selector = Selector.open();

            //注册接收事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
System.out.println("register selector");
            //创建消息处理器
            ServerHandlerBase serverHandler = new ServerHandlerImpl(1024);

            //非关闭，则进行执行
            while (this.isRun == 1) {
                //等待事件
                int eventNum = selector.select();
                System.out.println("eventNum = " + eventNum);

                //处理事件
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                   SelectionKey key =  keyIterator.next();
                   try {
                       //接收连接
                       if ( key.isAcceptable()) {
                           serverHandler.handleAccept(key);
                       }
                       //读取数据
                       if (key.isReadable()) {
                           serverHandler.handleRead(key);
                       }
                   } catch (IOException e){
                        e.printStackTrace();
                        key.channel().close();
                   } finally {
                       keyIterator.remove();
                   }

                }// end (keyIterator.hasNext())

                System.out.println("finished event");
            }
            //关闭连接
            if (selector != null) {
                selector.close();
            }
            if (serverSocketChannel != null) {
                serverSocketChannel.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
