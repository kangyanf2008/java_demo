package websocket.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerChannelPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline cp = Channels.pipeline();

        cp.addLast("decoder", new HttpRequestDecoder());
//		cp.addLast("decoder", new WebSocket00FrameDecoder());
        cp.addLast("encoder", new HttpResponseEncoder());
//		cp.addLast("downhandler", new HttpServerDownstreamHandler());
//		cp.addLast("uphandler", new HttpServerUpstreamHandler());
        cp.addLast("handler", new HttpServerChannelHandler());

        return cp;
    }
}
