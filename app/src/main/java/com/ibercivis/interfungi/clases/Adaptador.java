package com.ibercivis.interfungi.clases;

import android.content.Context;
import android.content.ContextWrapper;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<viewHolder> {

    List<proyectos> ListaObjeto;

    public Adaptador(List<proyectos> listaObjeto) {
        ListaObjeto = listaObjeto;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.proyectos_card, parent, false);
        return new viewHolder(vista, ListaObjeto);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.subtitulo.setText(ListaObjeto.get(position).getSubtitulo());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.descripcion.setText(Html.fromHtml(ListaObjeto.get(position).getDescripcion(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.descripcion.setText(Html.fromHtml(ListaObjeto.get(position).getDescripcion()));
        }
        // holder.descripcion.setText(ListaObjeto.get(position).getDescripcion());
        holder.aportaciones.setText(Integer.toString(ListaObjeto.get(position).getAportaciones()));
        holder.likes.setText(Integer.toString(ListaObjeto.get(position).getLikes()));

        if (ListaObjeto.get(position).getWeb() != ""){

            holder.web.setText(ListaObjeto.get(position).getWeb());
            holder.web.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            holder.web.setVisibility(View.GONE);
        }

        String urlfoto = ListaObjeto.get(position).logo;
        // Bitmap foto = new DownloadFilesTask().execute(urlfoto);
        Picasso.with(holder.animation.getContext()).load(urlfoto).into(holder.logo);
        ContextWrapper cw = new ContextWrapper(holder.animation.getContext());

        /* Esto era para que cargase las imagenes desde local
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,"profile.jpg");
        Log.d("adaptador",mypath.getAbsolutePath());
        Picasso.with(holder.animation.getContext()).load(mypath).into(holder.logo); */

        if(ListaObjeto.get(position).legusta == 1) {
            holder.animation.setProgress(1);
        } else {
            holder.animation.setProgress(0.2f);
        }

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
