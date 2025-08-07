package com.moviebook.moviebook.config.constants;

public enum SeatPricing {

    REGULAR(150.0),
    PREMIUM(250.0),
    RECLINER(400.0);

    private final double price;

    SeatPricing(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
