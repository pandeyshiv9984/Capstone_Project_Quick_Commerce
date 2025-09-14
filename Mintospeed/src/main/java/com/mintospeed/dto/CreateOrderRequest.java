package com.mintospeed.dto;


import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private Long userId;
    private Long addressId;
    private Long couponId;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private List<OrderItemDto> orderItems;

    @Getter
    @Setter
    public static class OrderItemDto {
        private Long itemId;
        private Integer quantity;
        private BigDecimal price;
    }
}
