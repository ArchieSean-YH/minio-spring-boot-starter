package com.github.minio.core;

import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.List;

/**
 * minio模板方法抽象类
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
public interface MinioOperations {
    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     * @throws Exception 异常
     */
    void createBucket(String bucketName) throws Exception;

    /**
     * 列出所有存储桶
     *
     * @return list
     * @throws Exception ex
     */
    List<Bucket> listBuckets() throws Exception;

    /**
     * 判断存储桶是否存在
     *
     * @param bucketName 桶名称
     * @return boolean
     * @throws Exception ex
     */
    boolean bucketExists(String bucketName) throws Exception;

    /**
     * 删除存储桶
     *
     * @param bucketName 桶名称
     * @throws Exception 异常
     */
    void deleteBucket(String bucketName) throws Exception;

    /**
     * 文件上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param ins         输入流
     * @param size        文件大小
     * @param contentType 文件mime
     * @return str
     * @throws Exception 异常
     */
    String uploadFile(String bucketName, String objectName, InputStream ins, Long size, String contentType) throws Exception;

    /**
     * 文件上传到默认桶
     *
     * @param objectName  文件名称
     * @param ins         输入流
     * @param size        文件大小
     * @param contentType 文件mime
     * @return str
     * @throws Exception 异常
     */
    String uploadFile(String objectName, InputStream ins, Long size, String contentType) throws Exception;

    /**
     * 分片上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        文件大小
     * @param contentType MIME类型
     * @throws Exception 异常
     */
    String uploadFileWithPart(String bucketName, String objectName, InputStream stream, Long size, String contentType) throws Exception;

    /**
     * 根据文件名获取指定桶中的文件信息
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return obj
     * @throws Exception ex
     */
    StatObjectResponse getFileInfo(String bucketName, String objectName) throws Exception;

    /**
     * 根据文件名前缀查找文件
     *
     * @param bucketName 桶名称
     * @param prefix     文件前缀
     * @param recursive  是否递归查找
     * @return list
     * @throws Exception ex
     */
    List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws Exception;

    /**
     * 根据文件名，查找指定的桶的文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return stream
     * @throws Exception ex
     */
    InputStream getObject(String bucketName, String objectName) throws Exception;

    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @throws Exception 异常
     */
    void removeObject(String bucketName, String objectName) throws Exception;

    /**
     * 文件合并
     *
     * @param bucketName       桶名称
     * @param chunkNames       文件分片集合
     * @param targetObjectName 目标文件名
     * @return ObjectWriteResponse
     * @throws Exception 异常
     */
    ObjectWriteResponse composeObject(String bucketName, List<String> chunkNames, String targetObjectName) throws Exception;

}

