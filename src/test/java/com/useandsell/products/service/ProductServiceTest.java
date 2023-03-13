package com.useandsell.products.service;

import com.useandsell.products.dto.Product;
import com.useandsell.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService underTest;

    @BeforeEach
    void setUp() {

        underTest = new ProductService(productRepository);
    }


    @Test
    void canGetProducts() throws Exception {
        //when
        underTest.getProducts();
        //then
        verify(productRepository).findAll();

    }

    @Test
    void canAddNewProduct() throws Exception {
        //given
        Product product = new Product(
                "Choco Milkshake",
                "Chocolate Milkshake, a name itself makes juices flowing in the mouth. No milkshake can ever come close to the divine taste of rich and creamy milkshake",
                2,
                "Milkshake",
                35,
                Long.parseLong("3"),
                10,
                new URL("https://themes.muffingroup.com/be/icecream2/wp-content/uploads/2019/09/icecream2-ourproducts-pic5.png")
        );

        //when
        underTest.addNewProduct(product);

        //then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);
        verify(productRepository)
                .save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct).isEqualTo(product);

    }

    @Test
    void deleteProduct() throws Exception {

        Product product = new Product(
                "Choco Milkshake",
                "Chocolate Milkshake, a name itself makes juices flowing in the mouth. No milkshake can ever come close to the divine taste of rich and creamy milkshake",
                2,
                "Milkshake",
                35,
                Long.parseLong("3"),
                10,
                new URL("https://themes.muffingroup.com/be/icecream2/wp-content/uploads/2019/09/icecream2-ourproducts-pic5.png")
        );

        product.setId(33L);
        given(productRepository.existsById(product.getId())).willReturn(true);
        given(productRepository.findSellerIDExists(product.getSellerid())).willReturn(true);
        assertThat(underTest.deleteProduct(product.getId(), product.getSellerid())).isEqualTo("Product Deleted Successfully.");

    }
}