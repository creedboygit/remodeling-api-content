package com.hanssem.remodeling.content.api.service.common;

import com.hanssem.remodeling.content.common.request.UploadRequest;
import com.hanssem.remodeling.content.common.response.UploadResponse;
import com.hanssem.remodeling.content.common.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Configuration
@RequiredArgsConstructor
public class CommonService {

    private final S3Uploader s3Uploader;

    public UploadResponse upload(UploadRequest request) {
        return s3Uploader.putS3Target(request.getFile(), "temp/sample");
    }

    public UploadResponse upload3(MultipartFile file) {
        return s3Uploader.putS3Target(file, "temp/sample");
    }
}
