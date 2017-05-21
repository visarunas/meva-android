package com.mechanics.meva;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    //returns list of JSONObject of get request
    public static ArrayList<JSONObject> httpGet (String urlString)
    {
        ArrayList<JSONObject> jsonList = new ArrayList<> ();
        URL url;
        try {
            url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String response = "";
            while ((inputLine = reader.readLine()) != null) {
                response += inputLine;
            }
            reader.close();

            Object jsonType = new JSONTokener(response).nextValue();
            if (jsonType instanceof JSONObject)
                jsonList.add (new JSONObject (response));
            else if (jsonType instanceof JSONArray)
            {
                JSONArray jsonArray = new JSONArray (response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonList.add(jsonArray.getJSONObject(i));
                }
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Throwable e){
            e.printStackTrace();
        }
        return jsonList;
    }

    //returns true if success, flase if not
    public static boolean httpPost (String urlString, JSONObject infoToSend)
    {
        URL url;
        try {
            url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream outputStream = connection.getOutputStream ();
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write (getPostDataString (infoToSend));

            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode=connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
                return true;
            else
                return false;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    //returns json into string to post - string1=value1&string2=value2&....
    private static String getPostDataString(JSONObject params) throws Exception
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

}
