package com.study.wmstdd.product.feature;

public class RegisterProduct {

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
