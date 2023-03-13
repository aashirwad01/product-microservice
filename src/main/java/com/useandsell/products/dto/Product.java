package com.useandsell.products.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"

    )
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private URL imageURL;
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private Integer price;


    @Column(nullable = false)
    private Long sellerid;

    @Column(nullable = false)
    private Integer quantity;

    public Product() {
    }

    public Product(Long id,
                   String name,
                   String description,
                   Integer age,
                   String tag,
                   Integer price,
                   Long sellerid,
                   Integer quantity,
                   URL imageURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.age = age;
        this.tag = tag;
        this.price = price;
        this.sellerid = sellerid;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public Product(String name,
                   String description,
                   Integer age,
                   String tag,
                   Integer price,
                   Long sellerid,
                   Integer quantity,
                   URL imageURL) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.tag = tag;
        this.price = price;
        this.sellerid = sellerid;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    public Long getSellerid() {
        return sellerid;
    }

    public void setSellerid(Long sellerid) {
        this.sellerid = sellerid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", age=" + age +
                ", tag='" + tag + '\'' +
                ", price=" + price +
                ", sellerid=" + sellerid +
                ", quantity=" + quantity +
                '}';
    }
}
