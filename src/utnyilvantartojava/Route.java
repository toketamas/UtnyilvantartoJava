package utnyilvantartojava;

import javafx.beans.property.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Route {

// private LocalDate datum;
    private Integer routeId;
    private final StringProperty datum;
    private final StringProperty indulas;
    private final StringProperty erkezes;
    private final IntegerProperty tavolsag;
    private final StringProperty ugyfel;
    private final BooleanProperty magan;
    private final BooleanProperty odaVissza;
    private final IntegerProperty spedometer;
    private final DoubleProperty fueling;
    private int cellId;

    public Route(int routeId, String datum,Boolean magan, String indulas, String erkezes,String ugyfel ,Double tankolas,Integer spedometer,Integer tavolsag, Boolean odaVissza,int celId) {
        this.routeId=routeId;
        this.datum = new SimpleStringProperty(datum);
        this.indulas = new SimpleStringProperty(indulas);
        this.erkezes = new SimpleStringProperty(erkezes);
        this.tavolsag = new SimpleIntegerProperty(tavolsag);
        this.ugyfel = new SimpleStringProperty(ugyfel);
        this.magan = new SimpleBooleanProperty(magan);
        this.odaVissza = new SimpleBooleanProperty(odaVissza);
        this.spedometer = new SimpleIntegerProperty(spedometer);
        this.fueling = new SimpleDoubleProperty(tankolas);
        this.cellId=celId;
    }

    public Route(String datum,Boolean magan, String indulas, String erkezes,String ugyfel ,Double tankolas,Integer spedometer,Integer tavolsag, Boolean odaVissza,int cellId) {
        this.datum = new SimpleStringProperty(datum);
        this.indulas = new SimpleStringProperty(indulas);
        this.erkezes = new SimpleStringProperty(erkezes);
        this.tavolsag = new SimpleIntegerProperty(tavolsag);
        this.ugyfel = new SimpleStringProperty(ugyfel);
        this.magan = new SimpleBooleanProperty(magan);
        this.odaVissza = new SimpleBooleanProperty(odaVissza);
        this.spedometer = new SimpleIntegerProperty(spedometer);
        this.fueling = new SimpleDoubleProperty(tankolas);
        this.cellId=cellId;
    }
    public Route() {
        this.spedometer = new SimpleIntegerProperty(0);
        this.fueling = new SimpleDoubleProperty(0);
        this.datum = new SimpleStringProperty("");
        this.indulas = new SimpleStringProperty("");
        this.erkezes = new SimpleStringProperty("");
        this.tavolsag = new SimpleIntegerProperty();
        this.ugyfel = new SimpleStringProperty("");
        this.magan = new SimpleBooleanProperty();
        this.odaVissza = new SimpleBooleanProperty();

    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getDatum() {
        return datum.get();
    }

    public StringProperty datumProperty() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }

    public String getIndulas() {
        return indulas.get();
    }

    public StringProperty indulasProperty() {
        return indulas;
    }

    public void setIndulas(String indulas) {
        this.indulas.set(indulas);
    }

    public String getErkezes() {
        return erkezes.get();
    }

    public StringProperty erkezesProperty() {
        return erkezes;
    }

    public void setErkezes(String erkezes) {
        this.erkezes.set(erkezes);
    }

    public int getTavolsag() {
        return tavolsag.get();
    }

    public IntegerProperty tavolsagProperty() {
        return tavolsag;
    }

    public void setTavolsag(int tavolsag) {
        this.tavolsag.set(tavolsag);
    }

    public String getUgyfel() {
        return ugyfel.get();
    }

    public StringProperty ugyfelProperty() {
        return ugyfel;
    }

    public void setUgyfel(String ugyfel) {
        this.ugyfel.set(ugyfel);
    }

    public boolean isMagan() {
        return magan.get();
    }

    public BooleanProperty maganProperty() {
        return magan;
    }

    public void setMagan(boolean magan) {
        this.magan.set(magan);
    }

    public int getSpedometer() {
        return spedometer.get();
    }

    public IntegerProperty spedometerProperty() {
        return spedometer;
    }

    public void setSpedometer(int spedometer) {
        this.spedometer.set(spedometer);
    }

    public double getFueling() {
        return fueling.get();
    }

    public DoubleProperty fuelingProperty() {
        return fueling;
    }

    public void setFueling(double fueling) {
        this.fueling.set(fueling);
    }

    public boolean isVissza() {
        return odaVissza.get();
    }

    public BooleanProperty visszaProperty() {
        return odaVissza;
    }

    public void setVissza(boolean vissza) {
        this.odaVissza.set(vissza);
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getCellId() {
        return cellId;
    }

    public ArrayList<String> readFile(String path) {

        ArrayList<String> list = new ArrayList<String>() ;
        File file = new File(path);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                list.add(read.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
