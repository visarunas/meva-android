package com.mechanics.meva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URI;


public class LoginActivity extends Activity {

    ProgressDialog prgDialog;
    EditText emailET;
    EditText pwdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.editName);
        // Find Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.editPassword);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    /**
     * Method gets triggered when Login button is clicked
     *
     * @param view
     */
    public void loginUser(View view){
        // Get Email Edit View Value
        String email = emailET.getText().toString();
        // Get Password Edit View Value
        String password = pwdET.getText().toString();
        // Instantiate Http Request Param Object
        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid
            if(Utility.validate(email)){

                invokeWS();
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     */

    public void invokeWS(){
        // Show Progress Dialog
        prgDialog.show();
        String ip = getString(R.string.MevaWebIP);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://postman-echo.com/get?test=123";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        prgDialog.hide();
                        Log.d("Response", response.toString());
                        try {
                            JSONObject obj = response.getJSONObject("headers");

                            Toast.makeText(getApplicationContext(), obj.getString("host"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        prgDialog.hide();
                    }
                }
        );

        queue.add(getRequest);
    }

    public String getResult(Void... arg0){
        String result = "";
        try{

        }
        catch(Exception e){
            result = "connection error";
        }

        return result;
    }

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(), UserAreaActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


}
