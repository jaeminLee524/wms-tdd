package com.study.wmstdd.inbound.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.util.Assert;

public class Inbound {

    private Long id;
    private final String title;
    private final String description;
    private final LocalDateTime orderRequestedAt;
    private final LocalDateTime estimatedArrivalAt;
    private final List<InboundItem> inboundItems;

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
