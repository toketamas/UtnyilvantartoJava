package utnyilvantartojava;

public class Settings {

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
    private Boolean lezarva;


    public Settings() {
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
        this.lezarva=false;
    }

    public Settings(String nev, String varos, String cim, String auto, String rendszam, String loketterfogat, String fogyasztas, int elozo_zaro, String aktualis_honap, String utolso_ugyfel, Boolean lezarva) {
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
        this.lezarva=lezarva;
    }

    public Settings(String nev, String varos, String cim, String auto, String rendszam, String loketterfogat, String fogyasztas, int elozo_zaro, String aktualis_honap) {
        this.nev = nev;
        this.varos = varos;
        this.cim = cim;
        this.auto = auto;
        this.rendszam = rendszam;
        this.loketterfogat = loketterfogat;
        this.fogyasztas = fogyasztas;
        this.elozo_zaro = elozo_zaro;
        this.aktualis_honap = aktualis_honap;
        this.utolso_ugyfel = "telephely";
        this.lezarva=lezarva;
    }


    public Boolean getLezarva() {
        return lezarva;
    }

    public void setLezarva(Boolean lezarva) {
        this.lezarva = lezarva;
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


}
