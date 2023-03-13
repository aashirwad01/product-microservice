package com.useandsell.products.service;

import com.useandsell.products.controller.ProductController;
import com.useandsell.products.dto.Product;
import com.useandsell.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductService {
    private static final Logger LOGGER =
            Logger.getLogger(ProductController.class.getName());
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() throws Exception {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public void addNewProduct(Product product) throws Exception {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public String deleteProduct(Long productId, Long sellerid) throws Exception {
        try {
            boolean productExists = productRepository.existsById(productId) && productRepository.findSellerIDExists(sellerid);
            if (!productExists) {
                throw new Exception("Product with ID " + productId + " does not exists for seller id " + sellerid);

            }
            productRepository.deleteById(productId);
            return "Product Deleted Successfully.";
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    public void updateQuantity(Long productId, Long sellerid, Integer number) throws Exception {
        try {
            boolean productExists = productRepository.existsById(productId) && productRepository.findSellerIDExists(sellerid);
            if (!productExists) {
                throw new IllegalStateException("Product with ID " + productId + " does not exists for seller id " + sellerid);

            }
            productRepository.updateQuantity(productId, number);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public Optional<Product> getProduct(Long productId) throws Exception {
        try {
            boolean productExists = productRepository.existsById(productId);
            if (!productExists) {
                throw new IllegalStateException("Product with ID does  not exists " + productId);

            }
            return productRepository.findProductById(productId);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public Double getProductPrice(Long productId) throws Exception {
        try {
            boolean productExists = productRepository.existsById(productId);
            if (!productExists) {
                throw new IllegalStateException("Product with ID " + productId);

            }
            return productRepository.findProductPrice(productId);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public Double getProductsPrice(Map<Long, Integer> orderitems) throws Exception {
        try {

            AtomicReference<Double> price = new AtomicReference<>((double) 0);
            orderitems.forEach((key, value) -> {
                try {
                    price.set(getProductPrice(key) * value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });

            return price.get();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public Long getSellerId(Long productId) throws Exception {
        try {
            boolean productExists = productRepository.existsById(productId);
            if (!productExists) {
                throw new IllegalStateException("Product with ID " + productId);

            }
            return productRepository.findProductSellerId(productId);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public void setOrderQuantity(Map<Long, Integer> orderitems) throws Exception {
        try {
            orderitems.forEach((key, value) -> {
                productRepository.updateQuantity(key, value);
            });
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public List<Product> sortAscProductByPrice() throws Exception {
        try {

            return productRepository.sortAscProductByPrice();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }

    }

    public List<Product> sortDescProductByPrice() throws Exception {
        try {

            return productRepository.sortDescProductByPrice();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }

    }

    public List<Product> filterProductsByPrice(Double startPrice, Double endPrice) throws Exception {
        try {
            if (productRepository.filterProductsByPrice(startPrice, endPrice) != null) {
                return productRepository.filterProductsByPrice(startPrice, endPrice);
            } else
                return null;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }

    }
}
