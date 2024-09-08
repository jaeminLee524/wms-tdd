package com.study.wmstdd.product.inbound.feature;

import com.study.wmstdd.product.inbound.feature.RegisterInboundControllerTest.RegisterInboundController.Request.Item;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class RegisterInboundControllerTest {

    private RegisterInboundController registerInboundController;

    @BeforeEach
    void setUp() {
        registerInboundController = new RegisterInboundController();
    }

    @DisplayName("입고를 등록한다.")
    @Test
    void registerInbound() {
        // given
        LocalDateTime orderRequestedAt = LocalDateTime.now();
        LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);

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

        RegisterInboundController.Request request = new RegisterInboundController.Request(
            "title",
            "description",
            orderRequestedAt,
            estimatedArrivalAt,
            inboundItems
        );
        registerInboundController.request(request);

        // when

        // then
    }

    public class RegisterInboundController {

        public void request(Request request) {

        }

        public record Request(
            String title,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<Item> inboundItems) {

            public Request {
                Assert.hasText(title, "입고 제목은 필수입니다.");
                Assert.hasText(description, "입고 설명은 필수입니다.");
                Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
                Assert.notNull(estimatedArrivalAt, "예상 도착일은 필수입니다.");
                Assert.notEmpty(inboundItems, "입고 상품은 필수입니다.");
            }

            public record Item(
                Long productNo,
                Long quantity,
                Long unitPrice,
                String description) {

                public Item {
                    Assert.notNull(productNo, "상품번호는 필수입니다.");
                    Assert.notNull(quantity, "수량은 필수입니다.");
                    if (1 > quantity) {
                        throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
                    }
                    if (0 > unitPrice) {
                        throw new IllegalArgumentException("단가는 0원 이상이어야 합니다.");
                    }
                    Assert.notNull(unitPrice, "단가는 필수입니다.");
                    Assert.hasText(description, "상품 설명은 필수입니다.");
                }
            }
        }
    }
}
