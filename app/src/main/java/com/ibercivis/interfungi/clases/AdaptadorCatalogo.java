package com.ibercivis.interfungi.clases;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibercivis.interfungi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCatalogo extends RecyclerView.Adapter<ViewHolderCatalogoSetas> {

    List<proyectos> ListaObjeto;
    int selected_position = -1;
    private TextView especie_identificada;

    public AdaptadorCatalogo(List<proyectos> listaObjeto, TextView especie_identificada) {
        ListaObjeto = listaObjeto;
        this.especie_identificada = especie_identificada;
    }

    @NonNull
    @Override
    public ViewHolderCatalogoSetas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogo_setas_card, parent, false);
        return new ViewHolderCatalogoSetas(vista, ListaObjeto);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCatalogoSetas holder, int position) {

        Log.d("AdaptadorSetas", "OnBindViewHolder");
        Log.d("AdaptadorSetasTitulo", ListaObjeto.get(position).getTitulo());
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        String foto = ListaObjeto.get(position).getLogo();
        Picasso.with(holder.foto.getContext()).load(foto).into(holder.foto);
        holder.card.setOnTouchListener(new View.OnTouchListener(){
            @Override public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() ==MotionEvent.ACTION_DOWN) {
                    especie_identificada.setText(ListaObjeto.get(position).getTitulo());
                    v.setElevation(0);
                    return true;
                }
                v.setElevation(5);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

    public void setFilter(ArrayList<proyectos> listaFiltrada){

        this.ListaObjeto = new ArrayList<>();
        this.ListaObjeto.addAll(listaFiltrada);
        notifyDataSetChanged();

    }

}
