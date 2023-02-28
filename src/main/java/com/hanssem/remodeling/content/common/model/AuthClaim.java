package com.hanssem.remodeling.content.common.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthClaim implements Serializable {

    // custNo에 대응하는 Online Id
    private Long userId;
    // intMbsCustNo에 대응하는 통합멤버쉽 아이디
    private Long membershipId;

    private String authorization;
}
