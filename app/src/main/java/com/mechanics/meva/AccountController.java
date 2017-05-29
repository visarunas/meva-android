package com.mechanics.meva;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Justas on 29/05/2017.
 */

public class AccountController {
    public AccountController(){

    }

    public Account getAccount(String username){
        Account acc = new Account();
        final String name = username;
        final String url = getApplicationContext().getString(R.string.MevaWebIP) + "Account/GetAccount";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] responseHolder = new Object[1];

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);

        JsonObjectRequest postRequest = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        countDownLatch.countDown();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        countDownLatch.countDown();
                    }
                }
        );
        queue.add(postRequest);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (responseHolder[0] instanceof VolleyError) {
            final VolleyError volleyError = (VolleyError) responseHolder[0];
            Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            return acc;
        } else {
            final JSONObject response = (JSONObject) responseHolder[0];
            try {
                acc.firstName = response.getString("firstName");
                acc.lastName = response.getString("lastName");
                acc.email = response.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return acc;
    }

    public ArrayList<Account> getAllAccounts(){
        final ArrayList<Account> accounts = new ArrayList<Account>();
        final String url = getApplicationContext().getString(R.string.MevaWebIP) + "Account/GetAllAccounts";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] responseHolder = new Object[1];
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                        countDownLatch.countDown();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        countDownLatch.countDown();
                    }
                }
        );

        queue.add(getRequest);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (responseHolder[0] instanceof VolleyError) {
            final VolleyError volleyError = (VolleyError) responseHolder[0];
            //TODO: Handle error...
        } else {
            final JSONArray response = (JSONArray) responseHolder[0];
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            try {
                for (int i = 0; i < (response != null ? response.length() : 0);
                     jsonObjects.add(response.getJSONObject(i++)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (JSONObject temp : jsonObjects) {
                Account ac = new Account();
                try {
                    ac.firstName = temp.getString("firstName");
                    ac.lastName = temp.getString("lastName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                accounts.add(ac);
            }
        }
        return accounts;
    }
}
