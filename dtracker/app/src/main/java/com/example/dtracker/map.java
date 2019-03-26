package com.example.dtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager l;
    LatLng ltg;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if(!checkLocationPermission())
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        l = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        try {
            if (l.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getApplicationContext(),"requesting for location update!!",Toast.LENGTH_SHORT).show();
                l.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 20, new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        welcome.distance+=20;
                        Toast.makeText(getApplicationContext(),"location changed "+welcome.distance,Toast.LENGTH_SHORT).show();
                        ltg = new LatLng(lat, lng);
                        Geocoder g = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addresslist = g.getFromLocation(lat, lng, 1);
                            String locality = addresslist.get(0).getLocality() + ",";
                            locality += addresslist.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(ltg).title(locality));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltg, 10.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
            } else if (l.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                l.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20, 20, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        welcome.distance+=20;
                        ltg = new LatLng(lat, lng);
                        Geocoder g = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addresslist = g.getFromLocation(lat, lng, 1);
                            String locality = addresslist.get(0).getLocality() + ",";
                            locality += addresslist.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(ltg).title(locality));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltg, 10.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
            } else
                Toast.makeText(getApplicationContext(), "Your location is not available!!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {

        }
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
         LatLng sydney = new LatLng(-14, 151);
       // mMap.addMarker(new MarkerOptions().position(ltg).title("My location"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltg,10.2f));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}

