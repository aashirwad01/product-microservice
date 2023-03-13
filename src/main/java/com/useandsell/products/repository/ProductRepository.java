package com.useandsell.products.repository;

import com.useandsell.products.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT count(*)>0 FROM Product p WHERE p.sellerid = ?1")
    boolean findSellerIDExists(Long sellerid);

    @Transactional
    @Modifying
    @Query("UPDATE Product p set p.quantity = p.quantity-:number where p.id=:id")
    void updateQuantity(@Param("id") Long id,
                        @Param("number") Integer number
    );

    @Query("SELECT c FROM Product c WHERE c.id=?1")
    Optional<Product> findProductById(Long productid);

    @Query("SELECT p.price FROM Product p WHERE p.id=?1")
    Double findProductPrice(Long productid);

    @Query("SELECT p.sellerid FROM Product p WHERE p.id=?1")
    Long findProductSellerId(Long productid);

    @Query("SELECT p FROM Product p ORDER BY p.price ")
    List<Product> sortAscProductByPrice();

    @Query("SELECT p FROM Product p ORDER BY p.price desc ")
    List<Product> sortDescProductByPrice();

    @Query("SELECT p FROM Product p WHERE p.price>:startPrice and p.price<:endPrice ")
    List<Product> filterProductsByPrice(Double startPrice, Double endPrice);

}
