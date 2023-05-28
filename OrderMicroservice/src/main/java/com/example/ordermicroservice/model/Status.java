package com.example.ordermicroservice.model;

public enum Status {
    WAITING, // в ожидании
    IN_PROGRESS, // в работе
    DONE, // выполнен
    CANCELED; // отменён
}
