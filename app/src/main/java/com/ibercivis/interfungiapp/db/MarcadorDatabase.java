package com.ibercivis.interfungiapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibercivis.interfungiapp.db.DAO.MarcadorSetaDAO;
import com.ibercivis.interfungiapp.db.entities.MarcadorSeta;

@Database(entities = {

        MarcadorSeta.class

}, version = 3 )
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
