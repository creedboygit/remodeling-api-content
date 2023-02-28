package com.hanssem.remodeling.content.api.service.favorite;

import com.hanssem.remodeling.content.api.controller.favorite.request.FavoriteTestRequest;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteQuestionResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleSimpleResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteTestResponse;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import org.springframework.cache.annotation.Cacheable;

public interface FavoriteService {

    @Cacheable(cacheManager = "goodsCacheManager", cacheNames = "favoriteQuestions", key = "")
    FavoriteQuestionResponse questions();

    FavoriteTestResponse answer(FavoriteTestRequest request, AuthClaim authClaim);

    @Cacheable(cacheManager = "goodsCacheManager", cacheNames = "favoriteGetStyleForConsult", key = "#styleId")
    FavoriteStyleSimpleResponse getStyleForConsult(Long styleId);

    @Cacheable(cacheManager = "goodsCacheManager", cacheNames = "favoriteGetStyle", key = "#styleId")
    FavoriteTestResponse getStyle(Long styleId);
}
