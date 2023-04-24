package com.bdd.TP.dto;

import lombok.Data;

@Data
public class HelloWordResponse {
    private String text;
    public HelloWordResponse(String text){
        this.text = text;
    }

}
