package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

@Entity
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int limitedPeople;

    @Column(nullable = false)
    private String status = "PENDING-ACCEPT";

    @Column(nullable = false)
    private boolean pendingApproval = false; // Indicates if a request is pending approval

    @Column(nullable = false)
    private boolean editApproved = false;

    public boolean isEditApproved() {
        return editApproved;
    }

    public void setEditApproved(boolean editApproved) {
        this.editApproved = editApproved;
    }

    public boolean isPendingApproval() {
        return pendingApproval;
    }

    public void setPendingApproval(boolean pendingApproval) {
        this.pendingApproval = pendingApproval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Destination(Long id, String name, String description, String imageUrl, double price, int limitedPeople, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.limitedPeople = limitedPeople;
        this.status = status;
    }

    public Destination() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.imageUrl = null;
        this.price = 0;
        this.limitedPeople = 0;
        this.status = "PENDING-ACCEPT";
    }


    public int getLimitedPeople() {
        return limitedPeople;
    }

    public void setLimitedPeople(int limitedPeople) {
        this.limitedPeople = limitedPeople;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
