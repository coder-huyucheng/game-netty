package wing.springboot.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import wing.springboot.netty.client.NettyClient;

@SpringBootApplication
public class NettyClientMain{

    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ApplicationContext applicationContext = SpringApplication.run(NettyClientMain.class,args);
        //启动netty服务器
        NettyClient nettyClientService = applicationContext.getBean(NettyClient.class);
        nettyClientService.run();
    }
}