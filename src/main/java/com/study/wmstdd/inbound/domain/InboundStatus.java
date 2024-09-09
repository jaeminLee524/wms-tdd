package com.study.wmstdd.inbound.domain;

public enum InboundStatus {
    REQUESTED("요청됨"),
    CONFIRMED("승인됨"),
    CANCELED("취소됨");

    private final String description;

    InboundStatus(String description) {
        this.description = description;
    }
}
