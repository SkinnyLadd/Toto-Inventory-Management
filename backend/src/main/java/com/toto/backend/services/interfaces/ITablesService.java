package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Tables;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Tables service operations.
 * Defines business logic and repository operations for tables.
 * This interface replaces both ITableService and IDiningTableService.
 */
public interface ITablesService {
    
    /**
     * Find all tables.
     */
    List<Tables> findAll();
    
    /**
     * Find table by ID.
     */
    Optional<Tables> findById(Long id);
    
    /**
     * Save a table.
     */
    Tables save(Tables table);
    
    /**
     * Delete a table by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find tables by shape (round, rectangular, square).
     */
    List<Tables> findByShape(String shape);
    
    /**
     * Find tables by seating capacity.
     */
    List<Tables> findBySeatingCapacity(int seatingCapacity);
    
    /**
     * Find tables by whether they are extendable.
     */
    List<Tables> findByIsExtendable(boolean isExtendable);
    
    /**
     * Find tables by dimensions (length, width, height).
     */
    List<Tables> findByDimensions(double length, double width, double height);
    
    /**
     * Find tables by whether they have a glass top.
     */
    List<Tables> findByHasGlassTop(boolean hasGlassTop);
    
    /**
     * Find tables by seating capacity greater than or equal to the given value.
     */
    List<Tables> findByMinSeatingCapacity(int minSeatingCapacity);
    
    /**
     * Find tables by shape and seating capacity.
     */
    List<Tables> findByShapeAndSeatingCapacity(String shape, int seatingCapacity);
    
    /**
     * Find dining tables (tables with seating capacity >= 4).
     */
    List<Tables> findDiningTables();
    
    /**
     * Find coffee tables (small tables with height less than 50 cm).
     */
    List<Tables> findCoffeeTables();
    
    /**
     * Find tables by price range and shape.
     */
    List<Tables> findByPriceRangeAndShape(double minPrice, double maxPrice, String shape);
    
    /**
     * Find tables by manufacturer and whether they have a glass top.
     */
    List<Tables> findByManufacturerAndHasGlassTop(String manufacturer, boolean hasGlassTop);
    
    /**
     * Calculate discount for a table based on its properties.
     * Business logic example: Glass top tables get smaller discounts, extendable tables get larger discounts.
     */
    double calculateDiscount(Tables table);
    
    /**
     * Recommend table accessories based on table properties.
     * Business logic example: Different table types need different accessories.
     */
    String recommendAccessories(Tables table);
    
    /**
     * Estimate space requirements for a table.
     * Business logic example: Calculate the total space needed including clearance around the table.
     */
    double calculateSpaceRequirement(Tables table);
}