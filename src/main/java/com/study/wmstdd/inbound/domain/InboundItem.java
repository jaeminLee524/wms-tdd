package com.study.wmstdd.inbound.domain;

import com.study.wmstdd.product.domain.Product;
import org.springframework.util.Assert;

public class InboundItem {

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
