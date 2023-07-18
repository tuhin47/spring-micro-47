package me.tuhin47.productservice.conttoller.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.tuhin47.exporter.ExporterType;
import me.tuhin47.exporter.ExporterUtils;
import me.tuhin47.productservice.conttoller.ProductController;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponse;
import me.tuhin47.productservice.service.ProductService;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ApplicationContext applicationContext;

    @Override
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

        log.info("ProductController | addProduct is called");

        log.info("ProductController | addProduct | productRequest : " + productRequest.toString());

        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : " + productId);

        ProductResponse productResponse
                = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Override
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    ) {

        log.info("ProductController | reduceQuantity is called");

        log.info("ProductController | reduceQuantity | productId : " + productId);
        log.info("ProductController | reduceQuantity | quantity : " + quantity);

        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
    }

    @Override
    @RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Page<ProductResponse>> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request) {
        return new ResponseEntity<>(productService.getAllProductBySearch(searchCriteria,request), HttpStatus.OK);
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

