package com.toto.backend.repositories;

import com.toto.backend.entities.Supplier;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.SupplierStatus;
import com.toto.backend.entities.enums.SupplierType;
import com.toto.backend.entities.enums.WoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Supplier entities.
 * Provides methods for supplier-specific operations in a Pakistani furniture store.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    /**
     * Find suppliers by company name containing the given text (case-insensitive).
     */
    List<Supplier> findByCompanyNameContainingIgnoreCase(String companyName);
    
    /**
     * Find suppliers by owner name containing the given text (case-insensitive).
     */
    List<Supplier> findByOwnerNameContainingIgnoreCase(String ownerName);
    
    /**
     * Find suppliers by contact person containing the given text (case-insensitive).
     */
    List<Supplier> findByContactPersonContainingIgnoreCase(String contactPerson);
    
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
    List<Supplier> findByCityIgnoreCase(String city);
    
    /**
     * Find suppliers by area.
     */
    List<Supplier> findByAreaContainingIgnoreCase(String area);
    
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
    List<Supplier> findByMinimumOrderAmountLessThanEqual(double amount);
    
    /**
     * Find suppliers by standard lead time in days less than or equal to the given days.
     */
    List<Supplier> findByStandardLeadTimeInDaysLessThanEqual(Integer days);
    
    /**
     * Find suppliers by bulk order discount rate greater than or equal to the given rate.
     */
    List<Supplier> findByBulkOrderDiscountRateGreaterThanEqual(double rate);
    
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
    @Query("SELECT s FROM Supplier s JOIN s.woodTypesOffered w WHERE w = :woodType")
    List<Supplier> findByWoodTypeOffered(@Param("woodType") WoodType woodType);
    
    /**
     * Find suppliers that have a specific specialty.
     */
    @Query("SELECT s FROM Supplier s JOIN s.specialties spec WHERE spec LIKE %:specialty%")
    List<Supplier> findBySpecialty(@Param("specialty") String specialty);
    
    /**
     * Find suppliers that service a specific city.
     */
    @Query("SELECT s FROM Supplier s JOIN s.serviceCities c WHERE LOWER(c) = LOWER(:city)")
    List<Supplier> findByServiceCity(@Param("city") String city);
    
    /**
     * Find manufacturers (suppliers with type MANUFACTURER).
     */
    @Query("SELECT s FROM Supplier s WHERE s.supplierType = com.toto.backend.entities.enums.SupplierType.MANUFACTURER")
    List<Supplier> findManufacturers();
    
    /**
     * Find active suppliers (suppliers with status ACTIVE).
     */
    @Query("SELECT s FROM Supplier s WHERE s.status = com.toto.backend.entities.enums.SupplierStatus.ACTIVE")
    List<Supplier> findActiveSuppliers();
    
    /**
     * Find suppliers by city and supplier type.
     */
    List<Supplier> findByCityIgnoreCaseAndSupplierType(String city, SupplierType supplierType);
}