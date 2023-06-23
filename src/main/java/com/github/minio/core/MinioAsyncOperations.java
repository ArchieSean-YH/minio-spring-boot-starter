package com.github.minio.core;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * minio模板方法抽象类
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
public interface MinioAsyncOperations {
    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     * @return CompletableFuture<Void>
     * @throws Exception 异常
     */
    CompletableFuture<Void> createBucket(String bucketName) throws Exception;

    /**
     * 查询所有桶
     *
     * @return list
     * @throws Exception 异常
     */
    CompletableFuture<List<Bucket>> listBuckets() throws Exception;

    /**
     * 判断桶是否存在
     *
     * @param bucketName 桶名称
     * @return boolean
     * @throws Exception ex
     */

    CompletableFuture<Boolean> bucketExists(String bucketName) throws Exception;

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     * @return obj
     * @throws Exception 异常
     */

    CompletableFuture<Void> deleteBucket(String bucketName) throws Exception;

    /**
     * 单文件上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param ins         输入流
     * @param contentType 文件mime
     * @return str
     * @param size 文件大小
     * @throws Exception 异常
     */

    CompletableFuture<ObjectWriteResponse> uploadFile(String bucketName, String objectName, InputStream ins,
                                                      Long size, String contentType) throws Exception;

    /**
     * 单文件上传
     *
     * @param objectName  文件名称
     * @param ins         输入流
     * @param contentType 文件mime
     * @return str
     * @throws Exception 异常
     */
    CompletableFuture<ObjectWriteResponse> uploadFile(String objectName, InputStream ins, Long size, String contentType) throws Exception;

    /**
     * 分片上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        文件大小
     * @param contentType MIME类型
     * @return obj
     * @throws Exception ex
     */
    CompletableFuture<ObjectWriteResponse> uploadFileWithPart(String bucketName, String objectName,
                                                              InputStream stream, Long size, String contentType)
            throws Exception;

    /**
     * 根据桶名称、文件名称获取文件信息
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return obj
     * @throws Exception ex
     */
    CompletableFuture<StatObjectResponse> getFileInfo(String bucketName, String objectName) throws Exception;

    /**
     * 根据文件前缀搜索文件
     *
     * @param bucketName 桶名称
     * @param prefix     文件前缀
     * @param recursive  是否递归查找
     * @return list
     * @throws Exception ex
     */

    List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws Exception;

    /**
     * 根据桶名称、文件名称读取文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return is
     * @throws Exception ex
     */

    CompletableFuture<GetObjectResponse> getObject(String bucketName, String objectName) throws Exception;

    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @throws Exception ex
     * @return  obj
     */

    CompletableFuture<Void> removeObject(String bucketName, String objectName) throws Exception;

    /**
     * 合并文件
     *
     * @param bucketName       桶名称
     * @param chunkNames       文件分片集合
     * @param targetObjectName 目标文件名
     * @return str
     * @throws Exception ex
     */

    CompletableFuture<ObjectWriteResponse> composeObject(String bucketName, List<String> chunkNames,
                                                         String targetObjectName) throws Exception;
}

