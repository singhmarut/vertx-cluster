package io.vertx.examples.spring.entity;

/**
 * Trivial JPA entity for vertx-spring demo
 */

public class Product {


      public Product(Integer productId, String description){
            this.productId = productId;
            this.description = description;
      }

      private Integer productId;

      private String description;

      public Integer getProductId() {
        return productId;
      }

      public String getDescription() {
        return description;
      }
}
