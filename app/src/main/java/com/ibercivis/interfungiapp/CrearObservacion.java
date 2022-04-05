package com.ibercivis.interfungiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ibercivis.interfungiapp.R;
import com.ibercivis.interfungiapp.clases.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CrearObservacion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int atributo_added = 0, photo_added = 0, esPrivado = 0, logo_added = 0;
    TextView textomarcadores;
    Button addLogo, addtextatribute, addnumberatribute, createproject;
    LinearLayout phot, atri1, atri2, atri3, atri4, atri5, atri6, atri7, atri8, atri9, atri10, atri11, atri12, atri13, atri14, atri15, atri16, atri17, atri18, atri19, atri20, atri21, atri22, atri23;
    Integer isText1 = 0, isText2 = 0, isText3 = 0, isText4 = 0, isText5 = 0, isText6 = 0, isText7 = 0, isText8 = 0, isText9 = 0, isText10 = 0, isText11 = 0, isText12 = 0, isText13 = 0, isText14 = 0, isText15 = 0, isText16 = 0, isText17 = 0, isText18 = 0, isText19 = 0, isText20 = 0, isText21 = 0, isText22 = 0, isText23 = 0;
    ImageView logo_miniatura, cancelatri1, cancelatri2, cancelatri3, cancelatri4, cancelatri5, cancelatri6, cancelatri7, cancelatri8, cancelatri9, cancelatri10, cancelatri11, cancelatri12, cancelatri13, cancelatri14, cancelatri15, cancelatri16, cancelatri17, cancelatri18, cancelatri19, cancelatri20, cancelatri21, cancelatri22, cancelatri23;
    TextInputEditText atributo1, atributo2, atributo3, atributo4, atributo5, atributo6, atributo7, atributo8, atributo9, atributo10, atributo11, atributo12, atributo13, atributo14, atributo15, atributo16, atributo17, atributo18, atributo19, atributo20, atributo21, atributo22, atributo23;
    TextInputEditText desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12, desc13, desc14, desc15, desc16, desc17, desc18, desc19, desc20, desc21, desc22, desc23;
    TextInputLayout pass_privado, hintatributo1, hintatributo2, hintatributo3, hintatributo4, hintatributo5, hintatributo6, hintatributo7, hintatributo8, hintatributo9, hintatributo10, hintatributo11, hintatributo12, hintatributo13, hintatributo14, hintatributo15, hintatributo16, hintatributo17, hintatributo18, hintatributo19, hintatributo20, hintatributo21, hintatributo22, hintatributo23;
    TextInputEditText titulo_proyecto, descripcion_proyecto, web_proyecto, pass_proyecto;
    Switch foto, switch_privado;

    String error_check;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String base64String="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_observacion);

        /*-----Hooks-----*/

        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view3);
        toolbar = findViewById(R.id.toolbar3);
        textomarcadores = findViewById(R.id.texto_marcadores);

        logo_miniatura = findViewById(R.id.miniatura_logo);

        //Buttons
        foto = findViewById(R.id.switchfoto);
        addtextatribute = findViewById(R.id.add_textatribute);
        addnumberatribute = findViewById(R.id.add_numberatribute);
        createproject = findViewById(R.id.btn_crearproyecto);
        addLogo = findViewById(R.id.upload_logo);



        /*-----Toolbar-----*/
        setSupportActionBar(toolbar);
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
        navigationView.setCheckedItem(R.id.nav_crear);

        /*----- Para los botones -----*/


        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        logo_miniatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_projects:
                Intent intent2 = new Intent(getApplicationContext(), ListadoProyectos.class);
                startActivity(intent2);
                break;
            case R.id.nav_crear:

                break;

            case  R.id.nav_misproyectos:
                Intent intent5 = new Intent(getApplicationContext(), Usuario.class);
                startActivity(intent5);
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

    public void crearProyecto(int numat1, int foto, EditText at1, EditText at2, EditText at3, EditText at4, EditText at5, EditText at6, EditText at7, EditText at8, EditText at9, EditText at10, EditText at11, EditText at12, EditText at13, EditText at14, EditText at15, EditText at16){

        String atrib1 = at1.getText().toString(); String atrib9 = at1.getText().toString();
        String atrib2 = at2.getText().toString(); String atrib10 = at1.getText().toString();
        String atrib3 = at3.getText().toString(); String atrib11 = at1.getText().toString();
        String atrib4 = at4.getText().toString(); String atrib12 = at1.getText().toString();
        String atrib5 = at5.getText().toString(); String atrib13 = at1.getText().toString();
        String atrib6 = at6.getText().toString(); String atrib14 = at1.getText().toString();
        String atrib7 = at7.getText().toString(); String atrib15 = at1.getText().toString();
        String atrib8 = at8.getText().toString(); String atrib16 = at1.getText().toString();


        // Marcador marcadorpersonalizado = new Marcador(numat1, foto, atrib1, atrib2, atrib3, atrib4, atrib5, atrib6, atrib7, atrib8, atrib9, atrib10, atrib11, atrib12, atrib13, atrib14, atrib15, atrib16);


    }

    public void addprojectRequest (View view) {
        final LinearLayout cargar = findViewById(R.id.cargando);

        atributo1 = findViewById(R.id.edit_atributo1); atributo9 = findViewById(R.id.edit_atributo9);
        atributo2 = findViewById(R.id.edit_atributo2); atributo10 = findViewById(R.id.edit_atributo10);
        atributo3 = findViewById(R.id.edit_atributo3); atributo11 = findViewById(R.id.edit_atributo11);
        atributo4 = findViewById(R.id.edit_atributo4); atributo12 = findViewById(R.id.edit_atributo12);
        atributo5 = findViewById(R.id.edit_atributo5); atributo13 = findViewById(R.id.edit_atributo13);
        atributo6 = findViewById(R.id.edit_atributo6); atributo14 = findViewById(R.id.edit_atributo14);
        atributo7 = findViewById(R.id.edit_atributo7); atributo15 = findViewById(R.id.edit_atributo15);
        atributo8 = findViewById(R.id.edit_atributo8); atributo16 = findViewById(R.id.edit_atributo16);

        titulo_proyecto = findViewById(R.id.project_titulo);
        descripcion_proyecto = findViewById(R.id.project_description);
        web_proyecto = findViewById(R.id.project_web);
        pass_proyecto = findViewById(R.id.project_password);
        cargar.setVisibility(View.VISIBLE);

        if(atributo_added != 0) {
            if (checkInputLogin()) {
                String url = getString(R.string.base_url) + "/crearProyecto.php";

                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response.toString());

                            JSONObject responseJSON = new JSONObject(response);

                            if ((int) responseJSON.get("result") == 1) {

                                cargar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.crear63), Toast.LENGTH_SHORT).show();
                                openMain();


                            } else {
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast;
                                CharSequence text;

                                text = "Error subiendo proyecto: " + responseJSON.get("message") + ".";
                                toast = Toast.makeText(getApplicationContext(), text, duration);
                                toast.show();

                                // Clean the text fields for new entries
                                //login_username_textview.setText("");
                                //login_password_textview.setText("");
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
                        login_params.put("titulo", titulo_proyecto.getText().toString());
                        login_params.put("descripcion", descripcion_proyecto.getText().toString());
                        login_params.put("web", web_proyecto.getText().toString());
                        login_params.put("privado", String.valueOf(esPrivado));
                        login_params.put("password", pass_proyecto.getText().toString());
                        login_params.put("numAtributos", String.valueOf(atributo_added));
                        login_params.put("foto", String.valueOf(photo_added));
                        login_params.put("atributo1", atributo1.getText().toString());
                        login_params.put("atributo2", atributo2.getText().toString());
                        login_params.put("atributo3", atributo3.getText().toString());
                        login_params.put("atributo4", atributo4.getText().toString());
                        login_params.put("atributo5", atributo5.getText().toString());
                        login_params.put("atributo6", atributo6.getText().toString());
                        login_params.put("atributo7", atributo7.getText().toString());
                        login_params.put("atributo8", atributo8.getText().toString());
                        login_params.put("atributo9", atributo9.getText().toString());
                        login_params.put("atributo10", atributo10.getText().toString());
                        login_params.put("atributo11", atributo11.getText().toString());
                        login_params.put("atributo12", atributo12.getText().toString());
                        login_params.put("atributo13", atributo13.getText().toString());
                        login_params.put("atributo14", atributo14.getText().toString());
                        login_params.put("atributo15", atributo15.getText().toString());
                        login_params.put("atributo16", atributo16.getText().toString());
                        login_params.put("atributo17", atributo17.getText().toString());
                        login_params.put("atributo18", atributo18.getText().toString());
                        login_params.put("atributo19", atributo19.getText().toString());
                        login_params.put("atributo20", atributo20.getText().toString());
                        login_params.put("atributo21", atributo21.getText().toString());
                        login_params.put("atributo22", atributo22.getText().toString());
                        login_params.put("atributo23", atributo23.getText().toString());
                        login_params.put("isText1", String.valueOf(isText1));
                        login_params.put("isText2", String.valueOf(isText2));
                        login_params.put("isText3", String.valueOf(isText3));
                        login_params.put("isText4", String.valueOf(isText4));
                        login_params.put("isText5", String.valueOf(isText5));
                        login_params.put("isText6", String.valueOf(isText6));
                        login_params.put("isText7", String.valueOf(isText7));
                        login_params.put("isText8", String.valueOf(isText8));
                        login_params.put("isText9", String.valueOf(isText9));
                        login_params.put("isText10", String.valueOf(isText10));
                        login_params.put("isText11", String.valueOf(isText11));
                        login_params.put("isText12", String.valueOf(isText12));
                        login_params.put("isText13", String.valueOf(isText13));
                        login_params.put("isText14", String.valueOf(isText14));
                        login_params.put("isText15", String.valueOf(isText15));
                        login_params.put("isText16", String.valueOf(isText16));
                        login_params.put("isText17", String.valueOf(isText17));
                        login_params.put("isText18", String.valueOf(isText18));
                        login_params.put("isText19", String.valueOf(isText19));
                        login_params.put("isText20", String.valueOf(isText20));
                        login_params.put("isText21", String.valueOf(isText21));
                        login_params.put("isText22", String.valueOf(isText22));
                        login_params.put("isText23", String.valueOf(isText23));
                        login_params.put("desc1", desc1.getText().toString());
                        login_params.put("desc2", desc2.getText().toString());
                        login_params.put("desc3", desc3.getText().toString());
                        login_params.put("desc4", desc4.getText().toString());
                        login_params.put("desc5", desc5.getText().toString());
                        login_params.put("desc6", desc6.getText().toString());
                        login_params.put("desc7", desc7.getText().toString());
                        login_params.put("desc8", desc8.getText().toString());
                        login_params.put("desc9", desc9.getText().toString());
                        login_params.put("desc10", desc10.getText().toString());
                        login_params.put("desc11", desc11.getText().toString());
                        login_params.put("desc12", desc12.getText().toString());
                        login_params.put("desc13", desc13.getText().toString());
                        login_params.put("desc14", desc14.getText().toString());
                        login_params.put("desc15", desc15.getText().toString());
                        login_params.put("desc16", desc16.getText().toString());
                        login_params.put("desc17", desc17.getText().toString());
                        login_params.put("desc18", desc18.getText().toString());
                        login_params.put("desc19", desc19.getText().toString());
                        login_params.put("desc20", desc20.getText().toString());
                        login_params.put("desc21", desc21.getText().toString());
                        login_params.put("desc22", desc22.getText().toString());
                        login_params.put("desc23", desc23.getText().toString());
                        login_params.put("logo", base64String);

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
            } else {
                // Do nothing, error has been shown in a toast and views clean
                cargar.setVisibility(View.GONE);
            }
        } else {
            cargar.setVisibility(View.GONE);
            int duration = Toast.LENGTH_SHORT;
            Toast toast;
            CharSequence text;
            text = getResources().getString(R.string.crear64);
            toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();

        }

    }

    /** Open MAIN Activity */
    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void showError (CharSequence error) {
        int duration = Toast.LENGTH_LONG;
        Toast toast;

        toast = Toast.makeText(getApplicationContext(), error, duration);
        toast.show();
    }

    private boolean checkLength( String text, String fieldName, int min, int max ) {
        if ( text.length() > max || text.length() < min ) {
            error_check = error_check + "El parámetro" + fieldName + " debe tener entre " +
                    min + " y " + max + " caracteres.\n";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkRegexp(String text, Pattern regexp, String errorMessage) {
        if (!regexp.matcher(text).matches()) {
            error_check = error_check + errorMessage + "\n";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkInputLogin () {

        error_check = "";
        boolean valid = true;

        // valid is evaluated in the second part to force the check function being called always, so all the errors are displayed at the same time (&& conditional evaluation)
        valid = checkLength( titulo_proyecto.getText().toString(), "Titulo", 1, 100 ) && valid;
        valid = checkLength( descripcion_proyecto.getText().toString(), "Descripcion", 1, 250 ) && valid;
//"/^[a-z]([0-9a-z_ ])+$/i"
        // In the regular expression for the username and password we do not use {3,16} (for instance),
        // to control the length through the regex, since it is most accurate to indicate the length error
        // separately, so it is not considered the length in the regex (it has been taken into account previously
        if(esPrivado == 1){

            valid = checkLength( pass_proyecto.getText().toString(), "Password", 3, 16 ) && valid;

        }

        if(atributo_added == 1){
            valid = checkLength( atributo1.getText().toString(), "Atributo1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 2){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 3){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 4){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 5){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 6){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 7){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 8){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 9){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 10){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 11){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            // valid = checkRegexp( atributo11.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 12){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            // valid = checkRegexp( atributo11.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            // valid = checkRegexp( atributo12.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 13){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            // valid = checkRegexp( atributo11.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            // valid = checkRegexp( atributo12.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            // valid = checkRegexp( atributo13.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 14){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            // valid = checkRegexp( atributo11.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            // valid = checkRegexp( atributo12.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            // valid = checkRegexp( atributo13.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            // valid = checkRegexp( atributo14.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 15){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            // valid = checkRegexp( atributo1.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            // valid = checkRegexp( atributo2.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            // valid = checkRegexp( atributo3.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            // valid = checkRegexp( atributo4.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            // valid = checkRegexp( atributo5.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            // valid = checkRegexp( atributo6.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            // valid = checkRegexp( atributo7.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            // valid = checkRegexp( atributo8.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            // valid = checkRegexp( atributo9.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            // valid = checkRegexp( atributo10.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            // valid = checkRegexp( atributo11.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            // valid = checkRegexp( atributo12.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            // valid = checkRegexp( atributo13.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            // valid = checkRegexp( atributo14.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            // valid = checkRegexp( atributo15.getText().toString(), Pattern.compile("^[a-zA-Z][a-zA-Z0-9 _]+$"), "Los atributos deben estar completados adecuadamente." ) && valid;

        } else if(atributo_added == 16){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
        } else if(atributo_added == 17){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;

        } else if(atributo_added == 18){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;

        } else if(atributo_added == 19){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;
            valid = checkLength( atributo19.getText().toString(), "Atributo 19", 1, 100 ) && valid;

        } else if(atributo_added == 20){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;
            valid = checkLength( atributo19.getText().toString(), "Atributo 19", 1, 100 ) && valid;
            valid = checkLength( atributo20.getText().toString(), "Atributo 20", 1, 100 ) && valid;

        } else if(atributo_added == 21){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;
            valid = checkLength( atributo19.getText().toString(), "Atributo 19", 1, 100 ) && valid;
            valid = checkLength( atributo20.getText().toString(), "Atributo 20", 1, 100 ) && valid;
            valid = checkLength( atributo21.getText().toString(), "Atributo 21", 1, 100 ) && valid;

        } else if(atributo_added == 22){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;
            valid = checkLength( atributo19.getText().toString(), "Atributo 19", 1, 100 ) && valid;
            valid = checkLength( atributo20.getText().toString(), "Atributo 20", 1, 100 ) && valid;
            valid = checkLength( atributo21.getText().toString(), "Atributo 21", 1, 100 ) && valid;
            valid = checkLength( atributo22.getText().toString(), "Atributo 22", 1, 100 ) && valid;

        } else if(atributo_added == 23){
            valid = checkLength( atributo1.getText().toString(), "Atributo 1", 1, 100 ) && valid;
            valid = checkLength( atributo2.getText().toString(), "Atributo 2", 1, 100 ) && valid;
            valid = checkLength( atributo3.getText().toString(), "Atributo 3", 1, 100 ) && valid;
            valid = checkLength( atributo4.getText().toString(), "Atributo 4", 1, 100 ) && valid;
            valid = checkLength( atributo5.getText().toString(), "Atributo 5", 1, 100 ) && valid;
            valid = checkLength( atributo6.getText().toString(), "Atributo 6", 1, 100 ) && valid;
            valid = checkLength( atributo7.getText().toString(), "Atributo 7", 1, 100 ) && valid;
            valid = checkLength( atributo8.getText().toString(), "Atributo 8", 1, 100 ) && valid;
            valid = checkLength( atributo9.getText().toString(), "Atributo 9", 1, 100 ) && valid;
            valid = checkLength( atributo10.getText().toString(), "Atributo 10", 1, 100 ) && valid;
            valid = checkLength( atributo11.getText().toString(), "Atributo 11", 1, 100 ) && valid;
            valid = checkLength( atributo12.getText().toString(), "Atributo 12", 1, 100 ) && valid;
            valid = checkLength( atributo13.getText().toString(), "Atributo 13", 1, 100 ) && valid;
            valid = checkLength( atributo14.getText().toString(), "Atributo 14", 1, 100 ) && valid;
            valid = checkLength( atributo15.getText().toString(), "Atributo 15", 1, 100 ) && valid;
            valid = checkLength( atributo16.getText().toString(), "Atributo 16", 1, 100 ) && valid;
            valid = checkLength( atributo17.getText().toString(), "Atributo 17", 1, 100 ) && valid;
            valid = checkLength( atributo18.getText().toString(), "Atributo 18", 1, 100 ) && valid;
            valid = checkLength( atributo19.getText().toString(), "Atributo 19", 1, 100 ) && valid;
            valid = checkLength( atributo20.getText().toString(), "Atributo 20", 1, 100 ) && valid;
            valid = checkLength( atributo21.getText().toString(), "Atributo 21", 1, 100 ) && valid;
            valid = checkLength( atributo22.getText().toString(), "Atributo 22", 1, 100 ) && valid;
            valid = checkLength( atributo23.getText().toString(), "Atributo 23", 1, 100 ) && valid;
        }




        if (!error_check.equals("")){
            showError(error_check);
        }

        return valid;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                logo_added = 1;
                base64String = getBase64String(bitmap); // foto en base64
                logo_miniatura.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            logo_added = 1;
            //logo_miniatura.setImageBitmap();
        }
    }

    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

}
