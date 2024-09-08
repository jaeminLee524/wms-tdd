package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundItem;
import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.feature.RegisterInboundController.Request.Item;
import com.study.wmstdd.product.domain.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegisterInboundController {

    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/inbounds")
    public void request(@RequestBody @Valid Request request) {
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
        return new InboundItem(
            productRepository.getBy(item.productNo),
            item.quantity,
            item.unitPrice,
            item.description);
    }

    public record Request(
        @NotBlank(message = "입고 제목은 필수입니다.")
        String title,
        @NotBlank(message = "입고 설명은 필수입니다.")
        String description,
        @NotNull(message = "입고 요청일은 필수입니다.")
        LocalDateTime orderRequestedAt,
        @NotNull(message = "예상 도착일은 필수입니다.")
        LocalDateTime estimatedArrivalAt,
        @NotEmpty(message = "입고 상품은 필수입니다.")
        List<Request.Item> inboundItems) {

        public record Item(
            @NotNull(message = "상품번호는 필수입니다.")
            Long productNo,
            @NotNull(message = "수량은 필수입니다.")
            @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
            Long quantity,
            @NotNull(message = "단가는 필수입니다.")
            @Min(value = 0, message = "단가는 0원 이상이어야 합니다.")
            Long unitPrice,
            @NotBlank(message = "상품 설명은 필수입니다.")
            String description) {
        }
    }
}
