package com.mechanics.meva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void navigateToReportActivity(View view){
        Toast.makeText(getApplicationContext(), "Creating report", Toast.LENGTH_LONG).show();
    }
}
