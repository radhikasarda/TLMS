package com.example.tlms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Invoice extends AppCompatActivity implements View.OnClickListener {

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

        from_date = (EditText) findViewById(R.id.editTextDate);


        from_date.setOnClickListener(this);
        //to_date.setOnClickListener(this);



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

    @Override
    public void onClick(View v) {

        //Log.e("V",v+"###################################################################");
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            from_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

    }
}
