package com.hanssem.remodeling.content.api.service.favorite.mapper;

import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteImageResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleImageResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteTagResponse;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImage;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyle;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyleImage;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteTag;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    List<FavoriteImageResponse> toFavoriteImageDtos(List<FavoriteImage> imageEntities);

    @Mapping(source = "imagePath", target = "imageUrl")
    FavoriteStyleImageResponse toFavoriteStyleImageDto(FavoriteStyleImage favoriteStyleImage);

    @Mapping(source = "styleImages", target = "styleImages", ignore = true)
    FavoriteStyleResponse toFavoriteStyleDto(FavoriteStyle favoriteStyle);

    @Mapping(source = "etc", target = "color")
    @Mapping(source = "imagePath", target = "imageUrl")
    FavoriteTagResponse toFavoriteTagDto(FavoriteTag favoriteTag);

    List<FavoriteTagResponse> toFavoriteDtos(List<FavoriteTag> colorEntities);

}