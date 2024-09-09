package com.study.wmstdd.inbound.feature;

import com.study.wmstdd.inbound.domain.Inbound;
import com.study.wmstdd.inbound.domain.InboundRepository;

class ConfirmInboundController {

    private final InboundRepository inboundRepository;

    public ConfirmInboundController(InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(Long inboundNo) {
        Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }
}
