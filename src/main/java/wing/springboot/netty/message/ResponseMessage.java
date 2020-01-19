package wing.springboot.netty.message;

/**
 * ResponseMessage是server向client端发送数据的传输载体，将需要进行传输的pojo对象统一封装到ResponseMessage对象中，
 * 这样会为编解码工作带来很大的方便性和统一性，同时也可以携带其它信息， 对于后面对程序进行扩展会有非常大的帮助
 */
public class ResponseMessage{

    private String responseId;

    private Message message;

    private Class<?> messageClass;

    public String getResponseId(){
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
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