package com.example.usamanaseer.googlemap;

import android.app.Dialog;
import android.app.usage.UsageStatsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.zip.Inflater;

public class EntryActivity extends AppCompatActivity {
EditText e1,e2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E1 = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E2 = 2;
    LatLng l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Button destination = (Button) findViewById(R.id.b_currentlocation);
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button route = (Button) findViewById(R.id.b_route);
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View v1 = getLayoutInflater().inflate(R.layout.dialog_custom,null);

                final Dialog dialog = new Dialog(EntryActivity.this);
                dialog.setContentView(R.layout.dialog_custom);
                e1 =(EditText) dialog.findViewById(R.id.et_Startingpoint);
                e1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent intent =
                                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                            .build(EntryActivity.this);
                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_E1);
                        } catch (GooglePlayServicesRepairableException e) {
                            // TODO: Handle the error.
                        } catch (GooglePlayServicesNotAvailableException e) {
                            // TODO: Handle the error.
                        }

                    }
                });
                e2 =(EditText) dialog.findViewById(R.id.et_destinationpoint);
                e2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent intent =
                                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                            .build(EntryActivity.this);
                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_E2);
                        } catch (GooglePlayServicesRepairableException e) {
                            // TODO: Handle the error.
                        } catch (GooglePlayServicesNotAvailableException e) {
                            // TODO: Handle the error.
                        }

                    }
                });
                Button b1 = (Button)dialog.findViewById(R.id.b_ok);
                Button b2 = (Button)dialog.findViewById(R.id.b_cancel);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EntryActivity.this,RouteActivity.class);
                        i.putExtra("Latlng1",e1.getText().toString());
                        i.putExtra("Latlng2",e2.getText().toString());
                        startActivity(i);
                        finish();
                        //String s = e1.getText().toString() + " " + e2.getText().toString();
                        //Toast.makeText(EntryActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Button mnearestPlaces = (Button)findViewById(R.id.btn_nearestplaces);
        mnearestPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this,NearestPlacesActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                l1 = place.getLatLng();
                e1.setText(l1.latitude+","+l1.longitude);
                Log.i("Usama", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Usama", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E2) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                l1 = place.getLatLng();
                e2.setText(l1.latitude+","+l1.longitude);
                Log.i("Usama", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Usama", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
