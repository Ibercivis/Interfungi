package com.ibercivis.interfungi.repository;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ibercivis.interfungi.db.entities.MarcadorSeta;

import java.util.List;

public interface MarcadorSetaRepository {

    List<MarcadorSeta> getAllMarcadorSetaRepo();
    MarcadorSeta findByIdMarcadorSetaRepo(int idPropio);
    void insertMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void updateMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void deleteMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void deleteAllMarcadorSetaRepo(List<MarcadorSeta> marcadorSeta);

}
