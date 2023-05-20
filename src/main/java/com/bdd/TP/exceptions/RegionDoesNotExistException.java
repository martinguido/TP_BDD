package com.bdd.TP.exceptions;

public class RegionDoesNotExistException extends RuntimeException{
    public RegionDoesNotExistException(String errorMessage) {super(errorMessage); }
}