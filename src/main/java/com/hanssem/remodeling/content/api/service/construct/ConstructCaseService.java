package com.hanssem.remodeling.content.api.service.construct;

import com.hanssem.remodeling.content.api.controller.construct.request.ConstructCaseRequest;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseResponse;
import com.hanssem.remodeling.content.api.repository.client.HanssemMallClient;
import com.hanssem.remodeling.content.api.service.construct.dto.ConstructCaseDto;
import com.hanssem.remodeling.content.api.service.construct.vo.MallConstructCaseVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConstructCaseService {

    private final HanssemMallClient hanssemMallClient;

    public PageResponse<ConstructCaseResponse> getConstructCase(ConstructCaseRequest request) {

        ConstructCaseDto dto = new ConstructCaseDto(
                request.getSort(),
                request.getPage(),
                request.getSize(),
                request.getCustNo(),
                request.getShopId(),
                request.getGoodsId(),
                request.getExhibitHall(),
                request.getTypeValue()
        );

        MallConstructCaseVo mallConstructCaseVo = hanssemMallClient.getHanssemMallConstructCase(dto);

        if (mallConstructCaseVo.getCode().equals("500")) {
            throw new CustomException(ResponseCode.ERROR_FEIGN_CLIENT, mallConstructCaseVo.getMessage());
        }

        if (mallConstructCaseVo.getCode().equals(("401"))) {
            throw new CustomException(ResponseCode.CONSTRUCT_REQUIRED_PARAMS_OMITTED, mallConstructCaseVo.getMessage());
        }

        return makePageResponse(dto, mallConstructCaseVo);

    }

    private static PageResponse makePageResponse(ConstructCaseDto request, MallConstructCaseVo mallConstructCaseVo) {

        List<ConstructCaseResponse> list = new ArrayList<>();

        for (MallConstructCaseVo.ContentsListResponse x : mallConstructCaseVo.getContentsList()) {

            ConstructCaseResponse constructCaseResponse = ConstructCaseResponse.builder()
                    .id(Integer.parseInt(x.getSeq()))
                    .title(x.getTitle())
                    .detailUrl(x.getDetailUrl())
                    .imageUrl(x.getImgUrl())
                    .wishYn(x.getWishYn())
                    .newYn(x.getNewYN())
                    .contentsType(x.getContentsTypeCd())
                    .vrUrl(x.getVrUrl())
                    .apartment(ConstructCaseResponse.Apartment.builder()
                            .name(x.getAddrAptNm())
                            .area(Integer.parseInt(x.getArea1()))
//                            .ptyp(Integer.parseInt(x.getArea2()))
                            .ptyp(x.getArea2())
                            .sidoGungu(x.getSidoSggNm())
                            .build())
                    .shop(ConstructCaseResponse.Shop.builder()
                            .name(x.getShopNm())
                            .image(x.getShopImgUrl())
                            .build())
                    .build();

            list.add(constructCaseResponse);
        }

        return PageResponse.of(
                list,
                PageRequest.of(
                        Integer.parseInt(request.getCurPage()) - 1,
                        Integer.parseInt(request.getPageRow())),
                Long.parseLong(mallConstructCaseVo.getPagingMap().getTotalRecordCount()));
    }
}
