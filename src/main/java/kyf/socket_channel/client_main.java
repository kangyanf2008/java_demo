package kyf.socket_channel;

import java.util.concurrent.*;

public class client_main {
    public static void main(String[] args) {
        new Client().start("127.0.0.1",8080);

        Executor executor = new ThreadPoolExecutor(1,1,10, TimeUnit.SECONDS,new LinkedBlockingDeque<>(1000), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                });
    }
}
