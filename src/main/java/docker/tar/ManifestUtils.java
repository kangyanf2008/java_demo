package docker.tar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ManifestUtils {

    public static void main(String[] args) throws IOException {
        List<ManifestInfo> manifestInfo =  get("image/bash.tar");
        System.out.println("manifestInfo = " + manifestInfo);
    }

    /**
     * [{"Config":"9306da3708d91535d7c2f9d36175365fdab646e1a0d7fca0cf228c531572b405.json","Layers":["151867abb38ff6dd8ed42974160d8b58aea4cf984d64ddde061a6c91ae402a06/layer.tar","5083a16e574a65da9709309235577ebb8525e55ed59cfc35e8c820be0af96b36/layer.tar","b61af5d688bd74a4dd1c0a34ea530af402279f63d60d0baa5fa1c2faa03f04a4/layer.tar"]}]
     * @param imagePath
     * @return
     * @throws IOException
     */
    public static List<ManifestInfo> get(String imagePath) throws IOException {
        TarFile tarFile = new TarFile(new File(imagePath));
        List<TarArchiveEntry> entryList = tarFile.getEntries();
        for(TarArchiveEntry o : entryList){
            if("manifest.json".equals(o.getName())) {
                try {
                    byte[] manifest = IOUtils.readFully(tarFile.getInputStream(o), (int)o.getRealSize());
                    System.out.println(JSONObject.parse(manifest).toString());
                    return JSONArray.parseArray(new String(manifest), ManifestInfo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}
