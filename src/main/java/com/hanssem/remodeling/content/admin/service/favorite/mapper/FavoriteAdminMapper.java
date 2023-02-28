package com.hanssem.remodeling.content.admin.service.favorite.mapper;

import com.hanssem.remodeling.content.admin.service.favorite.dto.AddFavoriteStyleDto;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyle;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FavoriteAdminMapper {

    FavoriteAdminMapper INSTANCE = Mappers.getMapper(FavoriteAdminMapper.class);

    FavoriteStyle toFavoriteStyle(AddFavoriteStyleDto favoriteDto);

}