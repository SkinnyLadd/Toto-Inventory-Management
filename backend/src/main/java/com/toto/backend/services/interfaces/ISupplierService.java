package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Supplier;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.SupplierStatus;
import com.toto.backend.entities.enums.SupplierType;
import com.toto.backend.entities.enums.WoodType;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Supplier service operations.
 * Defines business logic and repository operations for suppliers.
 */
public interface ISupplierService {
    
    /**
     * Find all suppliers.
     */
    List<Supplier> findAll();
    
    /**
     * Find supplier by ID.
     */
    Optional<Supplier> findById(Long id);
    
    /**
     * Save a supplier.
     */
    Supplier save(Supplier supplier);
    
    /**
     * Delete a supplier by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find suppliers by company name.
     */
    List<Supplier> findByCompanyName(String companyName);
    
    /**
     * Find suppliers by owner name.
     */
    List<Supplier> findByOwnerName(String ownerName);
    
    /**
     * Find suppliers by contact person.
     */
    List<Supplier> findByContactPerson(String contactPerson);
    
    /**
     * Find supplier by email.
     */
    Supplier findByEmail(String email);
    
    /**
     * Find supplier by primary phone number.
     */
    Supplier findByPrimaryPhone(String primaryPhone);
    
    /**
     * Find suppliers by city.
     */
    List<Supplier> findByCity(String city);
    
    /**
     * Find suppliers by area.
     */
    List<Supplier> findByArea(String area);
    
    /**
     * Find supplier by NTN number (National Tax Number).
     */
    Supplier findByNtnNumber(String ntnNumber);
    
    /**
     * Find supplier by CNIC number.
     */
    Supplier findByCnicNumber(String cnicNumber);
    
    /**
     * Find suppliers by supplier type.
     */
    List<Supplier> findBySupplierType(SupplierType supplierType);
    
    /**
     * Find suppliers by status.
     */
    List<Supplier> findByStatus(SupplierStatus status);
    
    /**
     * Find suppliers by preferred payment method.
     */
    List<Supplier> findByPreferredPaymentMethod(PaymentMethod preferredPaymentMethod);
    
    /**
     * Find suppliers by minimum order amount less than or equal to the given amount.
     */
    List<Supplier> findByMaxMinimumOrderAmount(double amount);
    
    /**
     * Find suppliers by standard lead time in days less than or equal to the given days.
     */
    List<Supplier> findByMaxStandardLeadTime(Integer days);
    
    /**
     * Find suppliers by bulk order discount rate greater than or equal to the given rate.
     */
    List<Supplier> findByMinBulkOrderDiscountRate(double rate);
    
    /**
     * Find suppliers by whether they provide custom work.
     */
    List<Supplier> findByProvidesCustomWork(boolean providesCustomWork);
    
    /**
     * Find suppliers by whether they provide installation.
     */
    List<Supplier> findByProvidesInstallation(boolean providesInstallation);
    
    /**
     * Find suppliers that offer a specific wood type.
     */
    List<Supplier> findByWoodTypeOffered(WoodType woodType);
    
    /**
     * Find suppliers that have a specific specialty.
     */
    List<Supplier> findBySpecialty(String specialty);
    
    /**
     * Find suppliers that service a specific city.
     */
    List<Supplier> findByServiceCity(String city);
    
    /**
     * Find manufacturers (suppliers with type MANUFACTURER).
     */
    List<Supplier> findManufacturers();
    
    /**
     * Find active suppliers (suppliers with status ACTIVE).
     */
    List<Supplier> findActiveSuppliers();
    
    /**
     * Find suppliers by city and supplier type.
     */
    List<Supplier> findByCityAndSupplierType(String city, SupplierType supplierType);
    
    /**
     * Add a wood type to a supplier's offered wood types.
     * Business logic: Adds a wood type to the supplier's woodTypesOffered list.
     */
    Supplier addWoodTypeOffered(Long supplierId, WoodType woodType);
    
    /**
     * Remove a wood type from a supplier's offered wood types.
     * Business logic: Removes a wood type from the supplier's woodTypesOffered list.
     */
    Supplier removeWoodTypeOffered(Long supplierId, WoodType woodType);
    
    /**
     * Add a specialty to a supplier's specialties.
     * Business logic: Adds a specialty to the supplier's specialties list.
     */
    Supplier addSpecialty(Long supplierId, String specialty);
    
    /**
     * Remove a specialty from a supplier's specialties.
     * Business logic: Removes a specialty from the supplier's specialties list.
     */
    Supplier removeSpecialty(Long supplierId, String specialty);
    
    /**
     * Add a service city to a supplier's service cities.
     * Business logic: Adds a service city to the supplier's serviceCities list.
     */
    Supplier addServiceCity(Long supplierId, String city);
    
    /**
     * Remove a service city from a supplier's service cities.
     * Business logic: Removes a service city from the supplier's serviceCities list.
     */
    Supplier removeServiceCity(Long supplierId, String city);
    
    /**
     * Calculate supplier reliability score based on various factors.
     * Business logic: Score is based on lead time, discount rate, services offered, etc.
     */
    int calculateReliabilityScore(Supplier supplier);
    
    /**
     * Mark suppliers as on hold.
     * Business logic: Marks active suppliers as on hold based on criteria.
     */
    int markSuppliersOnHold(List<Long> supplierIds);
}