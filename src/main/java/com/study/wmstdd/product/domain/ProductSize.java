package com.study.wmstdd.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSize {

    @Column(name = "width_in_millimeters", nullable = false)
    @Comment("상품 너비(mm)")
    private Long widthInMillimeters;
    @Column(name = "height_in_millimeters", nullable = false)
    @Comment("상품 높이(mm)")
    private Long heightInMillimeters;
    @Column(name = "length_in_millimeters", nullable = false)
    @Comment("상품 길이(mm)")
    private Long lengthInMillimeters;

    public ProductSize(Long widthInMillimeters,
        Long heightInMillimeters,
        Long lengthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        this.heightInMillimeters = heightInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
    }
}
