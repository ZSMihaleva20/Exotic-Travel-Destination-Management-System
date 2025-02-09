package org.codingburgas.zsmihaleva20.exotic_destination_management_system.models;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private int stars;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Rating() {}

    public Rating(Destination destination, User user, int stars) {
        this.destination = destination;
        this.user = user;
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
