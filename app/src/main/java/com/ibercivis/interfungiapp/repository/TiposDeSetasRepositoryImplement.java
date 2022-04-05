package com.ibercivis.interfungiapp.repository;

import com.ibercivis.interfungiapp.db.DAO.TiposDeSetasDAO;
import com.ibercivis.interfungiapp.db.entities.TiposDeSetas;

import java.util.List;

public class TiposDeSetasRepositoryImplement implements TiposDeSetasRepository{

    TiposDeSetasDAO dao;

    public TiposDeSetasRepositoryImplement(TiposDeSetasDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<TiposDeSetas> getAllTiposDeSetasRepo() {
        return dao.getAllTiposDeSetas();
    }

    @Override
    public TiposDeSetas findByIdTiposDeSetasRepo(int idSeta) {
        return dao.findByIdTiposDeSetas(idSeta);
    }

    @Override
    public void insertTiposDeSetasRepo(TiposDeSetas tiposDeSetas) {
        dao.insertTiposDeSetas(tiposDeSetas);
    }

    @Override
    public void updateTiposDeSetasRepo(TiposDeSetas tiposDeSetas) {
        dao.updateTiposDeSetas(tiposDeSetas);
    }

    @Override
    public void deleteTiposDeSetasRepo(TiposDeSetas tiposDeSetas) {
        dao.deleteTiposDeSetas(tiposDeSetas);
    }

    @Override
    public void deleteAllTiposDeSetasRepo(List<TiposDeSetas> tiposDeSetasList) {
        dao.deleteAllTiposDeSetas(tiposDeSetasList);
    }
}
