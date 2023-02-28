package com.hanssem.remodeling.content.api.controller.status;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Kubernetes 에서 RollingUpdate 중에 종료되는 Pod에 트래픽이 유입되는것을 막고자
 * API를 통해 현재 서비스 상태를 바꿔준다.
 * 상태를 바꿔주면 acutator에서 "OUT_OF_SERVICE"상태가 되며 트래픽이 더 이상 들어오지 않게 된다.
 */
@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/status/change")
public class StatusChangeController {

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/readiness/refuse")
    public String markReadinessRefuse() {
        AvailabilityChangeEvent.publish(applicationEventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
        return "REFUSING_TRAFFIC";
    }

    @GetMapping("/readiness/accept")
    public String markReadinessAccept() {
        AvailabilityChangeEvent.publish(applicationEventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
        return "ACCEPTING_TRAFFIC";
    }
}
