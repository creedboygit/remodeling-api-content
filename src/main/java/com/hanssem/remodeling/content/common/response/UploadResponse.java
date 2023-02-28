package com.hanssem.remodeling.content.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UploadResponse {

    private int fileWidth;
    private int fileHeight;
    private long fileSize;
    private String fileName;
    private String fileExtension;
    private String fileMimeType;
    private String filePath;
    private String fileUrl;

}
