package com.study.wmstdd.product.inbound.feature;

import com.study.wmstdd.product.domain.Category;
import com.study.wmstdd.product.domain.Product;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.domain.ProductSize;
import com.study.wmstdd.product.domain.TemperatureZone;
import com.study.wmstdd.product.inbound.feature.RegisterInboundControllerTest.RegisterInboundController.Request.Item;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

public class RegisterInboundControllerTest {

    private RegisterInboundController registerInboundController;
    private ProductRepository productRepository;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        // stub
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInboundController = new RegisterInboundController(productRepository, inboundRepository);
    }

    @DisplayName("입고를 등록한다.")
    @Test
    void registerInbound() {
        // given
        Product value = new Product("name",
            "code",
            "description",
            "brand",
            "maker",
            "origin",
            Category.ELECTRONICS,
            TemperatureZone.ROOM_TEMPERATURE,
            1000L,
            new ProductSize(100L, 100L, 100L));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(value));

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
        registerInboundController.request(request);

        // when

        // then
    }

    public class RegisterInboundController {

        private final ProductRepository productRepository;
        private final InboundRepository inboundRepository;

        public RegisterInboundController(ProductRepository productRepository, InboundRepository inboundRepository) {
            this.productRepository = productRepository;
            this.inboundRepository = inboundRepository;
        }


        public void request(Request request) {
            List<InboundItem> inboundItems = request.inboundItems.stream()
                .map(item -> new InboundItem(productRepository.findById(item.productNo).orElseThrow(),
                    item.quantity,
                    item.unitPrice,
                    item.description))
                .toList();

            Inbound inbound = new Inbound(
                request.title,
                request.description,
                request.orderRequestedAt,
                request.estimatedArrivalAt,
                inboundItems
            );

            inboundRepository.save(inbound);
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

    public class Inbound {

        private final String title;
        private final String description;
        private final LocalDateTime orderRequestedAt;
        private final LocalDateTime estimatedArrivalAt;
        private final List<InboundItem> inboundItems;
        private Long id;

        public Inbound(
            String title,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<InboundItem> inboundItems) {
            validateConstructor(
                title,
                description,
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems);

            this.title = title;
            this.description = description;
            this.orderRequestedAt = orderRequestedAt;
            this.estimatedArrivalAt = estimatedArrivalAt;
            this.inboundItems = inboundItems;
        }

        private static void validateConstructor(String title, String description, LocalDateTime orderRequestedAt, LocalDateTime estimatedArrivalAt,
            List<InboundItem> inboundItems) {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.hasText(description, "입고 설명은 필수입니다.");
            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "예상 도착일은 필수입니다.");
            Assert.notEmpty(inboundItems, "입고 상품은 필수입니다.");
        }

        public void assignId(Long id) {
            this.id = id;
        }
    }

    private class InboundItem {

        private final Product product;
        private final Long quantity;
        private final Long unitPrice;
        private final String description;

        public InboundItem(
            Product product,
            Long quantity,
            Long unitPrice,
            String description) {
            validateConstructor(product, quantity, unitPrice, description);
            this.product = product;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.description = description;
        }

        private static void validateConstructor(
            Product product,
            Long quantity,
            Long unitPrice,
            String description) {
            Assert.notNull(product, "상품은 필수입니다.");
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

    public class InboundRepository {

        private final Map<Long, Inbound> inbounds = new HashMap<>();
        private Long sequence = 1L;

        public void save(Inbound inbound) {
            inbound.assignId(sequence);
            inbounds.put(sequence++, inbound);
        }
    }
}