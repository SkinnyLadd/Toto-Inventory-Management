package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Sofa;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Sofa service operations.
 * Defines business logic and repository operations for sofas.
 */
public interface ISofaService {

    /**
     * Find all sofas.
     */
    List<Sofa> findAll();

    /**
     * Find sofa by ID.
     */
    Optional<Sofa> findById(Long id);

    /**
     * Save a sofa.
     */
    Sofa save(Sofa sofa);

    /**
     * Delete a sofa by ID.
     */
    void deleteById(Long id);

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
    List<Sofa> findByUpholsteryType(String upholsteryType);

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
    List<Sofa> findByMinSeatingCapacity(int minSeatingCapacity);

    /**
     * Find sofas by upholstery type and seating capacity.
     */
    List<Sofa> findByUpholsteryTypeAndSeatingCapacity(String upholsteryType, int seatingCapacity);

    /**
     * Find convertible sofas (sofas that can be converted to beds).
     */
    List<Sofa> findConvertibleSofas();

    /**
     * Find recliner sofas (sofas with recliners).
     */
    List<Sofa> findReclinerSofas();

    /**
     * Find luxury sofas (leather sofas with recliners).
     */
    List<Sofa> findLuxurySofas();

    /**
     * Find sofas by price range and upholstery type.
     */
    List<Sofa> findByPriceRangeAndUpholsteryType(
            double minPrice, double maxPrice, String upholsteryType);

    /**
     * Find sofas by manufacturer and whether they have recliners.
     */
    List<Sofa> findByManufacturerAndHasRecliners(String manufacturer, boolean hasRecliners);

    /**
     * Calculate discount for a sofa based on its properties.
     * Business logic example: Luxury sofas get smaller discounts, convertible sofas get larger discounts.
     */
    double calculateDiscount(Sofa sofa);

    /**
     * Recommend cleaning products based on upholstery type.
     * Business logic example: Different upholstery types need different cleaning products.
     */
    String recommendCleaningProducts(Sofa sofa);

    /**
     * Estimate delivery difficulty based on sofa properties.
     * Business logic example: Larger sofas with recliners are more difficult to deliver.
     */
    String estimateDeliveryDifficulty(Sofa sofa);
}