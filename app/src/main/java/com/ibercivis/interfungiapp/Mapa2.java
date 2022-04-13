package com.ibercivis.interfungiapp;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ibercivis.interfungiapp.clases.AdaptadorCatalogo;
import com.ibercivis.interfungiapp.clases.Marcador;
import com.ibercivis.interfungiapp.clases.SessionManager;
import com.ibercivis.interfungiapp.clases.marcadorTipo;
import com.ibercivis.interfungiapp.db.AppDatabase;
import com.ibercivis.interfungiapp.db.DAO.MarcadorSetaDAO;
import com.ibercivis.interfungiapp.db.DAO.TiposDeSetasDAO;
import com.ibercivis.interfungiapp.db.MarcadorDatabase;
import com.ibercivis.interfungiapp.db.entities.MarcadorSeta;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;
import com.ibercivis.interfungiapp.repository.MarcadorSetaRepository;
import com.ibercivis.interfungiapp.repository.MarcadorSetaRepositoryImplement;
import com.ibercivis.interfungiapp.repository.TiposDeSetasRepository;
import com.ibercivis.interfungiapp.repository.TiposDeSetasRepositoryImplement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.wms.WMSTileSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Mapa2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerCatalogoSetas;
    AdaptadorCatalogo recyclerAdapter;
    Toolbar toolbar;
    RelativeLayout paraMapa;
    LinearLayout layout_marcador, layout_foto, atri1;
    LinearLayout layout_atributo1, layout_atributo2, layout_atributo3;
    TextInputLayout titulo1;
    TextInputEditText edit_atri1;
    ImageView marco_foto;
    LinearLayout marcador_mostrado, layout_info;
    TextView titulo_addmarker, enunciado_atri1, enunciado_atri2, enunciado_atri3;
    TextView respuesta_atri1, respuesta_atri2, respuesta_atri3;
    Button back, back_info;
    Button cancelar, aceptar, btnfoto, btnfoto2, btndelete, btnedit;
    double mLatitude, mLongitude;
    Location currentLocation;
    LocationManager lm;
    ImageView miniatura_camara, miniatura_camara1, layerBtn;
    ImageButton btCenterMap;
    private MyLocationNewOverlay mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CopyrightOverlay mCopyrightOverlay;
    int idProyecto;
    String titleProyecto;
    TextView display_titulo, copyrightTxt;

    HashMap<String, Marcador> mapaMarcadores = new HashMap<String, Marcador>();
    ArrayList<TiposDeSetas> ListaCatalogo = new ArrayList<>();
    Marcador marcador_pulsado;

    String urlfoto;
    TextView txt_info;
    ImageView btninfo1, btninfo2, btninfo3, btninfo4, btninfo5, btninfo6, btninfo7, btninfo8, btninfo9, btninfo10, btninfo11, btninfo12, btninfo13, btninfo14, btninfo15, btninfo16, btninfo17, btninfo18, btninfo19, btninfo20, btninfo21, btninfo22, btninfo23;
    CheckBox checkBox;
    TextView especie_identificada;

    String info_atributo1, info_atributo2, info_atributo3, info_atributo4, info_atributo5, info_atributo6, info_atributo7, info_atributo8, info_atributo9, info_atributo10, info_atributo11, info_atributo12, info_atributo13, info_atributo14, info_atributo15, info_atributo16, info_atributo17, info_atributo18, info_atributo19, info_atributo20, info_atributo21, info_atributo22, info_atributo23;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private ImageView mPhotoImageView;

    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String mCurrentPhotoPath;
    private String mCurrentPhotoPath1;
    private Uri photoURI;
    private Uri photoURI1;
    String base64String = "";
    String base64String1 = "";
    double longitude = 0.0;
    double latitude = 0.0;

    ByteArrayOutputStream arrayParaBlob = new ByteArrayOutputStream();
    ByteArrayOutputStream arrayParaBlob1 = new ByteArrayOutputStream();
    byte[] foto_blob;
    byte[] foto_blob1;

    int select_layer = 1;

    List<TiposDeSetas> listaSetas;


    // Marcador marcador_prueba = new Marcador(16,1, "Atributo1: ", "Índice pH: ", "Indice cloro: ", "Color: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ", "Indice cloro: ");
    Marcador marcador_descargado;
    marcadorTipo marcador_tipo;
    public MapView map;
    Marker markpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ContextWrapper cw = this;
        suggestedFix(cw);

        /*-----Hooks-----*/
        map = findViewById(R.id.map);
        drawerLayout = findViewById(R.id.drawer_layout4);
        navigationView = findViewById(R.id.nav_view4);
        toolbar = findViewById(R.id.toolbar2);

        FloatingActionButton floating = findViewById(R.id.fab);

        marcador_mostrado = findViewById(R.id.mostrarMarker);
        marco_foto = findViewById(R.id.foto_marcador);

        back = findViewById(R.id.cerrar_marcador);
        btndelete = findViewById(R.id.borrar_marcador);
        btnedit = findViewById(R.id.editar_marcador);
        layerBtn = findViewById(R.id.button_layers);
        titulo_addmarker = findViewById(R.id.title_addmarker);
        copyrightTxt = findViewById(R.id.txtCopyright);
        enunciado_atri1 = findViewById(R.id.enunciado_at1);
        enunciado_atri2 = findViewById(R.id.enunciado_at2);
        enunciado_atri3 = findViewById(R.id.enunciado_at3);
        checkBox = findViewById(R.id.check_curiosidad);
        especie_identificada = findViewById(R.id.especie_identificada);


        respuesta_atri1 = findViewById(R.id.respuesta_at1);
        respuesta_atri2 = findViewById(R.id.respuesta_at2);


        layout_atributo1 = findViewById(R.id.marco_atributo1);
        layout_atributo2 = findViewById(R.id.marco_atributo2);
        layout_atributo3 = findViewById(R.id.marco_atributo3);


        layout_marcador = findViewById(R.id.marco_marker);
        layout_foto = findViewById(R.id.photo);
        paraMapa = findViewById(R.id.mapa);
        layout_info = findViewById(R.id.displayinfo);

        atri1 = findViewById(R.id.atributo1);

        //EN ESTE PROYECTO SÓLO HAY UN ATRIBUTO, ASÍ QUE:
       /* atri2.setVisibility(View.GONE);
        atri3.setVisibility(View.GONE);
        atri4.setVisibility(View.GONE);
        atri5.setVisibility(View.GONE);
        atri6.setVisibility(View.GONE);
        atri7.setVisibility(View.GONE);
        atri8.setVisibility(View.GONE);
        atri9.setVisibility(View.GONE);
        atri10.setVisibility(View.GONE);
        atri11.setVisibility(View.GONE);
        atri12.setVisibility(View.GONE);
        atri13.setVisibility(View.GONE);
        atri14.setVisibility(View.GONE);
        atri15.setVisibility(View.GONE);
        atri16.setVisibility(View.GONE);
        atri17.setVisibility(View.GONE);
        atri18.setVisibility(View.GONE);
        atri19.setVisibility(View.GONE);
        atri20.setVisibility(View.GONE);
        atri21.setVisibility(View.GONE);
        atri22.setVisibility(View.GONE);
        atri23.setVisibility(View.GONE); */

        edit_atri1 = findViewById(R.id.edit_atributo1);


        edit_atri1.setText("");


        titulo1 = findViewById(R.id.txt_atributo1);


        cancelar = findViewById(R.id.cancel_but);
        aceptar = findViewById(R.id.acept_but);
        miniatura_camara = findViewById(R.id.camera);
        miniatura_camara1 = findViewById(R.id.camera1);
        txt_info = findViewById(R.id.text_info);

        btninfo1 = findViewById(R.id.info1);


        back_info = findViewById(R.id.volver_info);
        display_titulo = findViewById(R.id.txtTitulo);


        /*-----Toolbar-----*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*-----Navigation Drawer Menu -----*/

        //Hide or show items

        Menu menu = navigationView.getMenu();

        menu.findItem(R.id.nav_logout).setVisible(true);

        //Fit Navigation Drawer

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer, R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        /*
        //PERMISOS PARA MANAGE_EXTERNAL_STORAGE Y QUE FUNCIONEN LOS TILES DEL MAPA EN SDK>30

        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Snackbar.make(findViewById(android.R.id.content), "¡Permisos necesarios para ver el mapa!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                    startActivity(intent);
                                }
                            }
                        })
                        .show();
            }
        }
        */
        // LOCALIZACIÓN

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);

                recreate();

                return;
            }
        }


        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        //AQUÍ EMPIEZA EL BARRO DE LA CONEXIÓN O NO CONEXIÓN

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if (connected == true) {
            //HAY CONEXIÓN A INTERNET. LANZAMOS LA ACTIVIDAD EN MODO NORMAL.

            /*-- COMPROBAMOS SI HAY MARCADORES POR SUBIR EN LA BASE DE DATOS --*/
            MarcadorDatabase db = MarcadorDatabase.getInstance(this.getApplicationContext());
            MarcadorSetaDAO dao = db.marcadorSetaDAO();
            MarcadorSetaRepository repo = new MarcadorSetaRepositoryImplement(dao);

            List<MarcadorSeta> listaMarcadores;

            listaMarcadores = repo.getAllMarcadorSetaRepo();
            repo.deleteAllMarcadorSetaRepo(listaMarcadores);

            if (listaMarcadores.size() > 0){
                subirMarcadorDeDatabase(listaMarcadores.get(0));
            }

            /*-----MAPA OSMDROID-----*/

            idProyecto = getIntent().getIntExtra("idProyecto", 48);
            titleProyecto = getIntent().getStringExtra("tituloProyecto");
            display_titulo.setText("Interfungi");


            GpsMyLocationProvider provider = new GpsMyLocationProvider(this.getApplicationContext());
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




            copyrightTxt.setText("");

            map.setTileSource(TileSourceFactory.MAPNIK);

            layerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (select_layer == 1) {
                        map.setTileSource(TileSourceFactory.OpenTopo);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Capa topográfica";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                        select_layer = 2;
                    } else if (select_layer == 2) {
                        map.setTileSource(new WMSTileSource("PNOAWMS", new String[]{"http://www.idee.es/wms/PNOA/PNOA?SERVICE=WMS"}, "PNOA", "1.3.0", "&crs=CRS:84", "", 256));
                        select_layer = 3;
                        map.setMinZoomLevel(8.0);
                        map.setMaxZoomLevel(20.0);
                        copyrightTxt.setText("PNOA cedido por © Instituto Geográfico Nacional de España");

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Capa de ortofotos del PNOA (Tarda en cargar)";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();


                    } else if (select_layer == 3) {
                        map.setTileSource(new WMSTileSource("PNOAWMS2", new String[]{"http://www.ign.es/wms-inspire/mapa-raster?SERVICE=WMS"}, "mtn_rasterizado", "1.3.0", "&crs=CRS:84", "", 256));
                        select_layer = 4;
                        map.setMinZoomLevel(8.0);
                        map.setMaxZoomLevel(20.0);
                        copyrightTxt.setText("CC BY 4.0 ign.es");
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Capa de cartografía raster (Tarda en cargar)";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    } else if (select_layer == 4) {
                        map.setTileSource(TileSourceFactory.MAPNIK);
                        map.setMinZoomLevel(1.0);
                        map.setMaxZoomLevel(20.0);
                        select_layer = 1;
                        copyrightTxt.setText("");
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Capa de Open Street Map";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    }
                }
            });


            Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

            map.setMultiTouchControls(true);

            IMapController mapController = map.getController();
            mapController.setZoom(9);
            map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

            GeoPoint myPoint = mLocationOverlay.getMyLocation();
            mapController.setCenter(myPoint);

            mapController.setZoom(17.00);
            markpress = new Marker(map);

            esperarYCentrarMapa(mapController, 2000);
            //SOLICITAR EL TIPO DE MARCADOR DEL PROYECTO
            getCustomMarkerRequest();


        } else {

            layout_marcador.setVisibility(View.VISIBLE);
            paraMapa.setVisibility(View.GONE);
            cargarCatalogoSetasOffline(connected);

            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Marcador", "Call to subir marcador");

                    boolean connected = false;
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        connected = true;
                    } else
                        connected = false;

                    if (connected == true) {
                        subirMarcador();
                    } else {
                        subirMarcadorOffline();
                    }


                }
            });

        }


        /*-----MÉTODOS PROPIOS DE ESTA ACTIVITY-----*/

        btCenterMap = (ImageButton) findViewById(R.id.ic_center_map);

        btCenterMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocationOverlay.getMyLocation() != null) {
                    GeoPoint myPosition = mLocationOverlay.getMyLocation();
                    map.getController().animateTo(myPosition);
                    map.getController().setZoom(17.00);
                } else {


                }
            }
        });


        //COMENTO ABAJO, MÉTODO PARA AÑADIR MARCADORES HACIENDO LONGPRESS SOBRE EL MAPA
        /*
        Overlay overlay = new Overlay(getBaseContext()) {

            ItemizedIconOverlay<OverlayItem> items = null;


            @Override
            // public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView) {
            public boolean onLongPress(MotionEvent e, MapView mapView) {
                Projection proj = mapView.getProjection();
                GeoPoint loc = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
                longitude = loc.getLongitude();
                latitude = loc.getLatitude();
                mLatitude = latitude;
                mLongitude = longitude;


                ArrayList<OverlayItem> markers = new ArrayList<>();
                OverlayItem item = new OverlayItem("", "", new GeoPoint(latitude, longitude));
                Drawable newMarker = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_location_on_invisible_24dp);
                item.setMarker(newMarker);
                // item.setMarker(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_clear_red_24dp));
                markers.add(item);

                if (items == null) {
                    items = new ItemizedIconOverlay<>(getBaseContext(), markers, null);
                    map.getOverlays().add(items);
                    map.invalidate();
                    //metodo para añadir marcadores
                    addmarker(marcador_tipo, true);
                } else {

                    items = new ItemizedIconOverlay<>(getBaseContext(), markers, null);
                    map.getOverlays().add(items);
                    //metodo para añadir marcadores
                    addmarker(marcador_tipo, true);
                }
                return true;
            }

        };

        map.getOverlays().add(overlay);
         */

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SessionManager session = new SessionManager(getApplicationContext());


                Long tsLong = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(tsLong);
                String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();

                //final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(Mapa2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Mapa2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(currentLocation != null) {
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();

                    addmarker(marcador_tipo, true);
                } else {
                    GeoPoint myPoint = mLocationOverlay.getMyLocation();
                    latitude = myPoint.getLatitude();
                    longitude = myPoint.getLongitude();

                    addmarker(marcador_tipo, true);
                }


                };
        });



        back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_info.setVisibility(View.GONE);
            }
        });

        btninfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_info.setVisibility(View.VISIBLE);
                if (info_atributo1.equals("")) {
                    txt_info.setText("¿Alguna curiosidad sobre la observación que vas a subir? ¡Cuéntanos!");
                } else {
                    txt_info.setText("¿Alguna curiosidad sobre la observación que vas a subir? ¡Cuéntanos!");
                }
            }
        });


        int duration = Toast.LENGTH_LONG;
        Toast toast;
        CharSequence text;

        text = getResources().getString(R.string.mapa);
        toast = Toast.makeText(getApplicationContext(), text, duration);
        //toast.show();


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_projects:
                Intent intent2 = new Intent(getApplicationContext(), ListadoProyectos.class);
                startActivity(intent2);
                break;
            case R.id.nav_crear:
                Intent intent3 = new Intent(getApplicationContext(), Mapa2.class);
                startActivity(intent3);
                break;

            case R.id.nav_logout:
                SessionManager session = new SessionManager(getApplicationContext());
                session.setClear();
                Intent intent4 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.nav_misproyectos:
                Intent intent5 = new Intent(getApplicationContext(), Usuario.class);
                startActivity(intent5);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(Mapa2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(Mapa2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void esperarYCentrarMapa(final IMapController mapController, int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                GeoPoint myPoint = mLocationOverlay.getMyLocation();
                mapController.animateTo(myPoint);

                mapController.setZoom(17.00);
                if(myPoint != null){
                    final Double longitud_reserva = myPoint.getLongitude();
                    final Double latitud_reserva = myPoint.getLatitude();
                }

            }
        }, milisegundos);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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

    public String generarSnippet(Marcador marcador, marcadorTipo marcadortipo) {

        String resultado = "";

        if (marcador.getAtributo3().equals("CURIOSIDAD")) {
            resultado = "Participa en el concurso" + "<br>" + "Especie: " + marcador.getAtributo2() + "<br>" + marcadortipo.getAtributo1() + ": " + marcador.getAtributo1();
        } else resultado = "Especie: " + marcador.getAtributo2() + "<br>" + marcadortipo.getAtributo1() + ": " + marcador.getAtributo1();

        return resultado;
    }

    public void addmarker(marcadorTipo marcador, Boolean conectado) {

        aceptar.setText(getResources().getString(R.string.mapa3));
        titulo_addmarker.setText(getResources().getString(R.string.mapa2));

        edit_atri1.setText("");


        layout_marcador.setVisibility(View.VISIBLE);
        paraMapa.setVisibility(View.GONE);

        cargarCatalogoSetas(conectado);
        if (marcador_tipo.getFoto() == 1) {
            layout_foto.setVisibility(View.VISIBLE);
            miniatura_camara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPhoto(0);
                }
            });
            miniatura_camara1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPhoto(1);
                }
            });
        }


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_marcador.setVisibility(View.GONE);
                paraMapa.setVisibility(View.VISIBLE);

            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Marcador", "Call to subir marcador");

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;

                if (connected == true) {
                    subirMarcador();
                } else {
                    subirMarcadorOffline();
                }


            }
        });

    }

    public void addPhoto(Integer tipo) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        226);
            }
        } else {
            dispatchTakePictureIntent(tipo);
        }
    }

    private void dispatchTakePictureIntent(Integer tipo) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
      //  if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(tipo);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                if (tipo == 0) {
                    Log.d("Camera", "Tipo is 0");
                    photoURI = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    photoURI1 = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                }

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                if (tipo == 0) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
                }
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    //}

    private File createImageFile(Integer tipo) throws IOException {
        // Create an image file name
        Log.d("Camera", "Here");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (tipo == 0) {
            mCurrentPhotoPath = image.getAbsolutePath();
            Log.d("Camera", "Tipo is 0 in createFileImage");
            Log.d("Camera", mCurrentPhotoPath.toString());
        } else {
            mCurrentPhotoPath1 = image.getAbsolutePath();
            Log.d("Camera", mCurrentPhotoPath1.toString());
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Camera", "Here2");


        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            Bitmap bitmap1;
            Bitmap rotatedBitmap;
            Bitmap rotatedBitmap1;
            if (photoURI != null) {
                try {
                    Log.d("Camera PhotoURI", photoURI.toString());
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                    Bitmap scaledBitmap = getScaledBitmap(bitmap, 800, 600);



                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, arrayParaBlob);

                    rotatedBitmap = rotateImageIfRequired(this, scaledBitmap, photoURI);



                    base64String = getBase64String(rotatedBitmap); // foto en base64

                    foto_blob = arrayParaBlob.toByteArray();
                    String debugfoto = String.valueOf(foto_blob);

                    miniatura_camara.setImageBitmap(rotatedBitmap);
                    //miniatura_camara.setRotation(0);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (photoURI1 != null) {
                try {
                    Log.d("Camera PhotoURI1", photoURI1.toString());
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI1);
                    Bitmap scaledBitmap1 = getScaledBitmap(bitmap1, 800, 600);

                    scaledBitmap1.compress(Bitmap.CompressFormat.PNG, 80, arrayParaBlob1);

                    rotatedBitmap1 = rotateImageIfRequired(this, scaledBitmap1, photoURI1);

                    base64String1 = getBase64String(rotatedBitmap1); // foto en base64


                    foto_blob1 = arrayParaBlob1.toByteArray();

                    miniatura_camara1.setImageBitmap(rotatedBitmap1);
                    //miniatura_camara1.setRotation(0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras(); // Aquí es null
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mPhotoImageView.setImageBitmap(imageBitmap);
            }*/

        }
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight) {
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        if (nWidth > reqWidth) {
            int ratio = bWidth / reqWidth;
            if (ratio > 0) {
                nWidth = reqWidth;
                nHeight = bHeight / ratio;
            }
        }

        if (nHeight > reqHeight) {
            int ratio = bHeight / reqHeight;
            if (ratio > 0) {
                nHeight = reqHeight;
                nWidth = bWidth / ratio;
            }
        }

        return Bitmap.createScaledBitmap(b, nWidth, nHeight, true);
    }

    private String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

    /*
     *  To decode the base64 string back to bitmap image:
     *  byte[] decodedByteArray = Base64.decode(base64String, Base64.NO_WRAP);
     *  Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedString.length);
     * */

    public void getCustomMarkerRequest() {
        final LinearLayout cargar = findViewById(R.id.cargando);
        String idUser, toktok;
        int item;
        SessionManager session = new SessionManager(Mapa2.this);
        cargar.setVisibility(View.VISIBLE);

        // Input data ok, so go with the request

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        String url = getString(R.string.base_url) + "/proyecto.php?idUser=" + idUser + "&token=" + toktok + "&idProyecto=" + idProyecto;

        RequestQueue queue = Volley.newRequestQueue(Mapa2.this);
        queue.getCache().clear();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        int value;
                        int i = 0;
                        int j = 0;
                        JSONArray jsonArray = responseJSON.getJSONArray("data");

                        for (i = 0; i < jsonArray.length(); i++) {

                            // JSONArray jsonArray1 = jsonArray.getJSONArray(i); //Diferentes proyectos


                            int numeroatributos = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("numAtributos")));
                            int tieneFoto = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("foto")));
                            String atribu1 = String.valueOf(jsonArray.getJSONObject(i).get("atributo1"));
                            String atribu2 = String.valueOf(jsonArray.getJSONObject(i).get("atributo2"));
                            String atribu3 = String.valueOf(jsonArray.getJSONObject(i).get("atributo3"));
                            String atribu4 = String.valueOf(jsonArray.getJSONObject(i).get("atributo4"));
                            String atribu5 = String.valueOf(jsonArray.getJSONObject(i).get("atributo5"));
                            String atribu6 = String.valueOf(jsonArray.getJSONObject(i).get("atributo6"));
                            String atribu7 = String.valueOf(jsonArray.getJSONObject(i).get("atributo7"));
                            String atribu8 = String.valueOf(jsonArray.getJSONObject(i).get("atributo8"));
                            String atribu9 = String.valueOf(jsonArray.getJSONObject(i).get("atributo9"));
                            String atribu10 = String.valueOf(jsonArray.getJSONObject(i).get("atributo10"));
                            String atribu11 = String.valueOf(jsonArray.getJSONObject(i).get("atributo11"));
                            String atribu12 = String.valueOf(jsonArray.getJSONObject(i).get("atributo12"));
                            String atribu13 = String.valueOf(jsonArray.getJSONObject(i).get("atributo13"));
                            String atribu14 = String.valueOf(jsonArray.getJSONObject(i).get("atributo14"));
                            String atribu15 = String.valueOf(jsonArray.getJSONObject(i).get("atributo15"));
                            String atribu16 = String.valueOf(jsonArray.getJSONObject(i).get("atributo16"));
                            String atribu17 = String.valueOf(jsonArray.getJSONObject(i).get("atributo17"));
                            String atribu18 = String.valueOf(jsonArray.getJSONObject(i).get("atributo18"));
                            String atribu19 = String.valueOf(jsonArray.getJSONObject(i).get("atributo19"));
                            String atribu20 = String.valueOf(jsonArray.getJSONObject(i).get("atributo20"));
                            String atribu21 = String.valueOf(jsonArray.getJSONObject(i).get("atributo21"));
                            String atribu22 = String.valueOf(jsonArray.getJSONObject(i).get("atributo22"));
                            String atribu23 = String.valueOf(jsonArray.getJSONObject(i).get("atributo23"));
                            int esTexto1 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText1")));
                            int esTexto2 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText2")));
                            int esTexto3 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText3")));
                            int esTexto4 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText4")));
                            int esTexto5 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText5")));
                            int esTexto6 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText6")));
                            int esTexto7 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText7")));
                            int esTexto8 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText8")));
                            int esTexto9 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText9")));
                            int esTexto10 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText10")));
                            int esTexto11 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText11")));
                            int esTexto12 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText12")));
                            int esTexto13 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText13")));
                            int esTexto14 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText14")));
                            int esTexto15 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText15")));
                            int esTexto16 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText16")));
                            int esTexto17 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText17")));
                            int esTexto18 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText18")));
                            int esTexto19 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText19")));
                            int esTexto20 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText20")));
                            int esTexto21 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText21")));
                            int esTexto22 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText22")));
                            int esTexto23 = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("isText23")));
                            info_atributo1 = String.valueOf(jsonArray.getJSONObject(i).get("desc1"));
                            info_atributo2 = String.valueOf(jsonArray.getJSONObject(i).get("desc2"));
                            info_atributo3 = String.valueOf(jsonArray.getJSONObject(i).get("desc3"));
                            info_atributo4 = String.valueOf(jsonArray.getJSONObject(i).get("desc4"));
                            info_atributo5 = String.valueOf(jsonArray.getJSONObject(i).get("desc5"));
                            info_atributo6 = String.valueOf(jsonArray.getJSONObject(i).get("desc6"));
                            info_atributo7 = String.valueOf(jsonArray.getJSONObject(i).get("desc7"));
                            info_atributo8 = String.valueOf(jsonArray.getJSONObject(i).get("desc8"));
                            info_atributo9 = String.valueOf(jsonArray.getJSONObject(i).get("desc9"));
                            info_atributo10 = String.valueOf(jsonArray.getJSONObject(i).get("desc10"));
                            info_atributo11 = String.valueOf(jsonArray.getJSONObject(i).get("desc11"));
                            info_atributo12 = String.valueOf(jsonArray.getJSONObject(i).get("desc12"));
                            info_atributo13 = String.valueOf(jsonArray.getJSONObject(i).get("desc13"));
                            info_atributo14 = String.valueOf(jsonArray.getJSONObject(i).get("desc14"));
                            info_atributo15 = String.valueOf(jsonArray.getJSONObject(i).get("desc15"));
                            info_atributo16 = String.valueOf(jsonArray.getJSONObject(i).get("desc16"));
                            info_atributo17 = String.valueOf(jsonArray.getJSONObject(i).get("desc17"));
                            info_atributo18 = String.valueOf(jsonArray.getJSONObject(i).get("desc18"));
                            info_atributo19 = String.valueOf(jsonArray.getJSONObject(i).get("desc19"));
                            info_atributo20 = String.valueOf(jsonArray.getJSONObject(i).get("desc20"));
                            info_atributo21 = String.valueOf(jsonArray.getJSONObject(i).get("desc21"));
                            info_atributo22 = String.valueOf(jsonArray.getJSONObject(i).get("desc22"));
                            info_atributo23 = String.valueOf(jsonArray.getJSONObject(i).get("desc23"));
                            marcador_tipo = new marcadorTipo(numeroatributos, tieneFoto, atribu1, atribu2, atribu3, atribu4, atribu5, atribu6, atribu7, atribu8, atribu9, atribu10, atribu11, atribu12, atribu13, atribu14, atribu15, atribu16, atribu17, atribu18, atribu19, atribu20, atribu21, atribu22, atribu23, esTexto1, esTexto2, esTexto3, esTexto4, esTexto5, esTexto6, esTexto7, esTexto8, esTexto9, esTexto10, esTexto11, esTexto12, esTexto13, esTexto14, esTexto15, esTexto16, esTexto17, esTexto18, esTexto19, esTexto20, esTexto21, esTexto22, esTexto23);


                        }

                        cargar.setVisibility(View.GONE);
                        getMarkersRequest();

                        // usernametxt.setText(user);


                    } else {
                        Log.println(Log.ASSERT, "Error", "Algo ha fallado que la respuesta es 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> signup_params = new HashMap<String, String>();


                return signup_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    public void getMarkersRequest() {
        final LinearLayout cargar = findViewById(R.id.cargando);
        String idUser, toktok;
        int item;
        SessionManager session = new SessionManager(Mapa2.this);
        cargar.setVisibility(View.VISIBLE);


        // Input data ok, so go with the request

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        String url = getString(R.string.base_url) + "/marcadores.php?idUser=" + idUser + "&token=" + toktok + "&idProyecto=" + idProyecto;

        RequestQueue queue = Volley.newRequestQueue(Mapa2.this);
        queue.getCache().clear();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        int value;
                        int i = 0;
                        JSONArray jsonArray = responseJSON.getJSONArray("data");

                        for (i = 0; i < jsonArray.length(); i++) {

                            // JSONArray jsonArray1 = jsonArray.getJSONArray(i); //Diferentes proyectos


                            int id = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id")));
                            int idUser = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("idUser")));
                            double latitud = Double.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("latitud")));
                            double longitud = Double.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("longitud")));
                            String fechaCorte = String.valueOf(jsonArray.getJSONObject(i).get("fechaCorte"));
                            int hasFoto = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("hasphoto")));
                            String atributo1 = String.valueOf(jsonArray.getJSONObject(i).get("atributo1"));
                            String atributo2 = String.valueOf(jsonArray.getJSONObject(i).get("atributo2"));
                            String atributo3 = String.valueOf(jsonArray.getJSONObject(i).get("atributo3"));
                            String atributo4 = String.valueOf(jsonArray.getJSONObject(i).get("atributo4"));
                            String atributo5 = String.valueOf(jsonArray.getJSONObject(i).get("atributo5"));
                            String atributo6 = String.valueOf(jsonArray.getJSONObject(i).get("atributo6"));
                            String atributo7 = String.valueOf(jsonArray.getJSONObject(i).get("atributo7"));
                            String atributo8 = String.valueOf(jsonArray.getJSONObject(i).get("atributo8"));
                            String atributo9 = String.valueOf(jsonArray.getJSONObject(i).get("atributo9"));
                            String atributo10 = String.valueOf(jsonArray.getJSONObject(i).get("atributo10"));
                            String atributo11 = String.valueOf(jsonArray.getJSONObject(i).get("atributo11"));
                            String atributo12 = String.valueOf(jsonArray.getJSONObject(i).get("atributo12"));
                            String atributo13 = String.valueOf(jsonArray.getJSONObject(i).get("atributo13"));
                            String atributo14 = String.valueOf(jsonArray.getJSONObject(i).get("atributo14"));
                            String atributo15 = String.valueOf(jsonArray.getJSONObject(i).get("atributo15"));
                            String atributo16 = String.valueOf(jsonArray.getJSONObject(i).get("atributo16"));
                            String atributo17 = String.valueOf(jsonArray.getJSONObject(i).get("atributo17"));
                            String atributo18 = String.valueOf(jsonArray.getJSONObject(i).get("atributo18"));
                            String atributo19 = String.valueOf(jsonArray.getJSONObject(i).get("atributo19"));
                            String atributo20 = String.valueOf(jsonArray.getJSONObject(i).get("atributo20"));
                            String atributo21 = String.valueOf(jsonArray.getJSONObject(i).get("atributo21"));
                            String atributo22 = String.valueOf(jsonArray.getJSONObject(i).get("atributo22"));
                            String atributo23 = String.valueOf(jsonArray.getJSONObject(i).get("atributo23"));

                            marcador_descargado = new Marcador(id, idUser, hasFoto, latitud, longitud, fechaCorte, atributo1, atributo2, atributo3, atributo4, atributo5, atributo6, atributo7, atributo8, atributo9, atributo10, atributo11, atributo12, atributo13, atributo14, atributo15, atributo16, atributo17, atributo18, atributo19, atributo20, atributo21, atributo22, atributo23);

                            addMarcadores2(map, marcador_descargado, marcador_tipo, mapaMarcadores, i);


                        }

                        cargar.setVisibility(View.GONE);


                        // usernametxt.setText(user);


                    } else {
                        Log.println(Log.ASSERT, "Error", "Algo ha fallado que la respuesta es 0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> signup_params = new HashMap<String, String>();


                return signup_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    public void subirMarcador() {
        final LinearLayout cargar = findViewById(R.id.cargando);


        cargar.setVisibility(View.VISIBLE);


        // Input data ok, so go with the request

        // Url for the webservice
        String url = getString(R.string.base_url) + "/crearMarcador.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;
                        edit_atri1.setText("");


                        text = "Marcador subido correctamente";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                        recreate();


                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Error while login: " + responseJSON.get("message") + ".";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();

                        // Clean the text fields for new entries

                        cargar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cargar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                CharSequence text;
                text = "Error while login: " + error.getLocalizedMessage() + ".";
                toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                cargar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> login_params = new HashMap<String, String>();
                Long tsLong = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(tsLong);
                String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();
                SessionManager session = new SessionManager(getApplicationContext());
                Log.d("Corte", date);
                login_params.put("idUser", String.valueOf(session.getIdUser()));
                login_params.put("token", session.getToken());
                login_params.put("foto", String.valueOf(base64String));
                login_params.put("foto1", String.valueOf(base64String1));
                login_params.put("idProyecto", String.valueOf(idProyecto));
                login_params.put("latitud", String.valueOf(latitude));
                login_params.put("longitud", String.valueOf(longitude));
                login_params.put("fechaCorte", date);
                login_params.put("atributo1", edit_atri1.getText().toString());
                login_params.put("atributo2", especie_identificada.getText().toString());
                if(checkBox.isChecked() == true){
                    login_params.put("atributo3", "CURIOSIDAD");
                }

                Log.d("Date", date);


                return login_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
        Log.v("Queue", queue.toString());
    }

    public void marcadorFromFloat(){

        ItemizedIconOverlay<OverlayItem> items = null;

        Long tsLong = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(tsLong);
        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();

        ArrayList<OverlayItem> markers = new ArrayList<>();
        OverlayItem item = new OverlayItem("", "", new GeoPoint(latitude, longitude));
        Drawable newMarker = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_location_on_invisible_24dp);
        item.setMarker(newMarker);
        // item.setMarker(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_clear_red_24dp));
        markers.add(item);

        if (items == null) {
            items = new ItemizedIconOverlay<>(getBaseContext(), markers, null);
            map.getOverlays().add(items);
            map.invalidate();
            //metodo para añadir marcadores
            addmarker(marcador_tipo, true);
        } else {

            items = new ItemizedIconOverlay<>(getBaseContext(), markers, null);
            map.getOverlays().add(items);
            //metodo para añadir marcadores
            addmarker(marcador_tipo, true);
        }

    }

    public void subirMarcadorOffline() {

        final LinearLayout cargar = findViewById(R.id.cargando);


        cargar.setVisibility(View.VISIBLE);

        SessionManager session = new SessionManager(getApplicationContext());

        MarcadorDatabase db = MarcadorDatabase.getInstance(this.getApplicationContext());
        MarcadorSetaDAO dao = db.marcadorSetaDAO();
        MarcadorSetaRepository repo = new MarcadorSetaRepositoryImplement(dao);

        MarcadorSeta marcador = new MarcadorSeta();

        Long tsLong = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(tsLong);
        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        marcador.setIdUserMarcador(String.valueOf(session.getIdUser()));
        marcador.setTokenMarcador(session.getToken());
        marcador.setFotoMarcador(String.valueOf(base64String));
        marcador.setFoto1Marcador(String.valueOf(base64String1));
        marcador.setIdProyectoMarcador(String.valueOf(idProyecto));
        marcador.setLatitudMarcador(String.valueOf(currentLocation.getLatitude()));
        marcador.setLongitudMarcador(String.valueOf(currentLocation.getLongitude()));
        marcador.setFechaCorteMarcador(date);
        marcador.setAtributo1Marcador(edit_atri1.getText().toString());
        marcador.setAtributo2Marcador(especie_identificada.getText().toString());
        if(checkBox.isChecked() == true){
            marcador.setAtributo3Marcador("CURIOSIDAD");
        }

        repo.insertMarcadorSetaRepo(marcador);

        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        CharSequence text;
        edit_atri1.setText("");


        text = "Marcador almacenado correctamente. Se subirá cuando haya conexión a internet.";
        toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
        recreate();

    }

    public void subirMarcadorDeDatabase(MarcadorSeta marcadorSeta) {
        final LinearLayout cargar = findViewById(R.id.cargando);


        cargar.setVisibility(View.VISIBLE);

        MarcadorDatabase db = MarcadorDatabase.getInstance(this.getApplicationContext());
        MarcadorSetaDAO dao = db.marcadorSetaDAO();
        MarcadorSetaRepository repo = new MarcadorSetaRepositoryImplement(dao);

        List<MarcadorSeta> listaMarcadores;

        listaMarcadores = repo.getAllMarcadorSetaRepo();


        // Input data ok, so go with the request

        // Url for the webservice
        String url = getString(R.string.base_url) + "/crearMarcador.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        repo.deleteMarcadorSetaRepo(listaMarcadores.get(0));

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;
                        edit_atri1.setText("");


                        text = "Marcador desde Database subido correctamente";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();



                        recreate();


                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Error while login: " + responseJSON.get("message") + ".";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();

                        // Clean the text fields for new entries

                        cargar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cargar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                CharSequence text;
                text = "Error while login: " + error.getLocalizedMessage() + ".";
                toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                cargar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> login_params = new HashMap<String, String>();
                Long tsLong = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(tsLong);
                String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();
                SessionManager session = new SessionManager(getApplicationContext());
                Log.d("Corte", date);
                login_params.put("idUser", marcadorSeta.getIdUserMarcador());
                login_params.put("token", marcadorSeta.getTokenMarcador());
                login_params.put("foto", marcadorSeta.getFotoMarcador());
                login_params.put("foto1", marcadorSeta.getFoto1Marcador());
                login_params.put("idProyecto", marcadorSeta.getIdProyectoMarcador());
                login_params.put("latitud", marcadorSeta.getLatitudMarcador());
                login_params.put("longitud", marcadorSeta.getLongitudMarcador());
                login_params.put("fechaCorte", marcadorSeta.getFechaCorteMarcador());
                login_params.put("atributo1", marcadorSeta.getAtributo1Marcador());
                login_params.put("atributo2", marcadorSeta.getAtributo2Marcador());
                if(marcadorSeta.getAtributo3Marcador() == "CURIOSIDAD"){
                    login_params.put("atributo3", marcadorSeta.getAtributo3Marcador());
                }

                Log.d("Date", date);


                return login_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
        Log.v("Queue", queue.toString());
    }

    public void addMarcadores2(MapView mapa, Marcador marc, final marcadorTipo marcadortipo, final HashMap<String, Marcador> myMap, int contador) {

        String snippet;
        String id = String.valueOf(contador);


        GeoPoint startPoint = new GeoPoint(marc.getLatitud(), marc.getLongitud());
        Marker startMarker = new Marker(mapa);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("");


        snippet = generarSnippet(marc, marcadortipo);
        startMarker.setSnippet(snippet);
        startMarker.setId(id);
        mapa.getOverlays().add(startMarker);
        myMap.put(startMarker.getId(), marc);

        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                marcador_pulsado = myMap.get(marker.getId());
                System.out.println("Prueba de log");
                System.out.println(marcador_pulsado.getAtributo1());
                System.out.println(String.valueOf(marcador_pulsado.getAtributo2()));
                mostrarMarcador(marcador_pulsado, marcadortipo);

                return true;
            }
        });

    }

    public void mostrarMarcador(final Marcador marcador, marcadorTipo marcadorTipo) {

        marcador_mostrado.setVisibility(View.VISIBLE);

        SessionManager session = new SessionManager(Mapa2.this);


        if (marcador.getIdUser() == session.getIdUser()) {

            btndelete.setVisibility(View.VISIBLE);

            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMarkerRequest(marcador);
                }
            });

        } else {
            btndelete.setVisibility(View.GONE);
        }

        if (marcador.getIdUser() == session.getIdUser()) {

           // btnedit.setVisibility(View.VISIBLE); NO PERMITIMOS EDITAR MARCADORES, SÓLO ELIMINARLOS
            btnedit.setVisibility(View.GONE);
            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Metodo para editar
                    marcador_mostrado.setVisibility(View.GONE);
                    editmarker(marcador_tipo, marcador);
                }
            });

        } else {
            btnedit.setVisibility(View.GONE);
        }


        if (marcador.getHasPhoto() == 0) {

            marco_foto.setVisibility(View.GONE);
        } else {
            mostrarFoto(marcador);
        }
        if (marcadorTipo.getNum_atributos() == 1) {
            enunciado_atri1.setText("Especie:");
            respuesta_atri1.setText(marcador.getAtributo2());
            enunciado_atri1.setVisibility(View.VISIBLE);
            respuesta_atri1.setVisibility(View.VISIBLE);
            layout_atributo1.setVisibility(View.VISIBLE);

            layout_atributo2.setVisibility(View.VISIBLE);
            enunciado_atri2.setVisibility(View.VISIBLE);
            respuesta_atri2.setVisibility(View.VISIBLE);
            enunciado_atri2.setText(marcadorTipo.getAtributo1() + ":");
            respuesta_atri2.setText(marcador.getAtributo1());


            if(marcador.getAtributo3().equals("CURIOSIDAD")){
                layout_atributo3.setVisibility(View.VISIBLE);
                enunciado_atri3.setVisibility(View.VISIBLE);
                enunciado_atri3.setText("¡Es una curiosidad!");
            }

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcador_mostrado.setVisibility(View.GONE);
            }
        });
    }

    public void mostrarFoto(Marcador marcador) {


        urlfoto = "https://interfungi.ibercivis.es/uploads/marcadores/" + String.valueOf(marcador.getId()) + ".jpg";

        Picasso.with(this).load(urlfoto).into(marco_foto);

        marco_foto.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    public void editmarker(marcadorTipo marcador, final Marcador marker) {

        layout_marcador.setVisibility(View.VISIBLE);
        paraMapa.setVisibility(View.GONE);
        aceptar.setText(getResources().getString(R.string.mapa5));
        titulo_addmarker.setText(getResources().getString(R.string.mapa4));

        edit_atri1.setText(marker.getAtributo1());
        especie_identificada.setText(marker.getAtributo2());

        titulo1.setHint(marcador.getAtributo1());

        if(marker.getAtributo3().equals("CURIOSIDAD")){
            checkBox.setChecked(true);
        } else checkBox.setChecked(false);

        cargarCatalogoSetas(true);




        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_marcador.setVisibility(View.GONE);
                paraMapa.setVisibility(View.VISIBLE);

            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMarkerRequest(marker);
            }
        });

    }

    public void editMarkerRequest(final Marcador marker) {

        final LinearLayout cargar = findViewById(R.id.cargando);

        cargar.setVisibility(View.VISIBLE);


        // Url for the webservice
        String url = getString(R.string.base_url) + "/editarMarcador.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;
                        edit_atri1.setText("");

                        miniatura_camara.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera_black_24dp));

                        text = "Marcador editado correctamente";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                        titulo_addmarker.setText("Nuevo Marcador");
                        aceptar.setText("Añadir Marcador");
                        recreate();


                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Algo ha ocurrido. Inténtalo más tarde.";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();

                        // Clean the text fields for new entries

                        cargar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cargar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                CharSequence text;
                text = "Error while login: " + error.getLocalizedMessage() + ".";
                toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                cargar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> login_params = new HashMap<String, String>();

                SessionManager session = new SessionManager(getApplicationContext());
                login_params.put("idUser", String.valueOf(session.getIdUser()));
                login_params.put("token", String.valueOf(session.getToken()));
                login_params.put("idMarcador", String.valueOf(marker.getId()));
                login_params.put("idProyecto", String.valueOf(idProyecto));
                login_params.put("latitud", String.valueOf(marker.getLatitud()));
                login_params.put("longitud", String.valueOf(marker.getLongitud()));
                login_params.put("atributo1", edit_atri1.getText().toString());
                login_params.put("atributo2", especie_identificada.getText().toString());
                if(checkBox.isChecked() == true){
                    login_params.put("atributo3", "CURIOSIDAD");
                }
                login_params.put("atributo4", "");
                login_params.put("atributo5", "");
                login_params.put("atributo6", "");
                login_params.put("atributo7", "");
                login_params.put("atributo8", "");
                login_params.put("atributo9", "");
                login_params.put("atributo10", "");
                login_params.put("atributo11", "");
                login_params.put("atributo12", "");
                login_params.put("atributo13", "");
                login_params.put("atributo14", "");
                login_params.put("atributo15", "");
                login_params.put("atributo16", "");
                login_params.put("atributo17", "");
                login_params.put("atributo18", "");
                login_params.put("atributo19", "");
                login_params.put("atributo20", "");
                login_params.put("atributo21", "");
                login_params.put("atributo22", "");
                login_params.put("atributo23", "");


                return login_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void deleteMarkerRequest(final Marcador marcador_delete) {

        final LinearLayout cargar = findViewById(R.id.cargando);

        cargar.setVisibility(View.VISIBLE);


        // Url for the webservice
        String url = getString(R.string.base_url) + "/deleteMarcador.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "El marcador ha sido eliminado";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();

                        cargar.setVisibility(View.GONE);

                        recreate();


                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast;
                        CharSequence text;

                        text = "Algo ha ocurrido. Inténtalo más tarde.";
                        toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();

                        // Clean the text fields for new entries

                        cargar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cargar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                CharSequence text;
                text = "Error while login: " + error.getLocalizedMessage() + ".";
                toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                cargar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> login_params = new HashMap<String, String>();

                SessionManager session = new SessionManager(getApplicationContext());
                login_params.put("idUser", String.valueOf(session.getIdUser()));
                login_params.put("token", String.valueOf(session.getToken()));
                login_params.put("id", String.valueOf(marcador_delete.getId()));


                return login_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void cargarCatalogoSetas (Boolean conectado){
        final String idUser, toktok;
        SessionManager session = new SessionManager(Mapa2.this);
        recyclerCatalogoSetas = findViewById(R.id.recycler_catalogo_setas);

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        TiposDeSetasDAO dao = db.tiposDeSetasDAO();
        TiposDeSetasRepository repo = new TiposDeSetasRepositoryImplement(dao);


        String url = getString(R.string.base_url) + "/listaDeSetas.php?idUser=" + idUser + "&token=" + toktok;
        RequestQueue queue = Volley.newRequestQueue(Mapa2.this);
        queue.getCache().clear();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try{
                    int i;
                    System.out.println(response.toString());
                    JSONObject responseJSON = new JSONObject(response);
                    JSONArray jsonArray = responseJSON.getJSONArray("data");

                    for (i=0; i < jsonArray.length(); i++){
                        /*
                        int id = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id")));
                        String titulo = String.valueOf(jsonArray.getJSONObject(i).get("titulo"));
                        String urlFoto = "https://interfungi.ibercivis.es/uploads/proyectos/"+String.valueOf(id)+".jpg";
                        */

                        TiposDeSetas tiposDeSetas = new TiposDeSetas();

                        // JSONArray jsonArray1 = jsonArray.getJSONArray(i); //Diferentes proyectos

                        int id = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id")));
                        int voted = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("voted")));
                        int aportaciones = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("marcadores")));
                        int likes = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("votos")));
                        String title = String.valueOf(jsonArray.getJSONObject(i).get("titulo"));
                        String subtitle = String.valueOf(jsonArray.getJSONObject(i).get("subtitulo"));
                        String description = String.valueOf(jsonArray.getJSONObject(i).get("descripcion"));
                        String web = String.valueOf(jsonArray.getJSONObject(i).get("web"));
                        String URL_imagen = "https://interfungi.ibercivis.es/uploads/proyectos/"+String.valueOf(id)+".jpg";
                        tiposDeSetas.setIdSeta(id);
                        tiposDeSetas.setLegustaSeta(voted);
                        tiposDeSetas.setAportacionesSeta(aportaciones);
                        tiposDeSetas.setLikesSeta(likes);
                        tiposDeSetas.setNombreSeta(title);
                        tiposDeSetas.setCientificoSeta(subtitle);
                        tiposDeSetas.setDescripcionSeta(description);
                        tiposDeSetas.setWebSeta(web);
                        tiposDeSetas.setLogoSeta(URL_imagen);
                        if(repo.findByIdTiposDeSetasRepo(id) == null){
                            tiposDeSetas.setIdSeta(id);
                            tiposDeSetas.setLegustaSeta(voted);
                            tiposDeSetas.setAportacionesSeta(aportaciones);
                            tiposDeSetas.setLikesSeta(likes);
                            tiposDeSetas.setNombreSeta(title);
                            tiposDeSetas.setCientificoSeta(subtitle);
                            tiposDeSetas.setDescripcionSeta(description);
                            tiposDeSetas.setWebSeta(web);
                            tiposDeSetas.setLogoSeta(URL_imagen);

                            new Thread(new Runnable() {
                                public void run() {

                                    String img = saveToInternalStorage(URL_imagen, String.valueOf(tiposDeSetas.getIdSeta())+".jpg", tiposDeSetas, repo);
                                    Log.d("filepath", img);

                                }
                            }).start();

                            // repo.insertTiposDeSetasRepo(tiposDeSetas);
                        }



                        ListaCatalogo.add(tiposDeSetas);
                    }

                    recyclerCatalogoSetas.setHasFixedSize(true);
                    LinearLayoutManager layout = new LinearLayoutManager(Mapa2.this);
                    layout.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerAdapter = new AdaptadorCatalogo(ListaCatalogo, findViewById(R.id.especie_identificada), conectado);
                    recyclerCatalogoSetas.setAdapter(recyclerAdapter);
                    recyclerCatalogoSetas.setLayoutManager(layout);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> signup_params = new HashMap<String, String>();


                return signup_params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    public void cargarCatalogoSetasOffline (Boolean conectado){

        //Creamos instancias de la base de datos
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        TiposDeSetasDAO dao = db.tiposDeSetasDAO();
        TiposDeSetasRepository repo = new TiposDeSetasRepositoryImplement(dao);

        recyclerCatalogoSetas = findViewById(R.id.recycler_catalogo_setas);

        //ListaCatalogo.add(new proyectos(id, titulo, urlFoto));
        listaSetas = repo.getAllTiposDeSetasRepo();

        recyclerCatalogoSetas.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(Mapa2.this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerAdapter = new AdaptadorCatalogo(listaSetas, findViewById(R.id.especie_identificada), conectado);
        recyclerCatalogoSetas.setAdapter(recyclerAdapter);
        recyclerCatalogoSetas.setLayoutManager(layout);



    }

    private String saveToInternalStorage(String myUrl, String name, TiposDeSetas seta, TiposDeSetasRepository repo){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);
        Log.d("mypath", mypath.getAbsolutePath());
        FileOutputStream fos = null;

        //Picasso.with(holder.animation.getContext()).load(urlfoto).into(holder.logo);
        try {
            fos = new FileOutputStream(mypath);
            Bitmap bitmap = Picasso.with(getApplicationContext()).load(myUrl).get();


            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            seta.setLogoSeta(mypath.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        seta.setLogoSeta(mypath.getAbsolutePath());
        repo.insertTiposDeSetasRepo(seta);
        return mypath.getAbsolutePath();
    }

    private void suggestedFix(final ContextWrapper contextWrapper) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            return;
        }
        final File root = contextWrapper.getFilesDir();
        final File osmdroidBasePath = new File(root, "osmdroid");
        osmdroidBasePath.mkdirs();
        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
// NOT: File basePath = new File(getCacheDir().getAbsolutePath(), "osmdroid");
// NOT: osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(getCacheDir().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    };

}

