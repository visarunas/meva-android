package com.mechanics.meva;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {
    TextView firstNameTV;
    TextView lastNameTV;
    TextView emailTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //AccountController accController = new AccountController();
        //Account acc = accController.getAccount("10");
        //firstNameTV = (TextView)findViewById(R.id.nameTextView);
        //lastNameTV = (TextView)findViewById(R.id.lastNameTextView);
        //emailTV = (TextView)findViewById(R.id.emailTextView);

        //firstNameTV.setText(acc.firstName);
        //lastNameTV.setText(acc.lastName);
        //emailTV.setText(acc.email);
    }

    public void navigateToReportActivity(View view){
        Intent homeIntent = new Intent(getApplicationContext(), ReportWritingActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
