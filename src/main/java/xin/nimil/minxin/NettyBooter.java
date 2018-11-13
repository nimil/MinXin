package xin.nimil.minxin;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xin.nimil.minxin.netty.WSServer;

/**
 * @Author:nimil e-mail:nimilgg@qq.com
 * @Date:2018/11/13
 * @Time:22:56
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            if (contextRefreshedEvent.getApplicationContext().getParent() == null){
                try {
                    WSServer.getInstance().start();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
    }
}
