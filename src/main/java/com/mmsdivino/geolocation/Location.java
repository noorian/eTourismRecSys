package com.mmsdivino.geolocation;

public class Location {
    private double north;
    private double south;
    private double west;
    private double east;

    public Location() {

    }

    public Location(double north, double south, double west, double east) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    @Override
    public String toString() {
        return this.south + ","
                + this.west + ","
                + this.north + ","
                + this.east;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }
}
