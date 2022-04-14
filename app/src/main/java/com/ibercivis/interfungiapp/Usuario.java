package com.ibercivis.interfungiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ibercivis.interfungiapp.clases.AdaptadorSetas;
import com.ibercivis.interfungiapp.clases.Marcador;
import com.ibercivis.interfungiapp.clases.SessionManager;
import com.ibercivis.interfungiapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Usuario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //Propios de esta Activity
    RecyclerView recyclerListaSetas;
    AdaptadorSetas recyclerAdapter;
    ArrayList<Marcador> ListaSetas = new ArrayList<>();
    LinearLayout marco_photo;
    ImageView photoView;
    Button backViewButton;

    TextView texto_noHayGustan, texto_noHayPropios;

    Bitmap bitmap_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        /*-----Hooks-----*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        marco_photo = findViewById(R.id.marco_visualizar_foto);
        photoView = findViewById(R.id.foto_ver);
        backViewButton = findViewById(R.id.foto_volver);

        /*-----Toolbar-----*/
        setSupportActionBar(toolbar);


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
        navigationView.setCheckedItem(R.id.nav_misproyectos);

        backViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marco_photo.setVisibility(View.GONE);
            }
        });

        getUserRequest();

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
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
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


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void getUserRequest () {
        final LinearLayout cargar = findViewById(R.id.cargando);
        final String idUser, toktok;
        int item;
        SessionManager session = new SessionManager(Usuario.this);
        cargar.setVisibility(View.VISIBLE);
        recyclerListaSetas = findViewById(R.id.recyclerproyectosgustan);

        // Input data ok, so go with the request

        // Url for the webservice
        idUser = String.valueOf(session.getIdUser());
        toktok = session.getToken();

        String url = getString(R.string.base_url) + "/getMySetas.php?idUser=" + idUser +"&token=" + toktok;

        RequestQueue queue = Volley.newRequestQueue(Usuario.this);
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

                            // JSONArray jsonArray1 = jsonArray.getJSONArray(i); //Diferentes proyectos


                            int id = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("id")));
                            String photo_0 = "https://interfungi.ibercivis.es/uploads/marcadores/"+String.valueOf(id)+".jpg";
                            String photo_1 = "https://interfungi.ibercivis.es/uploads/marcadores/"+String.valueOf(id)+"_1.jpg";
                            String fechaCorte = String.valueOf(jsonArray.getJSONObject(i).get("fechaCorte"));
                            String atributo2 = String.valueOf(jsonArray.getJSONObject(i).get("atributo2"));
                            ListaSetas.add(new Marcador(id, atributo2, fechaCorte, photo_0, photo_1));

                        }

                        if(ListaSetas.size() > 0){
                            Log.d("ListaSetas", ListaSetas.toString());
                            // texto_noHayGustan.setVisibility(View.GONE);
                            recyclerListaSetas.setVisibility(View.VISIBLE);
                            recyclerListaSetas.setHasFixedSize(true);
                            recyclerAdapter = new AdaptadorSetas(ListaSetas, marco_photo, photoView);
                            recyclerListaSetas.setAdapter(recyclerAdapter);
                            LinearLayoutManager layout = new LinearLayoutManager(Usuario.this);
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerListaSetas.setLayoutManager(layout);
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

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap;

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap_logo = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
        }
    }

}


