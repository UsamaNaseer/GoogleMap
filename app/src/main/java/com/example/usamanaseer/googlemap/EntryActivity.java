package com.example.usamanaseer.googlemap;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.zip.Inflater;

public class EntryActivity extends AppCompatActivity {
EditText e1,e2;
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
                e2 =(EditText) dialog.findViewById(R.id.et_destinationpoint);
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
    }
}
