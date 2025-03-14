package com.mystore.app.repositories;

import com.mystore.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Search products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filter products by category
    List<Product> findByCategoryIgnoreCase(String category);

    // Filter products by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Filter products by stock quantity range
    List<Product> findByStockQuantityBetween(Integer minStock, Integer maxStock);

    // Custom JPQL query for filtering products by name and category
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR LOWER(p.category) = LOWER(:category)) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minStock IS NULL OR p.stockQuantity >= :minStock) AND " +
            "(:maxStock IS NULL OR p.stockQuantity <= :maxStock)")
    List<Product> filterProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minStock") Integer minStock,
            @Param("maxStock") Integer maxStock
    );

}
