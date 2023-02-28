package com.hanssem.remodeling.content.api.controller.sample.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SampleCacheResponse {

    String filedString;
    int filedInt;
    @Builder.Default
    List<String> filedStringList = new ArrayList<>();
    SampleRedisObjectParam filedObject;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SampleRedisObjectParam {

        String strMember;
        int intMember;
        List<String> listMember;
    }
}
