package com.study.wmstdd.inbound.feature.api;

import com.study.wmstdd.common.Scenario;
import com.study.wmstdd.inbound.feature.RegisterInboundController;
import com.study.wmstdd.inbound.feature.RegisterInboundController.Request.Item;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

public class RegisterInboundApi {

    private String title = "title";
    private String description = "description";
    private LocalDateTime orderRequestedAt = LocalDateTime.now();
    private LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
    private List<Item> inboundItems = List.of(new Item(
        1L,
        1L,
        1500L,
        "description"
    ));

    public RegisterInboundApi title(String title) {
        this.title = title;
        return this;
    }

    public RegisterInboundApi description(String description) {
        this.description = description;
        return this;
    }

    public RegisterInboundApi orderRequestedAt(LocalDateTime orderRequestedAt) {
        this.orderRequestedAt = orderRequestedAt;
        return this;
    }

    public RegisterInboundApi estimatedArrivalAt(LocalDateTime estimatedArrivalAt) {
        this.estimatedArrivalAt = estimatedArrivalAt;
        return this;
    }

    public RegisterInboundApi inboundItems(Item... inboundItems) {
        this.inboundItems = List.of(inboundItems);
        return this;
    }

    public Scenario request() {
        RegisterInboundController.Request request = new RegisterInboundController.Request(
            title,
            description,
            orderRequestedAt,
            estimatedArrivalAt,
            inboundItems
        );

        // when
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/inbounds").then().log().all()
            .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
