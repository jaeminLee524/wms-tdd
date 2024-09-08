package com.study.wmstdd.product.inbound.feature;

import com.study.wmstdd.inbound.domain.InboundRepository;
import com.study.wmstdd.inbound.feature.RegisterInboundController;
import com.study.wmstdd.inbound.feature.RegisterInboundController.Request.Item;
import com.study.wmstdd.product.domain.Category;
import com.study.wmstdd.product.domain.Product;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.domain.ProductSize;
import com.study.wmstdd.product.domain.TemperatureZone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RegisterInboundControllerTest {

    private RegisterInboundController registerInboundController;
    private ProductRepository productRepository;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        // stub
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInboundController = new RegisterInboundController(productRepository, inboundRepository);
    }

    @DisplayName("입고를 등록한다.")
    @Test
    void registerInbound() {
        // given
        Product value = new Product("name",
            "code",
            "description",
            "brand",
            "maker",
            "origin",
            Category.ELECTRONICS,
            TemperatureZone.ROOM_TEMPERATURE,
            1000L,
            new ProductSize(100L, 100L, 100L));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(value));

        Long productNo = 1L;
        Long quantity = 1L;
        Long unitPrice = 1500L;
        RegisterInboundController.Request.Item inboundItem = new RegisterInboundController.Request.Item(
            productNo,
            quantity,
            unitPrice,
            "description"
        );
        List<Item> inboundItems = List.of(inboundItem);

        LocalDateTime orderRequestedAt = LocalDateTime.now();
        LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        RegisterInboundController.Request request = new RegisterInboundController.Request(
            "title",
            "description",
            orderRequestedAt,
            estimatedArrivalAt,
            inboundItems
        );
        registerInboundController.request(request);

        // when

        // then
    }

}