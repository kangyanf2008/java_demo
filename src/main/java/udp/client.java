package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class client {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket();
        String message = "hello word";
        new Thread(()->{
            byte[] data = new byte[1500-28];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                while (true) {
                    ds.receive(packet);
                    String reply = new String(data, 0, packet.getLength());
                    System.out.println("reply = " + reply);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        while (true){
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("127.0.0.1"), 8080);
            ds.send(dp);
        }
    }
}
