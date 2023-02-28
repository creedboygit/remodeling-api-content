package com.hanssem.remodeling.content.admin.service.adminlog;

import com.hanssem.remodeling.content.admin.repository.client.HanssemMallManagerClient;
import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminEventLogVo;
import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminExcelDownloadLogVo;
import com.hanssem.remodeling.content.admin.service.adminlog.mapper.AdminLogMapper;
import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLogService {

    private final HanssemMallManagerClient hanssemMallManagerClient;
    private final AdminEventInfo adminEventInfo;

    @Value("${content.admin-api-logging:true}")
    private boolean isLogging;

    public void sendAdminGetEventLog() {
        if (isLogging) {
            HanssemMallAdminEventLogVo logData = AdminLogMapper.INSTANCE.convertAdminEventInfoToLogVo(adminEventInfo);
            hanssemMallManagerClient.adminEventLogInsert(logData).checkResult();
        }
    }

    public void sendAdminPostEventLog(String postBody) {
        if (isLogging) {
            HanssemMallAdminEventLogVo logData = AdminLogMapper.INSTANCE.convertAdminEventInfoToLogVo(adminEventInfo);
            logData.setQueryString(postBody);
            hanssemMallManagerClient.adminEventLogInsert(logData).checkResult();
        }
    }

    public void sendAdminExcelDownloadLog(int rowCnt, String fileName) {
        if (isLogging) {
            HanssemMallAdminExcelDownloadLogVo logData = AdminLogMapper.INSTANCE.convertAdminExcelDownloadToDownloadLogVo(adminEventInfo);
            logData.setDownDataCnt(rowCnt);
            logData.setExcelID(fileName);
            hanssemMallManagerClient.adminExcelDownloadLogInsert(logData).checkResult();
        }
    }
}
