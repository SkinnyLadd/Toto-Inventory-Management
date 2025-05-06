package com.toto.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.toto.backend.entities.enums.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @Pattern(regexp = "^(\\+92|0)[0-9]{10}$", message = "Please provide a valid Pakistani phone number")
    @Column(nullable = false)
    private String primaryPhone;

    @Pattern(regexp = "^(\\+92|0)[0-9]{10}$")
    private String secondaryPhone;

    @Column(length = 13)
    private String cnic;  // Pakistani National ID

    @Column(nullable = false)
    private String city;

    private String area;  // e.g., DHA, Gulberg, etc.

    @Column(columnDefinition = "TEXT")
    private String completeAddress;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    private PaymentMethod preferredPaymentMethod;

    private boolean marketingConsent;
    private String specialNotes;
    private String referralSource;  // e.g., "Friend", "Social Media", etc.

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
        status = CustomerStatus.ACTIVE;
        if (customerType == null) {
            customerType = CustomerType.FIRST_TIME;
        }
    }

    // Helper methods
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
