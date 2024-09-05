package com.study.wmstdd.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("상품")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    @Comment("상품 번호")
    private Long productNo;
    @Column(name = "name", nullable = false)
    @Comment("상품명")
    private String name;
    @Column(name = "code", nullable = false, unique = true)
    @Comment("상품코드")
    private String code;
    @Column(name = "description", nullable = false)
    @Comment("상품설명")
    private String description;
    @Column(name = "brand", nullable = false)
    @Comment("브랜드")
    private String brand;
    @Column(name = "maker", nullable = false)
    @Comment("제조사")
    private String maker;
    @Column(name = "origin", nullable = false)
    @Comment("원산지")
    private String origin;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    @Comment("카테고리")
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_zone", nullable = false)
    @Comment("온도대")
    private TemperatureZone temperatureZone;
    @Column(name = "weight_in_grams", nullable = false)
    @Comment("무게(그램)")
    private Long weightInGrams;
    @Embedded
    private ProductSize productSize;

    public Product(
        String name,
        String code,
        String description,
        String brand,
        String maker,
        String origin,
        Category category,
        TemperatureZone temperatureZone,
        Long weightInGrams,
        ProductSize productSize) {

        Assert.notNull(name, "상품명은 필수입니다.");
        Assert.notNull(code, "상품코드는 필수입니다.");
        Assert.notNull(description, "상품설명은 필수입니다.");
        Assert.notNull(brand, "브랜드는 필수입니다.");
        Assert.notNull(maker, "제조사는 필수입니다.");
        Assert.notNull(origin, "원산지는 필수입니다.");
        Assert.notNull(category, "카테고리는 필수입니다.");
        Assert.notNull(temperatureZone, "온도대는 필수입니다.");
        Assert.notNull(weightInGrams, "무게(그램)은 필수입니다.");
        Assert.notNull(productSize, "상품 크기는 필수입니다.");

        this.name = name;
        this.code = code;
        this.description = description;
        this.brand = brand;
        this.maker = maker;
        this.origin = origin;
        this.category = category;
        this.temperatureZone = temperatureZone;
        this.weightInGrams = weightInGrams;
        this.productSize = productSize;
    }
}
