package com.ibercivis.interfungiapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MarcadorSeta")
public class MarcadorSeta {

    @PrimaryKey(autoGenerate = true)
    public int idPropio;
    @ColumnInfo(name = "idUser")
    public String idUser;
    @ColumnInfo(name = "token")
    public String token;
    @ColumnInfo(name = "foto")
    public String foto;
    @ColumnInfo(name = "foto1")
    public String foto1;
    @ColumnInfo(name = "idProyecto")
    public String idProyecto;
    @ColumnInfo(name = "latitud")
    public String latitud;
    @ColumnInfo(name = "longitud")
    public String longitud;
    @ColumnInfo(name = "fechaCorte")
    public String fechaCorte;
    @ColumnInfo(name = "Observaciones")
    public String atributo1;
    @ColumnInfo(name = "Especie")
    public String atributo2;
    @ColumnInfo(name = "Curiosidad")
    public String atributo3;
    public int getIdPropioMarcador() {
        return idPropio;
    }

    public void setIdPropioMarcador(int idPropio) {
        this.idPropio = idPropio;
    }

    public String getIdUserMarcador() {
        return idUser;
    }

    public void setIdUserMarcador(String idUser) {
        this.idUser = idUser;
    }

    public String getTokenMarcador() {
        return token;
    }

    public void setTokenMarcador(String token) {
        this.token = token;
    }

    public String getFotoMarcador() {
        return foto;
    }

    public void setFotoMarcador(String foto) {
        this.foto = foto;
    }

    public String getFoto1Marcador() {
        return foto1;
    }

    public void setFoto1Marcador(String foto1) {
        this.foto1 = foto1;
    }

    public String getIdProyectoMarcador() {
        return idProyecto;
    }

    public void setIdProyectoMarcador(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getLatitudMarcador() {
        return latitud;
    }

    public void setLatitudMarcador(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitudMarcador() {
        return longitud;
    }

    public void setLongitudMarcador(String longitud) {
        this.longitud = longitud;
    }

    public String getFechaCorteMarcador() {
        return fechaCorte;
    }

    public void setFechaCorteMarcador(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getAtributo1Marcador() {
        return atributo1;
    }

    public void setAtributo1Marcador(String atributo1) {
        this.atributo1 = atributo1;
    }

    public String getAtributo2Marcador() {
        return atributo2;
    }

    public void setAtributo2Marcador(String atributo2) {
        this.atributo2 = atributo2;
    }

    public String getAtributo3Marcador() {
        return atributo3;
    }

    public void setAtributo3Marcador(String atributo3) {
        this.atributo3 = atributo3;
    }
}
