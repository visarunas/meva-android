package com.mechanics.meva;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Justas on 29/05/2017.
 */

public class ReportController {
    ProgressDialog prgDialog;
    public ReportController(){

    }

    public void sendReport(Report report) throws JSONException {
        //prgDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://10.3.8.45:45455/api/Account/SendReport";

        JsonObjectRequest postRequest = new JsonObjectRequest(url, new JSONObject(reportToJSON(report)),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        //prgDialog.hide();
                        try {
                            if(response.getString("Status").equals("OK")){
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        //prgDialog.hide();
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
        );
        queue.add(postRequest);
    }

    public String reportToJSON(Report report){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", report.ID);
            jsonObject.put("isRead", report.isRead);
            jsonObject.put("isMarked", report.isMarked);
            jsonObject.put("cause", report.cause);
            jsonObject.put("account", report.account);
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }
}