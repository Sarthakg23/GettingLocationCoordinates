package com.example.gettinglocationcoordinates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class Login_Driver extends AppCompatActivity {

    FirebaseUser firebaseUser;
    EditText number;

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseUser!=null)
        {
            Intent i=new Intent(getApplicationContext(),ProfileActivity.class);
            i.putExtra("phone",number.getText().toString().trim());
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__driver);

       number = (EditText)findViewById(R.id.ed);

        Button b = (Button)findViewById(R.id.b);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),VerifyPhoneActivity_LoginDriver.class);
                i.putExtra("phone",number.getText().toString().trim());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
