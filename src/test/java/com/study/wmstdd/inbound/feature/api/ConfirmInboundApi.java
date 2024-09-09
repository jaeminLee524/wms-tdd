package com.study.wmstdd.inbound.feature.api;

import com.study.wmstdd.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class ConfirmInboundApi {

    private Long inboundNo = 1L;

    public Scenario request() {
        // when
        RestAssured.given().log().all()
            .when()
            .post("/inbounds/{inboundNo}/confirm", inboundNo)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
