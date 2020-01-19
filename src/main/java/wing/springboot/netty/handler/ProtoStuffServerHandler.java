package wing.springboot.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wing.springboot.netty.message.Message;
import wing.springboot.netty.message.RequestMessage;
import wing.springboot.netty.message.ResponseMessage;
import wing.springboot.netty.message.pojo.User;


public class ProtoStuffServerHandler extends ChannelInboundHandlerAdapter {

    private static final Log logger = LogFactory.getLog(ProtoStuffServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收到的对象的类型为EchoRequest
        RequestMessage requestMessage = (RequestMessage) msg;
        logger.info(requestMessage.getRequestId() + " : " + requestMessage.getMessage());
        // 创建需要传输的user对象
        Message message = requestMessage.getMessage();
        User user = (User) message;
        user.setName("server");
        user.setAge(22);
        // 创建传输的user对象载体EchoRequest对象
        ResponseMessage responseMessage = new ResponseMessage();
        // 设置responseId
        responseMessage.setResponseId("222");
        // 设置需要传输的对象
        responseMessage.setMessage(user);
        // 设置需要传输的对象的类型
        responseMessage.setMessageClass(User.class);
        // 调用writeAndFlush将数据发送到socketChannel
        ctx.writeAndFlush(responseMessage);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}