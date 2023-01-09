package docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Identifier;
import com.github.dockerjava.api.model.PushResponseItem;
import com.github.dockerjava.api.model.Repository;
import docker.tar.ManifestInfo;
import docker.tar.ManifestUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DockerClientService {
    public static void main(String[] args) throws IOException {
        DockerClient dockerClient = null;
        FileInputStream fis = null;
        try {
            //连接docker
            dockerClient  = DockerClientUtils.connectDocker("127.0.0.1:2375");
            String imagePath = "image/bash.tar";

            //导入镜像
            fis = new FileInputStream(new File(imagePath));
            dockerClient.loadImageCmd(fis).exec();

            //设置tag
            List<ManifestInfo> listManifestInfo = ManifestUtils.get(imagePath);
            String imageId = listManifestInfo.get(0).getConfig().split("\\.")[0];
            dockerClient.tagImageCmd(imageId, "127.0.0.1:5000/bash","v1").exec();

            final CountDownLatch latch = new CountDownLatch(1);

            //push仓库
            Identifier Identifier = new Identifier(new Repository("127.0.0.1:5000/bash"),"v1");
            dockerClient.pushImageCmd(Identifier).exec(new ResultCallback<PushResponseItem>() {
                @Override
                public void onStart(Closeable closeable) {
                    System.out.println("closeable = " + closeable);
                }

                @Override
                public void onNext(PushResponseItem object) {
                    System.out.println("object = " + object);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                    latch.countDown();
                }

                @Override
                public void onComplete() {
                    System.out.println("Identifier = " + Identifier);
                    latch.countDown();
                }

                @Override
                public void close() throws IOException {
                    System.out.println("Identifier = " + Identifier);
                    latch.countDown();
                }
            });
            latch.await();
            //删除旧镜像
            dockerClient.removeImageCmd(imageId).exec();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(dockerClient != null) {
                dockerClient.close();
            }
            if(fis != null) {
                fis.close();
            }
        }
    }



}

