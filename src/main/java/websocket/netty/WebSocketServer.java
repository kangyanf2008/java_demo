package websocket.netty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("http-codec", new HttpServerCodec());// 添加 HttpServerCodec 将请求和应答消息编码或解码为HTTP 消息
                    pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // 增加HttpObjectAggregator ，将HTTP 消息的多个部分组合成一条完整的HTTP消息
                    pipeline.addLast("http-chunked", new ChunkedWriteHandler());// 添加ChunkedWriteHandler，来向客户端发送HTML5文件，主要用于支持浏览器和服务端进行WebSocket 通信。
                    pipeline.addLast("handle", new WebSocketServerHandler()); // 增加WebSocket 服务端的handler
                }
            });
            Channel ch = b.bind(port).sync().channel();

            System.out.println("Web socket server started at port : " + port + '.');
            System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
            ch.closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new WebSocketServer().run(port);

    }
}
