package com.journaldev.spring.service;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {

    private Long id;

    public IncorrectPasswordException(Long id) {
        super("Incorrect password exception => Id Taxi = " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
