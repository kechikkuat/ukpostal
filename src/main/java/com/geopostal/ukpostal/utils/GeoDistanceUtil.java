package com.geopostal.ukpostal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoDistanceUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(GeoDistanceUtil.class);

    //Earth Radius in Kilometer(KM)
    private final static double EARTH_RADIUS = 6371;

    // <editor-fold defaultstate="collapsed" desc="Utils method to calculate Distance between two coordinate(Lat/Long)">
    // Using Haversine formula! (Source: Wikipedia)
    public static double calculateDistance(double latitude, double longitude, double latitude2, double longitude2){

        LOGGER.debug("Start of CalculateDistance Method");

        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);

        double a = haversine(lat1Radians, lat2Radians)
                + (Math.cos(lat1Radians)
                * Math.cos(lat2Radians)
                * haversine(lon1Radians, lon2Radians));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        LOGGER.debug("End of CalculateDistance Method (Returning result from calculation)");
        return (EARTH_RADIUS * c);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="segment code section for haversine calculating method">
    private static double haversine(double deg1, double deg2){
        return Math.pow(Math.sin((deg1 - deg2) / 2.0), 2);
    }
    //</editor-fold>
}
