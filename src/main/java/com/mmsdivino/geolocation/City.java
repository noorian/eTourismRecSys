package com.mmsdivino.geolocation;

import com.mmsdivino.json.JSONData;
import org.json.JSONArray;
import org.json.JSONObject;

public class City extends Location {
    private String urlCity;

    public City(String urlCity) {
        this.urlCity = urlCity;
        this.getBoundingbox();
    }

    public void getBoundingbox() {
        JSONObject city = new JSONArray(JSONData.getURLContent(this.getUrlCity())).getJSONObject(0);
        JSONArray boundingBox = city.getJSONArray("boundingbox");

        this.setWest(boundingBox.getDouble(0));
        this.setEast(boundingBox.getDouble(1));
        this.setSouth(boundingBox.getDouble(2));
        this.setNorth(boundingBox.getDouble(3));
    }

    public String getUrlCity() {
        return urlCity;
    }

    public void setUrlCity(String urlCity) {
        this.urlCity = urlCity;
    }
}
