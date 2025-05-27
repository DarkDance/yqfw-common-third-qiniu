package cn.jzyunqi.common.third.qiniu.oss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wiiyaya
 * @since 2025/5/27
 */
@Configuration
@Slf4j
public class QiniuOssConfig {

    @Bean
    public QiniuOssClient qiniuOssClient() {
        return new QiniuOssClient();
    }
}
