package com.hanssem.remodeling.content.api.service.commoncode;

import com.hanssem.remodeling.content.api.repository.client.CommoncodeClient;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import com.hanssem.remodeling.content.constant.CommoncodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommoncodeService {

    private final CommoncodeClient commoncodeClient;

    public CommoncodeVo getCommoncode(CommoncodeType code) {
        CommoncodeVo commoncode = commoncodeClient.getCommoncode(code.name());
        return commoncode;
    }
}
