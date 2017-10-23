package com.mmsdivino.poi;

import com.mmsdivino.geolocation.City;
import com.mmsdivino.json.JSONData;
import com.mmsdivino.user.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

public class OverlayData {
    private City city;
    private String poiUrl;
    private String name;
    private HashMap<Integer, POIData> poiDataHashMap;

    private static String DOMAIN = "http://overpass-api.de/api/interpreter/?data=[out:json];";
    private static String SUFIX = ";(._;%3E;);out;&bbox=";

    public OverlayData(City city, String poiUrl, String name) {
        this.city = city;
        this.poiUrl = poiUrl;
        this.name = name;
        this.poiDataHashMap = new HashMap<Integer, POIData>();
    }

    public void getPOIData() {
        JSONObject data = new JSONObject(JSONData.getURLContent(
                DOMAIN + this.poiUrl + SUFIX + this.city.toString()
        ));
        JSONArray elements = data.getJSONArray("elements");

        elements.forEach((Object item) -> {
            JSONObject element = (JSONObject) item;

            if (element.getString("type").equals("node") &&
                    element.has("tags") &&
                    element.getJSONObject("tags").has("name")) {
                POIData poiData = new POIData();
                poiData.setOverpassData(this.newOverpassDataObject(element));
                poiData.setgMapsData(this.newGMapsData());
                poiData.getgMapsData().getPlaces(
                        element.getDouble("lat"),
                        element.getDouble("lon"),
                        element.getJSONObject("tags").getString("name")
                );

                this.getPoiDataHashMap().put(
                        element.getInt("id"),
                        poiData
                );
            }
        });
    }

    private GMapsData newGMapsData() {
        GMapsData gMapsData = new GMapsData();

        return gMapsData;
    }

    private OverpassData newOverpassDataObject(JSONObject element) {
        OverpassData overpassData = new OverpassData(
                element.getInt("id"),
                element.getDouble("lat"),
                element.getDouble("lon"),
                element.getJSONObject("tags").getString("name")
        );

        return overpassData;
    }

    public void createRatingFile() {
        try {
            File file = new File(System.getProperty("user.dir") +
                    "/data/googlemaps/rating/ratings_0.txt"
            );

            if (file.createNewFile()) {
                this.writeRatingsFile(file);
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeRatingsFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);

            this.getPoiDataHashMap().forEach((Integer k, POIData v) -> {
                Double lat = v.getOverpassData().getLat();
                Double lon = v.getOverpassData().getLon();
                Integer poiId = v.getOverpassData().getId();
                v.getgMapsData().getReviews().forEach((User user) -> {
                    try {
                        writer.write(
                                user.getId() + " " +
                                        poiId + " " +
                                        user.getRating() + " " +
                                        this.getName() + " " +
                                        lat + " " + lon);
                        writer.write(System.getProperty("line.separator"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPoiUrl() {
        return poiUrl;
    }

    public void setPoiUrl(String poiUrl) {
        this.poiUrl = poiUrl;
    }

    public HashMap<Integer, POIData> getPoiDataHashMap() {
        return poiDataHashMap;
    }

    public void setPoiDataHashMap(HashMap<Integer, POIData> poiDataHashMap) {
        this.poiDataHashMap = poiDataHashMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
