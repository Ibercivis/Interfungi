package com.ibercivis.interfungi.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ibercivis.interfungi.db.entities.TiposDeSetas;

import java.util.List;

@Dao
public interface TiposDeSetasDAO {

    @Query("select * from TiposDeSetas")
    List<TiposDeSetas> getAllTiposDeSetas();

    @Query("select * from TiposDeSetas where idSeta = :idSeta")
    TiposDeSetas findByIdTiposDeSetas(int idSeta);

    @Insert
    void insertTiposDeSetas(TiposDeSetas tiposDeSetas);

    @Update
    void updateTiposDeSetas(TiposDeSetas tiposDeSetas);

    @Delete
    void deleteTiposDeSetas(TiposDeSetas tiposDeSetas);
    @Delete
    void deleteAllTiposDeSetas(List<TiposDeSetas> tiposDeSetas);


}
