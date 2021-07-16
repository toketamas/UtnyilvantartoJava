package utnyilvantartojava;

import javafx.beans.property.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Route implements IDbObject {

    final String TABLE_NAME_IN_DB="routes";
// private LocalDate datum;
    private Integer id;
    private StringProperty datum;
    private StringProperty indulas;
    private StringProperty erkezes;
    private IntegerProperty tavolsag;
    private StringProperty ugyfel;
    private BooleanProperty magan;
    private BooleanProperty odaVissza;
    private IntegerProperty spedometer;
    private DoubleProperty fueling;
    private int cellId;
    private String induloUgyfel;

    

    public Route(int id, String datum, Boolean magan, String indulas, String erkezes, String ugyfel , Double tankolas, Integer spedometer, Integer tavolsag, Boolean odaVissza, int celId) {
        this.id = id;
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
    
    public Route(int id, String datum, Boolean magan, String indulas, String induloUgyfel, String erkezes, String ugyfel , Double tankolas, Integer spedometer, Integer tavolsag, Boolean odaVissza, int celId) {
        this.id = id;
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
        this.induloUgyfel=induloUgyfel;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public String getInduloUgyfel() {
        return induloUgyfel;
    }

    public void setInduloUgyfel(String induloUgyfel) {
        this.induloUgyfel = induloUgyfel;
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
           // Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }


    @Override
    public DoubleList doubleList() {
   /*     DoubleList list = new DoubleList();
        list.add("routeid", this.routeid);
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
        list.add("active", this.active);*/
        return null;
    }

    @Override
    public int columnsInDb() {
        return 12;
    }

    @Override
    public void updateDb() {

    }

    @Override
    public void insertDb() {

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
    public String jsonPost() {
        return null;
    }
}
