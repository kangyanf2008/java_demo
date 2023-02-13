package gc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JInfoTest {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            byte[] arr = new byte[1024 * 1024];
            list.add(arr);
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
