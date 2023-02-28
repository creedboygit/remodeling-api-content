package com.hanssem.remodeling.content.common.util;

import com.hanssem.remodeling.content.common.config.WebConfig;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import com.hanssem.remodeling.content.common.response.UploadResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    private final WebConfig webConfig;

    public String makeCdnUrl(String path) {
        if (path == null || path.isEmpty()) return null;
        return String.format("%s/%s", webConfig.getCdnUrl(), path);
    }

    public UploadResponse putS3Target(MultipartFile file, final String path) {
        val objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(MediaType.IMAGE_PNG_VALUE.equals(file.getContentType()) ? MediaType.IMAGE_PNG_VALUE : MediaType.IMAGE_JPEG_VALUE);
        log.info("==> bucket:{}, cdnUrl:{}", webConfig.getBucket(), webConfig.getCdnUrl());

        val fileName = new StringJoiner("/").add(path).add(Utility.getJoinString(UUID.randomUUID().toString(), ".", FilenameUtils.getExtension(file.getOriginalFilename()))).toString();
        try {
            amazonS3Client.putObject(new PutObjectRequest(webConfig.getBucket(), fileName, file.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return UploadResponse.builder()
            .fileSize(file.getSize())
            .fileName(file.getOriginalFilename())
            .fileExtension(FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase())
            .fileMimeType(file.getContentType())
            .filePath(fileName)
            .fileUrl(makeCdnUrl(fileName))
            .build();
    }

    /**
     * 디자인 > 랜더링 이미지 업로드
     */
    public UploadResponse putS3ForUrlImage(final String url, final String s3Key) throws Exception {

        UploadResponse uploadResponse = putS3(s3Key, new URL(url).openStream());

        // url 업로드인 경우 width, height 임의 세팅 (0*0)
        uploadResponse.setFileWidth(0);
        uploadResponse.setFileHeight(0);

        return uploadResponse;
    }

    private String putS3(MultipartFile multipartFile) {
        val objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(MediaType.IMAGE_PNG_VALUE.equals(multipartFile.getContentType()) ? MediaType.IMAGE_PNG_VALUE : MediaType.IMAGE_JPEG_VALUE);

        val fileName = new StringJoiner("/").add("temp").add(Utility.getJoinString(UUID.randomUUID().toString(), ".", FilenameUtils.getExtension(multipartFile.getOriginalFilename()))).toString();
        try {
            amazonS3Client.putObject(new PutObjectRequest(webConfig.getBucket(), fileName, multipartFile.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 파일 저장
     */
    private UploadResponse putS3(final String fullPath, final InputStream stream) throws IOException {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        PutObjectResult result = amazonS3Client.putObject(
            new PutObjectRequest(webConfig.getBucket(), fullPath, stream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        stream.close();

        return UploadResponse.builder()
            .fileSize(result.getMetadata().getContentLength())
            .fileName(FilenameUtils.getName(fullPath))
            .fileExtension(fullPath.substring(fullPath.lastIndexOf(".") + 1))
            .fileMimeType(objectMetadata.getContentType())
            .filePath(fullPath)
            .fileUrl(makeCdnUrl(fullPath))
            .build();
    }

    /**
     * 파일 삭제
     */
    public void removeS3(final String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        amazonS3Client.deleteObject(webConfig.getBucket(), filePath);
    }

    public String putS3forBudgetStyleImageUpload(InputStream openStream) throws Exception {
        final String fullPath = String.format("content/budget/style/%s.jpg" ,UUID.randomUUID());

        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        amazonS3Client.putObject(new PutObjectRequest(webConfig.getBucket(), fullPath, openStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        openStream.close();

        return fullPath;
    }
}
