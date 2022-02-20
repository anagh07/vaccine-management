package com.tintinpharma;

/**
 * Assignment 1
 * © Anagh Mehran
 * Written by: Anagh Mehran (40137948)
 */

/**
 * Vaccine class that hold attributes required for vaccine objects.
 */
public class Vaccine {

    public enum Brands {Pfizer, Moderna, AstraZeneca, Janssen}

    private Brands brand;
    private double dose;
    private String expiry;
    private long id;
    private double priceTag;
    /**
     * Keeps track of the number of vaccines created
     */
    private static long vaccineCount = 0;

    /**
     * Constructor that takes in all the field attributes as arguments.
     *
     * @param brand    Brand name of the vaccine
     * @param dose     Dosage amount
     * @param expiry   Date of expiry
     * @param id       Identity number
     * @param priceTag Maximum retail price
     */
    public Vaccine(Brands brand, double dose, String expiry, long id, double priceTag) {
        this.brand = brand;
        this.dose = dose;
        this.expiry = expiry;
        this.id = id;
        this.priceTag = priceTag;
        vaccineCount++;
    }

    public Vaccine(Brands brand, double dose, String expiry, double priceTag) {
        this(brand, dose, expiry, (long) (Math.random() * Math.pow(10, 9)), priceTag);
    }

    /**
     * Overload constructor. This allows the creation of vaccine objects with default values for all
     * the fields.
     */
    public Vaccine() {
        this(Brands.Pfizer, 1.55, "2023-01-01", 25.5);
    }

    /**
     * Compare equality for two vaccines.
     * @param vaccine the vaccine object with which the current vaccine object is to be compared with.
     * @return returns true if both vaccines have the same brand and dose, otherwise returns false.
     */
    public boolean equals(Vaccine vaccine) {
        return (this.brand.equals(vaccine.getBrand()) && this.dose == vaccine.getDose()) ? true : false;
    }

    /**
     * Information about how many vaccines have been created so far.
     * @return returns number of vaccine objects created. Returns 0 if no vaccines have been created.
     */
    public long findNumberOfCreatedVaccines() {
        return vaccineCount;
    }

    /**
     * Retrieve all information about a vaccine.
     * @return all the field values in the form of a single string.
     */
    public String toString() {
        return "ID: " +
                this.id +
                ", Brand: " +
                this.brand +
                ", Dose: " +
                this.dose +
                ", Expiry: " +
                this.expiry +
                ", Price: " +
                this.priceTag;
    }

    /**
     * Getter method for id field. There is no setter method for id. This makes it so that id cannot be modified
     * after creation of the vaccine.
     *
     * @return the id for the created vaccine.
     */
    public long getId() {
        return id;
    }

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public double getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(double priceTag) {
        this.priceTag = priceTag;
    }
}
