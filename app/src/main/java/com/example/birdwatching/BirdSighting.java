package com.example.birdwatching;

public class BirdSighting {

    public String birdName, personName;
    public Integer zipCode;

    public BirdSighting(){
    }

    public BirdSighting(String birdName, Integer zipCode, String personName) {
        this.birdName = birdName;
        this.personName = personName;
        this.zipCode = zipCode;
    }
}
