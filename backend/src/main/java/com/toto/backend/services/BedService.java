package com.toto.backend.services;

import com.toto.backend.entities.Bed;
import com.toto.backend.repositories.BedRepository;
import com.toto.backend.services.interfaces.IBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Bed entities.
 * Provides business logic and encapsulates repository operations for beds.
 */
@Service
@Transactional
public class BedService implements IBedService {

    public BedRepository bedRepository;

    @Autowired
    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    /**
     * Find all beds.
     */
    public List<Bed> findAll() {
        return bedRepository.findAll();
    }

    /**
     * Find bed by ID.
     */
    public Optional<Bed> findById(Long id) {
        return bedRepository.findById(id);
    }

    /**
     * Save a bed.
     */
    public Bed save(Bed bed) {
        return bedRepository.save(bed);
    }

    /**
     * Delete a bed by ID.
     */
    public void deleteById(Long id) {
        bedRepository.deleteById(id);
    }

    /**
     * Find beds by size (single, double, queen, king).
     */
    public List<Bed> findBySize(String size) {
        return bedRepository.findBySizeContainingIgnoreCase(size);
    }

    /**
     * Find beds by whether they have a headboard.
     */
    public List<Bed> findByHasHeadboard(boolean hasHeadboard) {
        return bedRepository.findByHasHeadboard(hasHeadboard);
    }

    /**
     * Find beds by whether they have a footboard.
     */
    public List<Bed> findByHasFootboard(boolean hasFootboard) {
        return bedRepository.findByHasFootboard(hasFootboard);
    }

    /**
     * Find beds by whether they have storage drawers.
     */
    public List<Bed> findByHasStorageDrawers(boolean hasStorageDrawers) {
        return bedRepository.findByHasStorageDrawers(hasStorageDrawers);
    }

    /**
     * Find beds by mattress type.
     */
    public List<Bed> findByMattressType(String mattressType) {
        return bedRepository.findByMattressTypeContainingIgnoreCase(mattressType);
    }

    /**
     * Find beds by whether they are adjustable.
     */
    public List<Bed> findByIsAdjustable(boolean isAdjustable) {
        return bedRepository.findByIsAdjustable(isAdjustable);
    }

    /**
     * Find beds by size and whether they have storage drawers.
     */
    public List<Bed> findBySizeAndHasStorageDrawers(String size, boolean hasStorageDrawers) {
        return bedRepository.findBySizeAndHasStorageDrawers(size, hasStorageDrawers);
    }

    /**
     * Find beds by size and whether they have a headboard.
     */
    public List<Bed> findBySizeAndHasHeadboard(String size, boolean hasHeadboard) {
        return bedRepository.findBySizeAndHasHeadboard(size, hasHeadboard);
    }

    /**
     * Find premium beds (king or queen size with headboard and footboard).
     */
    public List<Bed> findPremiumBeds() {
        return bedRepository.findPremiumBeds();
    }

    /**
     * Find storage beds (beds with storage drawers).
     */
    public List<Bed> findStorageBeds() {
        return bedRepository.findByHasStorageDrawersTrue();
    }

    /**
     * Find beds by price range and size.
     */
    public List<Bed> findByPriceRangeAndSize(double minPrice, double maxPrice, String size) {
        return bedRepository.findByPriceBetweenAndSizeContainingIgnoreCase(minPrice, maxPrice, size);
    }

    /**
     * Find beds by manufacturer and size.
     */
    public List<Bed> findByManufacturerAndSize(String manufacturer, String size) {
        return bedRepository.findByManufacturerContainingIgnoreCaseAndSizeContainingIgnoreCase(manufacturer, size);
    }

    /**
     * Calculate discount for a bed based on its properties.
     * Business logic example: Premium beds get smaller discounts, storage beds get larger discounts.
     */
    public double calculateDiscount(Bed bed) {
        double baseDiscount = 0.05; // 5% base discount

        // Premium beds (king/queen with headboard and footboard) get smaller discounts
        if ((bed.getSize().equalsIgnoreCase("king") || bed.getSize().equalsIgnoreCase("queen")) 
                && bed.isHasHeadboard() && bed.isHasFootboard()) {
            baseDiscount = 0.03; // 3% discount for premium beds
        }

        // Storage beds get additional discount
        if (bed.isHasStorageDrawers()) {
            baseDiscount += 0.02; // Additional 2% for storage beds
        }

        // Adjustable beds get additional discount
        if (bed.isAdjustable()) {
            baseDiscount += 0.02; // Additional 2% for adjustable beds
        }

        return baseDiscount;
    }
}
