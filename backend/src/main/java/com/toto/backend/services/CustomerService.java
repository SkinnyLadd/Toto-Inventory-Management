package com.toto.backend.services;

import com.toto.backend.entities.Customer;
import com.toto.backend.entities.enums.CustomerStatus;
import com.toto.backend.entities.enums.CustomerType;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.repositories.CustomerRepository;
import com.toto.backend.services.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing Customer entities.
 * Provides business logic and encapsulates repository operations for customers.
 */
@Service
@Transactional
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Find all customers.
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Find customer by ID.
     */
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Save a customer.
     */
    public Customer save(Customer customer) {
        // Set registration date for new customers
        if (customer.getId() == null && customer.getRegistrationDate() == null) {
            customer.setRegistrationDate(LocalDateTime.now());
        }

        // Set default status for new customers if not specified
        if (customer.getStatus() == null) {
            customer.setStatus(CustomerStatus.ACTIVE);
        }

        return customerRepository.save(customer);
    }

    /**
     * Delete a customer by ID.
     */
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    /**
     * Find customers by first name.
     */
    public List<Customer> findByFirstName(String firstName) {
        return customerRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    /**
     * Find customers by last name.
     */
    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    /**
     * Find customers by full name.
     */
    public List<Customer> findByFullName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name);
    }

    /**
     * Find customer by primary phone number.
     */
    public Customer findByPrimaryPhone(String primaryPhone) {
        return customerRepository.findByPrimaryPhone(primaryPhone);
    }

    /**
     * Find customer by CNIC (Pakistani National ID).
     */
    public Customer findByCnic(String cnic) {
        return customerRepository.findByCnic(cnic);
    }

    /**
     * Find customers by city.
     */
    public List<Customer> findByCity(String city) {
        return customerRepository.findByCityIgnoreCase(city);
    }

    /**
     * Find customers by area.
     */
    public List<Customer> findByArea(String area) {
        return customerRepository.findByAreaContainingIgnoreCase(area);
    }

    /**
     * Find customers by status.
     */
    public List<Customer> findByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    /**
     * Find customers by customer type.
     */
    public List<Customer> findByCustomerType(CustomerType customerType) {
        return customerRepository.findByCustomerType(customerType);
    }

    /**
     * Find VIP customers.
     */
    public List<Customer> findVipCustomers() {
        return customerRepository.findVipCustomers();
    }

    /**
     * Find customers by preferred payment method.
     */
    public List<Customer> findByPreferredPaymentMethod(PaymentMethod preferredPaymentMethod) {
        return customerRepository.findByPreferredPaymentMethod(preferredPaymentMethod);
    }

    /**
     * Find customers by marketing consent.
     */
    public List<Customer> findByMarketingConsent(boolean marketingConsent) {
        return customerRepository.findByMarketingConsent(marketingConsent);
    }

    /**
     * Find customers by referral source.
     */
    public List<Customer> findByReferralSource(String referralSource) {
        return customerRepository.findByReferralSourceContainingIgnoreCase(referralSource);
    }

    /**
     * Find customers registered after a certain date.
     */
    public List<Customer> findByRegistrationDateAfter(LocalDateTime date) {
        return customerRepository.findByRegistrationDateAfter(date);
    }

    /**
     * Find customers registered between two dates.
     */
    public List<Customer> findByRegistrationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return customerRepository.findByRegistrationDateBetween(startDate, endDate);
    }

    /**
     * Find customers with no orders.
     */
    public List<Customer> findCustomersWithNoOrders() {
        return customerRepository.findCustomersWithNoOrders();
    }

    /**
     * Find customers with at least a certain number of orders.
     */
    public List<Customer> findCustomersWithMinimumOrders(int minOrders) {
        return customerRepository.findCustomersWithMinimumOrders(minOrders);
    }

    /**
     * Find customers by city and customer type.
     */
    public List<Customer> findByCityAndCustomerType(String city, CustomerType customerType) {
        return customerRepository.findByCityIgnoreCaseAndCustomerType(city, customerType);
    }

    /**
     * Upgrade customer to VIP status based on order history.
     * Business logic: Customers with 5 or more orders are eligible for VIP status.
     */
    public boolean upgradeToVipIfEligible(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getOrders() != null && customer.getOrders().size() >= 5 
                    && customer.getCustomerType() != CustomerType.VIP) {
                customer.setCustomerType(CustomerType.VIP);
                customerRepository.save(customer);
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate customer loyalty score based on order history and registration duration.
     * Business logic: Score is based on number of orders and years as a customer.
     */
    public int calculateLoyaltyScore(Customer customer) {
        int score = 0;

        // Points for each order
        if (customer.getOrders() != null) {
            score += customer.getOrders().size() * 10;
        }

        // Points for registration duration
        if (customer.getRegistrationDate() != null) {
            LocalDateTime now = LocalDateTime.now();
            long years = java.time.temporal.ChronoUnit.YEARS.between(customer.getRegistrationDate(), now);
            score += years * 5;
        }

        // Bonus points for VIP status
        if (customer.getCustomerType() == CustomerType.VIP) {
            score += 20;
        }

        return score;
    }

    /**
     * Mark inactive customers based on last order date.
     * Business logic: Customers with no orders in the last year are marked as inactive.
     */
    public int markInactiveCustomers() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        List<Customer> customers = customerRepository.findAll();
        int count = 0;

        for (Customer customer : customers) {
            if (customer.getStatus() == CustomerStatus.ACTIVE) {
                boolean hasRecentOrder = false;

                if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
                    for (var order : customer.getOrders()) {
                        if (order.getOrderDate().isAfter(oneYearAgo)) {
                            hasRecentOrder = true;
                            break;
                        }
                    }
                }

                if (!hasRecentOrder) {
                    customer.setStatus(CustomerStatus.INACTIVE);
                    customerRepository.save(customer);
                    count++;
                }
            }
        }

        return count;
    }
}
