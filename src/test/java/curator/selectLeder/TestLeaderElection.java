package curator.selectLeder;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Test;

/**
 * 测试Apache Curator框架的两种选举方案
 * @author zifangsky
 *
 */
public class TestLeaderElection {
    //会话超时时间
    private final int SESSION_TIMEOUT = 30 * 1000;

    //连接超时时间
    private final int CONNECTION_TIMEOUT = 3 * 1000;

    //客户端数量
    private final int CLIENT_NUMBER = 10;

    //ZooKeeper服务地址
    private static final String SERVER = "10.97.8.171:2181";

    private final String PATH = "/curator/latchPath";

    //创建连接实例
    private CuratorFramework client = null;

    /**
     * baseSleepTimeMs：初始的重试等待时间
     * maxRetries：最多重试次数
     */
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    //自定义LeaderSelectorListenerAdapter实例集合
    List<CustomLeaderSelectorListenerAdapter> leaderSelectorListenerList
            = new ArrayList<CustomLeaderSelectorListenerAdapter>();

    @Test
    public void test() throws Exception{
        //创建 CuratorFrameworkImpl实例
        client = CuratorFrameworkFactory.newClient(SERVER, SESSION_TIMEOUT, CONNECTION_TIMEOUT, retryPolicy);
        client.start();

        for(int i=0;i<CLIENT_NUMBER;i++){
            //创建LeaderSelectorListenerAdapter实例
            CustomLeaderSelectorListenerAdapter leaderSelectorListener =
                    new CustomLeaderSelectorListenerAdapter(client, PATH, "Client #" + i);

            leaderSelectorListener.start();
            leaderSelectorListenerList.add(leaderSelectorListener);
        }

        //暂停当前线程，防止单元测试结束，可以让leader选举过程持续进行
        TimeUnit.SECONDS.sleep(600);
    }

    /**
     * 测试完毕关闭连接
     */
    @After
    public void close(){
        for(CustomLeaderSelectorListenerAdapter tmp : leaderSelectorListenerList){
            CloseableUtils.closeQuietly(tmp);
        }

        CloseableUtils.closeQuietly(client);
    }

}