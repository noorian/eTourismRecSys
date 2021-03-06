package com.mmsdivino.poi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.*;
import com.google.maps.model.*;
import com.mmsdivino.user.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GMapsData {
    private ArrayList<User> reviews;
    protected static String API_KEY = "";

    public GMapsData() {
        this.reviews = new ArrayList<User>();
    }

    public void getPlaces(Double lat, Double lon, String name) {
        LatLng location = new LatLng(lat, lon);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        PlacesSearchResponse results = null;
        PlaceDetails resultID = null;

        try {
            results = PlacesApi.textSearchQuery(context, name)
                    .location(location)
                    .radius(1)
                    .await();

            if (results.results.length > 0) {
                PlacesSearchResult result = results.results[0];
                resultID = PlacesApi.placeDetails(context, result.placeId).await();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JSONArray reviews = new JSONObject(gson.toJson(resultID)).getJSONArray("reviews");
                this.loadUserReviews(reviews);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserReviews(JSONArray reviews) {
        reviews.forEach((Object item) -> {
            JSONObject review = (JSONObject) item;

            User user = new User(
                    this.longHash(review.getString("authorName")),
                    review.getDouble("rating")
            );

            this.getReviews().add(user);
        });
    }

    private long longHash(String string) {
        long h = 98764321261L;
        int l = string.length();
        char[] chars = string.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31*h + chars[i];
        }
        return h;
    }

    public ArrayList<User> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<User> reviews) {
        this.reviews = reviews;
    }
}
