package com.toto.backend.repositories;

import com.toto.backend.entities.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Tables entities.
 * Provides methods for table-specific operations in a Pakistani furniture store.
 * This repository replaces both TableRepository and DiningTableRepository.
 */
@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {
    
    /**
     * Find tables by shape (round, rectangular, square).
     */
    List<Tables> findByShapeContainingIgnoreCase(String shape);
    
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
    List<Tables> findByLengthAndWidthAndHeight(double length, double width, double height);
    
    /**
     * Find tables by whether they have a glass top.
     */
    List<Tables> findByHasGlassTop(boolean hasGlassTop);
    
    /**
     * Find tables by seating capacity greater than or equal to the given value.
     */
    List<Tables> findBySeatingCapacityGreaterThanEqual(int minSeatingCapacity);
    
    /**
     * Find tables by shape and seating capacity.
     */
    List<Tables> findByShapeAndSeatingCapacity(String shape, int seatingCapacity);
    
    /**
     * Find dining tables (tables with seating capacity >= 4).
     */
    @Query("SELECT t FROM Tables t WHERE t.seatingCapacity >= :minCapacity")
    List<Tables> findDiningTables(@Param("minCapacity") int minCapacity);
    
    /**
     * Find coffee tables (small tables with height less than a certain value).
     */
    List<Tables> findByHeightLessThan(double maxHeight);
    
    /**
     * Find tables by price range and shape.
     */
    List<Tables> findByPriceBetweenAndShapeContainingIgnoreCase(
            double minPrice, double maxPrice, String shape);
    
    /**
     * Find tables by manufacturer and whether they have a glass top.
     */
    List<Tables> findByManufacturerContainingIgnoreCaseAndHasGlassTop(
            String manufacturer, boolean hasGlassTop);
}