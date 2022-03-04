package com.ibercivis.interfungi.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibercivis.interfungi.db.DAO.MarcadorSetaDAO;
import com.ibercivis.interfungi.db.DAO.TiposDeSetasDAO;
import com.ibercivis.interfungi.db.entities.MarcadorSeta;
import com.ibercivis.interfungi.db.entities.TiposDeSetas;

@Database(entities = {

        TiposDeSetas.class

}, version = 2 )
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract TiposDeSetasDAO tiposDeSetasDAO();



    public static AppDatabase getInstance(Context context){

        if (INSTANCE==null){

            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "interfungi.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();

        }

            return INSTANCE;
    }

}
