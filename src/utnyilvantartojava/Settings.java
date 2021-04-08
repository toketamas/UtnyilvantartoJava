package utnyilvantartojava;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Settings implements IDbObject {

    private String nev;
    private String varos;
    private String cim;
    private String auto;
    private String rendszam;
    private String loketterfogat;
    private String fogyasztas;
    private int elozo_zaro;
    private String aktualis_honap;
    private String utolso_ugyfel;
    private int zaro_km;
    private String id;
    private  int sorszam;
    private String utolsoSzerkesztes;
    private Boolean active;





    public Settings(String id) {
        this.id = id;
        this.nev ="";
        this.varos ="";
        this.cim = "";
        this.auto = "";
        this.rendszam = "";
        this.loketterfogat = "";
        this.fogyasztas = "";
        this.elozo_zaro = 0;
        this.aktualis_honap = "";
        this.utolso_ugyfel = "telephely";
        this.zaro_km =0;
        this.active=true;

    }

    public Settings(String nev, String varos, String cim, String auto, String rendszam, String loketterfogat, String fogyasztas,
                    int elozo_zaro, String aktualis_honap, String utolso_ugyfel, int zaro_km, String id, int sorszam, String utolsoSzerkesztes, Boolean active)
    {
        this.nev = nev;
        this.varos = varos;
        this.cim = cim;
        this.auto = auto;
        this.rendszam = rendszam;
        this.loketterfogat = loketterfogat;
        this.fogyasztas = fogyasztas;
        this.elozo_zaro = elozo_zaro;
        this.aktualis_honap = aktualis_honap;
        this.utolso_ugyfel = utolso_ugyfel;
        this.zaro_km = zaro_km;
        this.id =id;
        this.sorszam=sorszam;
        this.utolsoSzerkesztes=utolsoSzerkesztes;
        this.active=active;
    };



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtolsoSzerkesztes() {
        return utolsoSzerkesztes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getZaroKm() {
        return zaro_km;
    }

    public void setZaro_km(int zaro_km) {
        this.zaro_km = zaro_km;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }

    public String getLoketterfogat() {
        return loketterfogat;
    }

    public void setLoketterfogat(String loketterfogat) {
        this.loketterfogat = loketterfogat;
    }

    public String getFogyasztas() {
        return fogyasztas;
    }

    public void setFogyasztas(String fogyasztas) {
        this.fogyasztas = fogyasztas;
    }

    public int getElozo_zaro() {
        return elozo_zaro;
    }

    public void setElozo_zaro(int elozo_zaro) {
        this.elozo_zaro = elozo_zaro;
    }

    public String getAktualis_honap() {
        return aktualis_honap;
    }

    public void setAktualis_honap(String aktualis_honap) {
        this.aktualis_honap = aktualis_honap;
    }

    public String getUtolso_ugyfel() {
        return utolso_ugyfel;
    }

    public void setUtolso_ugyfel(String utolso_ugyfel) {
        this.utolso_ugyfel = utolso_ugyfel;
    }

    @Override
    public List<Object> getAll(){
        List<Object>list=new ArrayList<>();
        list.add(this.nev);
        list.add(this.varos);
        list.add(this.cim);
        list.add(this.auto);
        list.add(this.rendszam);
        list.add(this.loketterfogat);
        list.add(this.fogyasztas);
        list.add(this.elozo_zaro);
        list.add(this.aktualis_honap);
        list.add(this.utolso_ugyfel);
        list.add(this.zaro_km);
        list.add(this.getId());
        list.add(this.sorszam);
        list.add(LocalDateTime.now().toString());
        list.add(this.active);
        return list;
    }


}
