package com.sanglt.aggregator.models.responses;

public class OrderResponse {

    private Integer id;

    private String description;

    public OrderResponse(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public OrderResponse() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
