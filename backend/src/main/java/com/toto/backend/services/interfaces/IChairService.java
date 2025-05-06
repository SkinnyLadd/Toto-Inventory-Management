package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Chair;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Chair service operations.
 * Defines business logic and repository operations for chairs.
 */
public interface IChairService {

    /**
     * Find all chairs.
     */
    List<Chair> findAll();
    
    /**
     * Find chair by ID.
     */
    Optional<Chair> findById(Long id);
    
    /**
     * Save a chair.
     */
    Chair save(Chair chair);
    
    /**
     * Delete a chair by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find chairs by seating capacity.
     */
    List<Chair> findBySeatingCapacity(int seatingCapacity);
    
    /**
     * Find chairs by whether they have armrests.
     */
    List<Chair> findByHasArmrests(boolean hasArmrests);
    
    /**
     * Find chairs by chair style (dining, office, rocking, etc.).
     */
    List<Chair> findByChairStyle(String chairStyle);
    
    /**
     * Find chairs by whether they are adjustable.
     */
    List<Chair> findByIsAdjustable(boolean isAdjustable);
    
    /**
     * Find chairs by whether they have wheels.
     */
    List<Chair> findByHasWheels(boolean hasWheels);
    
    /**
     * Find chairs by multiple criteria.
     */
    List<Chair> findByStyleAndArmrestsAndAdjustable(
            String chairStyle, boolean hasArmrests, boolean isAdjustable);
    
    /**
     * Find office chairs (chairs with wheels and adjustable height).
     */
    List<Chair> findOfficeChairs();
    
    /**
     * Find dining chairs (chairs without wheels and with a specific style).
     */
    List<Chair> findDiningChairs();
    
    /**
     * Find chairs by price range and seating capacity.
     */
    List<Chair> findByPriceRangeAndSeatingCapacity(
            double minPrice, double maxPrice, int seatingCapacity);
    
    /**
     * Find chairs by manufacturer and chair style.
     */
    List<Chair> findByManufacturerAndStyle(String manufacturer, String chairStyle);
    
    /**
     * Calculate discount for a chair based on its properties.
     * Business logic example: Office chairs get larger discounts, dining chairs get smaller discounts.
     */
    double calculateDiscount(Chair chair);
    
    /**
     * Recommend chair maintenance schedule based on chair type.
     * Business logic example: Different chair types need different maintenance schedules.
     */
    String recommendMaintenanceSchedule(Chair chair);
}