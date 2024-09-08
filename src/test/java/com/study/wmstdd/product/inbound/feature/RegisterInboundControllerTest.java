package com.study.wmstdd.product.inbound.feature;

import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.product.common.ApiTest;
import com.study.wmstdd.product.common.Scenario;
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