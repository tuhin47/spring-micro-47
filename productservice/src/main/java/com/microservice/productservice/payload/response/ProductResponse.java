package com.microservice.productservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.utils.ExcelDTO;
import me.tuhin47.utils.ExcelSheet;
import me.tuhin47.utils.ExportColumn;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet("Product")
public class ProductResponse implements ExcelDTO {

    @ExportColumn(value = "Name", sortOrder = 2)
    private String productName;
    private long productId;
    private long quantity;
    private long price;
}
