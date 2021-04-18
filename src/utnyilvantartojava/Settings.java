package utnyilvantartojava;

import java.sql.SQLException;
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
    private int sorszam;
    private String utolsoSzerkesztes;
    private Boolean active;

    final String SETTINGS_TABLE_IN_DB ="settings";

    //<editor-fold desc="constructors">

    public Settings() {
        this.id = "";
        this.nev = "";
        this.varos = "";
        this.cim = "";
        this.auto = "";
        this.rendszam = "";
        this.loketterfogat = "";
        this.fogyasztas = "";
        this.elozo_zaro = 0;
        this.aktualis_honap = "";
        this.utolso_ugyfel = "";
        this.zaro_km = 0;
        this.active = false;

    }

    public Settings(List<Object> list) {
        this.nev = (String) list.get(0);
        this.varos = (String) list.get(1);
        this.cim = (String) list.get(2);
        this.auto = (String) list.get(3);
        this.rendszam = (String) list.get(4);
        this.loketterfogat = (String) list.get(5);
        this.fogyasztas = (String) list.get(6);
        this.elozo_zaro = (int) list.get(7);
        this.aktualis_honap = (String) list.get(8);
        this.utolso_ugyfel = (String) list.get(9);
        this.zaro_km = (int) list.get(10);
        this.id = (String) list.get(11);
        this.sorszam = (int) list.get(12);
        this.utolsoSzerkesztes = (String) list.get(13);
        this.active = new NonFxFunctions().convertBool((String) list.get(14));

    }

    public Settings(String id) {
        this.id = id;
        this.nev = "";
        this.varos = "";
        this.cim = "";
        this.auto = "";
        this.rendszam = "";
        this.loketterfogat = "";
        this.fogyasztas = "";
        this.elozo_zaro = 0;
        this.aktualis_honap = "";
        this.utolso_ugyfel = "telephely";
        this.zaro_km = 0;
        this.active = true;

    }

    public Settings(String nev, String varos, String cim, String auto, String rendszam, String loketterfogat, String fogyasztas,
                    int elozo_zaro, String aktualis_honap, String utolso_ugyfel, int zaro_km, String id, int sorszam, String utolsoSzerkesztes, Boolean active) {
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
        this.id = id;
        this.sorszam = sorszam;
        this.utolsoSzerkesztes = utolsoSzerkesztes;
        this.active = active;
    }
    //</editor-fold>

    //<editor-fold desc="getters-setters">
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
    //</editor-fold>

    @Override
    public int columnsInDb() {

        return 15;
    }

    @Override
    public void updateDb() {


    }

    @Override
    public void insertDb() {
    SqlBuilder sqlBuilder=new SqlBuilder();
    sqlBuilder.insert(this.values(), SETTINGS_TABLE_IN_DB);
    sqlBuilder.close();
    }

    @Override
    public List<String> keys() {

        return doubleList().keys();
    }

    @Override
    public List<Object> values() {

        return doubleList().values();
    }


    @Override
    public DoubleList doubleList() {
        DoubleList list = new DoubleList();
        list.add("nev", this.nev);
        list.add("varos", this.varos);
        list.add("cim", this.cim);
        list.add("auto", this.auto);
        list.add("rendszam", this.rendszam);
        list.add("loketterfogat", this.loketterfogat);
        list.add("fogyasztas", this.fogyasztas);
        list.add("elozo_zaro", this.elozo_zaro);
        list.add("aktualis_honap", this.aktualis_honap);
        list.add("utolso_ugyfel", this.utolso_ugyfel);
        list.add("zaro_km", this.zaro_km);
        // list.add("id",this.getId());
        //list.add("sorszam",this.sorszam);
        list.add("utolso_szerkesztes", LocalDateTime.now().toString());
        list.add("active", this.active);
        return list;
    }




}
