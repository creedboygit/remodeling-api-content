package com.hanssem.remodeling.content.admin.service.common;

import com.hanssem.remodeling.content.common.request.UploadRequest;
import com.hanssem.remodeling.content.common.response.UploadResponse;
import com.hanssem.remodeling.content.common.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Configuration
@RequiredArgsConstructor
public class CommonAdminService {

    private final S3Uploader s3Uploader;

    public UploadResponse goodsImageUpload(UploadRequest request) {
        return s3Uploader.putS3Target(request.getFile(), "temp/sample");
    }

    public UploadResponse goodsImageUpload(UploadRequest request, String path) {
        return s3Uploader.putS3Target(request.getFile(), path);
    }

    public void remove(String path) {
        s3Uploader.removeS3(path);
    }

    public UploadResponse goodsImageUrlToUpload(String url, String path) throws Exception {
        return s3Uploader.putS3ForUrlImage(url, path);
    }
}
