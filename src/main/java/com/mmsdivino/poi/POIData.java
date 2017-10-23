package com.mmsdivino.poi;

public class POIData {
    private OverpassData overpassData;
    private GMapsData gMapsData;

    public POIData() {
    }

    public POIData(OverpassData overpassData, GMapsData gMapsData) {
        this.overpassData = overpassData;
        this.gMapsData = gMapsData;
    }

    public OverpassData getOverpassData() {
        return overpassData;
    }

    public void setOverpassData(OverpassData overpassData) {
        this.overpassData = overpassData;
    }

    public GMapsData getgMapsData() {
        return gMapsData;
    }

    public void setgMapsData(GMapsData gMapsData) {
        this.gMapsData = gMapsData;
    }
}
