package me.tuhin47.productservice.conttoller.Impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.exporter.ExporterType;
import me.tuhin47.exporter.ExporterUtils;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.productservice.conttoller.ProductController;
import me.tuhin47.productservice.payload.response.ProductTypeCountReport;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import me.tuhin47.productservice.service.ProductService;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ApplicationContext applicationContext;

    @Override
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody @Valid ProductRequest productRequest) {

        String productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @Override
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") String productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") String productId) {
        productService.deleteProductById(productId);
    }

    @Override
    @RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Page<ProductResponseExporter>> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request) {
        return new ResponseEntity<>(productService.getAllProductBySearch(searchCriteria,request), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity<List<ProductTypeCountReport>> getProductTypeCount() {
        return new ResponseEntity<>(productService.getProductTypeReport(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/excel",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<byte[]> exportExcel(List<SearchCriteria> searchCriteria, ExporterType exporterType, HttpServletRequest request) {
        var products = productService.getAllProductBySearch(searchCriteria, request);
        var content = products.getContent();
        var excelExporter = ExporterUtils.getDataExporter(applicationContext, exporterType);
        return new ResponseEntity<>(excelExporter.generate(content), excelExporter.getHTTPHeaders(), HttpStatus.OK);
    }
}

