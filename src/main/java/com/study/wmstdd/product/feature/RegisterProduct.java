package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.domain.Category;
import com.study.wmstdd.product.domain.Product;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.domain.ProductSize;
import com.study.wmstdd.product.domain.TemperatureZone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegisterProduct {

    private final ProductRepository productRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products")
    public void request(@RequestBody Request request) {
        Product product = request.toDomain();

        productRepository.save(product);
    }

    public record Request(
        String name,
        String code,
        String description,
        String brand,
        String maker,
        String origin,
        Category electronics,
        TemperatureZone temperatureZone,
        Long weightInGrams,
        Long widthInMillimeters,
        Long heightInMillimeters,
        Long lengthInMillimeters
    ) {

        public Product toDomain() {
            return new Product(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                electronics,
                temperatureZone,
                weightInGrams,
                new ProductSize(widthInMillimeters,
                    heightInMillimeters,
                    lengthInMillimeters)
            );
        }

    }
}
