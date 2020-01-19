package wing.springboot.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import wing.springboot.netty.util.ProtoStuffUtil;

import java.util.List;

/**
 * PojoDecoder继承自Netty中的MessageToMessageDecoder类，
 * 并重写抽象方法decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out)
 * 首先从数据报msg（数据类型取决于继承MessageToMessageDecoder时填写的泛型类型）中获取需要解码的byte数组
 * 然后调用使用序列化工具类将其反序列化（解码）为Object对象 将解码后的对象加入到解码列表out中，这样就完成了解码操作
 */
public class ProtoStuffDecoder extends MessageToMessageDecoder<ByteBuf> {

    // 需要反序列对象所属的类型
    private Class<?> genericClass;

    // 构造方法，传入需要反序列化对象的类型
    public ProtoStuffDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // ByteBuf的长度
        int length = byteBuf.readableBytes();
        // 构建length长度的字节数组
        byte[] array = new byte[length];
        // 将ByteBuf数据复制到字节数组中
        byteBuf.readBytes(array);
        // 反序列化对象
        Object obj = ProtoStuffUtil.deserialize(array, this.genericClass);
        // 添加到反序列化对象结果列表
        list.add(obj);
    }
}