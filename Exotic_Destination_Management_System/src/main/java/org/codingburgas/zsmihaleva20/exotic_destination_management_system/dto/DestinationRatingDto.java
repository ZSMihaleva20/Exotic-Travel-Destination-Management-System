package org.codingburgas.zsmihaleva20.exotic_destination_management_system.dto;

// DTO representing a destination with its rating and popularity.
public class DestinationRatingDto {
    private Long id;
    private String name;
    private Double rating;
    private int popularity;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
