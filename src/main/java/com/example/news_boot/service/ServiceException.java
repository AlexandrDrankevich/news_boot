package com.example.news_boot.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -1291668902432628598L;

    public ServiceException(String e) {
        super(e);
    }

    public ServiceException(Exception e) {
        super(e);
    }
}
