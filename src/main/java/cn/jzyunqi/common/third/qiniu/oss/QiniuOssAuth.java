package cn.jzyunqi.common.third.qiniu.oss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/5/27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QiniuOssAuth {
    private String accessKey;
    private String secretKey;
    private long tokenExpiration;
    private String bucket;
    private Map<String, Object> policy;
}
