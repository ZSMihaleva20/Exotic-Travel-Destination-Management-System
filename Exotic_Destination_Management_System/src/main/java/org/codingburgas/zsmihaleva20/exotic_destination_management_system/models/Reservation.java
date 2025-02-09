package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

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

    public Reservation() {
        this.id = null;
        this.destination = null;
        this.name = "name";
        this.email = "email@email.com";
        this.numberOfPeople = 0;
        this.status = "PENDING";
        this.totalPrice = 0;
        /*this.cardName = "CardName";
        this.cardNumber = "CardNumber";
        this.expiryDate = "ExpiryDate";
        this.cvc = "cvc";*/
    }

    public Reservation(Long id, Destination destination, String name, String email, int numberOfPeople, double totalPrice /*String cardName String cardNumber, String expiryDate, String cvc, String status*/) {
        this.id = id;
        this.destination = destination;
        this.name = name;
        this.email = email;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        /*this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;*/
        this.status = status;
    }

    public Reservation(Destination destination, String name, String email, int numberOfPeople, double totalPrice) {
        this.destination = destination;
        this.name = name;
        this.email = email;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.status = "PENDING";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
}