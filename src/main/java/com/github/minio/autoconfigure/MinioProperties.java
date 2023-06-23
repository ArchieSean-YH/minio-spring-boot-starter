package com.github.minio.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio配置信息
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
@ConfigurationProperties(prefix = "spring.minio")
public class MinioProperties {
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 【默认】桶名称
     */
    private String bucketName;
    /**
     * 分段大小，单位：字节,大小在5MB到5GB之间,包含两端
     */
    private long partSize;
    /**
     * 连接地址
     */
    private String host;

    /**
     * 异步模板开关
     */
    private boolean asyncEnable;

    /**
     * 文件链接前缀，用于文件展示使用,不以斜杠结尾
     */
    private String prefixLink;

    public boolean getAsyncEnable() {
        return asyncEnable;
    }

    public void setAsyncEnable(boolean asyncEnable) {
        this.asyncEnable = asyncEnable;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getPartSize() {
        return partSize;
    }

    public void setPartSize(long partSize) {
        this.partSize = partSize;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPrefixLink() {
        return prefixLink;
    }

    public void setPrefixLink(String prefixLink) {
        this.prefixLink = prefixLink;
    }
}
