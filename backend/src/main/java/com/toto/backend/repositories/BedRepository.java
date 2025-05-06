package com.toto.backend.repositories;

import com.toto.backend.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Bed entities.
 * Provides methods for bed-specific operations in a Pakistani furniture store.
 */
@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    
    /**
     * Find beds by size (single, double, queen, king).
     */
    List<Bed> findBySizeContainingIgnoreCase(String size);
    
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
    List<Bed> findByMattressTypeContainingIgnoreCase(String mattressType);
    
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
    @Query("SELECT b FROM Bed b WHERE (b.size = 'king' OR b.size = 'queen') AND b.hasHeadboard = true AND b.hasFootboard = true")
    List<Bed> findPremiumBeds();
    
    /**
     * Find storage beds (beds with storage drawers).
     */
    List<Bed> findByHasStorageDrawersTrue();
    
    /**
     * Find beds by price range and size.
     */
    List<Bed> findByPriceBetweenAndSizeContainingIgnoreCase(
            double minPrice, double maxPrice, String size);
    
    /**
     * Find beds by manufacturer and size.
     */
    List<Bed> findByManufacturerContainingIgnoreCaseAndSizeContainingIgnoreCase(
            String manufacturer, String size);
}