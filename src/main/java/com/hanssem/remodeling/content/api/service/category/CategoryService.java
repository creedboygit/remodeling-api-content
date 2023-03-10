package com.hanssem.remodeling.content.api.service.category;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoWithChildrenResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailWithChildrenResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryLocationResponse;
import com.hanssem.remodeling.content.api.repository.client.HanssemMallClient;
import com.hanssem.remodeling.content.api.service.category.mapper.CategoryMapper;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryListVo;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryLocationVo;
import com.hanssem.remodeling.content.api.service.display.dto.DisplayTemplateInfoDto;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final HanssemMallClient hanssemMallClient;

    private final DisplayTemplateInfoDto displayTemplateInfoDto;


    public MallCategoryLocationResponse getMallCategoryLocation(int ctgNo) {

        log.debug("## ctgNo : " + ctgNo);

        MallCategoryLocationVo mallCategoryLocationVo = hanssemMallClient.getHanssemMallCategoryLocation(ctgNo);

        log.debug("mallCategoryLocationVo = " + mallCategoryLocationVo);

        if (!mallCategoryLocationVo.getCode().equals("200")) {
            throw new CustomException(ResponseCode.ERROR_FEIGN_CLIENT, "HANSSEM MALL API ?????? : " + mallCategoryLocationVo.getCode() + " : " + mallCategoryLocationVo.getMessage());
        }

        return CategoryMapper.INSTANCE.toMallCategoryLocationResponse(mallCategoryLocationVo);
    }

    public MallCategoryListResponse getMallApiCategoryList(int ctgNo, String level) {

        log.debug("## ctgNo : " + ctgNo + ", level : " + level);

        MallCategoryListVo mallCategoryListVo = hanssemMallClient.getHanssemMallCategoryList(ctgNo, level);

        log.debug("mallCategoryListVo = " + mallCategoryListVo);

        if (!mallCategoryListVo.getCode().equals("200")) {
            throw new CustomException(ResponseCode.ERROR_FEIGN_CLIENT, "HANSSEM MALL API ?????? : " + mallCategoryListVo.getCode() + " : " + mallCategoryListVo.getMessage());
        }

        return CategoryMapper.INSTANCE.toMallCategoryListResponse(mallCategoryListVo);
    }

    public MallCategoryListResponse getMallLargeCategoryList(int standardLargeCategoryNo) {

        MallCategoryListVo mallLargeCategoryListVo = hanssemMallClient.getHanssemMallCategoryList(standardLargeCategoryNo, "current");

        log.debug("mallLargeCategoryListVo = " + mallLargeCategoryListVo);

        return CategoryMapper.INSTANCE.toMallCategoryListResponse(mallLargeCategoryListVo);
    }

    @Cacheable(cacheManager = "categoryCacheManager", cacheNames = "getMallAllCategoryListCascade", key = "#standardLargeCategoryNo")
    public MallCategoryListDetailWithChildrenResponse getMallAllCategoryListCascade(int standardLargeCategoryNo) {

        List<MallCategoryListDetailInfoWithChildrenResponse> categoryList = getMallAllCategoryListCache(standardLargeCategoryNo);

        return MallCategoryListDetailWithChildrenResponse.builder().categoryList(categoryList).build();
    }

    @CacheEvict(cacheManager = "categoryCacheManager", cacheNames = {"*"}, allEntries = true)
    public void clearCategoryCacheAll() {
    }

    public List<MallCategoryListDetailInfoWithChildrenResponse> getMallAllCategoryListCache(int standardLargeCategoryNo) {

        // ??? ????????????(?????? ????????????) ????????? ??????
        List<MallCategoryListDetailInfoWithChildrenResponse> categoryLargeList = getMallApiCategoryList(standardLargeCategoryNo);

        getMallCategoryTotalList(categoryLargeList);

        return categoryLargeList;
    }

    public MallCategoryListDetailResponse getMallAllCategoryList() {

        MallCategoryListVo mallCategoryList = hanssemMallClient.getHanssemMallCategoryList();
        List<MallCategoryListDetailInfoResponse> categories = CategoryMapper.INSTANCE.toMallCategoryListDetailInfoResponse(mallCategoryList.getCategoryList());

        return MallCategoryListDetailResponse.builder().categoryList(categories).build();
    }

    private void getMallCategoryTotalList(List<MallCategoryListDetailInfoWithChildrenResponse> categoryList) {

        // ??? ??????????????? ?????? ??? ???????????? ????????? ??????
        for (MallCategoryListDetailInfoWithChildrenResponse categoryLarge : categoryList) {

            categoryLarge.setDisplayCategoryTemplateNo(displayTemplateInfoDto.getLargeCategoryTemplateNo());

            // ?????????????????? ????????? ??????
            categoryLarge.setDisplayYn("Y");

            List<MallCategoryListDetailInfoWithChildrenResponse> categoryMiddleList = getMallCategoryListDetail(categoryLarge);

            // ??? ??????????????? ?????? ??? ???????????? ????????? ??????
            for (MallCategoryListDetailInfoWithChildrenResponse categoryMiddle : categoryMiddleList) {

                categoryMiddle.setDisplayCategoryTemplateNo(displayTemplateInfoDto.getMiddleCategoryTemplateNo());
                setCategoryDisplayYn(categoryMiddle, categoryLarge);

                List<MallCategoryListDetailInfoWithChildrenResponse> categorySmallList = getMallCategoryListDetail(categoryMiddle);

                // ??? ??????????????? ?????? ??? ???????????? ????????? ??????
                for (MallCategoryListDetailInfoWithChildrenResponse categorySmall : categorySmallList) {

                    categorySmall.setDisplayCategoryTemplateNo(displayTemplateInfoDto.getSmallCategoryTemplateNo());
                    setCategoryDisplayYn(categorySmall, categoryMiddle);

                    List<MallCategoryListDetailInfoWithChildrenResponse> categoryTinyList = getMallCategoryListDetail(categorySmall);
//                    getMallCategoryListDetail(categorySmall);

                    // ??? ??????????????? ?????? ??? ???????????? ????????? ??????????????? ?????? ?????? ??????
                    for (MallCategoryListDetailInfoWithChildrenResponse categoryTiny : categoryTinyList) {
                        setCategoryDisplayYn(categoryTiny, categorySmall);
                    }
                }
            }
        }
    }

    /**
     * ??????????????? ?????? ??? ????????? ?????? X, ????????? ?????? O
     */
    private static void setCategoryDisplayYn(MallCategoryListDetailInfoWithChildrenResponse childCategory, MallCategoryListDetailInfoWithChildrenResponse parentCategory) {

        if (childCategory.getCategoryName().equals(parentCategory.getCategoryName())) {
            childCategory.setDisplayYn("N");
        } else {
            childCategory.setDisplayYn("Y");
        }
    }

    private List<MallCategoryListDetailInfoWithChildrenResponse> getMallCategoryListDetail(MallCategoryListDetailInfoWithChildrenResponse category) {
        List<MallCategoryListDetailInfoWithChildrenResponse> categoryList = getMallApiCategoryList(category.getCategoryNo());
        category.setChildren(categoryList);
        return categoryList;
    }

    private List<MallCategoryListDetailInfoWithChildrenResponse> getMallApiCategoryList(int categoryNo) {

        MallCategoryListVo mallCategoryList = hanssemMallClient.getHanssemMallCategoryList(categoryNo, "child");
        return CategoryMapper.INSTANCE.toMallCategoryListDetailInfoWithChildrenResponse(mallCategoryList.getCategoryList());
    }
}
