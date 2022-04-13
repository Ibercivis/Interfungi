package com.ibercivis.interfungiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ibercivis.interfungiapp.clases.JustifyTextView;
import com.ibercivis.interfungiapp.clases.SessionManager;
import com.ibercivis.interfungiapp.R;

public class QuienesSomos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quienes_somos);

        /*-----Hooks-----*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

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
        JustifyTextView txtquienes = findViewById(R.id.texto_quienes);
        txtquienes.setText("Aragón es una de las comunidades de Europa donde la recolección de setas está más arraigada entre su sociedad. De esa afición surge Interfungi, un proyecto de ciencia ciudadana con los siguientes objetivos:\n" +
                "\n" +
                "- Preservar los conocimientos micológicos y transmitirlos a las generaciones más jóvenes. Es decir, conseguir que todo lo que saben los mayores no se pierda\n" +
                "\n" +
                "- Conseguir datos (a través de observaciones) que ayudará a los investigadores a determinar el estado y calidad de las zonas de producción. Por ejemplo, creando mapas de presión recolectora.\n" +
                "\n" +
                "- Acercar el potencial de las zonas rurales a aquellos que no están familiarizados con esos conocimientos.\n" +
                "\n" +
                "¿Quieres ser uno de nuestros observadores?\n" +
                "¡Con tu participación estás ayudando a conocer y proteger las setas de Aragón!\n" +
                "\n" +
                "Interfungi surge del trabajo conjunto de la Fundación Ibercivis y del Centro de Investigación y Tecnología Agroalimentaria de Aragón (CITA).\n" +
                "\n" +
                "El proyecto ha sido financiado gracias a FECYT, Fundación Española de Ciencia y Tecnología - Ministerio de Ciencia e Innovación. ");



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
                Intent intent3 = new Intent(getApplicationContext(), Mapa2.class);
                startActivity(intent3);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
