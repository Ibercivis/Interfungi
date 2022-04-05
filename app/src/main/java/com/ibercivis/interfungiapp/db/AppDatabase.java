package com.ibercivis.interfungiapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibercivis.interfungiapp.db.DAO.TiposDeSetasDAO;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;

@Database(entities = {

        TiposDeSetas.class

}, version = 3 )
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
