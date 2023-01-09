package docker.tar;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * [{"Config":"9306da3708d91535d7c2f9d36175365fdab646e1a0d7fca0cf228c531572b405.json","Layers":["151867abb38ff6dd8ed42974160d8b58aea4cf984d64ddde061a6c91ae402a06/layer.tar","5083a16e574a65da9709309235577ebb8525e55ed59cfc35e8c820be0af96b36/layer.tar","b61af5d688bd74a4dd1c0a34ea530af402279f63d60d0baa5fa1c2faa03f04a4/layer.tar"]}]
 */
@Data
public class ManifestInfo {
    @JSONField(name="Config")
    private String config;
    @JSONField(name="Layers")
    private String[] layers;
}
