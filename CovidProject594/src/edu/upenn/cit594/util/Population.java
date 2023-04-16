package edu.upenn.cit594.util;

public class Population {


    private int population;
    private String zipCode;

    public Population(int population, String zipCode) {
        this.population = population;
        this.zipCode = zipCode;
    }

    public double getPopulation() {
        return population;
    }



    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "[Population=" + population + " " +
                "ZipCode=" + zipCode +"]";
    }
}

