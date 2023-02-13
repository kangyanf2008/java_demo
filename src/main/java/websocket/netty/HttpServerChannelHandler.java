package websocket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.unix.PreferredDirectByteBufAllocator;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.codec.http.websocketx.*;

import java.util.List;
import java.util.Map;

public class HttpServerChannelHandler extends SimpleChannelHandler {
    public static boolean debug = true;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Channel ch = e.getChannel();
        Object msg = e.getMessage();

        if(debug){
            System.out.println("---------------");
            System.out.println("message: "+msg.getClass());
        }
        //虽然是websocket，但在建立websocket连接前，先进行http握手,所以，这时也要处理http请求
        //在http握手完成后，才是websocket下的通信
        if(msg instanceof HttpRequest){
            processHttpRequest(ch, (HttpRequest)msg);
        }else if(msg instanceof WebSocketFrame){
            processWebsocketRequest(ch,(WebSocketFrame)msg);
        }else{
            //未处理的请求类型
        }
    }

    //这个方法：
    //1.完成websocket前的http握手
    //2.屏蔽掉非websocket握手请求
    void processHttpRequest(Channel channel,HttpRequest request){
        HttpHeaders headers = request.headers();
        if(debug){
            List<Map.Entry<String,String>> ls = headers.entries();
            for(Map.Entry<String,String> i: ls){
                System.out.println("header  "+i.getKey()+":"+i.getValue());
            }
        }

        //屏蔽掉非websocket握手请求
        //只接受http GET和headers['Upgrade']为'websocket'的http请求
        if(!HttpMethod.GET.equals(request.getMethod())
                || !"websocket".equalsIgnoreCase(headers.get("Upgrade"))){
            DefaultHttpResponse resp = new DefaultHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST);
            channel.write(resp);
            channel.close();
            return;
        }

        WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
                "ws://"+request.headers().get(HttpHeaders.Names.HOST),
                null,false );
        WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(request);
        if(null==wsShakerHandler){
            //无法处理的websocket版本
            wsShakerFactory.sendUnsupportedWebSocketVersionResponse(channel);
        }else{
            //向客户端发送websocket握手,完成握手
            //客户端收到的状态是101 sitching protocol
            wsShakerHandler.handshake(channel, request);
        }
    }

    //websocket通信
    void processWebsocketRequest(Channel channel, WebSocketFrame request){
        if(request instanceof CloseWebSocketFrame){
            channel.close();
        }else if(request instanceof PingWebSocketFrame){
            channel.write(new PongWebSocketFrame(request.getBinaryData()));
        }else if(request instanceof TextWebSocketFrame){
            //这个地方 可以根据需求，加上一些业务逻辑
            TextWebSocketFrame txtReq = (TextWebSocketFrame) request;
            if(debug){ System.out.println("txtReq:"+txtReq.getText());}
            //向ws客户端发送多个响应
            channel.write(new TextWebSocketFrame("receive:" + ((TextWebSocketFrame) request).getText()));
        }else{
            //WebSocketFrame还有一些
            if(debug){ System.out.println("txtReq:"+request.getBinaryData().toString());}
            //向ws客户端发送多个响应
            channel.write(new BinaryWebSocketFrame(request.getBinaryData()));
        }
    }
}
