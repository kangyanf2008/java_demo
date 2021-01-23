package kyf.server_socket_channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ServerHandlerImpl implements ServerHandlerBase {
    private int bufferSize = 1024;
    private String localCharset = "UTF-8";
    private CharsetDecoder charsetDecoder = null;

    public ServerHandlerImpl() {
    }

    public ServerHandlerImpl(int bufferSize) {
        this(bufferSize, null);
    }

    public ServerHandlerImpl(String localCharset) {
        this(-1, localCharset);
    }

    public ServerHandlerImpl(int bufferSize, String localCharset) {
        this.bufferSize = bufferSize > 0 ? bufferSize : this.bufferSize;
        this.localCharset = localCharset == null ? this.localCharset : localCharset;
        this.charsetDecoder = Charset.forName(this.localCharset).newDecoder();
    }

    @Override
    public void handleAccept(SelectionKey selectionKey) throws IOException {
        //获取连接channel
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

        SocketChannel socketChannel = serverSocketChannel.accept();

        //打印客户端IP
        String clientId = socketChannel.getRemoteAddress().toString();
        System.out.println("clientId = " + clientId+",connected");
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //注册读事件
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(this.bufferSize));
    }

    @Override
    public String handleRead(SelectionKey selectionKey) throws IOException {
        //获取连接channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //打印客户端IP
        String clientId = socketChannel.getRemoteAddress().toString();
        System.out.println("clientId = " + clientId + ",reading");

        //读取字节缓冲区
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

        String receivedStr = "";
        if (socketChannel.read(byteBuffer) == -1) {
            //如果未读到数据，则关闭客户端连接
            socketChannel.shutdownInput();
            socketChannel.shutdownOutput();
            socketChannel.close();
            System.out.println("clientId = " + clientId + ", disConnect");
        } else {
            byteBuffer.flip();
            //数据进行解码
            receivedStr = charsetDecoder.decode(byteBuffer).toString();
            byteBuffer.clear();

            //返回客户端信息
            byteBuffer = byteBuffer.put(("received string : " + receivedStr).getBytes(localCharset));
            //读取模式
            byteBuffer.flip();
            //socketChannel.write(byteBuffer.get());
            socketChannel.write(byteBuffer);

            //注册selector继续读数据
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, byteBuffer);
            byteBuffer.clear();

        }
        return receivedStr;
    }

    @Override
    public void handleWrite() throws IOException {

    }
}
