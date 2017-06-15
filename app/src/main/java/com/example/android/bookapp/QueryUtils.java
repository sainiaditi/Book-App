package com.example.android.bookapp;

import android.support.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dell on 5/29/2017.
 */

public class QueryUtils {

    public static final String LOG_TAG = MainActivity.class.getName();

    public QueryUtils(){}

    public static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Bad response");
            }

        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @NonNull
    public static String readFromStream(InputStream inputStream) throws IOException {
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

    public static List<Book> fetchBookData(String Url) {

        URL url = QueryUtils.createUrl(Url);
        String jsonResponse = "";
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            // TODO Handle the IOException
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);
        Log.i("Return", "list returned");
        return books;

    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON))
            return null;
        List<Book> books = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(bookJSON);
            JSONArray items = root.optJSONArray("items");
            if (items == null)
                return null;
            for (int i = 0; i < (items.length() > 10 ? 10 : items.length()); i++) {
                JSONObject book = items.getJSONObject(i);
                JSONObject volInfo = book.getJSONObject("volumeInfo");
                String authors;
                if (volInfo.has("authors")) {
                    JSONArray authorArray = volInfo.getJSONArray("authors");
                    authors = authorArray.getString(0);
                } else {
                    authors = "anonymous";
                }

                String title = volInfo.getString("title");

                books.add(new Book(title, authors));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        return books;

    }


}
