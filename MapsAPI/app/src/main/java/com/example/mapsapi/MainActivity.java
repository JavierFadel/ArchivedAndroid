package com.example.mapsapi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int ALL_PERMISSION_RESULT = 1111;
    private GoogleApiClient client;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsRejected = new ArrayList<>();

    private TextView locationDisplay;
    private LocationRequest locationRequest;

    public static final long UPDATE_INTERVAL = 5000;
    public static final long FASTEST_INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationDisplay = findViewById(R.id.location_info);

        // Main entry point for interacting with fused location provider.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        // Adding permissions.
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        // Check and request permissions.
        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]
                ), ALL_PERMISSION_RESULT);
            }
        }

        // Main entry point for Google Play Services.
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API) // Accessing location services
                .addOnConnectionFailedListener(this) // Called when unresolvable connection errors occurred.
                .addConnectionCallbacks(this) // Get info when automatically managed connection is established or suspended.
                .build();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermission) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermission) {
            // Checking whether permission is granted or not.
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private void hasPlayServices() {
        // Checking Google Play Service availability
        int errorCode = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this);

        // If services not available
        if (errorCode != ConnectionResult.SUCCESS) {
            // Create error dialog
            Dialog errorDialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, errorCode, errorCode,
                            new DialogInterface.OnCancelListener() { // Run this when event cancelled
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    Toast.makeText(MainActivity.this, "No Services", Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
            // Show error dialog
            errorDialog.show();
        }
    }

    private boolean hasPermission(String perm) {
        // Checking if current SDK version is higher than Marshmallow version code.
        // Make sure the version is not below Marshmallow.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Checking whether permission has been granted to the given package.
            return checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (client != null) {
            client.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
    }

    // Called when onResume() invokes.
    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Check Google play service availability
        hasPlayServices();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (client != null && client.isConnected()) {
            LocationServices.getFusedLocationProviderClient(this)
                    .removeLocationUpdates(new LocationCallback() { // Removes all location updates for the given location result listener.
                    });
            client.disconnect();
        }
    }

    // Connected to services.
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Check permissions.
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Get the most recent location
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Displaying location to TextView
                            locationDisplay.setText(MessageFormat.format("Latitude: {0} Longitude: {1}",
                                    location.getLatitude(), location.getLongitude()));
                        }
                    }
                });
        // Setting Location Request.
        startLocationUpdate();
    }

    private void startLocationUpdate() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Setting priority
        locationRequest.setInterval(UPDATE_INTERVAL); // Setting update interval
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Check permissions again.
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission required.", Toast.LENGTH_LONG).show();
        }

        // Updating location.
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        // Getting location updates.
                        if (locationResult != null) {
                            Location location = locationResult.getLastLocation();
                            locationDisplay.setText(MessageFormat.format("Latitude: {0} Longitude: {1}",
                                    location.getLatitude(), location.getLongitude()));
                        }
                    }

                    @Override
                    public void onLocationAvailability(LocationAvailability locationAvailability) {
                        super.onLocationAvailability(locationAvailability);
                    }
                }, null);
    }

    // Callback for the result from requesting permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ALL_PERMISSION_RESULT:
                // Check whether permissions is completed or not.
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }
                // If not, run this.
                if (permissionsRejected.size() > 0) {
                    // Always check
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("Permission required for application to work properly.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() { // Set ok button
                                        // Asking users the permissions again.
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]),
                                                        ALL_PERMISSION_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show(); // Set cancel button
                        }
                    }
                } else {
                    if (client != null) {
                        client.connect();
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // Updating location
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            locationDisplay.setText(MessageFormat.format("Latitude: {0} Longitude: {1}",
                    location.getLatitude(), location.getLongitude()));
        }
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
}
