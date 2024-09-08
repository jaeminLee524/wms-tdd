package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.common.ApiTest;
import com.study.wmstdd.common.Scenario;
import com.study.wmstdd.inbound.domain.InboundRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegisterInboundControllerTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @DisplayName("입고를 등록한다.")
    @Test
    void registerInbound() {
        // given
        Scenario.registerProduct().request()
            .registerInbound().request();

        // then
        Assertions.assertThat(inboundRepository.findAll()).hasSize(1);
    }
}