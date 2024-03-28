package me.tuhin47.config;

import lombok.Getter;
import lombok.Setter;
import me.tuhin47.payload.response.TopOrderDto;

import java.util.List;


@Getter
@Setter
public class RequestDataHolder {
    List<TopOrderDto> requestDataMonthly;
    List<TopOrderDto> requestDataYearly;
}
