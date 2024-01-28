package org.antontech.controller;

import org.antontech.model.Product;
import org.antontech.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = {"/product","/products"})
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProducts() {
        logger.info("I am in getProducts controller");
        List<Product> products = productService.getProducts();
        if(products.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(products);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable(name = "id") long id) {
        logger.info("I am in getById controller");
        Product p = productService.getById(id);
        if(p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(p);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody Product product){
        logger.info("Post a new product object {}", product.getName());
        if (productService.save(product))
            return "Product created successfully";
        return null;
    }

    @RequestMapping(value = "/{id}", params = {"name"}, method = RequestMethod.PATCH)
    public ResponseEntity<String> updateProductName(@PathVariable(name = "id") long id, @RequestParam(name = "name") String name){
        logger.info("Pass in variable id: {} and name {}.", id, name);
        if(getById(id).getStatusCode() == HttpStatus.ACCEPTED) {
            productService.updateName(id, name);
            return ResponseEntity.ok().body("Product name is updated successfully");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " is not found, fail to update the name");
    }

    @RequestMapping(value = "/{id}", params = {"description"}, method = RequestMethod.PATCH)
    public ResponseEntity<String> updateProductDescription(@PathVariable(name = "id") long id, @RequestParam(name = "description") String description){
        logger.info("Pass in variable id: {} and description {}.", id, description);
        if(getById(id).getStatusCode() == HttpStatus.ACCEPTED) {
            productService.updateDescription(id, description);
            return ResponseEntity.ok().body("Product description is updated successfully");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " is not found, fail to update the description");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        logger.info("I am in deleteProduct controller");
        if(getById(id).getStatusCode() == HttpStatus.ACCEPTED) {
            productService.delete(id);
            return ResponseEntity.ok().body("Product is deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID" + id + "is not found, fail to delete product.");
        }
    }

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> searchProductByKeyword(@PathVariable(name = "keyword") String keyword) {
        logger.info("Pass in variable description: {}.", keyword);
        List<Product> products =  productService.searchByDescription(keyword);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(products);
    }
}
