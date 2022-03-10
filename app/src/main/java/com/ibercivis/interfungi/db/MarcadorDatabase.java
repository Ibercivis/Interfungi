package com.ibercivis.interfungi.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibercivis.interfungi.db.DAO.MarcadorSetaDAO;
import com.ibercivis.interfungi.db.entities.MarcadorSeta;
import com.ibercivis.interfungi.db.entities.TiposDeSetas;

@Database(entities = {

        MarcadorSeta.class

}, version = 2 )
public abstract class MarcadorDatabase extends RoomDatabase {

    public static MarcadorDatabase INSTANCE;

    public abstract MarcadorSetaDAO marcadorSetaDAO();

    public static MarcadorDatabase getInstance(Context context){

        if (INSTANCE==null){

            INSTANCE = Room.databaseBuilder(context, MarcadorDatabase.class, "marcadores.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();

        }

        return INSTANCE;
    }

}
