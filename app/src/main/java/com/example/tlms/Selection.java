package com.example.tlms;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Selection extends AppCompatActivity {
    public Spinner sp_trade_types;
    private ArrayList<String> trade_types;
    public Button btn_next,btn_add;
    TableLayout selection_layout;
    TextView textView_title;
    AlertDialog.Builder builder;
    RadioButton radioButton;
    RadioGroup radioGroup;
    List<String> selected_trade_list;
    String selected_application_type;
    Intent intent;
    Spinner sp_fiscal_year;
    private ArrayList<String> fiscal_year_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("inside oncreate", "Selection created #####################################");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        selection_layout = (TableLayout) findViewById(R.id.selection_layout);
        sp_trade_types = (Spinner) findViewById(R.id.spinner3);
        textView_title = (TextView) findViewById(R.id.textView21);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        textView_title.setPaintFlags(textView_title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_next = (Button) findViewById(R.id.button4);
        btn_add = (Button) findViewById(R.id.button6);


        selected_trade_list = new ArrayList<>();
        builder = new AlertDialog.Builder(this);
        trade_types = new ArrayList<String>();
        trade_types.add("Select Trade Type");
        getData();


        //ViewGroup viewGroup = findViewById(android.R.id.content);
        //View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_invoice, viewGroup, false);
        //sp_fiscal_year = dialogView.findViewById(R.id.spinner4);
        //sp_fiscal_year.setAdapter(new ArrayAdapter<String>(Selection.this, android.R.layout.simple_spinner_dropdown_item, fiscal_year_list));
        //radioButton_new = (RadioButton) findViewById(R.id.radioButton);
        //radioButton_renew = (RadioButton) findViewById(R.id.radioButton2);


    }


    private void  getData(){
        //Creating a string request
        String Url = BuildConfig.SERVER_URL + "getTradeTypes.php";
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject j = null;
                        try {


                            JSONArray j = new JSONArray((response));


                            getTradeTypes(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error","volley error#########################################"+error);
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getTradeTypes(JSONArray j) {
        //Traversing through all the items in the json array

        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                String uname = "name_of_trade";
                //Adding the name of the student to array list

                trade_types.add(json.getString(uname));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        sp_trade_types.setAdapter(new ArrayAdapter<String>(Selection.this, android.R.layout.simple_spinner_dropdown_item, trade_types));
    }

    public void onClickAdd(View view)
    {
        String selected = sp_trade_types.getSelectedItem().toString();
        if(!selected.contains("Select"))
        {
            selection_layout.addView(createNewCheckbox(selected));
            selected_trade_list.add(selected);
        }
    }

    public void update_string_array(String unchecked)
    {
        Log.e("inside update unchecked",unchecked+"############################inside update");
        if(selected_trade_list.contains(unchecked)){
            Log.e("inside update if",unchecked+"############################inside update");
            selected_trade_list.remove(unchecked);
        }
    }

    public void onClickNext(View view)
    {
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1)
        {
            Toast.makeText(Selection.this, "Please Select Application Type!!", Toast.LENGTH_LONG).show();
        }else if(selected_trade_list.isEmpty())
        {
            Toast.makeText(Selection.this, "Please Select Trade Type!!", Toast.LENGTH_LONG).show();
        }

        else{

            // find the radiobutton by returned id
            radioButton = (RadioButton) findViewById(selectedId);
            selected_application_type = radioButton.getText().toString();

            intent = new Intent(Selection.this, Invoice.class);
            Bundle bundle = getIntent().getExtras();
            intent.putExtra("pan_no",bundle.getString("pan_no"));
            intent.putExtra("gst_no",bundle.getString("gst_no"));
            intent.putExtra("contact",bundle.getString("contact"));
            intent.putExtra("trade_owner_name", bundle.getString("trade_owner_name"));
            intent.putExtra("business_holding_name",bundle.getString("business_holding_name"));
            intent.putExtra("email", bundle.getString("email"));
            intent.putExtra("correspondance_address",bundle.getString("correspondance_address"));
            intent.putExtra("property_no",bundle.getString("property_no"));
            intent.putExtra("property_type",bundle.getString("property_type"));
            intent.putExtra("ward_no",bundle.getString("ward_no"));
            intent.putExtra("locality",bundle.getString("locality"));
            intent.putExtra("selected_application_type", selected_application_type);
            intent.putExtra("selected_trade_list", selected_trade_list.toString());
            intent.putExtras(bundle);
            //startActivity(intent);

            if(!selected_application_type.equals("New")){
                //if renewal
                //create fiscal year list
                fiscal_year_list = new ArrayList<String>();
                fiscal_year_list.add("Select");
                fiscal_year_list.add("2011-12");
                fiscal_year_list.add("2012-13");
                fiscal_year_list.add("2013-14");
                fiscal_year_list.add("2014-15");
                fiscal_year_list.add("2015-16");
                fiscal_year_list.add("2016-17");
                fiscal_year_list.add("2017-18");
                fiscal_year_list.add("2018-19");
                fiscal_year_list.add("2019-20");
                fiscal_year_list.add("2020-21");

                AlertDialog.Builder builder = new AlertDialog.Builder(Selection.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_invoice, viewGroup, false);
                sp_fiscal_year = dialogView.findViewById(R.id.spinner4);
                sp_fiscal_year.setAdapter(new ArrayAdapter<String>(Selection.this, android.R.layout.simple_spinner_dropdown_item, fiscal_year_list));
                builder.setView(dialogView);
                Button dialogButton = (Button) dialogView.findViewById(R.id.button7);


                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dialog.dismiss();
                        if(sp_fiscal_year.getSelectedItem().toString().toLowerCase().contains("select"))
                        {
                            Toast.makeText(Selection.this, "No Period selected!!", Toast.LENGTH_LONG).show();
                        }else{
                            intent.putExtra("selected_period",sp_fiscal_year.getSelectedItem().toString());
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
            else{
                startActivity(intent);
            }
        }
    }

    private CheckBox createNewCheckbox(String text)
    {
        final ConstraintLayout.LayoutParams lparams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        final CheckBox checkbox = new CheckBox(this);
        checkbox.setLayoutParams(lparams);
        checkbox.setChecked(true);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                update_string_array(checkbox.getText().toString());
                checkbox.setVisibility(View.GONE);
            }
        });
        checkbox.setText(text);
        return checkbox;
    }
}
