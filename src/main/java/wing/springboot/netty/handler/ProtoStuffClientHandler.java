package wing.springboot.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wing.springboot.netty.message.RequestMessage;
import wing.springboot.netty.message.ResponseMessage;
import wing.springboot.netty.message.pojo.User;


public class ProtoStuffClientHandler extends ChannelInboundHandlerAdapter {

    private static final Log logger = LogFactory.getLog(ProtoStuffClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 创建需要传输的user对象
        User user = new User();
        user.setName("client");
        user.setAge(11);
        // 创建传输的user对象载体EchoRequest对象
        RequestMessage requestMessage = new RequestMessage();
        // 设置requestId
        requestMessage.setRequestId("111");
        // 设置需要传输的对象
        requestMessage.setMessage(user);
        // 设置需要传输的对象的类型
        requestMessage.setMessageClass(User.class);
        // 调用writeAndFlush将数据发送到socketChannel
        ctx.writeAndFlush(requestMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        // 接收到的对象的类型为EchoResponse
        RequestMessage requestMessage = (RequestMessage) msg;
        logger.info(requestMessage.getRequestId() + " : " + requestMessage.getMessage());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
        ctx.close();
    }

}