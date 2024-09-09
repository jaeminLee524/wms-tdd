package com.study.wmstdd.inbound.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.wmstdd.common.ApiTest;
import com.study.wmstdd.common.Scenario;
import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.domain.InboundStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ConfirmInboundControllerTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @DisplayName("입고를 승인한다.")
    @Test
    void confirmInbound() {
        // given
        Scenario
            .registerProduct().request()
            .registerInbound().request();

        Long inboundNo = 1L;

        // when
        RestAssured.given().log().all()
            .when()
            .post("/inbounds/{inboundNo}/confirm", inboundNo)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());

        // then
        Inbound inbound = inboundRepository.getBy(inboundNo);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }
}
