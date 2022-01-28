package com.ibercivis.interfungi.clases;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.ibercivis.interfungi.clases.Marcador;
import com.ibercivis.interfungi.clases.SessionManager;
import com.ibercivis.interfungi.clases.proyectos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class ViewHolderCatalogoSetas extends RecyclerView.ViewHolder implements View.OnTouchListener{

    TextView titulo;
    ImageView foto;
    List<proyectos> ListaCatalogoSetas;
    CardView card;

    public ViewHolderCatalogoSetas(@NonNull View itemView, List<proyectos> datos) {
        super(itemView);

        ListaCatalogoSetas = datos;
        titulo = itemView.findViewById(R.id.titulo);
        card = itemView.findViewById(R.id.catalogo_setas_card);
        card.setOnTouchListener(this);
        foto= itemView.findViewById(R.id.foto);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() ==MotionEvent.ACTION_DOWN) {

            int position = getAdapterPosition();
            final proyectos proyecto = ListaCatalogoSetas.get(position);
            Log.d("Posicion", proyecto.getTitulo());
            v.setElevation(0);
            return true;
        }

        v.setElevation(5);
        return true;


    }

}



