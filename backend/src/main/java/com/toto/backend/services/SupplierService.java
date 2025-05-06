package com.toto.backend.services;

import com.toto.backend.entities.Supplier;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.SupplierStatus;
import com.toto.backend.entities.enums.SupplierType;
import com.toto.backend.entities.enums.WoodType;
import com.toto.backend.repositories.SupplierRepository;
import com.toto.backend.services.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service for managing Supplier entities.
 * Provides business logic and encapsulates repository operations for suppliers.
 */
@Service
@Transactional
public class SupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Find all suppliers.
     */
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    /**
     * Find supplier by ID.
     */
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    /**
     * Save a supplier.
     */
    public Supplier save(Supplier supplier) {
        // Set default status for new suppliers if not specified
        if (supplier.getStatus() == null) {
            supplier.setStatus(SupplierStatus.ACTIVE);
        }

        return supplierRepository.save(supplier);
    }

    /**
     * Delete a supplier by ID.
     */
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    /**
     * Find suppliers by company name.
     */
    public List<Supplier> findByCompanyName(String companyName) {
        return supplierRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }

    /**
     * Find suppliers by owner name.
     */
    public List<Supplier> findByOwnerName(String ownerName) {
        return supplierRepository.findByOwnerNameContainingIgnoreCase(ownerName);
    }

    /**
     * Find suppliers by contact person.
     */
    public List<Supplier> findByContactPerson(String contactPerson) {
        return supplierRepository.findByContactPersonContainingIgnoreCase(contactPerson);
    }

    /**
     * Find supplier by email.
     */
    public Supplier findByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }

    /**
     * Find supplier by primary phone number.
     */
    public Supplier findByPrimaryPhone(String primaryPhone) {
        return supplierRepository.findByPrimaryPhone(primaryPhone);
    }

    /**
     * Find suppliers by city.
     */
    public List<Supplier> findByCity(String city) {
        return supplierRepository.findByCityIgnoreCase(city);
    }

    /**
     * Find suppliers by area.
     */
    public List<Supplier> findByArea(String area) {
        return supplierRepository.findByAreaContainingIgnoreCase(area);
    }

    /**
     * Find supplier by NTN number (National Tax Number).
     */
    public Supplier findByNtnNumber(String ntnNumber) {
        return supplierRepository.findByNtnNumber(ntnNumber);
    }

    /**
     * Find supplier by CNIC number.
     */
    public Supplier findByCnicNumber(String cnicNumber) {
        return supplierRepository.findByCnicNumber(cnicNumber);
    }

    /**
     * Find suppliers by supplier type.
     */
    public List<Supplier> findBySupplierType(SupplierType supplierType) {
        return supplierRepository.findBySupplierType(supplierType);
    }

    /**
     * Find suppliers by status.
     */
    public List<Supplier> findByStatus(SupplierStatus status) {
        return supplierRepository.findByStatus(status);
    }

    /**
     * Find suppliers by preferred payment method.
     */
    public List<Supplier> findByPreferredPaymentMethod(PaymentMethod preferredPaymentMethod) {
        return supplierRepository.findByPreferredPaymentMethod(preferredPaymentMethod);
    }

    /**
     * Find suppliers by minimum order amount less than or equal to the given amount.
     */
    public List<Supplier> findByMaxMinimumOrderAmount(double amount) {
        return supplierRepository.findByMinimumOrderAmountLessThanEqual(amount);
    }

    /**
     * Find suppliers by standard lead time in days less than or equal to the given days.
     */
    public List<Supplier> findByMaxStandardLeadTime(Integer days) {
        return supplierRepository.findByStandardLeadTimeInDaysLessThanEqual(days);
    }

    /**
     * Find suppliers by bulk order discount rate greater than or equal to the given rate.
     */
    public List<Supplier> findByMinBulkOrderDiscountRate(double rate) {
        return supplierRepository.findByBulkOrderDiscountRateGreaterThanEqual(rate);
    }

    /**
     * Find suppliers by whether they provide custom work.
     */
    public List<Supplier> findByProvidesCustomWork(boolean providesCustomWork) {
        return supplierRepository.findByProvidesCustomWork(providesCustomWork);
    }

    /**
     * Find suppliers by whether they provide installation.
     */
    public List<Supplier> findByProvidesInstallation(boolean providesInstallation) {
        return supplierRepository.findByProvidesInstallation(providesInstallation);
    }

    /**
     * Find suppliers that offer a specific wood type.
     */
    public List<Supplier> findByWoodTypeOffered(WoodType woodType) {
        return supplierRepository.findByWoodTypeOffered(woodType);
    }

    /**
     * Find suppliers that have a specific specialty.
     */
    public List<Supplier> findBySpecialty(String specialty) {
        return supplierRepository.findBySpecialty(specialty);
    }

    /**
     * Find suppliers that service a specific city.
     */
    public List<Supplier> findByServiceCity(String city) {
        return supplierRepository.findByServiceCity(city);
    }

    /**
     * Find manufacturers (suppliers with type MANUFACTURER).
     */
    public List<Supplier> findManufacturers() {
        return supplierRepository.findManufacturers();
    }

    /**
     * Find active suppliers (suppliers with status ACTIVE).
     */
    public List<Supplier> findActiveSuppliers() {
        return supplierRepository.findActiveSuppliers();
    }

    /**
     * Find suppliers by city and supplier type.
     */
    public List<Supplier> findByCityAndSupplierType(String city, SupplierType supplierType) {
        return supplierRepository.findByCityIgnoreCaseAndSupplierType(city, supplierType);
    }

    /**
     * Add a wood type to a supplier's offered wood types.
     * Business logic: Adds a wood type to the supplier's woodTypesOffered list.
     */
    public Supplier addWoodTypeOffered(Long supplierId, WoodType woodType) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<WoodType> woodTypes = supplier.getWoodTypesOffered();
            woodTypes.add(woodType);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Remove a wood type from a supplier's offered wood types.
     * Business logic: Removes a wood type from the supplier's woodTypesOffered list.
     */
    public Supplier removeWoodTypeOffered(Long supplierId, WoodType woodType) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<WoodType> woodTypes = supplier.getWoodTypesOffered();
            woodTypes.remove(woodType);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Add a specialty to a supplier's specialties.
     * Business logic: Adds a specialty to the supplier's specialties list.
     */
    public Supplier addSpecialty(Long supplierId, String specialty) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<String> specialties = supplier.getSpecialties();
            specialties.add(specialty);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Remove a specialty from a supplier's specialties.
     * Business logic: Removes a specialty from the supplier's specialties list.
     */
    public Supplier removeSpecialty(Long supplierId, String specialty) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<String> specialties = supplier.getSpecialties();
            specialties.remove(specialty);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Add a service city to a supplier's service cities.
     * Business logic: Adds a service city to the supplier's serviceCities list.
     */
    public Supplier addServiceCity(Long supplierId, String city) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<String> serviceCities = supplier.getServiceCities();
            serviceCities.add(city);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Remove a service city from a supplier's service cities.
     * Business logic: Removes a service city from the supplier's serviceCities list.
     */
    public Supplier removeServiceCity(Long supplierId, String city) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            List<String> serviceCities = supplier.getServiceCities();
            serviceCities.remove(city);
            return supplierRepository.save(supplier);
        }
        return null;
    }

    /**
     * Calculate supplier reliability score based on various factors.
     * Business logic: Score is based on lead time, discount rate, services offered, etc.
     */
    public int calculateReliabilityScore(Supplier supplier) {
        int score = 0;

        // Lower lead time is better
        if (supplier.getStandardLeadTimeInDays() != null) {
            if (supplier.getStandardLeadTimeInDays() <= 7) {
                score += 20; // 1 week or less
            } else if (supplier.getStandardLeadTimeInDays() <= 14) {
                score += 15; // 2 weeks or less
            } else if (supplier.getStandardLeadTimeInDays() <= 30) {
                score += 10; // 1 month or less
            } else {
                score += 5; // More than 1 month
            }
        }

        // Higher bulk discount is better
        if (supplier.getBulkOrderDiscountRate() >= 0.15) {
            score += 15; // 15% or more
        } else if (supplier.getBulkOrderDiscountRate() >= 0.10) {
            score += 10; // 10% or more
        } else if (supplier.getBulkOrderDiscountRate() >= 0.05) {
            score += 5; // 5% or more
        }

        // Additional services
        if (supplier.isProvidesCustomWork()) {
            score += 10;
        }

        if (supplier.isProvidesInstallation()) {
            score += 10;
        }

        // Wood types offered
        if (supplier.getWoodTypesOffered() != null) {
            score += Math.min(15, supplier.getWoodTypesOffered().size() * 3); // Up to 15 points
        }

        // Specialties
        if (supplier.getSpecialties() != null) {
            score += Math.min(15, supplier.getSpecialties().size() * 3); // Up to 15 points
        }

        // Service cities
        if (supplier.getServiceCities() != null) {
            score += Math.min(15, supplier.getServiceCities().size() * 3); // Up to 15 points
        }

        return score;
    }

    /**
     * Mark suppliers as on hold.
     * Business logic: Marks active suppliers as on hold based on criteria.
     */
    public int markSuppliersOnHold(List<Long> supplierIds) {
        int count = 0;

        for (Long id : supplierIds) {
            Optional<Supplier> supplierOpt = supplierRepository.findById(id);
            if (supplierOpt.isPresent()) {
                Supplier supplier = supplierOpt.get();
                if (supplier.getStatus() == SupplierStatus.ACTIVE) {
                    supplier.setStatus(SupplierStatus.ON_HOLD);
                    supplierRepository.save(supplier);
                    count++;
                }
            }
        }

        return count;
    }
}
