package com.toto.backend.repositories;

import com.toto.backend.entities.Furniture;
import com.toto.backend.entities.enums.WoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Furniture entities.
 * Provides methods for common furniture operations in a Pakistani furniture store.
 */
@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    
    /**
     * Find furniture by name containing the given text (case-insensitive).
     */
    List<Furniture> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find furniture by price less than or equal to the given amount.
     */
    List<Furniture> findByPriceLessThanEqual(double price);
    
    /**
     * Find furniture by manufacturer.
     */
    List<Furniture> findByManufacturerContainingIgnoreCase(String manufacturer);
    
    /**
     * Find furniture by wood type.
     */
    List<Furniture> findByWoodType(WoodType woodType);
    
    /**
     * Find furniture by supplier ID.
     */
    List<Furniture> findBySupplier_Id(Long supplierId);
    
    /**
     * Find furniture by material containing the given text (case-insensitive).
     */
    List<Furniture> findByMaterialContainingIgnoreCase(String material);
    
    /**
     * Find furniture by price range.
     */
    List<Furniture> findByPriceBetween(double minPrice, double maxPrice);
    
    /**
     * Find top selling furniture items.
     * This is a custom query that joins with the order_items table to count how many times
     * each furniture item appears in orders.
     */
    @Query(value = "SELECT f.* FROM furniture f " +
            "JOIN order_items oi ON f.id = oi.furniture_id " +
            "GROUP BY f.id " +
            "ORDER BY COUNT(oi.order_id) DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Furniture> findTopSellingFurniture(@Param("limit") int limit);
    
    /**
     * Find furniture by supplier city.
     */
    @Query("SELECT f FROM Furniture f WHERE f.supplier.city = :city")
    List<Furniture> findBySupplierCity(@Param("city") String city);
    
    /**
     * Find furniture by supplier type.
     */
    @Query("SELECT f FROM Furniture f WHERE f.supplier.supplierType = :supplierType")
    List<Furniture> findBySupplierType(@Param("supplierType") String supplierType);
}