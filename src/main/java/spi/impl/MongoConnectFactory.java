package spi.impl;

import spi.DataConnectFactory;

public class MongoConnectFactory implements DataConnectFactory {
    @Override
    public Object connect(String address, String userName, String password) {
        System.out.printf("Mongo connected[address=%s,userName=%s,password=%s] \n", address, userName, password);
        return true;
    }
}
