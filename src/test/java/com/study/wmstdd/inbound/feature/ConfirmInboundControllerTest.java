package com.study.wmstdd.inbound.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundItem;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.domain.InboundStatus;
import com.study.wmstdd.product.fixture.ProductFixture;
import java.time.LocalDateTime;
import java.util.List;
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
        Inbound inbound = new Inbound(
            "상품명",
            "상품코드",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            List.of(new InboundItem(
                ProductFixture.aProduct().build(),
                1L,
                1500L,
                "상품 설명"
            ))
        );

        Mockito.when(inboundRepository.getBy(inboundNo)).thenReturn(inbound);

        // when
        confirmInboundController.request(inboundNo);

        // then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    private class ConfirmInboundController {

        private final InboundRepository inboundRepository;

        public ConfirmInboundController(InboundRepository inboundRepository) {
            this.inboundRepository = inboundRepository;
        }

        public void request(Long inboundNo) {
            Inbound inbound = inboundRepository.getBy(inboundNo);

            inbound.confirmed();
        }
    }
}
