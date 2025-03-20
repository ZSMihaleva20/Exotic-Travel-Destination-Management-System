package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Campaigns") // Maps this entity to the "Campaigns" table in the database
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID values
    private Long id;

    @OneToOne // One campaign is linked to one destination
    @JoinColumn(name = "destination_id") // Foreign key reference to the "destination" table
    private Destination destination;

    private double oldPrice; // Stores the original price before the discount
    private double discountPercentage;

    public Campaign(Long id, Destination destination, double oldPrice, double discountPercentage) {
        this.id = id;
        this.destination = destination;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
    }

    public Campaign() {
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
