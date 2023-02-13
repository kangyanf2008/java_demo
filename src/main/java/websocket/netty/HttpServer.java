package websocket.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServer implements Runnable {
    int port = 80;

    public HttpServer(int port){
        this.port = port;
    }

    @Override
    public void run() {
        ServerBootstrap b = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        b.setPipelineFactory(new HttpServerChannelPipelineFactory());
        b.setOption("child.tcpNoDelay", true);
        b.setOption("child.keepAlive", true);
        b.bind(new InetSocketAddress(port));
    }

    public static void main(String[] args){
        new HttpServer(80).run();
    }
}
