package com.example.gettinglocationcoordinates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    DatabaseReference userRef;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView txtLat = (TextView) findViewById(R.id.textView3);

        Intent i=getIntent();
        key=i.getStringExtra("mobile");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPSenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);



        if (isGPSenabled) {

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Toast.makeText(getApplicationContext(),"On Location Changed Called",Toast.LENGTH_SHORT).show();
                    TextView txtLat = (TextView) findViewById(R.id.textView3);
                    txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());


                    userRef = FirebaseDatabase.getInstance().getReference("drivers").child(key);

                    userRef.child("lat").setValue(location.getLatitude());

                    userRef.child("lng").setValue(location.getLongitude());

                    userRef.child("status").setValue("Online");


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                TextView txtLat = (TextView) findViewById(R.id.textView3);
                txtLat.setText("Getting Location");
            }
        } else {
            TextView txtLat = (TextView) findViewById(R.id.textView3);
            txtLat.setText("Access Not Granted");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.values,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                userRef.child("status").setValue("Offline");
                startActivity(new Intent(getApplicationContext(), Ask_User_type.class));
                finish();
                return true;
        }
        return false;

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"On Destroy Called",Toast.LENGTH_SHORT).show();
        userRef.child("status").setValue("Offline");
    }
}
