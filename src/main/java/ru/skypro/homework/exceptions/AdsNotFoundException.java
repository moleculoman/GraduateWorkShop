package ru.skypro.homework.exceptions;

public class AdsNotFoundException extends RuntimeException{
    public AdsNotFoundException(String message) {
        super(message);
    }
}