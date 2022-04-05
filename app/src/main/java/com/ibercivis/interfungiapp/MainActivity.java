package com.ibercivis.interfungiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ibercivis.interfungiapp.clases.SessionManager;
import com.ibercivis.interfungiapp.R;
import com.ibercivis.interfungiapp.db.AppDatabase;
import com.ibercivis.interfungiapp.db.DAO.TiposDeSetasDAO;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;
import com.ibercivis.interfungiapp.repository.TiposDeSetasRepository;
import com.ibercivis.interfungiapp.repository.TiposDeSetasRepositoryImplement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    CardView card_proyectos, card_crear, card_usuario, card_quienes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-----Hooks-----*/


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        card_proyectos = findViewById(R.id.proyectos_card);
        card_crear = findViewById(R.id.crear_card);
        card_quienes = findViewById(R.id.quienes_card);
        card_usuario = findViewById(R.id.usuario_card);

        CardView linkWebsite = findViewById(R.id.website);

        /*-----Toolbar-----*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*-----Navigation Drawer Menu -----*/

        //Hide or show items

        Menu menu = navigationView.getMenu();

        menu.findItem(R.id.nav_logout).setVisible(true);

        //Fit Navigation Drawer

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open_drawer, R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        /*-----MÉTODOS PROPIOS DE ESTA ACTIVITY-----*/

        card_proyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListadoProyectos.class);
                startActivity(intent);
            }
        });

        card_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Mapa2.class);
                startActivity(intent2);
            }
        });

        card_quienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), QuienesSomos.class);
                startActivity(intent3);
            }
        });

        card_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), Usuario.class);
                startActivity(intent4);
            }
        });

        linkWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.micoaragon.es/informacion/proyecto-interfungi");
            }
        });

        //PERMISOS

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);

               // recreate();

                return;
            }   if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

                // recreate();

                return;
            }  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

               // recreate();

                return;
            }  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);

              //  recreate();

                return;
            }
        }

        //ACTUALIZAR BASE DE DATOS EN CASO DE QUE HAYA CONEXIÓN

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        if(connected == true) {
            //Hay conexión a internet. Descargamos el catálogo y, en su caso, actualizamos la base de datos.
            getInfoRequest(connected);
        } else {

        }

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

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_projects:
                Intent intent = new Intent(getApplicationContext(), ListadoProyectos.class);
                startActivity(intent);
                break;
            case R.id.nav_crear:
                Intent intent2 = new Intent(getApplicationContext(), Mapa2.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                SessionManager session = new SessionManager(getApplicationContext());
                session.setClear();
                Intent intent4 = new Intent(getApplicationContext(), Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    public void getInfoRequest (Boolean conectado) {
        final LinearLayout cargar = findViewById(R.id.cargando);
        String idUser, toktok;
        int item;
        SessionManager session = new SessionManager(MainActivity.this);
        cargar.setVisibility(View.VISIBLE);


        //Creamos instancias de la base de datos
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        TiposDeSetasDAO dao = db.tiposDeSetasDAO();
        TiposDeSetasRepository repo = new TiposDeSetasRepositoryImplement(dao);

        /*
        //A modo de prueba, vacío la base de datos.
        listaSetas = repo.getAllTiposDeSetasRepo();
        repo.deleteAllTiposDeSetasRepo(listaSetas);
        */

        // Input data ok, so go with the request

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        String url = getString(R.string.base_url) + "/listaDeSetas.php?idUser=" + idUser +"&token=" + toktok;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.getCache().clear();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1){

                        int value;
                        int i = 0;
                        JSONArray jsonArray = responseJSON.getJSONArray("data");

                        for (i=0; i < jsonArray.length(); i++){

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


                        }

                        cargar.setVisibility(View.GONE);


                        // usernametxt.setText(user);


                    }
                    else {
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
                cargar.setVisibility(View.GONE);
            }
        }){
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

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}


