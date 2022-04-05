package com.ibercivis.interfungiapp.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ibercivis.interfungiapp.db.entities.MarcadorSeta;

import java.util.List;

@Dao
public interface MarcadorSetaDAO {

    @Query("select * from MarcadorSeta")
    List<MarcadorSeta> getAllMarcadorSeta();

    @Query("select * from MarcadorSeta where idPropio = :idPropio")
    MarcadorSeta findByIdMarcadorSeta(int idPropio);

    @Insert
    void insertMarcadorSeta(MarcadorSeta marcadorSeta);

    @Update
    void updateMarcadorSeta(MarcadorSeta marcadorSeta);

    @Delete
    void deleteMarcadorSeta(MarcadorSeta marcadorSeta);
    @Delete
    void deleteAllMarcadorSeta(List<MarcadorSeta> marcadorSeta);

}
