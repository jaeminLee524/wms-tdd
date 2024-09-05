package com.study.wmstdd.product.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ProductRepository {

    private final Map<Long, Product> map = new HashMap<>();
    private Long nextId = 1L;

    public void save(Product product) {
        product.assignId(nextId++);
        map.put(product.getId(), product);
    }

    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }
}
