package sg.edu.rp.c346.mybmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    TextView tvBMIResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvBMIResult = (TextView) findViewById(R.id.textViewBMIResult);

        Intent intentReceved = getIntent();
        float bmi = intentReceved.getFloatExtra("bmi",0);
        displayBMIResult(bmi);

    }



    private void displayBMIResult(float bmi){

        if (bmi==0.0) {
            tvBMIResult.setText("");
        } else if (bmi < 18.5) {
            tvBMIResult.setText("You are Underweight");
        }else if (bmi >= 18.5 && bmi <25) {
            tvBMIResult.setText("Your BMI is normal");
        }else if (bmi >= 25 && bmi <30) {
            tvBMIResult.setText("You are Overweight!");
        }else if (bmi >= 30) {
            tvBMIResult.setText("You are Obsese!");
        }else{
            tvBMIResult.setText("Error in calculation");
        }
    }
}
