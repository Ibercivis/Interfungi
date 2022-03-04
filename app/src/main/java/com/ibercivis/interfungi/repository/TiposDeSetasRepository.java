package com.ibercivis.interfungi.repository;

import com.ibercivis.interfungi.db.entities.TiposDeSetas;

import java.util.List;

public interface TiposDeSetasRepository {

    List<TiposDeSetas> getAllTiposDeSetasRepo();
    TiposDeSetas findByIdTiposDeSetasRepo(int idSeta);
    void insertTiposDeSetasRepo(TiposDeSetas tiposDeSetas);
    void updateTiposDeSetasRepo(TiposDeSetas tiposDeSetas);
    void deleteTiposDeSetasRepo(TiposDeSetas tiposDeSetas);
    void deleteAllTiposDeSetasRepo(List<TiposDeSetas> tiposDeSetasList);
}
