package com.mmsdivino;

import com.mmsdivino.geolocation.City;
import com.mmsdivino.poi.OverlayData;
import com.mmsdivino.recommendation.Driver;

public class Main {
    public static void main(String[] args) {
//        City city = new City("http://nominatim.openstreetmap.org/search/brazil/bahia/salvador?format=json");
//        OverlayData artsCentre = new OverlayData(
//                city,
//                "(node[amenity=arts_centre](bbox);way[amenity=arts_centre](bbox);rel[amenity=arts_centre](bbox))",
//                "arts_centre"
//        );
//        artsCentre.getPOIData();
//        artsCentre.createRatingFile();

        Driver.getRecommendations();

    }
}
