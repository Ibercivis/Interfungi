package com.ibercivis.interfungiapp.clases;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ibercivis.interfungiapp.R;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;

import java.util.List;

public class ViewHolderCatalogoSetas extends RecyclerView.ViewHolder implements View.OnTouchListener{

    TextView titulo;
    ImageView foto;
    List<TiposDeSetas> ListaCatalogoSetas;
    LinearLayout fondo;
    CardView card;
    int selected_position = -1;
    int last_position = -1;

    public ViewHolderCatalogoSetas(@NonNull View itemView, List<TiposDeSetas> datos) {
        super(itemView);

        ListaCatalogoSetas = datos;
        titulo = itemView.findViewById(R.id.titulo);
        card = itemView.findViewById(R.id.catalogo_setas_card);
        card.setOnTouchListener(this);
        foto= itemView.findViewById(R.id.foto);
        fondo = itemView.findViewById(R.id.fondo_card);


    }

    void fondoNormal(){
    fondo.setBackgroundColor(Color.parseColor("#966f3a"));
    }

    void fondoSeleccionado(){
        fondo.setBackgroundColor(Color.parseColor("#c99d66"));
    }

    public void getPosition2(int position) {
        selected_position = position;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() ==MotionEvent.ACTION_DOWN) {

            int position = getAdapterPosition();
            final TiposDeSetas proyecto = ListaCatalogoSetas.get(position);
            Log.d("Posicion", proyecto.getNombreSeta());
            v.setElevation(8);
            return true;
        }

        v.setElevation(8);
        return true;


    }

}



