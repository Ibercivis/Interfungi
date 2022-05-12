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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

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

public class VerMarcadorEnMapa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerCatalogoSetas;
    AdaptadorCatalogo recyclerAdapter;
    Toolbar toolbar;
    RelativeLayout paraMapa, relative_marker, layout_marcador;
    LinearLayout layout_foto, atri1, linear_especie, linear_formulario, linear_fotos_ejemplo, linear_ver_ejemplo;
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
    int idProyecto, paso = 1;
    String titleProyecto;
    TextView display_titulo, copyrightTxt, txt_pasos;

    HashMap<String, Marcador> mapaMarcadores = new HashMap<String, Marcador>();
    ArrayList<TiposDeSetas> ListaCatalogo = new ArrayList<>();
    Marcador marcador_pulsado;

    String urlfoto;
    TextView txt_info;
    ImageView btninfo1, btninfo2, btninfo3, btninfo4, btninfo5, btninfo6, btninfo7, btninfo8, btninfo9, btninfo10, btninfo11, btninfo12, btninfo13, btninfo14, btninfo15, btninfo16, btninfo17, btninfo18, btninfo19, btninfo20, btninfo21, btninfo22, btninfo23;
    CheckBox checkBox;
    TextView especie_identificada;
    RadioGroup grupo_agusanamiento, grupo_presion;
    String grado_agusanamiento, grado_presion;

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
    Button backToUser;
    Marker markpress;
    int indice_agusanamiento = -1, indice_presion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_marcador);

        ContextWrapper cw = this;
        suggestedFix(cw);

        /*-----Hooks-----*/
        map = findViewById(R.id.mapMarker);
        drawerLayout = findViewById(R.id.drawer_layoutMarcador);
        navigationView = findViewById(R.id.nav_viewMarcador);
        backToUser = findViewById(R.id.btn_backToUser);
















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

            GpsMyLocationProvider provider = new GpsMyLocationProvider(this.getApplicationContext());
            mLocationOverlay = new MyLocationNewOverlay(provider, map);
            mLocationOverlay.enableMyLocation();
            mLocationOverlay.setOptionsMenuEnabled(true);

            mScaleBarOverlay = new ScaleBarOverlay(map);
            mCopyrightOverlay = new CopyrightOverlay(this);


            map.getOverlays().add(mLocationOverlay);
            map.getOverlays().add(mScaleBarOverlay);
            map.getOverlays().add(this.mCopyrightOverlay);
            map.setMinZoomLevel(8.0);
            map.setMaxZoomLevel(20.0);








            map.setTileSource(TileSourceFactory.MAPNIK);



            Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

            map.setMultiTouchControls(true);

            IMapController mapController = map.getController();
            mapController.setZoom(9);
            map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

            GeoPoint myPoint = mLocationOverlay.getMyLocation();
            mapController.setCenter(myPoint);

            mapController.setZoom(17.00);
            markpress = new Marker(map);

            Bundle extras = getIntent().getExtras();
            double latitud = 0;
            double longitud = 0;
            if (extras != null) {
                latitud = extras.getDouble("latitud");
                longitud = extras.getDouble("longitud");
                //The key argument here must match that used in the other activity
            }

            if(latitud != 0 & longitud != 0) {

                GeoPoint startPoint = new GeoPoint(latitud, longitud);
                Marker startMarker = new Marker(map);
                startMarker.setPosition(startPoint);
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                map.getOverlays().add(startMarker);

                mapController.animateTo(startPoint);

                mapController.setZoom(17.00);

            }




            };

        backToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Usuario.class);
                startActivity(intent);
            }
        });


























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
                Intent intent3 = new Intent(getApplicationContext(), VerMarcadorEnMapa.class);
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
            if (ContextCompat.checkSelfPermission(VerMarcadorEnMapa.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(VerMarcadorEnMapa.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
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

    public void saberLocation(int milisegundos){
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                GeoPoint myPoint = mLocationOverlay.getMyLocation();

                if(myPoint != null) {
                    final Double longitud_reserva = myPoint.getLongitude();
                    final Double latitud_reserva = myPoint.getLatitude();
                }

            }
        }, milisegundos);
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















    public void getMarkersRequest() {
        final LinearLayout cargar = findViewById(R.id.cargando);
        String idUser, toktok;
        int item;
        SessionManager session = new SessionManager(VerMarcadorEnMapa.this);
        cargar.setVisibility(View.VISIBLE);


        // Input data ok, so go with the request

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        String url = getString(R.string.base_url) + "/marcadores.php?idUser=" + idUser + "&token=" + toktok + "&idProyecto=" + idProyecto;

        RequestQueue queue = Volley.newRequestQueue(VerMarcadorEnMapa.this);
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

        SessionManager session = new SessionManager(VerMarcadorEnMapa.this);


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
                login_params.put("atributo3", "");
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
        SessionManager session = new SessionManager(VerMarcadorEnMapa.this);
        recyclerCatalogoSetas = findViewById(R.id.recycler_catalogo_setas);

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        TiposDeSetasDAO dao = db.tiposDeSetasDAO();
        TiposDeSetasRepository repo = new TiposDeSetasRepositoryImplement(dao);


        String url = getString(R.string.base_url) + "/listaDeSetas.php?idUser=" + idUser + "&token=" + toktok;
        RequestQueue queue = Volley.newRequestQueue(VerMarcadorEnMapa.this);
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
                    LinearLayoutManager layout = new LinearLayoutManager(VerMarcadorEnMapa.this);
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
        LinearLayoutManager layout = new LinearLayoutManager(VerMarcadorEnMapa.this);
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
        org.osmdroid.config.IConfigurationProvider osmConf = Configuration.getInstance();
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

    void hacerObservacion (){

    }

}

