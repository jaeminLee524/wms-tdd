package com.study.wmstdd.product.domain;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findProduct(Long productNo) {
        return findById(productNo)
            .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다. %d".formatted(productNo)));
    }
}
