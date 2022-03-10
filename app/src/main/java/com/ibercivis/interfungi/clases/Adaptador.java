package com.ibercivis.interfungi.clases;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibercivis.interfungi.R;
import com.ibercivis.interfungi.db.entities.TiposDeSetas;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<viewHolder> {


    List<TiposDeSetas> ListaObjeto;
    Boolean Connected;

    public Adaptador(List<TiposDeSetas> listaObjeto, Boolean connected) {
        ListaObjeto = listaObjeto;
        Connected = connected;

    }





    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.proyectos_card, parent, false);
        return new viewHolder(vista, ListaObjeto);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.titulo.setText(ListaObjeto.get(position).getNombreSeta());
        holder.subtitulo.setText(ListaObjeto.get(position).getCientificoSeta());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.descripcion.setText(Html.fromHtml(ListaObjeto.get(position).getDescripcionSeta(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.descripcion.setText(Html.fromHtml(ListaObjeto.get(position).getDescripcionSeta()));
        }
        // holder.descripcion.setText(ListaObjeto.get(position).getDescripcion());
        holder.aportaciones.setText(Integer.toString(ListaObjeto.get(position).getAportacionesSeta()));

        /*
        if (ListaObjeto.get(position).getWebSeta() != ""){

            holder.web.setText(ListaObjeto.get(position).getWebSeta());
            holder.web.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            holder.web.setVisibility(View.GONE);
        }

         */

        String urlfoto = ListaObjeto.get(position).getLogoSeta();
        // Bitmap foto = new DownloadFilesTask().execute(urlfoto);

        //Comprobamos el estado de la conexión para cargar las fotos desde DB o desde URL
        if(Connected==true){
            //HAY CONEXIÓN, CARGAMOS DESDE URL
            Picasso.with(holder.logo.getContext()).load(urlfoto).into(holder.logo);
        } else {
            //NO HAY CONEXIÓN, CARGAMOS DESDE DB LOCAL
            Picasso.with(holder.logo.getContext()).load(new File(urlfoto)).into(holder.logo);
        }


        //ContextWrapper cw = new ContextWrapper(holder.animation.getContext());


        /* Esto era para que cargase las imagenes desde local
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,"profile.jpg");
        Log.d("adaptador",mypath.getAbsolutePath());
        Picasso.with(holder.animation.getContext()).load(mypath).into(holder.logo); */
        /*
        if(ListaObjeto.get(position).getLegustaSeta() == 1) {
            holder.animation.setProgress(1);
        } else {
            holder.animation.setProgress(0.2f);
        }
        */
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
