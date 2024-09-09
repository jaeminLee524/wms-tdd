package com.study.wmstdd.inbound.domain;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository<Inbound, Long> {

    default Inbound getBy(Long inboundNo) {
        return findById(inboundNo).orElseThrow(
            () -> new EntityNotFoundException("입고가 존재하지 않습니다. %d".formatted(inboundNo))
        );
    }
}
