package com.study.wmstdd.inbound.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundItem;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.domain.InboundStatus;
import com.study.wmstdd.product.fixture.ProductFixture;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

        Mockito.when(inboundRepository.findById(inboundNo))
            .thenReturn(Optional.of(inbound));

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
            Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow(
                () -> new EntityNotFoundException("입고가 존재하지 않습니다. %d".formatted(inboundNo))
            );

            inbound.confirmed();
        }
    }
}
