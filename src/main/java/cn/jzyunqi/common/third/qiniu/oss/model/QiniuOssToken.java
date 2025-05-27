package cn.jzyunqi.common.third.qiniu.oss.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wiiyaya
 * @date 2019/3/20.
 */
@Getter
@Setter
public class QiniuOssToken implements Serializable {
	@Serial
	private static final long serialVersionUID = 7158459875570122791L;

	/**
	 *  upload Token
	 */
	private String uploadToken;
}
