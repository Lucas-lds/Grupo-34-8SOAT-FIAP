package com.fiap.restaurante.core.domain;

public enum OrderStatus {
    RECEIVED(1),
    PREPARING(2),
    DONE(3),
    FINISHED(4),
    CANCELED(5);

    private final int statusCode;

    OrderStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static OrderStatus fromStatusCode(int statusCode) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + statusCode);
    }
}
