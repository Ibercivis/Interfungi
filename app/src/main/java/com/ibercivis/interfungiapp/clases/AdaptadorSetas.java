package com.ibercivis.interfungiapp.clases;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibercivis.interfungiapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorSetas extends RecyclerView.Adapter<ViewHolderSetas> {

    List<Marcador> ListaObjeto;
    LinearLayout marco_visualizar;
    ImageView fotoView;

    public AdaptadorSetas(List<Marcador> listaObjeto, LinearLayout marco_visualizar, ImageView fotoView) {
        ListaObjeto = listaObjeto;
        this.marco_visualizar = marco_visualizar;
        this.fotoView = fotoView;
    }

    @NonNull
    @Override
    public ViewHolderSetas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.marcador_card, parent, false);
        return new ViewHolderSetas(vista, ListaObjeto);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSetas holder, int position) {

        Log.d("AdaptadorSetas", "OnBindViewHolder");
        holder.titulo.setText(ListaObjeto.get(position).getAtributo2());
        holder.titulo2.setText(ListaObjeto.get(position).getFechaCorte());
        String photo_0 = ListaObjeto.get(position).photo_0;
        String photo_1 = ListaObjeto.get(position).photo_1;
        Picasso.with(holder.photo_0.getContext()).load(photo_0).into(holder.photo_0);
        Picasso.with(holder.photo_1.getContext()).load(photo_1).into(holder.photo_1);
        holder.photo_0.setClipToOutline(true);
        holder.photo_1.setClipToOutline(true);


        holder.photo_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo_0 != null){
                    marco_visualizar.setVisibility(View.VISIBLE);
                    Picasso.with(fotoView.getContext()).load(photo_0).into(fotoView);
                }
            }
        });

        holder.photo_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo_1 != null){
                    marco_visualizar.setVisibility(View.VISIBLE);
                    Picasso.with(fotoView.getContext()).load(photo_1).into(fotoView);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

    public void setFilter(ArrayList<Marcador> listaFiltrada){

        this.ListaObjeto = new ArrayList<>();
        this.ListaObjeto.addAll(listaFiltrada);
        notifyDataSetChanged();

    }



}
