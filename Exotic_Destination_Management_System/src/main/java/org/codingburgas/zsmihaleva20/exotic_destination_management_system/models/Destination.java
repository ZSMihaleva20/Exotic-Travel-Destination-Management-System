package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Destinations")
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
    private int remainingPeople;

    @Column(nullable = false)
    private String status = "PENDING-ACCEPT";

    @Column(nullable = false)
    private boolean pendingApproval = false; // Indicates if a request is pending approval

    @Column(nullable = false)
    private boolean editApproved = false;

    @Column()
    private double averageRating = 0;

    @Column(nullable = false)
    private int popularity = 0; // Number of reservations

    @Column(nullable = false)
    private LocalDate dateOfDeparture;

    @Column(nullable = false)
    private LocalDate dateOfReturn;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @Column(nullable = false)
    private boolean isOnPromotion = false;

    @OneToOne(mappedBy = "destination", cascade = CascadeType.ALL)
    private Promotion promotion;

    public double getAverageRating() {
        return this.averageRating;
    }

    public int getPopularity() {
        return popularity;
    }

    public void incrementPopularity(int numberOfPeople) {
        this.popularity += numberOfPeople;
    }

    public void decrementPopularity(int numberOfPeople) {
        this.popularity -= numberOfPeople;
        if (this.popularity < 0) {
            this.popularity = 0; // Ensure it doesn't go negative
        }
    }

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

    public Destination(Long id, String name, String description, String imageUrl, double price, int limitedPeople, String status, int remainingPeople, int ratingSum, int ratingCount, int popularity, double averageRating, LocalDate dateOfDeparture, LocalDate dateOfReturn, boolean isOnPromotion) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.limitedPeople = limitedPeople;
        this.status = status;
        this.remainingPeople = remainingPeople;
        this.popularity = popularity;
        this.averageRating = averageRating;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfReturn = dateOfReturn;
        this.isOnPromotion = isOnPromotion;
    }

    public Destination() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.imageUrl = null;
        this.price = 0;
        this.limitedPeople = 0;
        this.status = "PENDING-ACCEPT";
        this.remainingPeople = 0;
        this.popularity = 0;
        this.averageRating = 0;
        this.dateOfDeparture = LocalDate.now();
        this.dateOfReturn = LocalDate.now();
        this.isOnPromotion = false;
    }

    // Add Getters and Setters
    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
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

    public int getRemainingPeople() {
        return remainingPeople;
    }

    public void setRemainingPeople(int remainingPeople) {
        this.remainingPeople = remainingPeople;
    }


    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isOnPromotion() {
        return isOnPromotion;
    }

    public void setOnPromotion(boolean onPromotion) {
        isOnPromotion = onPromotion;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setDestination(this);
    }
}
