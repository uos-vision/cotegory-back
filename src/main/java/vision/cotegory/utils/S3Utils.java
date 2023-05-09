package vision.cotegory.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vision.cotegory.exception.exception.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Utils {

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucket;
    @Value("${cloud.aws.s3.dir.user-image.name}")
    private String userImageDirName;
    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) {
        String fileKey = userImageDirName + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        try (InputStream inputStream = multipartFile.getInputStream()){
            objMeta.setContentLength(inputStream.available());
            amazonS3.putObject(bucket, fileKey, inputStream, objMeta);
        } catch (IOException e) {
            throw new S3Exception(e);
        }

        return amazonS3.getUrl(bucket, fileKey).toString();
    }

    public void delete(String url) {
        if (url == null)
            return;
        try{
            url = URLDecoder.decode(url, "UTF-8");
        }catch (Exception e){
            throw new S3Exception(e);
        }
        AmazonS3URI amazonS3URI = new AmazonS3URI(url);
        String targetBucket = amazonS3URI.getBucket();
        String targetKey = amazonS3URI.getKey();
        log.info("[user-image-delete]bucket:{}, key:{}", targetBucket, targetKey);
        amazonS3.deleteObject(targetBucket, targetKey);
    }
}
