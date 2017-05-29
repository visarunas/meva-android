package com.mechanics.meva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReportWritingActivity extends AppCompatActivity {
    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_writing);
        message = (EditText)findViewById(R.id.reportEditText);
    }

    public void sendReport(View view){
        ReportController reporter = new ReportController();

        Report report = new Report(message.getText().toString());

        try{
            reporter.sendReport(report);
            Toast.makeText(getApplicationContext(), "Nusiskundimas nusiųstas!", Toast.LENGTH_LONG).show();
        }
        catch (JSONException ex){
            Toast.makeText(getApplicationContext(), "Nusiskundimo nusiųsti nepavyko!", Toast.LENGTH_LONG).show();
        }
        finally {
            this.finish();
        }
    }
}
