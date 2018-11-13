package xin.nimil.minxin.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/8
 * @Time:21:37
 *
 * 处理消息的handler
 * 在netty中，是用于专门为处理文本的对象frame是消息的载体
 */
public class CharHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于记录和管理客户端的channel
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 用户加入所调用的方法
     * 获取客户端的channel并且放到channelgroup中去管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());

    }

    /**
     * 当触发handleremoved之后会自动移出对应的客户端channel
     * 用户离开所调用的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText());
        System.out.println(ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        System.out.println(content);
        channels.forEach(e->{
            e.writeAndFlush(new TextWebSocketFrame("服务器接收到消息"+ LocalDateTime.now()+"时候接收到，消息为："+content));
        });
        //和上面的foreach一致
     //   channels.writeAndFlush(new TextWebSocketFrame("服务器接收到消息"+ LocalDateTime.now()+"时候接收到，消息为："+content));
    }

}
