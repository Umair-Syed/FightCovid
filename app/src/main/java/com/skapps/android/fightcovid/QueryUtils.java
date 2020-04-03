package com.skapps.android.fightcovid;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class QueryUtils {

//    public static String preferredState = "Maharashtra";
    public static String CONFIRMED_UPDATE_WORK_KEY = "confirmUpdateWorkKey";
    public static String CONFIRMED_INT_STATE_KEY = "confirmedIntKey";
    public static String CONFIRMED_INT_COUNTRY_KEY = "confirmedIntKeyCountry";

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static List<Location> fetchCovidStateData(String requestUrl, String cstate) {
        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        // Return the list of {@link Earthquake}s
        return extractFromJsonStateData(jsonResponse, cstate);
    }

    private static List<Location> extractFromJsonStateData(String covidJson, String cstate) {

        if (TextUtils.isEmpty(covidJson)) {
            return null;
        }

        List<Location> locations = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(covidJson);

            Iterator<String> iter = baseJsonResponse.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                if (key.equals(cstate)) {
                    JSONObject value = (JSONObject) baseJsonResponse.get(key);
                    JSONObject districtData = value.getJSONObject("districtData");
                    Iterator<String> iterDistrict = districtData.keys();
                    while (iterDistrict.hasNext()) {
                        String districtKey = iterDistrict.next();
                        JSONObject data = districtData.getJSONObject(districtKey);
                        int count = data.getInt("confirmed");
                        locations.add(new Location(count, districtKey));
                    }
                }

            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JsonStateData JSON results", e);
        }

        return locations;

    }


    public static List<Location> fetchCovidCountryData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link Earthquake}s
        return extractFromJsonCountryData(jsonResponse);
    }

    private static List<Location> extractFromJsonCountryData(String covidJson) {

        if (TextUtils.isEmpty(covidJson)) {
            return null;
        }

        List<Location> locationsStates = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(covidJson);
            JSONArray statesArray = baseJsonResponse.getJSONArray("statewise");

            for (int i = 0; i < statesArray.length(); i++) {
                JSONObject state = statesArray.getJSONObject(i);
                if (!state.getString("state").equals("Total")) {
                    locationsStates.add(new Location(state.getInt("confirmed"), state.getString("state")));
                }
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JsonCountryData JSON results", e);
        }

        return locationsStates;

    }

    public static List<Integer> fetchStateBarData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link Earthquake}s
        return extractFromJsonStateBarData(jsonResponse);
    }

    private static List<Integer> extractFromJsonStateBarData(String covidJson) {

        if (TextUtils.isEmpty(covidJson)) {
            return null;
        }


        List<Integer> data = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(covidJson);
            JSONArray stateswise = baseJsonResponse.getJSONArray("statewise");

            for (int i = 0; i < stateswise.length(); i++) {
                JSONObject state = stateswise.getJSONObject(i);
                if (state.getString("state").equals(MainActivity.mSelectedState)) {
                    data.add(state.getInt("confirmed"));
                    data.add(state.getJSONObject("delta").getInt("confirmed"));
                    data.add(state.getInt("recovered"));
                    data.add(state.getJSONObject("delta").getInt("recovered"));
                    data.add(state.getInt("deaths"));
                    data.add(state.getJSONObject("delta").getInt("deaths"));
                    data.add(getDateIntFromString(state.getString("lastupdatedtime")));
                    data.add(getTimeIntFromString(state.getString("lastupdatedtime")));
                }
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JsonStateBarData JSON results", e);
        }
        return data;

    }


    public static List<Integer> fetchCountryBarData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link Earthquake}s
        return extractFromJsonCountryBarData(jsonResponse);
    }

    private static List<Integer> extractFromJsonCountryBarData(String covidJson) {

        if (TextUtils.isEmpty(covidJson)) {
            return null;
        }

        List<Integer> data = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(covidJson);
            JSONArray values = baseJsonResponse.getJSONArray("statewise");
            JSONArray deltaValues = baseJsonResponse.getJSONArray("key_values");
            JSONObject countryStatusData = values.getJSONObject(0);
            JSONObject countryStatusDeltaData = deltaValues.getJSONObject(0);
            data.add(countryStatusData.getInt("confirmed"));
            data.add(countryStatusDeltaData.getInt("confirmeddelta"));
            data.add(countryStatusData.getInt("recovered"));
            data.add(countryStatusDeltaData.getInt("recovereddelta"));
            data.add(countryStatusData.getInt("deaths"));
            data.add(countryStatusDeltaData.getInt("deceaseddelta"));
            data.add(getDateIntFromString(countryStatusDeltaData.getString("lastupdatedtime")));
            data.add(getTimeIntFromString(countryStatusDeltaData.getString("lastupdatedtime")));

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JsonCountryBarData JSON results", e);
        }
        return data;

    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static int getDateIntFromString(String time) {
        int count = 0;
        StringBuilder newTime = new StringBuilder();
        for (int i = 0; i < time.length(); i++) {
            if (count < 8 && Character.isDigit(time.charAt(i))) {
                count++;
                newTime.append(time.charAt(i));
            }
        }

        return Integer.parseInt(newTime.toString());
    }

    private static int getTimeIntFromString(String time) {
        StringBuilder newTime = new StringBuilder();
        for (int i = 11; i < time.length() - 2; i++) {
            if (Character.isDigit(time.charAt(i))) {
                newTime.append(time.charAt(i));
            }
        }

        return Integer.parseInt(newTime.toString());
    }

    public static String getTimeString(String date, String time) {
        StringBuilder newDate = new StringBuilder();

        try {
            StringBuilder timeFormatted = new StringBuilder(time);
            if (date.length() == 8) {
                if (time.length() == 3)
                    timeFormatted.insert(0, '0');

                int n = Integer.parseInt(date.substring(2, 4));
                newDate.append(date.charAt(0))
                        .append(date.charAt(1))
                        .append(' ')
                        .append(new DateFormatSymbols().getMonths()[n - 1])
                        .append(" ").append(date.substring(4))
                        .append(" - ")
                        .append(timeFormatted, 0, 2).append(":")
                        .append(timeFormatted, 2, 4);

            } else {
                if (time.length() == 3)
                    timeFormatted.insert(0, '0');

                Log.d("utils", date + " " + time + " formated time " + timeFormatted);
                int n = Integer.parseInt(date.substring(1, 3));
                newDate.append('0')
                        .append(date.charAt(0))
                        .append(' ')
                        .append(new DateFormatSymbols().getMonths()[n - 1])
                        .append(" ").append(date.substring(3))
                        .append(" - ")
                        .append(timeFormatted, 0, 2).append(":")
                        .append(timeFormatted, 2, 4);

            }
        }catch (Exception e){
            newDate.append("");
            Log.e("QueryUtils", "Time and date error" + e);
        }


        return newDate.toString();
    }


}
