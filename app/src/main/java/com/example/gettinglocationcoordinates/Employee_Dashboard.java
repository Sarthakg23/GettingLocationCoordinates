package com.example.gettinglocationcoordinates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Employee_Dashboard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__dashboard);
        //Toast.makeText(this, "On create", Toast.LENGTH_SHORT).show();

        final List<String> list=new ArrayList<String>();
        final List<String> list_phone=new ArrayList<String>();

        final ListView lv=(ListView)findViewById(R.id.lv);

        DatabaseReference driverref = FirebaseDatabase.getInstance().getReference("drivers");
        driverref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot drivers:dataSnapshot.getChildren())
                {
                    Driver driver =drivers.getValue(Driver.class);
                   // Toast.makeText(getApplicationContext(), driver.getPhone()+driver.getStatus(), Toast.LENGTH_SHORT).show();
                    if(driver.getStatus().compareTo("Online")==0) {
                        //Toast.makeText(getApplicationContext(), "Inside Found Online", Toast.LENGTH_SHORT).show();
                        list.add(driver.getName() +" "+ driver.getPhone());
                        list_phone.add(driver.getPhone()); }

                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Employee_Dashboard.this,android.R.layout.simple_list_item_1,list);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                        intent.putExtra("phone",list_phone.get(i));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
