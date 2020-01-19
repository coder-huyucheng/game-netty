package wing.springboot.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import wing.springboot.netty.channel.NettyClientChannel;
import wing.springboot.netty.channel.ProtoStuffClientChannel;
import wing.springboot.netty.config.NettyConfig;
import wing.springboot.netty.util.BeanUtils;

import java.util.concurrent.TimeUnit;

@Service
public class NettyClient {

    private static final Log logger = LogFactory.getLog(NettyClient.class);

    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    /**唯一标记 */
    private boolean initFlag=true;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    public void run() {
        doConnect(new Bootstrap(), group);
    }

    /**
     * 重连
     */
    public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        ChannelFuture channelFuture = null;
        try {
            if (bootstrap != null) {
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//                bootstrap.handler(BeanUtils.getBean(NettyClientChannel.class));
                bootstrap.handler(new ProtoStuffClientChannel());
                bootstrap.remoteAddress(NettyConfig.getIP(),NettyConfig.getPORT());
                channelFuture = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        logger.info("与服务端断开连接!在10s之后准备尝试重连!");
                        eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
                    }
                });
                if(initFlag){
                    logger.info("Netty客户端启动成功!");
                    initFlag = false;
                }
                // 阻塞
                channelFuture.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            logger.info("客户端连接失败!"+e.getMessage());
        }

    }
}