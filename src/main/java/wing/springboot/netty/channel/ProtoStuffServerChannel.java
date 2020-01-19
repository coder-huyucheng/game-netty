package wing.springboot.netty.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import wing.springboot.netty.coder.ProtoStuffDecoder;
import wing.springboot.netty.coder.ProtoStuffEncoder;
import wing.springboot.netty.handler.ProtoStuffServerHandler;
import wing.springboot.netty.message.RequestMessage;

import java.util.concurrent.TimeUnit;

public class ProtoStuffServerChannel extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        // 添加编码器
        pipeline.addLast(new ProtoStuffDecoder(RequestMessage.class));
        // 添加×××
        pipeline.addLast(new ProtoStuffEncoder());
        // 业务逻辑实现类 handler
        pipeline.addLast(new ProtoStuffServerHandler());
    }
}