package cn.jzyunqi.common.third.qiniu.oss;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.qiniu.oss.constant.QiniuOssTokenParams;
import cn.jzyunqi.common.third.qiniu.oss.model.QiniuOssToken;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.DigestUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import jakarta.annotation.Resource;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2019/3/18.
 */
public class QiniuOssClient {

    @Resource
    private QiniuOssAuthRepository qiniuOssAuthRepository;

    @Resource
    private ObjectMapper objectMapper;

    public QiniuOssToken uploadToken(String accessKey, String uid, Map<String, Object> params) throws BusinessException {
        QiniuOssAuth qiniuOssAuth = qiniuOssAuthRepository.choosQiniuOssAuth(accessKey);
        try {
            Map<String, Object> currPolicy = new HashMap<>(qiniuOssAuth.getPolicy());
            if (CollectionUtilPlus.Map.isNotEmpty(params)) {
                currPolicy.putAll(params);
            }

            String key = (String) currPolicy.get(QiniuOssTokenParams.KEY);
            currPolicy.remove(QiniuOssTokenParams.KEY);
            currPolicy.put("scope", key == null ? qiniuOssAuth.getBucket() : qiniuOssAuth.getBucket() + ":" + key);
            currPolicy.put("deadline", System.currentTimeMillis() / 1000L + qiniuOssAuth.getTokenExpiration());

            byte[] policyByte = objectMapper.writeValueAsString(currPolicy).getBytes(StringUtilPlus.UTF_8);
            String policyStr = DigestUtilPlus.Base64.encodeBase64String(policyByte);
            String policySign = DigestUtilPlus.Mac.sign(policyStr, qiniuOssAuth.getSecretKey(), DigestUtilPlus.MacAlgo.H_SHA1, Boolean.TRUE);
            String token = StringUtilPlus.joinWith(StringUtilPlus.COLON, qiniuOssAuth.getAccessKey(), policySign, policyStr);

            QiniuOssToken qiniuOssToken = new QiniuOssToken();
            qiniuOssToken.setUploadToken(token);
            return qiniuOssToken;
        } catch (Exception e) {
            throw new BusinessException("common_error_qiniu_upload_token_error");
        }
    }
}

