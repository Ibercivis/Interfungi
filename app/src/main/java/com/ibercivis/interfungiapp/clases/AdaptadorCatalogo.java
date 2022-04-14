package com.ibercivis.interfungiapp.clases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    int last_position = -1;
    private TextView especie_identificada;
    Boolean Connected;
    ViewHolderCatalogoSetas lastHolder = null;
    Resources resources = Resources.getSystem();

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCatalogoSetas holder, @SuppressLint("RecyclerView") int position) {

        Log.d("AdaptadorSetas", "OnBindViewHolder");
        Log.d("AdaptadorSetasTitulo", ListaObjeto.get(position).getNombreSeta());
        holder.titulo.setText(ListaObjeto.get(position).getNombreSeta());
        String urlfoto = ListaObjeto.get(position).getLogoSeta();

        if (position == selected_position){
            ViewGroup.LayoutParams params = holder.card.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 168, resources.getDisplayMetrics());;
            holder.card.setLayoutParams(params);

            holder.fondo.setBackgroundResource(R.drawable.shape);
            holder.titulo.setTextSize(13);
            holder.titulo.setTypeface(null, Typeface.BOLD);
            holder.titulo.setTextColor(Color.parseColor("#332019"));
            holder.card.setElevation(8);
        } else {
            ViewGroup.LayoutParams params = holder.card.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, resources.getDisplayMetrics());;
            holder.card.setLayoutParams(params);
            holder.fondo.setBackgroundResource(R.drawable.shape_selected);
            holder.titulo.setTextSize(12);
            holder.titulo.setTypeface(null, Typeface.NORMAL);
            holder.titulo.setTextColor(Color.parseColor("#ffffff"));

        }



        if(Connected==true){
            //HAY CONEXIÓN, CARGAMOS DESDE URL
            Picasso.with(holder.foto.getContext()).load(urlfoto).into(holder.foto);
        } else {
            //NO HAY CONEXIÓN, CARGAMOS DESDE DB LOCAL
            Picasso.with(holder.foto.getContext()).load(new File(urlfoto)).into(holder.foto);
        }
        holder.foto.setClipToOutline(true);





       // Picasso.with(holder.foto.getContext()).load(foto).into(holder.foto);


        holder.card.setOnTouchListener(new View.OnTouchListener(){
            @Override public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    last_position = selected_position;
                    selected_position = position;
                    //holder.getPosition2(position);
                    especie_identificada.setText(ListaObjeto.get(position).getNombreSeta());
                    especie_identificada.setTypeface(null, Typeface.BOLD);
                    notifyItemChanged(position);
                    return true;

                    /*

                    holder.card.setCardBackgroundColor(Color.parseColor("#c99d66"));



                    if (selected_position == position) {
                        holder.card.setCardBackgroundColor(Color.parseColor("#c99d66"));
                        holder.titulo.setTextSize(13);
                        holder.titulo.setTypeface(null, Typeface.BOLD);
                    } else {
                        holder.card.setCardBackgroundColor(Color.parseColor("#966f3a"));
                        holder.titulo.setTextSize(12);
                        holder.titulo.setTypeface(null, Typeface.NORMAL);
                    }



                    // if (e.getAction() ==MotionEvent.ACTION_DOWN) {
                    especie_identificada.setText(ListaObjeto.get(position).getNombreSeta());
                    especie_identificada.setTypeface(null, Typeface.BOLD);


                    last_position = selected_position;
                    selected_position = position;
                    if(last_position != -1){
                        notifyItemChanged(last_position);
                    }
                    notifyItemChanged(position);

                    return true;
                    //  }

                    //   v.setElevation(5);
                    //  lastHolder = holder;
                    //   return true;

                */  }
                if(last_position != -1) {
                    notifyItemChanged(last_position);
                    return true;
                } return true;
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
