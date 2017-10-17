package com.example.usamanaseer.googlemap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.common.api.*;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearestPlacesActivity extends AppCompatActivity {
    LatLng loc;
    private FusedLocationProviderClient mFusedLocationClient;
    ImageButton mbank, mhospital, mresturant, mschool, mmall, mmosque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_places);
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
        mbank = (ImageButton) findViewById(R.id.img_bank);
        mbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                startActivity(i);
                i.putExtra("type",1);

        /*        if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","bank");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });
                                }
                            }
                        });*/


            }
        });

        mhospital = (ImageButton) findViewById(R.id.img_hospital);
        mhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                                    startActivity(i);
                                    i.putExtra("type",0);

                                /*    loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","hospital");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });*/
                                }
                            }
                        });


            }
        });

        mresturant = (ImageButton) findViewById(R.id.img_restaurant);
        mresturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                                    startActivity(i);
                                    i.putExtra("type",4);

                                    /*loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","restaurant");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });*/
                                }
                            }
                        });


            }
        });


        mschool = (ImageButton) findViewById(R.id.img_school);
        mschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object

                                    Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                                    startActivity(i);
                                    i.putExtra("type",5);


                                  /*  loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","school");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });*/
                                }
                            }
                        });


            }
        });


        mmall = (ImageButton) findViewById(R.id.img_mall);
        mmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                                    startActivity(i);
                                    i.putExtra("type",2);


                                  /*  loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","shopping_mall");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });*/
                                }
                            }
                        });


            }
        });


        mmosque = (ImageButton) findViewById(R.id.img_mosque);
        mmosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearestPlacesActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(NearestPlacesActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    Intent i = new Intent(NearestPlacesActivity.this,NearestPlacesMapActivity.class);
                                    startActivity(i);
                                    i.putExtra("type",3);


                                   /* loc = new LatLng(location.getLatitude(), location.getLongitude());
                                    RetrofitInterface retrofit11 = RetrofitBuilder.retrofit.create(RetrofitInterface.class);
                                    Map<String,String> options = new HashMap<String, String>();
                                    options.put("location",loc.latitude +"," + loc.longitude);
                                    options.put("radius","1000");
                                    options.put("type","mosque");
                                    options.put("key","AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo");

                                    Call<PlacesModel> call = retrofit11.placeslist(options);

                                    call.enqueue(new Callback<PlacesModel>() {
                                        @Override
                                        public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                                            PlacesModel mPlaces = response.body();
                                            List<Result> mRes = mPlaces.getResults();

                                            LinearLayout lL1 = (LinearLayout) findViewById(R.id.ll_forPlaces);
                                            lL1.setVisibility(View.VISIBLE);
                                            LinearLayout lL2 = (LinearLayout) findViewById(R.id.ll_withoutPlaces);
                                            lL2.setVisibility(View.GONE);
                                            ListView l1 = (ListView)findViewById(R.id.lv_forPlaces);
                                            PlacesAdapter placesAdapter = new PlacesAdapter(getApplicationContext(),R.layout.item_places,mRes);
                                            l1.setAdapter(placesAdapter);

                                        }

                                        @Override
                                        public void onFailure(Call<PlacesModel> call, Throwable t) {
                                        }
                                    });*/
                                }
                            }
                        });


            }
        });



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
