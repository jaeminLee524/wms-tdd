package com.study.wmstdd.product.inbound.feature;

import static org.mockito.ArgumentMatchers.anyLong;

import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.feature.RegisterInboundController;
import com.study.wmstdd.inbound.feature.RegisterInboundController.Request.Item;
import com.study.wmstdd.product.common.ApiTest;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.fixture.ProductFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

public class RegisterInboundControllerTest extends ApiTest {

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private InboundRepository inboundRepository;

    @DisplayName("입고를 등록한다.")
    @Test
    void registerInbound() {
        // given
        Mockito.when(productRepository.getBy(anyLong()))
            .thenReturn(ProductFixture.aProduct().build());

        Long productNo = 1L;
        Long quantity = 1L;
        Long unitPrice = 1500L;
        RegisterInboundController.Request.Item inboundItem = new RegisterInboundController.Request.Item(
            productNo,
            quantity,
            unitPrice,
            "description"
        );
        List<Item> inboundItems = List.of(inboundItem);

        LocalDateTime orderRequestedAt = LocalDateTime.now();
        LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        RegisterInboundController.Request request = new RegisterInboundController.Request(
            "title",
            "description",
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


        // then
        Assertions.assertThat(inboundRepository.findAll()).hasSize(1);
    }
}