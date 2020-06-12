package com.example.tlms;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewRegistration extends AppCompatActivity  {

    public TextView tvTitle;
    public EditText edit_text_pan_no,edit_text_gst_no,edit_text_contact_no,edit_text_trade_owner_name,edit_text_business_holding_name,edit_text_email,edit_text_address;
    String pan_no,gst_no,contact_no,email,correspondance_address,trade_owner_name,business_holding_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);

        tvTitle = (TextView) findViewById(R.id.textView5);
        tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        edit_text_pan_no = (EditText) findViewById(R.id.editTextTextPersonName);
        edit_text_gst_no = (EditText) findViewById(R.id.editTextTextPersonName4);
        edit_text_contact_no = (EditText) findViewById(R.id.editTextTextPersonName7);
        edit_text_trade_owner_name= (EditText) findViewById(R.id.editTextTextPersonName2);
        edit_text_business_holding_name = (EditText) findViewById(R.id.editTextTextPersonName3);
        edit_text_email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        edit_text_address = (EditText) findViewById(R.id.editTextTextMultiLine);


    }

    public void OnClickNext(View view){
        Log.e("Inside Next","Inside On click NEXT############################################################");
            pan_no = edit_text_pan_no.getText().toString();
            gst_no = edit_text_gst_no.getText().toString();
            contact_no = edit_text_contact_no.getText().toString();
            trade_owner_name = edit_text_trade_owner_name.getText().toString();
            business_holding_name = edit_text_business_holding_name.getText().toString();
            email = edit_text_email.getText().toString();
            correspondance_address = edit_text_address.getText().toString();

            if(pan_no.isEmpty() || gst_no.isEmpty() || contact_no.isEmpty() || trade_owner_name.isEmpty() || business_holding_name.isEmpty()){

                Toast.makeText(NewRegistration.this, "Some fields are Empty!!", Toast.LENGTH_LONG).show();
            }

            else
            {
                Intent intent = new Intent(NewRegistration.this,NewRegistration2.class);
                intent.putExtra("pan_no",pan_no);
                intent.putExtra("gst_no",gst_no);
                Log.e("Contact",contact_no+"***"+"############################################################");
                intent.putExtra("contact",contact_no);
                intent.putExtra("trade_owner_name",trade_owner_name);
                intent.putExtra("business_holding_name",business_holding_name);
                intent.putExtra("email",email);
                intent.putExtra("correspondance_address",correspondance_address);
                Log.e("inside else","Intent created #####################################");
                startActivity(intent);

                //setContentView(R.layout.activity_new_registration2);

            }
    }




}
