package com.example.gettinglocationcoordinates;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupDriver extends AppCompatActivity {

    private EditText editTextMobile,name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_driver);

        editTextMobile = findViewById(R.id.phone);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "on click", Toast.LENGTH_SHORT).show();

                String mobile = editTextMobile.getText().toString().trim();
                String n=name.getText().toString();
                String e=email.getText().toString();
                String p=password.getText().toString();

                if(mobile.isEmpty() || mobile.length() < 10) {
                    Toast.makeText(getApplicationContext(), "inside if", Toast.LENGTH_SHORT).show();
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    Toast.makeText(getApplicationContext(), "wrong", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "inside else", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupDriver.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("name", n);
                    intent.putExtra("email", e);
                    intent.putExtra("password", p);
                    intent.putExtra("previous_activity", "signup");
                    Toast.makeText(getApplicationContext(), "starting intent", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


        TextView login = (TextView)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Login_Driver.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
    }

}