package com.toto.backend.repositories;

import com.toto.backend.entities.Customer;
import com.toto.backend.entities.enums.CustomerStatus;
import com.toto.backend.entities.enums.CustomerType;
import com.toto.backend.entities.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing Customer entities.
 * Provides methods for customer-specific operations in a Pakistani furniture store.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    /**
     * Find customers by first name containing the given text (case-insensitive).
     */
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    
    /**
     * Find customers by last name containing the given text (case-insensitive).
     */
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find customers by full name (first name and last name) containing the given text (case-insensitive).
     */
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %:name%")
    List<Customer> findByFullNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find customers by primary phone number.
     */
    Customer findByPrimaryPhone(String primaryPhone);
    
    /**
     * Find customers by CNIC (Pakistani National ID).
     */
    Customer findByCnic(String cnic);
    
    /**
     * Find customers by city.
     */
    List<Customer> findByCityIgnoreCase(String city);
    
    /**
     * Find customers by area.
     */
    List<Customer> findByAreaContainingIgnoreCase(String area);
    
    /**
     * Find customers by status.
     */
    List<Customer> findByStatus(CustomerStatus status);
    
    /**
     * Find customers by customer type.
     */
    List<Customer> findByCustomerType(CustomerType customerType);
    
    /**
     * Find VIP customers.
     */
    @Query("SELECT c FROM Customer c WHERE c.customerType = com.toto.backend.entities.enums.CustomerType.VIP")
    List<Customer> findVipCustomers();
    
    /**
     * Find customers by preferred payment method.
     */
    List<Customer> findByPreferredPaymentMethod(PaymentMethod preferredPaymentMethod);
    
    /**
     * Find customers by marketing consent.
     */
    List<Customer> findByMarketingConsent(boolean marketingConsent);
    
    /**
     * Find customers by referral source.
     */
    List<Customer> findByReferralSourceContainingIgnoreCase(String referralSource);
    
    /**
     * Find customers registered after a certain date.
     */
    List<Customer> findByRegistrationDateAfter(LocalDateTime date);
    
    /**
     * Find customers registered between two dates.
     */
    List<Customer> findByRegistrationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find customers with no orders.
     */
    @Query("SELECT c FROM Customer c WHERE c.orders IS EMPTY")
    List<Customer> findCustomersWithNoOrders();
    
    /**
     * Find customers with at least a certain number of orders.
     */
    @Query("SELECT c FROM Customer c WHERE SIZE(c.orders) >= :minOrders")
    List<Customer> findCustomersWithMinimumOrders(@Param("minOrders") int minOrders);
    
    /**
     * Find customers by city and customer type.
     */
    List<Customer> findByCityIgnoreCaseAndCustomerType(String city, CustomerType customerType);
}