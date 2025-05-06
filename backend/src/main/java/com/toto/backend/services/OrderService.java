package com.toto.backend.services;

import com.toto.backend.entities.Furniture;
import com.toto.backend.entities.Order;
import com.toto.backend.entities.enums.OrderStatus;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.PaymentPlan;
import com.toto.backend.entities.enums.PaymentStatus;
import com.toto.backend.repositories.OrderRepository;
import com.toto.backend.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing Order entities.
 * Provides business logic and encapsulates repository operations for orders.
 */
@Service
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Find all orders.
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Find order by ID.
     */
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Save an order.
     */
    public Order save(Order order) {
        // Set order date for new orders
        if (order.getId() == null && order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        // Set default status for new orders if not specified
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }

        // Set default payment plan if not specified
        if (order.getPaymentPlan() == null) {
            order.setPaymentPlan(PaymentPlan.FULL_PAYMENT);
        }

        // Recalculate total amount
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            double total = order.getItems().stream()
                    .mapToDouble(Furniture::getPrice)
                    .sum();
            order.setTotalAmount(total);
        }

        return orderRepository.save(order);
    }

    /**
     * Delete an order by ID.
     */
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Find orders by customer ID.
     */
    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_Id(customerId);
    }

    /**
     * Find orders by order status.
     */
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * Find orders by payment method.
     */
    public List<Order> findByPaymentMethod(PaymentMethod paymentMethod) {
        return orderRepository.findByPaymentMethod(paymentMethod);
    }

    /**
     * Find orders by payment plan.
     */
    public List<Order> findByPaymentPlan(PaymentPlan paymentPlan) {
        return orderRepository.findByPaymentPlan(paymentPlan);
    }

    /**
     * Find orders by payment status.
     */
    public List<Order> findByPaymentStatus(PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus);
    }

    /**
     * Find orders by order date after a certain date.
     */
    public List<Order> findByOrderDateAfter(LocalDateTime date) {
        return orderRepository.findByOrderDateAfter(date);
    }

    /**
     * Find orders by order date between two dates.
     */
    public List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    /**
     * Find orders by expected delivery date after a certain date.
     */
    public List<Order> findByExpectedDeliveryDateAfter(LocalDateTime date) {
        return orderRepository.findByExpectedDeliveryDateAfter(date);
    }

    /**
     * Find orders by actual delivery date between two dates.
     */
    public List<Order> findByActualDeliveryDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByActualDeliveryDateBetween(startDate, endDate);
    }

    /**
     * Find orders by delivery city.
     */
    public List<Order> findByDeliveryCity(String city) {
        return orderRepository.findByDeliveryCityIgnoreCase(city);
    }

    /**
     * Find orders by delivery area.
     */
    public List<Order> findByDeliveryArea(String area) {
        return orderRepository.findByDeliveryAreaContainingIgnoreCase(area);
    }

    /**
     * Find orders by whether they require assembly.
     */
    public List<Order> findByRequiresAssembly(boolean requiresAssembly) {
        return orderRepository.findByRequiresAssembly(requiresAssembly);
    }

    /**
     * Find orders by whether they require installation.
     */
    public List<Order> findByRequiresInstallation(boolean requiresInstallation) {
        return orderRepository.findByRequiresInstallation(requiresInstallation);
    }

    /**
     * Find orders by sales person.
     */
    public List<Order> findBySalesPerson(String salesPerson) {
        return orderRepository.findBySalesPersonContainingIgnoreCase(salesPerson);
    }

    /**
     * Find orders by total amount greater than a certain amount.
     */
    public List<Order> findByTotalAmountGreaterThan(Double amount) {
        return orderRepository.findByTotalAmountGreaterThan(amount);
    }

    /**
     * Find orders by total amount between two amounts.
     */
    public List<Order> findByTotalAmountBetween(Double minAmount, Double maxAmount) {
        return orderRepository.findByTotalAmountBetween(minAmount, maxAmount);
    }

    /**
     * Find pending orders (orders with status PENDING).
     */
    public List<Order> findPendingOrders() {
        return orderRepository.findPendingOrders();
    }

    /**
     * Find orders with overdue delivery (expected delivery date before current date and status not DELIVERED).
     */
    public List<Order> findOverdueDeliveries() {
        return orderRepository.findOverdueDeliveries();
    }

    /**
     * Find orders with pending payments (payment status PENDING or PARTIAL).
     */
    public List<Order> findOrdersWithPendingPayments() {
        return orderRepository.findOrdersWithPendingPayments();
    }

    /**
     * Find orders by customer and status.
     */
    public List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status) {
        return orderRepository.findByCustomer_IdAndStatus(customerId, status);
    }

    /**
     * Find orders containing a specific furniture item.
     */
    public List<Order> findOrdersContainingFurniture(Long furnitureId) {
        return orderRepository.findOrdersContainingFurniture(furnitureId);
    }

    /**
     * Find orders by city and payment method.
     */
    public List<Order> findByCityAndPaymentMethod(String city, PaymentMethod paymentMethod) {
        return orderRepository.findByDeliveryCityIgnoreCaseAndPaymentMethod(city, paymentMethod);
    }

    /**
     * Update order status.
     * Business logic: Updates the order status and performs any necessary side effects.
     */
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            OrderStatus oldStatus = order.getStatus();
            order.setStatus(newStatus);

            // Handle status-specific logic
            if (newStatus == OrderStatus.DELIVERED && oldStatus != OrderStatus.DELIVERED) {
                order.setActualDeliveryDate(LocalDateTime.now());
            } else if (newStatus == OrderStatus.CANCELLED && 
                    (oldStatus == OrderStatus.PENDING || oldStatus == OrderStatus.PROCESSING)) {
                // No additional action needed for cancellation
            }

            return orderRepository.save(order);
        }
        return null;
    }

    /**
     * Update payment status.
     * Business logic: Updates the payment status and calculates remaining payment if needed.
     */
    public Order updatePaymentStatus(Long orderId, PaymentStatus newStatus, Double paymentAmount) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setPaymentStatus(newStatus);

            // Handle payment amount if provided
            if (paymentAmount != null && paymentAmount > 0) {
                if (order.getAdvancePayment() == null) {
                    order.setAdvancePayment(paymentAmount);
                } else {
                    order.setAdvancePayment(order.getAdvancePayment() + paymentAmount);
                }

                // Calculate remaining payment
                if (order.getTotalAmount() != null) {
                    double remaining = order.getTotalAmount() - order.getAdvancePayment();
                    order.setRemainingPayment(Math.max(0, remaining));

                    // Update payment status based on remaining amount
                    if (remaining <= 0) {
                        order.setPaymentStatus(PaymentStatus.COMPLETED);
                    } else {
                        order.setPaymentStatus(PaymentStatus.PARTIAL);
                    }
                }
            }

            return orderRepository.save(order);
        }
        return null;
    }

    /**
     * Calculate installment plan.
     * Business logic: Calculates monthly installment amount based on total amount and installment months.
     */
    public Order calculateInstallmentPlan(Long orderId, Integer installmentMonths) {
        if (installmentMonths == null || installmentMonths <= 0) {
            return null;
        }

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Set payment plan to installment
            order.setPaymentPlan(PaymentPlan.INSTALLMENTS);
            order.setInstallmentMonths(installmentMonths);

            // Calculate monthly installment amount
            if (order.getTotalAmount() != null) {
                double advancePayment = order.getAdvancePayment() != null ? order.getAdvancePayment() : 0;
                double remainingAmount = order.getTotalAmount() - advancePayment;

                if (remainingAmount > 0) {
                    double monthlyAmount = remainingAmount / installmentMonths;
                    order.setMonthlyInstallmentAmount(monthlyAmount);
                    order.setRemainingPayment(remainingAmount);
                }
            }

            return orderRepository.save(order);
        }
        return null;
    }
}
