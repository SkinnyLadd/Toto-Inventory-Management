package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Bed;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Bed service operations.
 * Defines business logic and repository operations for beds.
 */
public interface IBedService {

    /**
     * Find all beds.
     */
    List<Bed> findAll();

    /**
     * Find bed by ID.
     */
    Optional<Bed> findById(Long id);

    /**
     * Save a bed.
     */
    Bed save(Bed bed);

    /**
     * Delete a bed by ID.
     */
    void deleteById(Long id);

    /**
     * Find beds by size (single, double, queen, king).
     */
    List<Bed> findBySize(String size);

    /**
     * Find beds by whether they have a headboard.
     */
    List<Bed> findByHasHeadboard(boolean hasHeadboard);

    /**
     * Find beds by whether they have a footboard.
     */
    List<Bed> findByHasFootboard(boolean hasFootboard);

    /**
     * Find beds by whether they have storage drawers.
     */
    List<Bed> findByHasStorageDrawers(boolean hasStorageDrawers);

    /**
     * Find beds by mattress type.
     */
    List<Bed> findByMattressType(String mattressType);

    /**
     * Find beds by whether they are adjustable.
     */
    List<Bed> findByIsAdjustable(boolean isAdjustable);

    /**
     * Find beds by size and whether they have storage drawers.
     */
    List<Bed> findBySizeAndHasStorageDrawers(String size, boolean hasStorageDrawers);

    /**
     * Find beds by size and whether they have a headboard.
     */
    List<Bed> findBySizeAndHasHeadboard(String size, boolean hasHeadboard);

    /**
     * Find premium beds (king or queen size with headboard and footboard).
     */
    List<Bed> findPremiumBeds();

    /**
     * Find storage beds (beds with storage drawers).
     */
    List<Bed> findStorageBeds();

    /**
     * Find beds by price range and size.
     */
    List<Bed> findByPriceRangeAndSize(double minPrice, double maxPrice, String size);

    /**
     * Find beds by manufacturer and size.
     */
    List<Bed> findByManufacturerAndSize(String manufacturer, String size);

    /**
     * Calculate discount for a bed based on its properties.
     * Business logic example: Premium beds get smaller discounts, storage beds get larger discounts.
     */
    double calculateDiscount(Bed bed);
}