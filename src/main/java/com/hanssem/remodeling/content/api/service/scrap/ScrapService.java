package com.hanssem.remodeling.content.api.service.scrap;

import com.hanssem.remodeling.content.api.repository.client.ScrapClient;
import com.hanssem.remodeling.content.api.service.scrap.vo.ScrapExistenceVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.constant.ScrapType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapService {

    private final ScrapClient scrapClient;

    public ScrapExistenceVo getScrapExistence(String token, Long userId, Long goodsId) {

        if (userId != null) {
            return scrapClient.getScrapExistence(token, userId, String.valueOf(goodsId), String.valueOf(ScrapType.REMODELING_GOODS));
        } else {
            return new ScrapExistenceVo(String.valueOf(ResponseCode.SUCCESS), ResponseCode.SUCCESS.getMessage(), false);
        }
    }
}
