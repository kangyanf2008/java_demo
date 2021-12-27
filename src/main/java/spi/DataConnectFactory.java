package spi;

public interface DataConnectFactory {
    Object connect(String address, String userName, String password);
}
