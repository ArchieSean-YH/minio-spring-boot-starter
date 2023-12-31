# minio-spring-boot-starter



## 使用步骤：

* 将本项目打包引入项目当中

```xml
<!--        自定义minio-starter-->
<dependency>
  <groupId>com.github</groupId>
  <artifactId>minio-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

* 配置：

```yaml
spring:
  minio:
    access-key: minio账号
    secret-key: minio密码
    bucket-name: 默认桶名称
    part-size: 分片大小，单位:字节
    host: minio的上传地址
    async-enable: 是否开启异步模板方法，选值（true/false）
    prefix-link: 图片访问统一前缀
```

* 使用案例：

```java
/**
 * 文件上传
 *
 * @author ArchieSean
 * @create 2023/6/23
 */
@RequestMapping("file")
@RestController
@RequiredArgsConstructor
public class TestController {
		
  	/**
  	*同步monio模板，MinioAsyncTemplate异步模板，异步时基本所有返回值均为CompletableFuture，根据业务自行处理
  	**/
    private final MinioTemplate minioTemplate;

    @PostMapping("upload")
    @AnonymousAccess
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String fileName = "test/" + SnowflakeId.createId() + suffix;
        String images = minioTemplate.uploadFile(fileName, file.getInputStream(), file.getSize(), file.getContentType());
        return Result.success(images);
    }
}
```

