package com.ibercivis.interfungiapp.repository;

import com.ibercivis.interfungiapp.db.entities.MarcadorSeta;

import java.util.List;

public interface MarcadorSetaRepository {

    List<MarcadorSeta> getAllMarcadorSetaRepo();
    MarcadorSeta findByIdMarcadorSetaRepo(int idPropio);
    void insertMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void updateMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void deleteMarcadorSetaRepo(MarcadorSeta marcadorSeta);
    void deleteAllMarcadorSetaRepo(List<MarcadorSeta> marcadorSeta);

}
