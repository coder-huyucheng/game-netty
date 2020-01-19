package wing.springboot.netty.coder;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import wing.springboot.netty.util.ProtoStuffUtil;

/**
 * PojoEncoder继承自Netty中的MessageToByteEncoder类，
 * 并重写抽象方法encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
 * 它负责将Object类型的POJO对象编码为byte数组，然后写入到ByteBuf中
 */
public class ProtoStuffEncoder extends MessageToByteEncoder<Object>{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,Object t, ByteBuf byteBuf) throws Exception {
        // 直接生成序列化对象
        // 需要注意的是，使用protostuff序列化时，不需要知道pojo对象的具体类型也可以进行序列化时
        // 在反序列化时，只要提供序列化后的字节数组和原来pojo对象的类型即可完成反序列化
        byte[] array = ProtoStuffUtil.serialize(t);
        byteBuf.writeBytes(array);
    }
}