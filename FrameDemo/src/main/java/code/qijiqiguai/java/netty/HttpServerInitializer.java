package code.qijiqiguai.java.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author wangqi
 *
 * 1. Channel 代表了一个socket.
 * 2. ChannelPipeline 就是一个“羊肉串”，这个“羊肉串”里边的每一块羊肉就是一个 handler.
 *    Handler分为两种，inbound handler、outbound handler 。顾名思义，分别处理 流入、流出。
 * 3. HttpServerCodec 是 http消息的编解码器。
 * 4. HttpObjectAggregator是Http消息聚合器，Aggregator这个单次就是“聚合，聚集”的意思。
 *      Http消息在传输的过程中可能是一片片的消息片端，所以当服务器接收到的是一片片的时候，就需要HttpObjectAggregator来把它们聚合起来。
 * 5. 接收到请求之后，你要做什么，准备怎么做，就在HttpRequestHandler中实现。
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // http 编解码
        pipeline.addLast(new HttpServerCodec());
        // http 消息聚合器 512*1024为接收的最大content-length
        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
        pipeline.addLast(new HttpRequestHandler());
    }
}
