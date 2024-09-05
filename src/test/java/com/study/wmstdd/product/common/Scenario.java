package com.study.wmstdd.product.common;

import com.study.wmstdd.product.feature.api.RegisterProductApi;

public class Scenario {

    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }
}
