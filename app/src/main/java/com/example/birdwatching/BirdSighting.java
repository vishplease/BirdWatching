package com.example.birdwatching;

public class BirdSighting {

    public String birdName, personName;
    public Integer zipCode, sightingImportance;

    public BirdSighting(){
    }

    public BirdSighting(String birdName, String personName, Integer zipCode, Integer sightingImportance) {
        this.birdName = birdName;
        this.personName = personName;
        this.zipCode = zipCode;
        this.sightingImportance = sightingImportance;
    }
}
