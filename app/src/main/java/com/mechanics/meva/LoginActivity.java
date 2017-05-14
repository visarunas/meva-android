package com.mechanics.meva;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

import static com.mechanics.meva.R.id.editName;
import static com.mechanics.meva.R.id.editPassword;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.editName);
                String username = text.getText().toString();

                EditText text2 = (EditText)findViewById(R.id.editPassword);
                String password = text.getText().toString();

                postData(username, password);
            }
        });

    }

    public void postData(String username, String password) {
        URL url = null;
        HttpURLConnection client = null;


        try {
            url = new URL(getString(R.string.MevaWebIP));
            client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("POST");
            client.setRequestProperty("USERNAME", username);
            client.setRequestProperty("PASSWORD", password);

            client.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(client.getOutputStream());


            dStream.flush();
            dStream.close();

        }
        catch(MalformedURLException error) {

        }
        catch(SocketTimeoutException error) {

        }
        catch (IOException error) {

        }



    }

}
