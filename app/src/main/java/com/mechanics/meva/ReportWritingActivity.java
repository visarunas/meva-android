package com.mechanics.meva;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.TimeoutException;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReportWritingActivity extends AppCompatActivity {
    EditText message;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_writing);
        message = (EditText)findViewById(R.id.reportEditText);
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    public void sendReport(View view){


        ReportController reporter = new ReportController();

        Report report = new Report(message.getText().toString());

        try{
            prgDialog.show();
            reporter.sendReport(report);
            prgDialog.hide();
            Toast.makeText(getApplicationContext(), "Nusiskundimas nusiųstas!", Toast.LENGTH_LONG).show();
        }
        catch (JSONException ex){
            prgDialog.hide();
            Toast.makeText(getApplicationContext(), "Nusiskundimo nusiųsti nepavyko!", Toast.LENGTH_LONG).show();
        }
        finally {
            this.finish();
        }
    }
}
