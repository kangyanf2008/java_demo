package spi.impl;

import spi.DataConnectFactory;

public class HbaseConnectFactory implements DataConnectFactory {
    @Override
    public Object connect(String address, String userName, String password) {
        System.out.printf("Hbase connected[address=%s,userName=%s,password=%s] \n", address, userName, password);
        return true;
    }
}
