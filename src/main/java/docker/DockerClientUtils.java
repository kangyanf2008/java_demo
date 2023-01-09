package docker;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerClientUtils {
    /**
     * 连接docker服务器
     * @return
     */
    public static DockerClient connectDocker(String serverAddress) {
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://" + serverAddress).build();
        Info info = dockerClient.infoCmd().exec();
        String infoStr = JSONObject.toJSONString(info);
        System.out.println("docker的环境信息如下：=================");
        System.out.println(infoStr);
        return dockerClient;
    }
}
