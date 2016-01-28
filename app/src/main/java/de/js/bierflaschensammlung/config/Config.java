package de.js.bierflaschensammlung.config;

/**
 * Created by Jasmin on 28.01.2016.
 */
public class Config {

    private static final String URL = "http://192.168.0.201/bierbank/";
    private static final String PATH_TO_REST_API = "rest/";

    public static String getRestUrl() {
        return URL+PATH_TO_REST_API;
    }



}
