package com.example.tlms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewRegistration2 extends AppCompatActivity implements ResultListener{

    public EditText edit_text_property_no,edit_text_ward_no,edit_text_locality;
    String property_no,locality,ward_no,property_type;
    public Spinner sp_property_type;
    String pan_no,gst_no,contact,email,correspondance_address,trade_owner_name,business_holding_name;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("inside oncreate","Reg2 created #####################################");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration2);

        edit_text_property_no = (EditText) findViewById(R.id.editTextTextPersonName5);
        edit_text_locality = (EditText) findViewById(R.id.editTextTextPersonName6);
        edit_text_ward_no = (EditText) findViewById(R.id.editTextNumber);
        sp_property_type = (Spinner) findViewById(R.id.spinner2);
        builder = new AlertDialog.Builder(this);

        sp_property_type.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        property_type = parent.getItemAtPosition(position).toString();


                        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {


                    }

                }
        );
    }

    public void OnClickSubmit(View view)
    {
        property_no = edit_text_property_no.getText().toString();
        locality = edit_text_locality.getText().toString();
        //To be changed to spinner later
        ward_no = edit_text_ward_no.getText().toString();
        Log.e("inside submit","submit created #####################################");
        if(property_no.isEmpty() || locality.isEmpty() || ward_no.isEmpty() || property_type.isEmpty()){
            Log.e("inside if","submit created #####################################");

            Toast.makeText(NewRegistration2.this, "Some fields are Empty!!", Toast.LENGTH_LONG).show();
        }
        else{
            Log.e("inside else","submit created #####################################");
            Bundle bundle = getIntent().getExtras();
            pan_no = bundle.getString("pan_no");
            gst_no = bundle.getString("gst_no");
            contact = bundle.getString("contact");
            trade_owner_name = bundle.getString("trade_owner_name");
            business_holding_name = bundle.getString("business_holding_name");
            email = bundle.getString("email");
            correspondance_address = bundle.getString("correspondance_address");

            String type = "new_registration";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this, this);
            backgroundWorker.execute(type, pan_no, gst_no,contact,trade_owner_name,business_holding_name,email,correspondance_address,property_no,locality,ward_no,property_type);
        }
    }

    @Override
    public void setResult(String result)
    {
            Log.e("inside result","submit #####################################");

            builder.setMessage(result);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    launchIntent();
                }
            });
            AlertDialog alert = builder.create();
            alert.setTitle("Registration Status");

            alert.show();

    }

    public void launchIntent(){

        Intent intent = new Intent(NewRegistration2.this,LoginActivity.class);
        startActivity(intent);
    }
}
