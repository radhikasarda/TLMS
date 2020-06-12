package com.example.tlms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResultListener {

    private TextView tvTitle;
    public Button b;
    public EditText pass;
    public Spinner sp_user;
    private ArrayList<String> users;
    private JSONArray result;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.titletextView);
        tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        pass = (EditText) findViewById(R.id.editText);
        pass.setTransformationMethod(new PasswordTransformationMethod());

        sp_user = (Spinner) findViewById(R.id.spinner);

        b = (Button) findViewById(R.id.button);
        builder = new AlertDialog.Builder(this);
        users = new ArrayList<String>();
        users.add("Select UID Here");
        getData();

        sp_user.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();


                        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {


                    }

                }
        );


    }


    public void setResult(String result) {

        if (result.contains("fail") || result.contains("error")) {
            builder.setMessage("Invalid Username or Password !!. TRY AGAIN");
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Login Status");
            alert.show();

        } else {
            String uid = result.toString();
            //String selected_user = sp_user.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            //intent.putExtra("message", circle_names);
            intent.putExtra("user",uid);
            startActivity(intent);

        }


    }


    private void getData() {
        //Creating a string request
        String Url = BuildConfig.SERVER_URL + "login1.php";
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject j = null;
                        try {


                            JSONArray j = new JSONArray((response));


                            getUsers(j);
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

    private void getUsers(JSONArray j) {
        //Traversing through all the items in the json array

        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                String uname = "uid";
                //Adding the name of the student to array list

                users.add(json.getString(uname));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        sp_user.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, users));
    }

    public void OnLogin(View view) {
        String username = sp_user.getSelectedItem().toString();
        String password = pass.getText().toString();

        if (!username.toLowerCase().contains("select") && !password.isEmpty()) {

            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this, this);
            backgroundWorker.execute(type, username, password);
        } else {
            Toast.makeText(MainActivity.this, "No UID selected OR Password Empty!!", Toast.LENGTH_LONG).show();
        }


    }


}

