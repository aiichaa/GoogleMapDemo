package com.example.info.googlemapdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;


import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    DataBaseHelper myDb;
    private Switch mySwitch;


    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



///////////////////////////switch//////////////////////////////////////////////////
        mySwitch = (Switch) findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    Toast.makeText(getApplicationContext(),"all night activated",Toast.LENGTH_LONG).show();

                    mMap.clear();

                    LatLng pharmacie2 = new LatLng(32.891295, -6.911988);
                    mMap.addMarker(new MarkerOptions().position(pharmacie2).title("pharmacie hopital").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie7 = new LatLng(32.891992, -6.898728);
                    mMap.addMarker(new MarkerOptions().position(pharmacie7).title("Pharmacie Oum Elqora").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie8 = new LatLng(32.882712, -6.917010);
                    mMap.addMarker(new MarkerOptions().position(pharmacie8).title("PHARMACIE 21 AOU ").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie10 = new LatLng(32.876591, -6.908131);
                    mMap.addMarker(new MarkerOptions().position(pharmacie10).title("pharmacie du village").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));




                }else{
                 Toast.makeText(getApplicationContext(),"all night deactivated",Toast.LENGTH_LONG).show();

                    LatLng pharmacie1 = new LatLng(32.903714, -6.907153);
                    mMap.addMarker(new MarkerOptions().position(pharmacie1).title("Pharmacie Al Bassatine").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie2 = new LatLng(32.891295, -6.911988);
                    mMap.addMarker(new MarkerOptions().position(pharmacie2).title("pharmacie hopital").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie3 = new LatLng(32.871837, -6.910143);
                    mMap.addMarker(new MarkerOptions().position(pharmacie3).title("Pharmacie RYAD").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie4 = new LatLng(32.898493, -6.903191);
                    mMap.addMarker(new MarkerOptions().position(pharmacie4).title("pharmacie jmouhi").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie6 = new LatLng(32.877897, -6.919370);
                    mMap.addMarker(new MarkerOptions().position(pharmacie6).title("Pharmacie Rabbani").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie7 = new LatLng(32.891992, -6.898728);
                    mMap.addMarker(new MarkerOptions().position(pharmacie7).title("Pharmacie Oum Elqora").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie8 = new LatLng(32.882712, -6.917010);
                    mMap.addMarker(new MarkerOptions().position(pharmacie8).title("PHARMACIE 21 AOU ").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie9 = new LatLng(32.892280, -6.896636);
                    mMap.addMarker(new MarkerOptions().position(pharmacie9).title("Pharmacie Rochdi").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie10 = new LatLng(32.876591, -6.908131);
                    mMap.addMarker(new MarkerOptions().position(pharmacie10).title("pharmacie du village").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));

                    LatLng pharmacie11 = new LatLng(32.905491, -6.919968);
                    mMap.addMarker(new MarkerOptions().position(pharmacie11).title("Pharmacie Ouled Abdoun").icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie)));




                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });


    }




    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder((DirectionFinderListener) this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }










    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //////////////////////////////////////////////////////////////
        /////----------------------------------Zooming camera to position user-----------------

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
        //////////////////////////////////////////////////////////////



        //set My Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);



        //dra polylines to map through given coordinates
        //mMap.addPolyline(new PolylineOptions().add(pharmacie1,pharmacie2,pharmacie3).width(10).color(Color.RED));

        //build path between 2 address places using Google Maps Direction API

    }




    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }








    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Menu///////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //set Map Type there are 3 types : satellite ... normal ...terain
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        switch (item.getItemId()) {
            case R.id.item1:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(getApplicationContext(),"Normal Mode Activated",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getApplicationContext(),"Terrain Mode Activated",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(getApplicationContext(),"Satellite Mode Activated",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
