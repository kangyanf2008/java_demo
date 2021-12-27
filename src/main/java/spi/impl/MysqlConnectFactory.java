package spi.impl;

import spi.DataConnectFactory;

public class MysqlConnectFactory implements DataConnectFactory {
    @Override
    public Object connect(String address, String userName, String password) {
        System.out.printf("Mysql connected[address=%s,userName=%s,password=%s] \n", address, userName, password);
        return true;
    }
}
