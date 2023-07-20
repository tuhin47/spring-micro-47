package me.tuhin47.productservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.exporter.ExcelExporterDTO;
import me.tuhin47.exporter.ExcelSheet;
import me.tuhin47.exporter.ExportColumn;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet("Product")
public class ProductResponse implements ExcelExporterDTO {

    private String productId;
    @ExportColumn(value = "Name")
    private String productName;
    private long quantity;
    private long price;
}
