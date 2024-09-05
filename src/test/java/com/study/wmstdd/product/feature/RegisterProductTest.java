package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.feature.RegisterProductTest.RegisterProduct.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterProductTest {

    private ProductRepository productRepository;
    private RegisterProduct registerProduct;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        registerProduct = new RegisterProduct(productRepository);
    }

    @DisplayName("상품을 등록한다.")
    @Test
    void registerProduct() {
        // given
        final String name = "name";
        String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        final Long weightInGrams = 100L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;
        RegisterProduct.Request request = new Request(
            name,
            code,
            description,
            brand,
            maker,
            origin,
            Category.ELECTRONICS,
            TemperatureZone.ROOM_TEMPERATURE,
            weightInGrams,
            widthInMillimeters,
            heightInMillimeters,
            lengthInMillimeters
        );

        // when
        registerProduct.request(request);

        // then
        Assertions.assertThat(productRepository.findAll()).hasSize(1);
    }

    public static class RegisterProduct {
        private final ProductRepository productRepository;

        public RegisterProduct(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        public void request(Request request) {
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

    public static class ProductSize {

        private final Long widthInMillimeters;
        private final Long heightInMillimeters;
        private final Long lengthInMillimeters;

        public ProductSize(Long widthInMillimeters,
            Long heightInMillimeters,
            Long lengthInMillimeters) {
            this.widthInMillimeters = widthInMillimeters;
            this.heightInMillimeters = heightInMillimeters;
            this.lengthInMillimeters = lengthInMillimeters;
        }
    }

    public enum Category {
        ELECTRONICS("전자제품");

        private final String description;

        Category(String description) {
            this.description = description;
        }
    }

    public enum TemperatureZone {
        ROOM_TEMPERATURE("상온");

        private final String description;

        TemperatureZone(String description) {
            this.description = description;
        }
    }

    public static class Product {

        private Long id;
        private final String name;
        private final String code;
        private final String description;
        private final String brand;
        private final String maker;
        private final String origin;
        private final Category electronics;
        private final TemperatureZone temperatureZone;
        private final Long weightInGrams;
        private final ProductSize productSize;

        public Product(
            String name,
            String code,
            String description,
            String brand,
            String maker,
            String origin,
            Category electronics,
            TemperatureZone temperatureZone,
            Long weightInGrams,
            ProductSize productSize) {

            this.name = name;
            this.code = code;
            this.description = description;
            this.brand = brand;
            this.maker = maker;
            this.origin = origin;
            this.electronics = electronics;
            this.temperatureZone = temperatureZone;
            this.weightInGrams = weightInGrams;
            this.productSize = productSize;
        }

        public void assignId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return this.id;
        }
    }

    public static class ProductRepository {
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
}