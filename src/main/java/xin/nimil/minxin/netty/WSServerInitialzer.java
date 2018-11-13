package xin.nimil.minxin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/8
 * @Time:21:26
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //websocket 基于http协议 所以要有编解码器
        pipeline.addLast(new HttpServerCodec());

        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //消息最大内容的长度  聚合器  聚合城fullhttprequest或者hrrpResponse
        //几乎在netty编程中都会使用到此handle
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //以上是用于支持http协议

        //此handle会帮你处理一些繁重的事情
        //会帮你处理握手动作handleshaking  心跳
        //不同数据类型对应的frams也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new CharHandler());

    }
}
