package wing.springboot.netty.message;

/**
 * EchoRequest是client向server端发送数据的传输载体，将需要进行传输的pojo对象统一封装到EchoRequest对象中，
 * 这样会为编解码工作带来很大的方便性和统一性，同时也可以携带其它信息， 对于后面对程序进行扩展会有非常大的帮助
 */
public class RequestMessage{

    private String requestId;

    private Message message;

    private Class<?> messageClass;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Class<?> getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(Class<?> messageClass) {
        this.messageClass = messageClass;
    }
}