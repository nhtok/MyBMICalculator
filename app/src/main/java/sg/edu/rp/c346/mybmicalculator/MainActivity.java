package sg.edu.rp.c346.mybmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCal, btnClear,btnDetail;
    TextView tvDatetime;
    TextView tvBMI;
    TextView tvBMIResult;
    float bmi;

    //Supporting ListView
    //ListView lvBMI;
    //ArrayList<String> bmiList = new ArrayList<String>();
    //ArrayAdapter<String> aaBMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = (EditText) findViewById(R.id.editTextWeight);
        etHeight = (EditText) findViewById(R.id.editTextHeight);

        btnCal = (Button) findViewById(R.id.buttonCalculate);
        btnClear = (Button)findViewById(R.id.buttonClearPast);
        btnDetail = (Button)findViewById(R.id.buttonDetail);

        tvDatetime = (TextView)findViewById(R.id.textViewDatetime);
        tvBMI = (TextView)findViewById(R.id.textViewBMI);
        tvBMIResult = (TextView)findViewById(R.id.textViewBMIResult);

        //btnDetail.setEnabled(false);
        bmi = 0;
        //Supporting ListView
        //lvBMI = (ListView)findViewById(R.id.listViewBMI);


        //Supporting ListView
        /*
        aaBMI = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bmiList);

        lvBMI.setAdapter(aaBMI);
           */
        btnCal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String strWeight = etWeight.getText().toString();
                String strHeight = etHeight.getText().toString();

                float weight = Float.parseFloat(strWeight);
                float height = Float.parseFloat(strHeight);



                bmi = weight / (height * height);

                //Supporting ListView
                //bmiList.add(0,String.format("%.5f", bmi)); //insert as the first element
                //aaBMI.notifyDataSetChanged();

                Calendar now = Calendar.getInstance();

                String datetime = now.get(Calendar.DAY_OF_MONTH)+ "/"+
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE);
                tvDatetime.setText(datetime);
                tvBMI.setText(String.format("%.3f", bmi)+"");
                etHeight.setText("");
                etWeight.setText("");
                //btnDetail.setEnabled(true);


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Supporting ListView
                //bmiList.clear();
                //aaBMI.notifyDataSetChanged();

                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor edit = prefs.edit();
                edit.clear();
                edit.commit();

                tvDatetime.setText("");
                tvBMI.setText("");
                tvBMIResult.setText("");
                bmi=0;

            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getBaseContext(), ResultActivity.class);
                intent.putExtra("bmi", bmi);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        String dateTimeString = tvDatetime.getText().toString();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("datetime",dateTimeString);
        edit.putFloat("lastBMIValue",(float)bmi);
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String datetimeString = prefs.getString("datetime","");
        float lastBMIValue = prefs.getFloat("lastBMIValue",0);
        tvDatetime.setText(datetimeString);
        tvBMI.setText(lastBMIValue+"");
        bmi = lastBMIValue;

    }

}
