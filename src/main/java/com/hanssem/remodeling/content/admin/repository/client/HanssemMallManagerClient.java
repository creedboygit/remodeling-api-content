package com.hanssem.remodeling.content.admin.repository.client;

import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminEventLogVo;
import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminExcelDownloadLogVo;
import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminLogResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "HanssemMallManagerClient", url = "${feign.baseUrl.HanssemMallManagerClient}")
public interface HanssemMallManagerClient {

    @PostMapping(value = "/API/logging/insertSyPageLog")
    HanssemMallAdminLogResultVo adminEventLogInsert(@RequestBody final HanssemMallAdminEventLogVo eventInfo);
    
    @PostMapping(value = "/API/logging/insertDownRoleUserHist")
    HanssemMallAdminLogResultVo adminExcelDownloadLogInsert(@RequestBody final HanssemMallAdminExcelDownloadLogVo cancelData);
    
}
