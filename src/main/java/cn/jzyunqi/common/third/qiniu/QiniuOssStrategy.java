package cn.jzyunqi.common.third.qiniu;


import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.feature.oss.OssHelper;
import cn.jzyunqi.common.third.qiniu.client.QiniuOssTokenGenClient;
import cn.jzyunqi.common.third.qiniu.model.QiniuOssToken;
import cn.jzyunqi.common.utils.StringUtilPlus;

import java.util.HashMap;

/**
 * @author wiiyaya
 * @date 2016/5/19
 */
public class QiniuOssStrategy implements OssHelper {

    private final QiniuOssTokenGenClient qiniuOssTokenGenHelper;

    public QiniuOssStrategy(QiniuOssTokenGenClient qiniuOssTokenGenHelper){
        this.qiniuOssTokenGenHelper = qiniuOssTokenGenHelper;
    }

    @Override
    public QiniuOssToken generateUploadToken(String uid) throws BusinessException {
        return qiniuOssTokenGenHelper.uploadToken(null, new HashMap<>());
    }

    @Override
    public String restoreFile(String uid, String headImgUrl, String bucket) throws BusinessException {
        return null;
    }

    @Override
    public String getFirstImageFullUrl(String images, boolean pc, int width, int height) {
        StringBuilder fileName = new StringBuilder();
        if (StringUtilPlus.isNotEmpty(images)) {
            fileName.append(StringUtilPlus.split(images, StringUtilPlus.COMMA)[0]);
            fileName.append("?imageView2/");
            fileName.append(pc ? 2 : 0);
            if (width > 0) {
                fileName.append("/w/");
                fileName.append(width);
            }
            if (height > 0) {
                fileName.append("/h/");
                fileName.append(height);
            }
        }
        return fileName.toString();
    }

}
