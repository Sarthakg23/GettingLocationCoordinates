package com.example.gettinglocationcoordinates;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference driverref;
    String phone;
    LatLng latlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i=getIntent();
        phone=i.getStringExtra("phone");
        Toast.makeText(getApplicationContext(),phone, Toast.LENGTH_SHORT).show();
        driverref = FirebaseDatabase.getInstance().getReference("drivers");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        driverref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot drivers:dataSnapshot.getChildren()) {
                    Driver driver = drivers.getValue(Driver.class);
                    Toast.makeText(getApplicationContext(),driver.getPhone()+driver.getName(),Toast.LENGTH_LONG).show();
                    if(driver.getPhone().compareTo(phone)==0) {
                        double lat = driver.getLat();
                        double lng = driver.getLng();
                        String res=Double.toString(lat)+" "+Double.toString(lng);
                        Toast.makeText(getApplicationContext(),res+driver.getName(),Toast.LENGTH_SHORT).show();
                        latlng = new LatLng(lat, lng);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,15));

                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
