package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(8080);
        byte[] byteBuffer = new byte[1500-28];
        DatagramPacket dp = new DatagramPacket(byteBuffer, byteBuffer.length);
        while (true){
            ds.receive(dp);
            String receveMessage = new String(dp.getData(),0, dp.getLength());
            System.out.println("lenth="+dp.getLength()+",data = " + receveMessage);
            InetAddress address = dp.getAddress();
            int port = dp.getPort();
            String response = "服务器响应:"+ receveMessage;
            DatagramPacket packet2 = new DatagramPacket(response.getBytes(), response.getBytes().length, address, port);
            ds.send(packet2);
        }

    }
}
