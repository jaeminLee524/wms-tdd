package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class ConfirmInboundController {

    private final InboundRepository inboundRepository;

    @Transactional
    @PostMapping("/inbounds/{inboundNo}/confirm")
    public void request(@PathVariable("inboundNo") Long inboundNo) {
        Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }
}
