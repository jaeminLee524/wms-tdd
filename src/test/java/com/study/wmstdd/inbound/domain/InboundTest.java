package com.study.wmstdd.inbound.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InboundTest {

    @DisplayName("입고를 승인한다.")
    @Test
    void confirmed() {
        // given
        final Inbound inbound = InboundFixture.anInbound().build();

        // when
        InboundStatus status = inbound.getInboundStatus();

        inbound.confirmed();

        // then
        assertThat(status).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getInboundStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    @DisplayName("[실패케이스] 입고를 승인한다.  - 입고 상태가 요청이 아닌 경우 예외가 발생한다.")
    @Test
    void fail_invalid_status() {
        // given
        final Inbound inbound = InboundFixture.anInbound()
            .inboundStatus(InboundStatus.CONFIRMED)
            .build();

        // then
        assertThatThrownBy(() -> inbound.confirmed())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("입고 요청 상태가 아닙니다.");
    }
}