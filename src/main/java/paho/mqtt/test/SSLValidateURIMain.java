package paho.mqtt.test;

import org.eclipse.paho.client.mqttv3.internal.NetworkModuleService;
import org.eclipse.paho.client.mqttv3.spi.NetworkModuleFactory;

import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;

public class SSLValidateURIMain {
    public static void main(String[] args) throws InterruptedException {
        int max=100;
       ServiceLoader<NetworkModuleFactory> FACTORY_SERVICE_LOADER = ServiceLoader.load(NetworkModuleFactory.class, NetworkModuleService.class.getClassLoader());

        //NetworkModuleService.validateURI("ssl://127.0.0.1:9993");
        CountDownLatch cld = new CountDownLatch(max);
        for(int i=0; i < max; i++) {
            new Thread(()->{
                cld.countDown();
                try {
                    cld.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NetworkModuleService.validateURI("ssl://127.0.0.1:9993");
            }).start();
        }
        System.out.println("success");
        Thread.sleep(30000L);
    }
}
