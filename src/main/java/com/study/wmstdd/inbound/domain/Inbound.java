package com.study.wmstdd.inbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "inbound")
@Comment("입고")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inbound {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "inbound_no")
    @Comment("입고 번호")
    private Long inboundNo;
    @Column(name = "title", nullable = false)
    @Comment("입고 제목")
    private String title;
    @Column(name = "description", nullable = false)
    @Comment("입고 설명")
    private String description;
    @Column(name = "order_requested_at", nullable = false)
    @Comment("입고 요청일")
    private LocalDateTime orderRequestedAt;
    @Column(name = "estimated_arrival_at", nullable = false)
    @Comment("예상 도착일")
    private LocalDateTime estimatedArrivalAt;
    @OneToMany(mappedBy = "inbound", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InboundItem> inboundItems = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Comment("입고 상태")
    @Getter
    private InboundStatus inboundStatus = InboundStatus.REQUESTED;

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
        for (InboundItem inboundItem : inboundItems) {
            this.inboundItems.add(inboundItem);
            inboundItem.assignInbound(this);
        }
    }

    Inbound(
        Long inboundNo,
        String title,
        String description,
        LocalDateTime orderRequestedAt,
        LocalDateTime estimatedArrivalAt,
        List<InboundItem> inboundItems,
        InboundStatus inboundStatus
    ) {
        this(title, description, orderRequestedAt, estimatedArrivalAt, inboundItems);
        this.inboundNo = inboundNo;
        this.inboundStatus = inboundStatus;
    }

    private static void validateConstructor(String title, String description, LocalDateTime orderRequestedAt, LocalDateTime estimatedArrivalAt,
        List<InboundItem> inboundItems) {
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.hasText(description, "입고 설명은 필수입니다.");
        Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "예상 도착일은 필수입니다.");
        Assert.notEmpty(inboundItems, "입고 상품은 필수입니다.");
    }

    public void confirmed() {
        if (InboundStatus.REQUESTED != inboundStatus) {
            throw new IllegalStateException("입고 요청 상태가 아닙니다.");
        }
        this.inboundStatus = InboundStatus.CONFIRMED;
    }
}
