package com.study.wmstdd.inbound.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundFixture;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.domain.InboundStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConfirmInboundControllerTest {

    private ConfirmInboundController confirmInboundController;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        inboundRepository = Mockito.mock(InboundRepository.class);
        confirmInboundController = new ConfirmInboundController(inboundRepository);
    }

    @DisplayName("입고를 승인한다.")
    @Test
    void confirmInbound() {
        // given
        Long inboundNo = 1L;
        Inbound inbound = InboundFixture.anInbound().build();

        Mockito.when(inboundRepository.getBy(inboundNo)).thenReturn(inbound);

        // when
        confirmInboundController.request(inboundNo);

        // then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }
}
