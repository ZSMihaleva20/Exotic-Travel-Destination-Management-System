package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    private double oldPrice; // Stores the original price before discount
    private double discountPercentage;

    public Promotion(Long id, Destination destination, double oldPrice, double discountPercentage) {
        this.id = id;
        this.destination = destination;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
    }

    public Promotion() {
        this.id = null;
        this.destination = null;
        this.oldPrice = 0;
        this.discountPercentage = 0;
    }

    public double getNewPrice() {
        return oldPrice * (1 - discountPercentage / 100);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
