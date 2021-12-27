package spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataConnectFactoryTest {
    public static void main(String[] args) {
        //方法一
        ServiceLoader<DataConnectFactory> serviceLoader = ServiceLoader.load(DataConnectFactory.class);
        serviceLoader.iterator().forEachRemaining(o->{
            o.connect("127.0.0.1","root","admin123");
        });

        //方法二
        Iterator<DataConnectFactory> providers = Service.providers(DataConnectFactory.class);
        while (providers.hasNext()){
            DataConnectFactory dataConnectFactory = providers.next();
            dataConnectFactory.connect("127.0.0.1","root","admin123");
        }
    }
}
