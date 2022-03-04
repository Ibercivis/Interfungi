package com.ibercivis.interfungi.repository;

import com.ibercivis.interfungi.db.DAO.MarcadorSetaDAO;
import com.ibercivis.interfungi.db.entities.MarcadorSeta;
import com.ibercivis.interfungi.db.entities.TiposDeSetas;

import java.util.List;

public class MarcadorSetaRepositoryImplement implements MarcadorSetaRepository{

    MarcadorSetaDAO dao;

    public MarcadorSetaRepositoryImplement(MarcadorSetaDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<MarcadorSeta> getAllMarcadorSetaRepo() {
        return dao.getAllMarcadorSeta();
    }

    @Override
    public MarcadorSeta findByIdMarcadorSetaRepo(int idPropio) {
        return dao.findByIdMarcadorSeta(idPropio);
    }

    @Override
    public void insertMarcadorSetaRepo(MarcadorSeta marcadorSeta) {
        dao.insertMarcadorSeta(marcadorSeta);
    }

    @Override
    public void updateMarcadorSetaRepo(MarcadorSeta marcadorSeta) {
        dao.updateMarcadorSeta(marcadorSeta);
    }

    @Override
    public void deleteMarcadorSetaRepo(MarcadorSeta marcadorSeta) {
        dao.deleteMarcadorSeta(marcadorSeta);
    }

    @Override
    public void deleteAllMarcadorSetaRepo(List<MarcadorSeta> marcadorSetaList) {
        dao.deleteAllMarcadorSeta(marcadorSetaList);
    }

}
