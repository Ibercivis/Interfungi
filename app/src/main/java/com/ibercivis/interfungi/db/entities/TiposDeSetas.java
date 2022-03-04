package com.ibercivis.interfungi.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TiposDeSetas")
public class TiposDeSetas {

    @PrimaryKey(autoGenerate = true)
    int idPropio;
    @ColumnInfo(name = "idSeta")
    int idSeta;
    @ColumnInfo(name = "nombreSeta")
    String nombreSeta;
    @ColumnInfo(name = "cientificoSeta")
    String cientificoSeta;
    @ColumnInfo(name = "descripcionSeta")
    String descripcionSeta;
    @ColumnInfo(name = "webSeta")
    String webSeta;
    @ColumnInfo(name = "aportacionesSeta")
    int aportacionesSeta;
    @ColumnInfo(name = "likesSeta")
    int likesSeta;
    @ColumnInfo(name = "legustaSeta")
    int legustaSeta;
    @ColumnInfo(name = "logoSeta")
    String logoSeta;

    public int getIdSeta() {
        return idSeta;
    }

    public int getIdPropio() {
        return idPropio;
    }

    public void setIdPropio(int idPropio) {
        this.idPropio = idPropio;
    }

    public void setIdSeta(int idSeta) {
        this.idSeta = idSeta;
    }

    public String getNombreSeta() {
        return nombreSeta;
    }

    public void setNombreSeta(String nombreSeta) {
        this.nombreSeta = nombreSeta;
    }

    public String getCientificoSeta() {
        return cientificoSeta;
    }

    public void setCientificoSeta(String cientificoSeta) {
        this.cientificoSeta = cientificoSeta;
    }

    public String getDescripcionSeta() {
        return descripcionSeta;
    }

    public void setDescripcionSeta(String descripcionSeta) {
        this.descripcionSeta = descripcionSeta;
    }

    public String getWebSeta() {
        return webSeta;
    }

    public void setWebSeta(String webSeta) {
        this.webSeta = webSeta;
    }

    public int getAportacionesSeta() {
        return aportacionesSeta;
    }

    public void setAportacionesSeta(int aportacionesSeta) {
        this.aportacionesSeta = aportacionesSeta;
    }

    public int getLikesSeta() {
        return likesSeta;
    }

    public void setLikesSeta(int likesSeta) {
        this.likesSeta = likesSeta;
    }

    public int getLegustaSeta() {
        return legustaSeta;
    }

    public void setLegustaSeta(int legustaSeta) {
        this.legustaSeta = legustaSeta;
    }

    public String getLogoSeta() {
        return logoSeta;
    }

    public void setLogoSeta(String logoSeta) {
        this.logoSeta = logoSeta;
    }
}
