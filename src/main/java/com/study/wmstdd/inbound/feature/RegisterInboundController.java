package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundItem;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.feature.RegisterInboundController.Request.Item;
import com.study.wmstdd.product.domain.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.util.Assert;

public class RegisterInboundController {

    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    public RegisterInboundController(ProductRepository productRepository, InboundRepository inboundRepository) {
        this.productRepository = productRepository;
        this.inboundRepository = inboundRepository;
    }


    public void request(Request request) {
        Inbound inbound = createInbound(request);

        inboundRepository.save(inbound);
    }

    private Inbound createInbound(Request request) {
        return new Inbound(
            request.title,
            request.description,
            request.orderRequestedAt,
            request.estimatedArrivalAt,
            mapToInboundItems(request)
        );
    }

    private List<InboundItem> mapToInboundItems(Request request) {
        return request.inboundItems.stream()
            .map(this::newInboundItem)
            .toList();
    }

    private InboundItem newInboundItem(Item item) {
        return new InboundItem(productRepository.findProduct(item.productNo),
            item.quantity,
            item.unitPrice,
            item.description);
    }

    public record Request(
        String title,
        String description,
        LocalDateTime orderRequestedAt,
        LocalDateTime estimatedArrivalAt,
        List<Request.Item> inboundItems) {

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
