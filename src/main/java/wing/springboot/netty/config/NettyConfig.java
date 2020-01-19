package wing.springboot.netty.config;

public class NettyConfig{
    //监听IP
    private static final String IP = "127.0.0.1";
    //监听端口号
    private static final int PORT = 9099;
    //服务端处理业务的线程组数量
    private static final int SERVER_BOSS_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    //服务端每个线程组中线程的数量
    private static final int SERVER_WORKER_GROUP_SIZE = 4;
    //客户端处理业务的线程组数量
    private static final int CLIENT_BOSS_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    //客户端每个线程组中线程的数量
    private static final int CLIENT_WORKER_GROUP_SIZE = 4;

    public static String getIP() {
        return IP;
    }

    public static int getPORT() {
        return PORT;
    }

    public static int getServerBossGroupSize() {
        return SERVER_BOSS_GROUP_SIZE;
    }

    public static int getServerWorkerGroupSize() {
        return SERVER_WORKER_GROUP_SIZE;
    }

    public static int getClientBossGroupSize() {
        return CLIENT_BOSS_GROUP_SIZE;
    }

    public static int getClientWorkerGroupSize() {
        return CLIENT_WORKER_GROUP_SIZE;
    }
}