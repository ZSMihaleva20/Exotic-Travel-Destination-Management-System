package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private int numberOfPeople;

    @Column(nullable = false)
    private double totalPrice;

    /*@Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String cvc;*/

    @Column(nullable = false)
    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isDestinationRated = false;

    @Column(nullable = false)
    private int destinationRating = 0;

    @Column(columnDefinition = "TEXT") // Allow long comments
    private String comment;

    public Reservation() {
        this.id = null;
        this.destination = null;
        this.numberOfPeople = 0;
        this.status = "PENDING";
        this.totalPrice = 0;
        this.user = null;
        this.isDestinationRated = false;
        this.destinationRating = 0;
        this.comment = "";
        /*this.cardName = "CardName";
        this.cardNumber = "CardNumber";
        this.expiryDate = "ExpiryDate";
        this.cvc = "cvc";*/
    }

    public Reservation(Long id, Destination destination, User user, int numberOfPeople, double totalPrice, boolean isDestinationRated, String status, int destinationRating, String comment /*String cardName String cardNumber, String expiryDate, String cvc, String status*/) {
        this.id = id;
        this.destination = destination;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.user = user;
        this.isDestinationRated = isDestinationRated;
        /*this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;*/
        this.status = status;
        this.destinationRating = destinationRating;
        this.comment = comment;
    }

    public Reservation(Destination destination, User user, int numberOfPeople, double totalPrice, String status, int destinationRating, boolean isDestinationRated, String comment ) {
        this.destination = destination;
        this.user = user;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.status = status;
        this.isDestinationRated = isDestinationRated;
        this.destinationRating = destinationRating;
        this.comment = comment;

        // Update popularity with the number of people
        this.destination.incrementPopularity(numberOfPeople);
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

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    /*
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

     */

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDestinationRated() {
        return isDestinationRated;
    }

    public void setDestinationRated(boolean destinationRated) {
        isDestinationRated = destinationRated;
    }

    public int getDestinationRating() {
        return destinationRating;
    }

    public void setDestinationRating(int destinationRating) {
        this.destinationRating = destinationRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}