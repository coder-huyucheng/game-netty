package wing.springboot.netty.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;
import wing.springboot.netty.handler.NettyServerHandler;
import wing.springboot.netty.protobuf.UserInfo;
import wing.springboot.netty.util.BeanUtils;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
        channelPipeline.addLast(new ProtobufDecoder(UserInfo.UserMsg.getDefaultInstance()));
        channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        channelPipeline.addLast(new ProtobufEncoder());
        //业务逻辑实现类
        channelPipeline.addLast("nettyServerHandler",BeanUtils.getBean(NettyServerHandler.class));
    }

}