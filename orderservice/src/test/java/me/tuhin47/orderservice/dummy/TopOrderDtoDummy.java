package me.tuhin47.orderservice.dummy;

import me.tuhin47.payload.response.TopOrderDto;

public  class TopOrderDtoDummy {
        public static TopOrderDto getFirstTopOrderDto() {
            return new TopOrderDto("buyer1", 100, 1000.0);
        }

        public static TopOrderDto getSecondTopOrderDto() {
            return new TopOrderDto("buyer2", 200, 2000.0);
        }
    }