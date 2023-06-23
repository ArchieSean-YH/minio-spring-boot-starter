package com.github.minio.autoconfigure;

import com.github.minio.core.MinioAsyncTemplate;
import com.github.minio.core.MinioTemplate;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置类
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
@AutoConfiguration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {
    /**
     * 同步客户端模板
     *
     * @param properties 配置文件
     * @return MinioTemplate
     */
    @Bean(name = "minioTemplate")
    @ConditionalOnMissingBean
    public MinioTemplate minioTemplate(MinioProperties properties) {
        MinioTemplate template = new MinioTemplate();
        //minio同步客户端
        template.setMinioClient(MinioClient.builder()
                .endpoint(properties.getHost())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build());
        //设置配置
        template.setProperties(properties);
        return template;
    }

    /**
     * 异步客户端模板1
     *
     * @param properties 配置文件
     * @return MinioAsyncTemplate
     */
    @Bean(name = "minioAsyncTemplate")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.minio", name = "asyncEnable", havingValue = "true")
    public MinioAsyncTemplate minioAsyncTemplate(MinioProperties properties) {
        MinioAsyncTemplate template = new MinioAsyncTemplate();
        //minio异步客户端
        template.setMinioClient(MinioAsyncClient.builder()
                .endpoint(properties.getHost())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build());
        //设置配置
        template.setProperties(properties);
        return template;
    }

}
