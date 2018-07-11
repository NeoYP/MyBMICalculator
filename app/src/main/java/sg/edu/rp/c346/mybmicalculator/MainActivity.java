package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    TextView tvDate, tvBMI, tvOutcome;
    Button btnCal, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double bmi = Math.round((Double.parseDouble(etWeight.getText().toString()) / Double.parseDouble(etHeight.getText().toString()) / Double.parseDouble(etHeight.getText().toString()))*1000.0)/1000.0;
                String msg = Double.toString(bmi);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String text = "";
                String time = "";
                if (now.get(Calendar.HOUR_OF_DAY) > 12){
                    text = "pm";
                    time = Integer.toString(now.get(Calendar.HOUR_OF_DAY) - 12);
                }
                else{
                    text = "am";
                    time = Integer.toString(now.get(Calendar.HOUR_OF_DAY));
                }
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        time + ":" +
                        now.get(Calendar.MINUTE) + text;
                tvDate.append(datetime);
                tvBMI.append(msg);
                String output = "";
                if (bmi<18.5){
                    output = "You are underweight";
                }
                else if (bmi<25){
                    output = "Your BMI is normal";
                }
                else if (bmi<30){
                    output = "You are overweight";
                }
                else{
                    output = "You are obese";
                }
                tvOutcome.setText(output);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //Step 1c: Obtain an instance of the SharedPreferences Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();
                //Step 1d: Add the key value pair
                prefEdit.putString("bmi", msg);
                prefEdit.putString("datetime", datetime);
                prefEdit.putString("outcome", output);
                //Step 1e: Call commit() method to save the changes into the SharedPreferences
                prefEdit.commit();
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText(R.string.date);
                tvBMI.setText(R.string.bmi);
                etHeight.setText("");
                etWeight.setText("");
                tvOutcome.setText("");
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
        String msg = prefs.getString("bmi", "");
        String date = prefs.getString("datetime", "");
        String outcome = prefs.getString("outcome","");
        tvDate.append(date);
        tvBMI.append(msg);
        tvOutcome.setText(outcome);

    }
}