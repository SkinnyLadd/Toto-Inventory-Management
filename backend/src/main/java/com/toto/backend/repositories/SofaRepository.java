package com.toto.backend.repositories;

import com.toto.backend.entities.Sofa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Sofa entities.
 * Provides methods for sofa-specific operations in a Pakistani furniture store.
 */
@Repository
public interface SofaRepository extends JpaRepository<Sofa, Long> {
    
    /**
     * Find sofas by seating capacity.
     */
    List<Sofa> findBySeatingCapacity(int seatingCapacity);
    
    /**
     * Find sofas by whether they are convertible to a bed.
     */
    List<Sofa> findByIsConvertible(boolean isConvertible);
    
    /**
     * Find sofas by upholstery type (leather, fabric, etc.).
     */
    List<Sofa> findByUpholsteryTypeContainingIgnoreCase(String upholsteryType);
    
    /**
     * Find sofas by number of cushions.
     */
    List<Sofa> findByNumberOfCushions(int numberOfCushions);
    
    /**
     * Find sofas by whether they have recliners.
     */
    List<Sofa> findByHasRecliners(boolean hasRecliners);
    
    /**
     * Find sofas by seating capacity greater than or equal to the given value.
     */
    List<Sofa> findBySeatingCapacityGreaterThanEqual(int minSeatingCapacity);
    
    /**
     * Find sofas by upholstery type and seating capacity.
     */
    List<Sofa> findByUpholsteryTypeAndSeatingCapacity(String upholsteryType, int seatingCapacity);
    
    /**
     * Find convertible sofas (sofas that can be converted to beds).
     */
    List<Sofa> findByIsConvertibleTrue();
    
    /**
     * Find recliner sofas (sofas with recliners).
     */
    List<Sofa> findByHasReclinersTrue();
    
    /**
     * Find luxury sofas (leather sofas with recliners).
     */
    @Query("SELECT s FROM Sofa s WHERE s.upholsteryType LIKE '%leather%' AND s.hasRecliners = true")
    List<Sofa> findLuxurySofas();
    
    /**
     * Find sofas by price range and upholstery type.
     */
    List<Sofa> findByPriceBetweenAndUpholsteryTypeContainingIgnoreCase(
            double minPrice, double maxPrice, String upholsteryType);
    
    /**
     * Find sofas by manufacturer and whether they have recliners.
     */
    List<Sofa> findByManufacturerContainingIgnoreCaseAndHasRecliners(
            String manufacturer, boolean hasRecliners);
}