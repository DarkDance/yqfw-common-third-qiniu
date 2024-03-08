package cn.jzyunqi.common.third.qiniu.client;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.qiniu.constant.QiniuOssTokenParams;
import cn.jzyunqi.common.third.qiniu.model.QiniuOssToken;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.DigestUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2019/3/18.
 */
public class QiniuOssTokenGenClient {

	private final String accessKey;
	private final String secretKey;
	private final long tokenExpiration;
	private final String bucket;
	private final Map<String, Object> policy;

	private final ObjectMapper objectMapper;

	public QiniuOssTokenGenClient(String accessKey, String secretKey, long tokenExpiration, String bucket, Map<String, Object> policy) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.tokenExpiration = tokenExpiration;
		this.bucket = bucket;
		this.policy = policy;
		this.objectMapper = new ObjectMapper();
	}

	public QiniuOssToken uploadToken(String uid, Map<String, Object> params) throws BusinessException {
		try {
			Map<String, Object> currPolicy = new HashMap<>(policy);
			if(CollectionUtilPlus.Map.isNotEmpty(params)){
				currPolicy.putAll(params);
			}

			String key = (String)currPolicy.get(QiniuOssTokenParams.KEY);
			currPolicy.remove(QiniuOssTokenParams.KEY);
			currPolicy.put("scope", key == null ? bucket : bucket + ":" + key);
			currPolicy.put("deadline", System.currentTimeMillis() / 1000L + tokenExpiration);

			byte[] policyByte = objectMapper.writeValueAsString(currPolicy).getBytes(StringUtilPlus.UTF_8);
			String policyStr = DigestUtilPlus.Base64.encodeBase64String(policyByte);
			String policySign = DigestUtilPlus.Mac.sign(policyStr, secretKey, DigestUtilPlus.MacAlgo.H_SHA1, Boolean.TRUE);
			String token = StringUtilPlus.joinWith(StringUtilPlus.COLON, this.accessKey, policySign, policyStr);

			QiniuOssToken qiniuOssToken = new QiniuOssToken();
			qiniuOssToken.setUploadToken(token);
			return qiniuOssToken;
		} catch (Exception e) {
			throw new BusinessException("common_error_qiniu_upload_token_error");
		}
	}
}

