package com.emerghelp.emerghelp.data.models;


import com.emerghelp.emerghelp.data.constants.PaymentMethod;
import com.emerghelp.emerghelp.data.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "payments")
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "payment_description")
    private String paymentDescription;
    private Boolean payStatus;
}