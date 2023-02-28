package com.hanssem.remodeling.content.admin.repository.client.vo;

import com.hanssem.remodeling.content.common.error.CustomException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HanssemMallAdminLogResultVo implements Serializable{

	private int resultCode;

	private String resultMessage;
	
	public void checkResult() {
		validateResponse();
	}
    
    protected void validateResponse() {
        if (this.resultCode != HttpStatus.SC_OK) {
            throw new CustomException(resultCode, resultMessage);
        }
    }
}
