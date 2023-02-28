package com.hanssem.remodeling.content.admin.repository.client.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HanssemMallAdminExcelDownloadLogVo {
	private int downDataCnt;
    private String queryString;
    private int roleIdx;
    private String regId;
    private String pageNo;
    private String excelID;
}
