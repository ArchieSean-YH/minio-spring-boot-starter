package com.github.minio.core;

import com.github.minio.autoconfigure.MinioProperties;
import com.github.minio.constant.StrConstant;
import io.minio.BucketExistsArgs;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * minio模板
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
public class MinioTemplate implements MinioOperations {

    private MinioClient minioClient;

    private MinioProperties properties;

    public void setMinioClient(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void setProperties(MinioProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     * @throws Exception 异常
     */
    @Override
    public void createBucket(String bucketName) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 查询所有桶
     *
     * @return list
     * @throws Exception 异常
     */
    @Override
    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 判断桶是否存在
     *
     * @param bucketName 桶名称
     * @return boolean
     * @throws Exception ex
     */
    @Override
    public boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     * @throws Exception 异常
     */
    @Override
    public void deleteBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 单文件上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param ins         输入流
     * @param contentType 文件mime
     * @return str
     * @throws Exception 异常
     */
    @Override
    public String uploadFile(String bucketName, String objectName, InputStream ins, Long size, String contentType) throws Exception {
        return uploadFileWithPart(bucketName, objectName, ins, size, contentType);
    }

    /**
     * 单文件上传
     *
     * @param objectName  文件名称
     * @param ins         输入流
     * @param contentType 文件mime
     * @return str
     * @throws Exception 异常
     */
    @Override
    public String uploadFile(String objectName, InputStream ins, Long size, String contentType) throws Exception {
        return uploadFileWithPart(properties.getBucketName(), objectName, ins, size, contentType);
    }

    /**
     * 分片上传
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        文件大小
     * @param contentType MIME类型
     * @throws Exception ex
     */
    @Override
    public String uploadFileWithPart(String bucketName, String objectName, InputStream stream, Long size, String contentType) throws Exception {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream, size, properties.getPartSize())
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
        stream.close();
        return properties.getPrefixLink() + StrConstant.BIAS + bucketName + StrConstant.BIAS + objectName;
    }

    /**
     * 根据桶名称、文件名称获取文件信息
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return obj
     * @throws Exception ex
     */
    @Override
    public StatObjectResponse getFileInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    /**
     * 根据文件前缀搜索文件
     *
     * @param bucketName 桶名称
     * @param prefix     文件前缀
     * @param recursive  是否递归查找
     * @return list
     * @throws Exception ex
     */
    @Override
    public List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws Exception {
        List<Item> objectList = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .recursive(recursive)
                .build());
        while (objectsIterator.iterator().hasNext()) {
            objectList.add(objectsIterator.iterator().next().get());
        }
        return objectList;
    }

    /**
     * 根据桶名称、文件名称读取文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return is
     * @throws Exception ex
     */
    @Override
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @throws Exception ex
     */
    @Override
    public void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName)
                .object(objectName).build());
    }

    /**
     * 合并文件
     *
     * @param bucketName       桶名称
     * @param chunkNames       文件分片集合
     * @param targetObjectName 目标文件名
     * @return str
     * @throws Exception ex
     */
    @Override
    public ObjectWriteResponse composeObject(String bucketName, List<String> chunkNames, String targetObjectName) throws Exception {
        List<ComposeSource> sources = new ArrayList<>(chunkNames.size());
        for (String chunkName : chunkNames) {
            ComposeSource composeSource = ComposeSource.builder()
                    .bucket(bucketName)
                    .object(chunkName)
                    .build();
            sources.add(composeSource);
        }
        return minioClient.composeObject(ComposeObjectArgs.builder()
                .bucket(bucketName)
                .sources(sources)
                .object(targetObjectName)
                .build());

    }
}
