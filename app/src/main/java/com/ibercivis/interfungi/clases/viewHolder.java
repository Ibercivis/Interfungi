package com.ibercivis.interfungi.clases;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibercivis.interfungi.BorrarProyecto;
import com.ibercivis.interfungi.DescargarDatos;
import com.ibercivis.interfungi.EditarProyecto;
import com.ibercivis.interfungi.Add;
import com.ibercivis.interfungi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView titulo;
    TextView subtitulo;
    TextView descripcion;
    TextView aportaciones;
    TextView buttonViewOptions;
    TextView web;
    CardView card;
    Button btn;
    LottieAnimationView animation;
    TextView likes;
    ImageView logo;
    ImageView iconCreaObservacion;
    List<proyectos> ListaProyectos;


    public viewHolder(@NonNull View itemView, List<proyectos> datos) {
        super(itemView);

        titulo = itemView.findViewById(R.id.titulo);
        subtitulo = itemView.findViewById(R.id.subtitulo);
        descripcion = itemView.findViewById(R.id.descripcion);
        aportaciones = itemView.findViewById(R.id.numaportaciones);

        animation = itemView.findViewById(R.id.animation_view);
        likes = itemView.findViewById(R.id.numeroLikes);
        ListaProyectos = datos;
        card = itemView.findViewById(R.id.carta);
        logo= itemView.findViewById(R.id.logo_proyecto);
        buttonViewOptions = itemView.findViewById(R.id.textViewOptions);
        web = itemView.findViewById(R.id.sitioweb);
        iconCreaObservacion = itemView.findViewById(R.id.iconCreaObervacion);
        animation.setOnClickListener(this);
        buttonViewOptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        final proyectos proyecto = ListaProyectos.get(position);


        if(v.getId() == buttonViewOptions.getId()){

            PopupMenu popup = new PopupMenu(buttonViewOptions.getContext(), buttonViewOptions);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_card);
            //adding click listener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu1:
                            //handle menu1 click

                            int id = proyecto.getIdProyecto();
                            Intent intent = new Intent(buttonViewOptions.getContext(), DescargarDatos.class);
                            intent.putExtra("idProyecto", id);
                            startActivity(btn.getContext(), intent, null);

                            break;

                        case R.id.menu2:
                            //handle menu1 click

                            int id2 = proyecto.getIdProyecto();
                            Intent intent2 = new Intent(buttonViewOptions.getContext(), BorrarProyecto.class);
                            intent2.putExtra("idProyecto", id2);
                            startActivity(btn.getContext(), intent2, null);

                            break;

                        case R.id.menu3:
                            //handle menu1 click

                            int id3 = proyecto.getIdProyecto();
                            String tituloProyecto = proyecto.getTitulo();
                            String descripcionProyecto = proyecto.getDescripcion();
                            String webProyecto = proyecto.getWeb();
                            int esPrivado = proyecto.getPrivado();
                            String passProyecto = proyecto.getPassword();
                            int tieneLogo = proyecto.hasLogo;
                            String urlLogo = proyecto.logo;
                            Intent intent3 = new Intent(buttonViewOptions.getContext(), EditarProyecto.class);
                            intent3.putExtra("idProyecto", id3);
                            intent3.putExtra("tituloProyecto", tituloProyecto);
                            intent3.putExtra("descripcionProyecto", descripcionProyecto);
                            intent3.putExtra("webProyecto", webProyecto);
                            intent3.putExtra("esPrivado", esPrivado);
                            intent3.putExtra("passProyecto", passProyecto);
                            intent3.putExtra("tieneLogo", tieneLogo);
                            intent3.putExtra("urlLogo", urlLogo);
                            startActivity(btn.getContext(), intent3, null);

                            break;

                    }
                    return false;
                }
            });

            SessionManager session = new SessionManager(buttonViewOptions.getContext());

            if(proyecto.getIdUser() != session.getIdUser()){

                MenuItem item_delete = popup.getMenu().findItem(R.id.menu2);
                MenuItem item_edit = popup.getMenu().findItem(R.id.menu3);
                item_edit.setVisible(false);
                item_delete.setVisible(false);

            }

            popup.show();

        }

        if (v.getId() == iconCreaObservacion.getId()) {



            int id = 48; // TODO: Hardcoded
            String title = proyecto.getTitulo();
            Intent intent = new Intent(iconCreaObservacion.getContext(), Add.class);
            intent.putExtra("idProyecto", id);
            intent.putExtra("tituloProyecto", title);
            startActivity(iconCreaObservacion.getContext(), intent, null);


        }
        if (v.getId() == animation.getId()){
            if(proyecto.isLegusta() == 0) {
                votarProyecto(proyecto, v);
                proyecto.likes = (proyecto.likes + 1);
                likes.setText(Integer.toString(proyecto.likes));

                animation.playAnimation();
                proyecto.setLegusta(1);
            }
            else if(proyecto.isLegusta() == 1){
                votarProyecto(proyecto, v);
                proyecto.likes = (proyecto.likes - 1);
                likes.setText(Integer.toString(proyecto.likes));
                likes.setText(Integer.toString(proyecto.likes));

                animation.setProgress(0.2f);
                animation.pauseAnimation();
                proyecto.setLegusta(0);
            }
        }

    }

    public void votarProyecto (final proyectos proyecto, final View v) {




        // Input data ok, so go with the request

        // Url for the webservice
        String url = "https://interfungi.ibercivis.es/votar.php";

        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response.toString());

                    JSONObject responseJSON = new JSONObject(response);

                    if ((int) responseJSON.get("result") == 1) {

                        System.out.println("Votaci??n correcta");


                    } else {

                        System.out.println("Votaci??n incorrecta. Algo ha salido mal.");

                        // Clean the text fields for new entries


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                CharSequence text;
                text = "Algo ha fallado, vuelva a intentarlo.";
                toast = Toast.makeText(v.getContext(), text, duration);
                toast.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> login_params = new HashMap<String, String>();
                SessionManager session = new SessionManager(v.getContext());
                login_params.put("idUser", String.valueOf(session.getIdUser()));
                login_params.put("token", session.getToken());
                login_params.put("idProyecto", String.valueOf(proyecto.getIdProyecto()));


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

}


