package com.example.usamanaseer.googlemap;

import android.*;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearestPlacesMapActivity extends FragmentActivity implements OnMapReadyCallback {
    LatLng loc;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    String[] type = {"hospital","bank","shopping_mall","mosque","restaurant","school"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_places_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        if (ActivityCompat.checkSelfPermission(NearestPlacesMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(NearestPlacesMapActivity.this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            int type1 = getIntent().getIntExtra("type",0);

                            loc = new LatLng(location.getLatitude(),location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Karachi"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.latitude, loc.longitude), 15.0f));

                            RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                            Map<String,String> options = new HashMap<String, String>();
                            options.put("location",loc.latitude +"," + loc.longitude);
                            options.put("radius","1000");
                            options.put("type",type[type1]);
                            options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                            Call<PlacesModel> call = retrofit11.placeslist(options);

                            call.enqueue(new Callback<PlacesModel>() {
                                @Override
                                public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {


                                    PlacesModel mPlaces = response.body();
                                    List<Result> mRes = mPlaces.getResults();
                                    List<LatLng> AllNearPlaces = new ArrayList<LatLng>();
                                    for(int i=0;i<mRes.size();i++)
                                    {
                                        AllNearPlaces.add(new LatLng(mRes.get(i).getGeometry().getLocation().getLat(),mRes.get(i).getGeometry().getLocation().getLng()));
                                    }



                                    for(int i = 0 ; i < AllNearPlaces.size() ; i++ ) {

                                        createMarker(AllNearPlaces.get(i).latitude, AllNearPlaces.get(i).longitude, mRes.get(i).getName(), mRes.get(i).getVicinity());
                                    }

                                    /*LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                    lL1.setVisibility(View.VISIBLE);
                                    LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                    lL2.setVisibility(View.GONE);
                                    ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                    PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                    l1.setAdapter(placesAdapter);*/

                                }

                                @Override
                                public void onFailure(Call<PlacesModel> call, Throwable t) {
                                }
                            });
                        }
                        }});


    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

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
}
