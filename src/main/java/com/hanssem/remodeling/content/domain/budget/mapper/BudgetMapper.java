package com.hanssem.remodeling.content.domain.budget.mapper;

import com.hanssem.remodeling.content.api.controller.budget.response.StyleMaterialResponse;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyleMaterial;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {

    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

    @Mapping(target = "constructionField", ignore = true)
    List<StyleMaterialResponse> toStyleMaterialResponse(
            List<BudgetStyleMaterial> budgetStyleMaterialList);

}
