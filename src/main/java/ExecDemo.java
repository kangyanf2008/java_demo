import java.io.*;
import net.jimmc.jshortcut.JShellLink;

public class ExecDemo {
    static Runtime runtime = Runtime.getRuntime();
    public static void main(String[] args) {
        //ping("www.baidu.com");
        boolean isSucc = createLnk("C:\\Users\\EDZ\\Desktop\\", "imda-client.exe",
                "C:\\Users\\EDZ\\Desktop\\dist\\win-unpacked\\imda-client.exe");
        System.out.println(isSucc);
        runtime.exit(1);
    }
    public static void ping(String url) {
        String cmd = "ping " + url;
        try {
            Process process = runtime.exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    public static boolean createLnk(String savePath, String appName, String exePath) {
        try {
            File f = new File(exePath);
            File f2 = new File(savePath);
            if (!f.exists() || !f2.exists()) {
                return false;
            }
            JShellLink link = new JShellLink();
            // ���·��
            link.setFolder(savePath);
            // ��ݷ�ʽ����
            link.setName(appName);
            // ָ���exe
            link.setPath(exePath);
            link.save();
            return true;
        } catch (Throwable e) {
            // �Ǹ��ĺ��jarӦ�ã�ֱ��ȫ���׳�
            e.printStackTrace();
        }
        return false;
    }

}
