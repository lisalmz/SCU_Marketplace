package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.scu_mp.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import dagger.MapKey;

public class
MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private String TAG = "MapsActivity_TAG";
    private Button btnMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private ImageButton backButton;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static float DEFAULT_ZOOM = 15f;
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.3496, -121.9390);
    private static LatLng SELECTED_LOCATION = DEFAULT_LOCATION;
    private static int radius;
    private static String retrieveLocation;
    private static String retrieveItem;
    private static ArrayList<Marker> markerList;
    private static String retrieveRadius;
    private DatabaseReference mDatabase;

    private HashMap<String, String> markersHashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLocationPermission();
        if (isServicesOK()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
             */
            initMap();
        }
        markerList = new ArrayList<>();
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Marker x : markerList)
                {
                    if (x != null)
                    {
                        x.remove();
                    }
                }
                finish();
            }
        });
        radius = 400;
        retrieveItem = getIntent().getStringExtra("Item");
        retrieveLocation = getIntent().getStringExtra("Location");
        retrieveRadius = getIntent().getStringExtra("Radius");
        switch (retrieveRadius)
        {
            case "300m":
                radius = 300;
                DEFAULT_ZOOM = 15f;
                break;
            case "500m":
                radius = 500;
                DEFAULT_ZOOM = 15f;
                break;
            case "1000m":
                radius = 1000;
                DEFAULT_ZOOM = 14f;
                break;
            case "2500m":
                radius = 2500;
                DEFAULT_ZOOM = 13f;
                break;
            case "5000m":
                radius = 5000;
                DEFAULT_ZOOM = 12f;
                break;
            case "10km":
                radius = 10000;
                DEFAULT_ZOOM =11f;
                break;
            case "25km":
                radius = 25000;
                DEFAULT_ZOOM = 10f;
                break;
            default:
                radius = 400;
                DEFAULT_ZOOM = 15f;
                break;
        }

        markersHashmap = new HashMap<String, String>();

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        mDatabase.keepSynced(true);
        try {
            if (mLocationPermissionsGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            if (!retrieveLocation.equals(""))
                            {
                                Log.d(TAG, "retrieveLocation: " + retrieveLocation);
                                SELECTED_LOCATION = getLocation(retrieveLocation);
                            }
                            else {
                                Log.d(TAG, "New Current Location: ");
                                SELECTED_LOCATION = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            }
                            Log.d(TAG, "SELECTED_LOCATION: " + SELECTED_LOCATION.toString());
                            mMap.addCircle(
                                    new CircleOptions()
                                            .center(SELECTED_LOCATION)
                                            .radius(radius)
                                            .fillColor(0x220000FF)
                                            .strokeColor(R.color.theme_color)
                                            .strokeWidth(4f)
                            );
                            Query query = mDatabase.orderByChild("itemName");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                                        Blog blog = snapShot.getValue(Blog.class);
                                        String keyId = snapShot.getKey();
                                        Log.d(TAG, "keyID: " + keyId);
                                        String heading = blog.getItemName();
                                        Log.d(TAG, "Heading before: " + heading);
                                        String lowHeading = heading.toLowerCase();
                                        Log.d(TAG, "Heading after: " + heading);
                                        String user = blog.getUsername();
                                        String brand = blog.getCondition();
                                        String price = blog.getPrice();
                                        String tmp = retrieveItem.toLowerCase();
                                        String address = blog.getAddress();
                                        if (lowHeading.contains(tmp) && address != null)
                                        {
                                            markerList.add(addMarkers(address, heading, price, brand, user, keyId));
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            moveCamera(SELECTED_LOCATION, DEFAULT_ZOOM, "CURRENT LOCATION");
                        } else {
                            Log.d(TAG, "onComplete: Null location");
                            Toast.makeText(MapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "retrieveLocation: " + retrieveLocation);
                            if (!retrieveLocation.equals(""))
                            {
                                SELECTED_LOCATION = getLocation(retrieveLocation);
                            }
                            else
                            {
                                SELECTED_LOCATION = DEFAULT_LOCATION;
                            }
                            moveCamera(SELECTED_LOCATION, DEFAULT_ZOOM, "DEFAULT LOCATION");
                            mMap.addCircle(
                                    new CircleOptions()
                                            .center(SELECTED_LOCATION)
                                            .radius(radius)
                                            .fillColor(0x220000FF)
                                            .strokeColor(R.color.theme_color)
                                            .strokeWidth(4f)
                            );
                            Query query = mDatabase.orderByChild("itemName");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                                        Blog blog = snapShot.getValue(Blog.class);
                                        String keyId = snapShot.getKey();
                                        Log.d(TAG, "keyID: " + keyId);
                                        String heading = blog.getItemName();
                                        Log.d(TAG, "Heading before: " + heading);
                                        String lowHeading = heading.toLowerCase();
                                        Log.d(TAG, "Heading after: " + heading);
                                        String user = blog.getUsername();
                                        String brand = blog.getCondition();
                                        String price = blog.getPrice();
                                        String tmp = retrieveItem.toLowerCase();
                                        String address = blog.getAddress();
                                        if (lowHeading.contains(tmp))
                                        {
                                            markerList.add(addMarkers(address, heading, price, brand, user, keyId));
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: Security Exception: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to lat: " + latLng.latitude + " lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        /*if (!title.equals("CURRENT LOCATION"))
        {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }*/
    }

    private Marker addMarkers(String searchString, String heading, String price, String brand, String user, String keyId)
    {
        Log.d(TAG, "addMarkers");
        //String searchString = "Santa Clara University";
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        Marker res = null;
        Log.d(TAG, "MARKERS: " + SELECTED_LOCATION.toString());
        Log.d(TAG, "MARKERS: SEARCH STRING: " + searchString);
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e)
        {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        Log.d(TAG, "MARKERS: Successful");
        if (list.size() > 0)
        {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: Found location: " + address.toString());
            float[] results = new float[1];
            Location.distanceBetween(SELECTED_LOCATION.latitude, SELECTED_LOCATION.longitude,
                    address.getLatitude(), address.getLongitude(), results);

            Log.d(TAG, "After Marker2: " + String.valueOf(results[0]));
            if (results[0] < radius)
            {
                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(address.getLatitude(), address.getLongitude()))
                        .title(heading)
                        .snippet(String.format("%s\n%s\n$%s\n%s", searchString, user, price, brand));
                Log.d(TAG, "After Marker: " + options.toString());
                res = mMap.addMarker(options);
                markersHashmap.put(res.getId(), keyId);
            }
            //Log.i(TAG, "Distance between: " + String.valueOf(results[0]));
        }
        return res;
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    /*private void init()
    {
        btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

            }
        });
    }
     */

    private void getLocationPermission() {
        Log.i(TAG, "getLocationPermission");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;
        Log.i(TAG, "onRequestPermissionsResult");

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.i(TAG, "onRequestPermission Result Failed");
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private LatLng getLocation(String searchString)
    {
        Log.d(TAG, "geoLocate: geolocating");
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e)
        {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if(list.size() == 0)
        {
            Toast.makeText(this, "Invalid Location", Toast.LENGTH_SHORT).show();
            return DEFAULT_LOCATION;
        }
        Address address = list.get(0);
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    private void geoLocate()
    {
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = "Santa Clara University";
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e)
        {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (list.size() > 0)
        {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: Found location" + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }


    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOk: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Problem", Toast.LENGTH_SHORT).show();
        }
        return false;

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
        Log.i(TAG, "onMapReady");
        //Toast.makeText(this, "In Google Maps", Toast.LENGTH_SHORT).show();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                Intent intent = new Intent(getApplicationContext(), activity_description.class);
                String keyId = markersHashmap.get(marker.getId());
                Log.i(TAG, "MARKERS HASH: " + keyId);
                intent.putExtra("itemId", keyId);
                startActivity(intent);
            }
        });

        if (mLocationPermissionsGranted) {
            Log.i(TAG, "onMapReady: Permission Granted");
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            geoLocate();
        }
    }
}