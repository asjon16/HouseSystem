package com.system.housesystem.entity;

import com.system.housesystem.domain.exception.ResourceNotFoundException;

import java.util.Arrays;

public enum Status {

    ON("ON"),
    OFF("OFF");


    private String value;

    Status(String value){
        this.value = value;
    }

    public static Status fromValue(String value){
        return Arrays.asList(Status.values()).stream()
                .filter(role -> role.value.equals(value))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Status can be only ON/OFF"));
    }
}
