package com.hanssem.remodeling.content.admin.service.adminlog.mapper;


import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminEventLogVo;
import com.hanssem.remodeling.content.admin.repository.client.vo.HanssemMallAdminExcelDownloadLogVo;
import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminLogMapper {

    AdminLogMapper INSTANCE = Mappers.getMapper(AdminLogMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HanssemMallAdminEventLogVo convertAdminEventInfoToLogVo(AdminEventInfo eventInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HanssemMallAdminExcelDownloadLogVo convertAdminExcelDownloadToDownloadLogVo(AdminEventInfo eventInfo);


}
