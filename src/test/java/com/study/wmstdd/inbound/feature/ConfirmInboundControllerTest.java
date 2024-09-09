package com.study.wmstdd.inbound.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.wmstdd.common.ApiTest;
import com.study.wmstdd.common.Scenario;
import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.domain.InboundStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfirmInboundControllerTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @DisplayName("입고를 승인한다.")
    @Test
    void confirmInbound() {
        // given
        Scenario
            .registerProduct().request()
            .registerInbound().request()
            .confirmInbound().request();

        // then
        Inbound inbound = inboundRepository.getBy(1L);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }
}
