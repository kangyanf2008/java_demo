package curator.selectLeder;


import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class CustomLeaderSelectorListenerAdapter extends
        LeaderSelectorListenerAdapter implements Closeable {

    private String name;
    private LeaderSelector leaderSelector;
    public AtomicInteger leaderCount = new AtomicInteger();

    public CustomLeaderSelectorListenerAdapter(CuratorFramework client,String path,String name
    ) {
        this.name = name;
        this.leaderSelector = new LeaderSelector(client, path, this);

        /**
         * 自动重新排队
         * 该方法的调用可以确保此实例在释放领导权后还可能获得领导权
         */
        leaderSelector.autoRequeue();
    }

    public void start() throws IOException {
        leaderSelector.start();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    /**
     * 获取领导权
     */
    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds = 2;
        System.out.println(name + "成为当前leader");
        System.out.println(name + " 之前成为leader的次数：" + leaderCount.getAndIncrement() + "次");

        //TODO 其他业务代码
        try{
            //等待2秒后放弃领导权（模拟业务执行过程）
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        }catch ( InterruptedException e ){
            System.err.println(name + "已被中断");
            Thread.currentThread().interrupt();
        }finally{
            System.out.println(name + "放弃领导权\n");
        }

    }

}
