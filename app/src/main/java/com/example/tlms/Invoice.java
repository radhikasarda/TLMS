package com.example.tlms;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class Invoice extends AppCompatActivity{

    EditText from_date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String selected_application_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("inside oncreate", "Invoice created #####################################");
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        selected_application_type = bundle.getString("selected_application_type");
        Log.e("APP TYPE---",selected_application_type+"------"+"#######################");

        setContentView(R.layout.activity_invoice);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        int nextYearYear = cal.get(Calendar.YEAR);
        int nextYearMonth = cal.get(Calendar.MONTH);
        int nextYearDay = cal.get(Calendar.DAY_OF_MONTH);
        //if(selected_application_type.equals("New")){

           // Intent intent = new Intent(Invoice.this,Invoice_generate.class);
            //intent.putExtras(bundle);
            //startActivity(intent);
            //Log.e("Inside IF","##################");
            //SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            //String currentDate = df.format(new Date());
            //from_date.setText(currentDate);
            //to_date.setText(nextYearDay + "-" + (nextYearMonth+1) + "-" + nextYearYear);

        //}else{

            //to_date.setText(nextYearDay + "-" + (nextYearMonth+1) + "-" + nextYearYear);

        //}
    }

}
