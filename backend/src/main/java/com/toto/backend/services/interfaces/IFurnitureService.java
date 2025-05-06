package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Furniture;
import com.toto.backend.entities.enums.WoodType;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Furniture service operations.
 * Defines business logic and repository operations for furniture.
 */
public interface IFurnitureService {
    
    /**
     * Create a new furniture item.
     */
    void createFurniture(Furniture furniture);
    
    /**
     * Find all furniture items.
     */
    List<Furniture> findAll();
    
    /**
     * Find furniture by ID.
     */
    Optional<Furniture> findById(Long id);
    
    /**
     * Save a furniture item.
     */
    Furniture save(Furniture furniture);
    
    /**
     * Delete a furniture item by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find furniture by name containing the given text (case-insensitive).
     */
    List<Furniture> findByNameContaining(String name);
    
    /**
     * Find furniture by price less than or equal to the given amount.
     */
    List<Furniture> findByPriceLessThanEqual(double price);
    
    /**
     * Find furniture by manufacturer.
     */
    List<Furniture> findByManufacturer(String manufacturer);
    
    /**
     * Find furniture by wood type.
     */
    List<Furniture> findByWoodType(WoodType woodType);
    
    /**
     * Find furniture by supplier ID.
     */
    List<Furniture> findBySupplier(Long supplierId);
    
    /**
     * Find furniture by material.
     */
    List<Furniture> findByMaterial(String material);
    
    /**
     * Find furniture by price range.
     */
    List<Furniture> findByPriceRange(double minPrice, double maxPrice);
    
    /**
     * Find top selling furniture items.
     */
    List<Furniture> findTopSellingFurniture(int limit);
    
    /**
     * Find furniture by supplier city.
     */
    List<Furniture> findBySupplierCity(String city);
    
    /**
     * Find furniture by supplier type.
     */
    List<Furniture> findBySupplierType(String supplierType);
}