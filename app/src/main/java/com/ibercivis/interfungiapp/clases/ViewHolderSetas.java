package com.ibercivis.interfungiapp.clases;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.ibercivis.interfungiapp.R;

import java.util.List;

public class ViewHolderSetas extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView titulo;
    TextView titulo2;
    TextView descripcion;
    TextView aportaciones;
    TextView buttonViewOptions;
    TextView web;
    CardView card;
    Button btn;
    LottieAnimationView animation;
    TextView likes;
    ImageView photo_0;
    ImageView photo_1;
    ImageView iconCreaObservacion;
    List<Marcador> ListaSetas;
    Button deletebutton;
    Button delete_eliminar;
    Button delete_volver;
    Button btn_verEnMapa;
    TextView agusanamiento, presion;
    LinearLayout zonaA;
    LinearLayout zonaB;


    public ViewHolderSetas(@NonNull View itemView, List<Marcador> datos) {
        super(itemView);

        titulo = itemView.findViewById(R.id.titulo);
        titulo2 = itemView.findViewById(R.id.titulo2);
        ListaSetas = datos;
        card = itemView.findViewById(R.id.marcador_card);
        photo_0= itemView.findViewById(R.id.photo_0);
        photo_1= itemView.findViewById(R.id.photo_1);
        deletebutton = itemView.findViewById(R.id.delete_btn);
        delete_eliminar = itemView.findViewById(R.id.eliminar_confirmar);
        delete_volver = itemView.findViewById(R.id.eliminar_volver);
        zonaA = itemView.findViewById(R.id.zona_A);
        zonaB = itemView.findViewById(R.id.zona_B);
        agusanamiento = itemView.findViewById(R.id.dato_agusanamiento);
        presion = itemView.findViewById(R.id.dato_presion);
        btn_verEnMapa = itemView.findViewById(R.id.mapa_btn);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        final Marcador marcador = ListaSetas.get(position);


        if(v.getId() == buttonViewOptions.getId()){

           /* PopupMenu popup = new PopupMenu(buttonViewOptions.getContext(), buttonViewOptions);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_card);
            //adding click listener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu1:
                            //handle menu1 click

                            int id = marcador.getId();
                            Intent intent = new Intent(buttonViewOptions.getContext(), DescargarDatos.class);
                            intent.putExtra("idProyecto", id);
                            startActivity(btn.getContext(), intent, null);

                            break;

                        case R.id.menu2:
                            //handle menu1 click

                            int id2 = marcador.getId();
                            Intent intent2 = new Intent(buttonViewOptions.getContext(), BorrarProyecto.class);
                            intent2.putExtra("idProyecto", id2);
                            startActivity(btn.getContext(), intent2, null);

                            break;

                        case R.id.menu3:
                            //handle menu1 click

                            int id3 = marcador.getId();
                            String tituloSeta = "titulo";
                            String url_photo_0 = marcador.getPhoto0();
                            Intent intent3 = new Intent(buttonViewOptions.getContext(), EditarProyecto.class);
                            intent3.putExtra("idProyecto", id3);
                            intent3.putExtra("urlLogo", url_photo_0);
                            startActivity(btn.getContext(), intent3, null);

                            break;

                    }
                    return false;
                }
            });
            SessionManager session = new SessionManager(buttonViewOptions.getContext());
            popup.show();*/

        }
    }
}



