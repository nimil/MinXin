package xin.nimil.minxin.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/10/25
 * @Time:22:06
 */
public class WSServer {
   private EventLoopGroup main;
    private EventLoopGroup sub ;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;



    private static class SingletionWSServer{
     static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance(){
        return SingletionWSServer.instance;
    }

    public WSServer(){
        main = new NioEventLoopGroup();
        sub = new NioEventLoopGroup();
        serverBootstrap =new ServerBootstrap();
        serverBootstrap.group(main,sub).channel(NioServerSocketChannel.class).childHandler(new WSServerInitialzer());
    }
    public void start(){
        this.future = serverBootstrap.bind(8088);
        System.err.println("netty websocket server 启动完毕...");
    }

}
