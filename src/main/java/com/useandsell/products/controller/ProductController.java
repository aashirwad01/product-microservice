package com.useandsell.products.controller;

import com.useandsell.products.dto.Product;
import com.useandsell.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {
    private static final Logger LOGGER =
            Logger.getLogger(ProductController.class.getName());
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @RequestMapping(path = "api/productsAll")
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
    ) throws Exception {
        try {
            return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Seller id wrong.", e);
        }
    }


    @RequestMapping(path = "api/{sellerid}/addProduct")
    @PostMapping
    public ResponseEntity<String> registerNewProduct(
            @PathVariable("sellerid") Long sellerid,
            @RequestBody Map<String, String> map
    ) {
        try {
            System.out.println(map);
            URL url = new URL(map.get("imageurl").toString());
            System.out.println(url);
            Product product = new Product(map.get("name"), map.get("description"), (Integer.parseInt(map.get("age"))), map.get("tag"), (Integer.parseInt(map.get("price"))), Long.parseLong(map.get("sellerid")), (Integer.parseInt(map.get("quantity"))), url);
            System.out.println(product);
            productService.addNewProduct(product);
            return new ResponseEntity<>("Product Added Successfully ", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.OK);
        }

    }


    @DeleteMapping(path = "api/{sellerid}/{productId}/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productid,
                                                @PathVariable("sellerid") Long sellerid) {
        try {
            String response = productService.deleteProduct(productid, sellerid);
            return new ResponseEntity<>("Product Deleted Successfully ", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }


    @PutMapping(path = "api/{sellerid}/{productId}/updatequantity")
    public ResponseEntity<String> updateQuantity(
            @PathVariable("productId") Long productid,
            @PathVariable("sellerid") Long sellerid,
            @RequestParam("quantity") Integer number

    ) {
        try {
            productService.updateQuantity(productid, sellerid, number);
            return new ResponseEntity<>("Product Updated Successfully ", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }

    @RequestMapping(path = "api/{productid}")
    @GetMapping
    public ResponseEntity<Optional<Product>> getProduct(
            @PathVariable("productid") Long productid
    ) throws Exception {
        try {
            return new ResponseEntity<>(productService.getProduct(productid), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/sellerid/{productid}")
    @GetMapping
    public Long getSellerId(
            @PathVariable("productid") Long productid
    ) throws Exception {
        try {
            return productService.getSellerId(productid);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/{productid}/price")
    @GetMapping
    public Double getProductPrice(
            @PathVariable("productid") Long productid
    ) throws Exception {
        try {
            return productService.getProductPrice(productid);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/order/price")
    @PostMapping
    public Double getOrderPrice(
            @RequestBody Map<Long, Integer> orderitems
    ) throws Exception {
        try {

            return productService.getProductsPrice(orderitems);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/order/quantity")
    @PutMapping
    public void setOrderQuantity(
            @RequestBody Map<Long, Integer> orderitems
    ) throws Exception {
        try {
            productService.setOrderQuantity(orderitems);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }


    @RequestMapping(path = "api/sortascbyprice")
    @GetMapping
    public ResponseEntity<List<Product>> sortAscProductByPrice(

    ) throws Exception {
        try {
            return new ResponseEntity<>(productService.sortAscProductByPrice(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/sortdescbyprice")
    @GetMapping
    public ResponseEntity<List<Product>> sortDescProductByPrice(

    ) throws Exception {
        try {
            return new ResponseEntity<>(productService.sortDescProductByPrice(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/filterbyprice")
    @GetMapping
    public ResponseEntity<List<Product>> filterProductsByPrice(

            @RequestParam("startPrice") Double startPrice,
            @RequestParam("endPrice") Double endPrice
    ) throws Exception {
        try {
            if (productService.filterProductsByPrice(startPrice, endPrice) == null) {
                throw new Exception("No such product found for this price range.");

            } else {
                return new ResponseEntity<>(productService.filterProductsByPrice(startPrice, endPrice), HttpStatus.OK);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }


}
