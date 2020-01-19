package wing.springboot.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import wing.springboot.netty.channel.ProtoStuffServerChannel;
import wing.springboot.netty.config.NettyConfig;

@Service
public class NettyServer {

    private static final Log logger = LogFactory.getLog(NettyServer.class);

    //分配用于处理业务的线程组数量
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(NettyConfig.getServerBossGroupSize());
    //每个线程组中线程的数量
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(NettyConfig.getServerWorkerGroupSize());

    public void run(){
        try {
            logger.info("开始启动Netty服务器...");
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            // 设置服务端过滤器
//            bootstrap.childHandler(BeanUtils.getBean(NettyServerChannel.class));
            bootstrap.childHandler(new ProtoStuffServerChannel());
            // 服务器绑定端口监听
            ChannelFuture channelFuture = bootstrap.bind(NettyConfig.getIP(),NettyConfig.getPORT()).sync();
            logger.info("Netty服务器已启动完成");
            // 监听服务器关闭监听
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            shutdown();
        }
    }

    private void shutdown(){
        //关闭EventLoopGroup，释放掉所有资源包括创建的线程
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        logger.info("Netty服务器已关闭");
    }
}