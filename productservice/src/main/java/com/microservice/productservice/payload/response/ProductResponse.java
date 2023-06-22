package com.microservice.productservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.exporter.ExcelDTO;
import me.tuhin47.exporter.ExcelSheet;
import me.tuhin47.exporter.ExportColumn;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet("Product")
public class ProductResponse implements ExcelDTO {

    private long productId;
    @ExportColumn(value = "Name")
    private String productName;
    private long quantity;
    private long price;
}
