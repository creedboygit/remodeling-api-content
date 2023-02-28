package com.hanssem.remodeling.content.common.request;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Schema(name = "UploadRequest", description = "파일 업로드 시 요청 데이터")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public final class UploadRequest {

    @Schema(name = "file", title = "업로드 파일")
    @NotNull
    private MultipartFile file;

    public void validation() {
        if (Objects.isNull(file) || file.getSize() == 0)
            throw new CustomException(ResponseCode.BAD_REQUEST);
    }

}
