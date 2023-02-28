package com.hanssem.remodeling.content.api.repository.client;

import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryListVo;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryLocationVo;
import com.hanssem.remodeling.content.api.service.construct.dto.ConstructCaseDto;
import com.hanssem.remodeling.content.api.service.construct.vo.MallConstructCaseVo;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallComponentListVo;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallGnbVo;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallMenuVo;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventDetailVo;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventListVo;
import com.hanssem.remodeling.content.api.service.notification.vo.HsMallCustomerDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HanssemMallClient", url = "${feign.baseUrl.HanssemMallClient}")
public interface HanssemMallClient {

    /*
     * 전시
     */
    @PostMapping(value = "/API/v5/display/componentList")
    HsMallComponentListVo findComponentList(
            @RequestParam("dispTemplateNo") String dispTemplateNo,
            @RequestParam("ctgNo") String ctgNo,
            @RequestParam("templateApiCd") String templateApiCd);

    @PostMapping(value = "/API/v5/display/componentData")
    Object findComponentDataList(
            @RequestParam("dispComponentType") String dispComponentType // 필수
            , @RequestParam("dispComponentNo") String dispComponentNo // 필수
            , @RequestParam("componentLayoutCd") String componentLayoutCd //필수

            , @RequestParam("ctgNo") String ctgNo
            , @RequestParam("pcId") String pcId
            , @RequestParam("custNo") String custNo
            , @RequestParam("snbCd") String snbCd
            , @RequestParam("sort") String sort
            , @RequestParam("channelCd") String channelCd
    );

    @GetMapping(value = "API/v5/display/gnbList")
    HsMallGnbVo findTemplateGnbV5List(
            @RequestParam("viewType") String viewType
            , @RequestParam("typeValue") String typeValue);

    @GetMapping(value = "API/v5/display/menuList")
    HsMallMenuVo findMenuList(
            @RequestParam("typeValue") String typeValue
            , @RequestParam("viewType") String viewType
    );

    /*
     * 이벤트
     */
    @PostMapping(value = "/API/o4o/plan/planList")
    HsMallEventListVo findEventList(
            @RequestParam("planType") String planType
            , @RequestParam("mallType") String mallType
            , @RequestParam("channelCd") String channelCd
            , @RequestParam("endSrchDay") Integer endSrchDay
            , @RequestParam("curPage") String curPage
            , @RequestParam("pageRow") String pageRow
            , @RequestParam("showListYn") String showListYn // 이벤트 목록 노출 여부. 목록에는 노출하지 않고, 외부 링크로 접근가능한 이벤트에서 활용. (공무원 전용 등.)
    );

    @GetMapping(value = "/API/o4o/plan/planDetail")
    HsMallEventDetailVo findEventDetail(
            @RequestParam("shpNo") String shpNo
            , @RequestParam("planType") String planType
            , @RequestParam("endSrchDay") Integer endSrchDay
            , @RequestParam("custNo") String custNo
//            , @RequestParam("isAdmin") String isAdmin
    );

    /*
     * 카테고리.
     */
    @GetMapping(value = "/API/o4o/category/ctgLocation")
    MallCategoryLocationVo getHanssemMallCategoryLocation(@RequestParam("ctgNo") int ctgNo);

    @PostMapping(value = "/API/o4o/category/list")
    MallCategoryListVo getHanssemMallCategoryList(@RequestParam("ctgNo") int ctgNo, @RequestParam("level") String level);

    @PostMapping(value = "/API/o4o/category/list")
    MallCategoryListVo getHanssemMallCategoryList();

    /*
     * 홈아이디어
     */
    //    @PostMapping(value = "/API/v5/homeidea/getConstCaseByRemodeling")
    @PostMapping(value = "/API/v5/homeidea/getConstCaseByRemodeling")
//    MallConstructCaseVo getHanssemMallConstCase(@SpringQueryMap ConstructCaseRequest request);
    MallConstructCaseVo getHanssemMallConstructCase(@SpringQueryMap ConstructCaseDto request);

    /*
     * 알림(Notification)
     */
    @GetMapping(value = "/API/o4o/customer/detail")
    HsMallCustomerDetailVo findCustomerDetail(@RequestParam("custNo") String custNo);
}
