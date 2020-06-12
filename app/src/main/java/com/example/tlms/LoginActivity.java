package com.example.tlms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    public Button button_new_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_new_reg = (Button) findViewById(R.id.button2);

    }

    public void OnNewReg(View view) {

        Intent intent = new Intent(LoginActivity.this,NewRegistration.class);
        //intent.putExtra("message", circle_names);
        startActivity(intent);
    }


}