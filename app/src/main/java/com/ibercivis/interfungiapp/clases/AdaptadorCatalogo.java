package com.ibercivis.interfungiapp.clases;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibercivis.interfungiapp.R;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorCatalogo extends RecyclerView.Adapter<ViewHolderCatalogoSetas> {

    List<TiposDeSetas> ListaObjeto;
    int selected_position = -1;
    private TextView especie_identificada;
    Boolean Connected;

    public AdaptadorCatalogo(List<TiposDeSetas> listaObjeto, TextView especie_identificada, Boolean connected) {
        ListaObjeto = listaObjeto;
        this.especie_identificada = especie_identificada;
        Connected = connected;
    }

    @NonNull
    @Override
    public ViewHolderCatalogoSetas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogo_setas_card, parent, false);
        return new ViewHolderCatalogoSetas(vista, ListaObjeto);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCatalogoSetas holder, @SuppressLint("RecyclerView") int position) {

        Log.d("AdaptadorSetas", "OnBindViewHolder");
        Log.d("AdaptadorSetasTitulo", ListaObjeto.get(position).getNombreSeta());
        holder.titulo.setText(ListaObjeto.get(position).getNombreSeta());
        String urlfoto = ListaObjeto.get(position).getLogoSeta();

        if(Connected==true){
            //HAY CONEXIÓN, CARGAMOS DESDE URL
            Picasso.with(holder.foto.getContext()).load(urlfoto).into(holder.foto);
        } else {
            //NO HAY CONEXIÓN, CARGAMOS DESDE DB LOCAL
            Picasso.with(holder.foto.getContext()).load(new File(urlfoto)).into(holder.foto);
        }

       // Picasso.with(holder.foto.getContext()).load(foto).into(holder.foto);

        holder.card.setOnTouchListener(new View.OnTouchListener(){
            @Override public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() ==MotionEvent.ACTION_DOWN) {
                    especie_identificada.setText(ListaObjeto.get(position).getNombreSeta());
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

    public void setFilter(ArrayList<TiposDeSetas> listaFiltrada){

        this.ListaObjeto = new ArrayList<>();
        this.ListaObjeto.addAll(listaFiltrada);
        notifyDataSetChanged();

    }

}
