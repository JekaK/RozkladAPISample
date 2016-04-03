package com.rozkladkpi;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by kruku on 02.04.2016.
 */

/**
 * Class creates for prepare all info about schedules and make it easy
 */
public class PrepareInfo {
    /**
     * Array for schedule of some day
     */
    private ArrayList<String> schedule;

    /**
     * Getter
     *
     * @return schedule list of setted day
     */
    public ArrayList<String> getArrayLists() {
        return schedule;
    }

    /**
     * Set array
     *
     * @param arrayLists set current array list of schedules
     */
    public void setArrayLists(ArrayList<String> arrayLists) {
        this.schedule = arrayLists;
    }

    /**
     * Give url to parse it into html code
     *
     * @param urlToRead url to parse
     * @return string representation of html
     */
    public String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Initialise array list schedule
     *
     * @param url       url to parse
     * @param dayNumber some day for schedule
     * @throws ParseException JSON li throw it if they don't have normal result
     */
    public void initArray(String url, int dayNumber) throws ParseException {
        String s = getHTML(url);
        char week = url.charAt(url.length() - 1);

        JSONParser parser = new JSONParser();

        JSONObject jsonObj = (JSONObject) parser.parse(s);

        JSONArray jo = (JSONArray) jsonObj.get("data");

        schedule = new ArrayList<String>();

        int i = 1;
        for (int j = 0; j < jo.size(); j++) {
            JSONObject jsonObject = (JSONObject) jo.get(j);

            if (jsonObject.get("day_number").equals(String.valueOf(dayNumber)) && jsonObject.get("lesson_week").equals(String.valueOf(week))) {
                schedule.add(jsonObject.get("lesson_number") + "." + jsonObject.get("lesson_name").toString());

            }
        }

    }

}

