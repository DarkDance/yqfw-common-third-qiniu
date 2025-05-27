package cn.jzyunqi.common.third.qiniu.oss;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wiiyaya
 * @since 2025/5/27
 */
public abstract class QiniuOssAuthRepository implements InitializingBean {

    private final Map<String, QiniuOssAuth> authMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<QiniuOssAuth> aliOssAuthList = initAliOssAuthList();
        for (QiniuOssAuth qiniuOssAuth : aliOssAuthList) {
            authMap.put(qiniuOssAuth.getAccessKey(), qiniuOssAuth);
        }
    }

    public QiniuOssAuth choosAliOssAuth(String wxAppId) {
        return authMap.get(wxAppId);
    }

    public void addAliOssAuth(QiniuOssAuth qiniuOssAuth) {
        authMap.put(qiniuOssAuth.getAccessKey(), qiniuOssAuth);
    }

    public void removeAliOssAuth(String wxAppId) {
        authMap.remove(wxAppId);
    }

    public List<QiniuOssAuth> getAliOssAuthList() {
        return new ArrayList<>(authMap.values());
    }

    public abstract List<QiniuOssAuth> initAliOssAuthList();
}
