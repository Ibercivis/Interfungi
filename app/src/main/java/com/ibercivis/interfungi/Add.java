package com.ibercivis.interfungi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.ibercivis.interfungi.clases.Marcador;
import com.ibercivis.interfungi.BuildConfig;
import com.ibercivis.interfungi.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.HashMap;

public class Add extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RelativeLayout layoutMapa;

    HashMap<String, Marcador> mapaMarcadores = new HashMap<String, Marcador>();

    /* Map and location */
    public MapView map;
    Location currentLocation;
    private MyLocationNewOverlay mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CopyrightOverlay mCopyrightOverlay;

    private static String TAG = "AddClass";


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override public void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        map = findViewById(R.id.map);

        /* Toolbar */



        /* Mapa OSMDroid */
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);

                recreate();

                return ;
            }
        }

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        currentLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        GpsMyLocationProvider provider = new GpsMyLocationProvider(this.getApplicationContext());
        Log.d(TAG, provider.toString());
        mLocationOverlay = new MyLocationNewOverlay(provider, map);
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mCopyrightOverlay = new CopyrightOverlay(this);


        map.getOverlays().add(mLocationOverlay);
        map.getOverlays().add(mScaleBarOverlay);
        map.getOverlays().add(this.mCopyrightOverlay);
        map.setMinZoomLevel(8.0);
        map.setMaxZoomLevel(20.0);


        mLocationOverlay.enableMyLocation();
        mLocationOverlay.setOptionsMenuEnabled(true);

        // map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTileSource(new XYTileSource("MapQuest", 0, 18, 256, ".png", new String[] { "http://tile.openstreetmap.org/"}));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);


        GeoPoint myPoint = mLocationOverlay.getMyLocation();
        mapController.setCenter(myPoint);
        Log.d(TAG, currentLocation.toString());


        mapController.setZoom(17.00);

        esperarYCentrarMapa(mapController, 2000);
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"your message" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation=location;
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

    public void esperarYCentrarMapa(final IMapController mapController, int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                GeoPoint myPoint = mLocationOverlay.getMyLocation();
                Log.d(TAG, myPoint.toString());
                mapController.animateTo(myPoint);

                mapController.setZoom(17.00);

            }
        }, milisegundos);
    }

}
