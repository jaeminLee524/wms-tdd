package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfirmInboundControllerTest {

    private ConfirmInboundController confirmInboundController;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        confirmInboundController = new ConfirmInboundController();
    }

    @DisplayName("입고를 승인한다.")
    @Test
    void confirmInbound() {
        // given
        Long inboundNo = 1L;

        // when
        confirmInboundController.request(inboundNo);

        // then
//        inboundRepository.findById(inboundNo).get().getStatus().isEqualTo(InboundStatus.CONFIRMED);

    }

    private class ConfirmInboundController {

        public void request(Long inboundNo) {
            Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow(
                () -> new EntityNotFoundException("입고가 존재하지 않습니다. %d".formatted(inboundNo))
            );

            inbound.confirmed();
        }
    }
}
