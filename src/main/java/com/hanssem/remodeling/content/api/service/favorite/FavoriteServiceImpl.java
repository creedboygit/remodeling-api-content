package com.hanssem.remodeling.content.api.service.favorite;

import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteImageRepository;
import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteStyleRepository;
import com.hanssem.remodeling.content.admin.repository.favorite.FavoriteTagRepository;
import com.hanssem.remodeling.content.api.controller.favorite.request.FavoriteTestRequest;
import com.hanssem.remodeling.content.api.controller.favorite.response.ConsultResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteImageResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteQuestionResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleSimpleResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteTagResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteTestResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsSimpleResponse;
import com.hanssem.remodeling.content.api.repository.favorite.FavoriteQueryRepository;
import com.hanssem.remodeling.content.api.repository.goods.GoodsQueryRepository;
import com.hanssem.remodeling.content.api.service.commoncode.CommoncodeService;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo.CommoncodeResponse;
import com.hanssem.remodeling.content.api.service.favorite.mapper.FavoriteMapper;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.common.util.Utility;
import com.hanssem.remodeling.content.constant.CommoncodeType;
import com.hanssem.remodeling.content.constant.FavoriteTagType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImage;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyle;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    @Value("${content.favorite-test.consult.channel-type}")
    private String consultChannelType;

    private final FavoriteStyleRepository favoriteStyleRepository;
    private final FavoriteImageRepository favoriteImageRepository;
    private final FavoriteTagRepository favoriteTagRepository;
    private final FavoriteQueryRepository favoriteQueryRepository;
    private final GoodsQueryRepository goodsQueryRepository;
    private final CommoncodeService commoncodeService;

    @Override
    @Transactional(readOnly = true)
    public FavoriteQuestionResponse questions() {
        List<FavoriteImage> imageEntities = favoriteImageRepository.findAll();
        List<FavoriteTag> colorEntities = favoriteTagRepository.findByAllTags(FavoriteTagType.COLOR);
        List<FavoriteTag> keywordEntities = favoriteTagRepository.findByAllTags(FavoriteTagType.KEYWORD);
        List<FavoriteTag> materialEntities = favoriteTagRepository.findByAllTags(FavoriteTagType.MATERIAL);

        List<FavoriteImageResponse> imageDtos = FavoriteMapper.INSTANCE.toFavoriteImageDtos(imageEntities);
        List<FavoriteTagResponse> colorDtos = FavoriteMapper.INSTANCE.toFavoriteDtos(colorEntities);
        List<FavoriteTagResponse> keywordDtos = FavoriteMapper.INSTANCE.toFavoriteDtos(keywordEntities);
        List<FavoriteTagResponse> materialDtos = FavoriteMapper.INSTANCE.toFavoriteDtos(materialEntities);
        FavoriteQuestionResponse questionResponse = new FavoriteQuestionResponse();
        questionResponse.setImages(imageDtos);
        questionResponse.setColors(colorDtos);
        questionResponse.setKeywords(keywordDtos);
        questionResponse.setMaterials(materialDtos);

        return questionResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public FavoriteTestResponse answer(FavoriteTestRequest request, AuthClaim authClaim) {
        FavoriteTestResponse response = new FavoriteTestResponse();

        //스타일 로직 수행 -> 스타일 ID 추출
        String styleCode = calculateStyle(request);

        //스타일 ID 조회
        FavoriteStyle favoriteStyle = favoriteStyleRepository.findByStyleCode(styleCode);
        FavoriteStyleResponse favoriteStyleResponse = FavoriteMapper.INSTANCE.toFavoriteStyleDto(favoriteStyle);
        favoriteStyleResponse.setStyleImages(favoriteStyle.getStyleImages().stream().map(x -> x.getImagePath()).toList());
        response.setFavoriteStyle(favoriteStyleResponse);

        //상담신청 처리
        ConsultResponse consultResponse = ConsultResponse.builder().channelId(favoriteStyleResponse.getId()).channelType(consultChannelType).build();
        response.setConsultResponse(consultResponse);

        //추천상품 조회
        List<GoodsSimpleResponse> recommendGoods = getRecommendGoods(styleCode);

        //연관상품 조회
        List<GoodsSimpleResponse> relatedGoods = getRelatedGoods(styleCode);

        //코드명 변환
        CommoncodeVo commoncode = commoncodeService.getCommoncode(CommoncodeType.GOODS_BADGE_CODE);
        setBadgeCodeValues(commoncode, recommendGoods);
        setBadgeCodeValues(commoncode, relatedGoods);

        response.setRecommendGoods(recommendGoods);
        response.setRelatedGoods(relatedGoods);

        return response;
    }

    private void setBadgeCodeValues(CommoncodeVo commoncode, List<GoodsSimpleResponse> list) {
        for (GoodsSimpleResponse object : list) {
            for (CommoncodeResponse code : commoncode.getData()) {
                if (code.getCodeValue().equals(object.getGoodsBadgeTypeCode())) {
                    object.setGoodsBadgeTypeCodeName(code.getCodeValueName());
                    break;
                }
            }
        }
    }

    private List<GoodsSimpleResponse> getRecommendGoods(String styleCode) {
        return goodsQueryRepository.findByStyleCode(styleCode);
    }

    private List<GoodsSimpleResponse> getRelatedGoods(String styleCode) {
        return goodsQueryRepository.findByRelatedStyleCode(styleCode);
    }

    /**
     * 스타일 계산하기
     * (1) image 포함된 style 태그 수집
     * (2) color, keyword 포함된 style 태그 수집
     * (3) 1,2 결과 group by
     * (4) style count sort 가장 높은 수의 styleCode return
     */
    private String calculateStyle(FavoriteTestRequest request) {

        List<StyleGroupDto> result = new ArrayList<>();
        result.addAll(favoriteQueryRepository.findByImageStyle(request));
        result.addAll(favoriteQueryRepository.findByStyle(request));

        Map<String, List<StyleGroupDto>> map = result.stream()
            .collect(Collectors.groupingBy(StyleGroupDto::getStyleCode));

        Map<String, Integer> sortedMap = new HashMap<>();
        for (Map.Entry<String, List<StyleGroupDto>> pair : map.entrySet()) {
            sortedMap.put(pair.getKey(), pair.getValue().size());
        }

        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(sortedMap.entrySet());
        entryList.sort(((o1, o2) -> sortedMap.get(o1.getKey()) - sortedMap.get(o2.getKey())));

        if (entryList == null || entryList.size() < 1) {
            throw new CustomException(ResponseCode.FAVORITE_TEST_FEEDBACK_ERROR);
        }

        return entryList.get(entryList.size() - 1).getKey();
    }

    @Override
    @Transactional(readOnly = true)
    public FavoriteStyleSimpleResponse getStyleForConsult(Long styleId) {
        FavoriteStyle  favoriteStyle = favoriteStyleRepository.findById(styleId)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_DATA));;

        return FavoriteStyleSimpleResponse.builder()
            .id(favoriteStyle.getId())
            .styleCode(favoriteStyle.getStyleCode())
            .title(Utility.getJoinString(favoriteStyle.getStyleName(), "(", favoriteStyle.getManageName(), ")"))
            .thumbUrl(favoriteStyle.getStyleImages().get(0).getImagePath())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FavoriteTestResponse getStyle(Long styleId) {
        FavoriteTestResponse response = new FavoriteTestResponse();
        FavoriteStyle  favoriteStyle = favoriteStyleRepository.findById(styleId)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_DATA));;

        //
        FavoriteStyleResponse favoriteStyleResponse = FavoriteMapper.INSTANCE.toFavoriteStyleDto(favoriteStyle);
        favoriteStyleResponse.setStyleImages(favoriteStyle.getStyleImages().stream().map(x -> x.getImagePath()).toList());
        response.setFavoriteStyle(favoriteStyleResponse);

        //상담신청 처리
        ConsultResponse consultResponse = ConsultResponse.builder().channelId(favoriteStyleResponse.getId()).channelType(consultChannelType).build();
        response.setConsultResponse(consultResponse);

        //추천상품 조회
        List<GoodsSimpleResponse> recommendGoods = getRecommendGoods(favoriteStyle.getStyleCode());

        //연관상품 조회
        List<GoodsSimpleResponse> relatedGoods = getRelatedGoods(favoriteStyle.getStyleCode());
        //코드명 변환
        CommoncodeVo commoncode = commoncodeService.getCommoncode(CommoncodeType.GOODS_BADGE_CODE);
        setBadgeCodeValues(commoncode, recommendGoods);
        setBadgeCodeValues(commoncode, relatedGoods);

        response.setRecommendGoods(recommendGoods);
        response.setRelatedGoods(relatedGoods);
        //

        return response;
    }
}
