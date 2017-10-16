package com.example.usamanaseer.googlemap;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    String s,s1;
    LatLng l1,l2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                2000, mLocationListener);






        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Karachi"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20.0f));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        s = getIntent().getStringExtra("Latlng1");
        String[] words={};
        String[] words1 ={};
        if(s.contains(","))
        {
            words =  s.split(",");
            l1 = new LatLng(Double.parseDouble(words[0]), Double.parseDouble(words[1]));
        }
        else
        {
            s ="";
        }

        s1 = getIntent().getStringExtra("Latlng2");
        if(s1.contains(",")) {
            words1 = s1.split(",");
            l2 = new LatLng(Double.parseDouble(words1[0]), Double.parseDouble(words1[1]));
        }
        else
        {
            s1 = "";
        }

        if (l1!=null && l2!=null) {
            GoogleDirection.withServerKey("AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo")
                    .from(l1)
                    .to(l2)
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l1, 17.0f));
                                mMap.addMarker(new MarkerOptions().position(l1).title("Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                mMap.addMarker(new MarkerOptions().position(l2).title("Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                List<LatLng> ll2 = PolyUtil.decode(direction.getRouteList().get(0).getOverviewPolyline().getRawPointList());
                                mMap.addPolyline(new PolylineOptions().addAll(ll2).color(Color.RED).width(12));
                            } else {
                                Toast.makeText(getApplicationContext(),"No Direction Found",Toast.LENGTH_SHORT).show();
                                // Do something
                            }
                        }
                        @Override
                        public void onDirectionFailure(Throwable t) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l1, 17.0f));
                            Toast.makeText(getApplicationContext(),"Direction Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Karachi"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15.0f));
                            }
                        }
                    });
        }


    }
}
