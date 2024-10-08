package com.study.wmstdd.common;

import com.study.wmstdd.inbound.feature.api.ConfirmInboundApi;
import com.study.wmstdd.inbound.feature.api.RegisterInboundApi;
import com.study.wmstdd.product.feature.api.RegisterProductApi;

public class Scenario {

    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public static RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }
}
