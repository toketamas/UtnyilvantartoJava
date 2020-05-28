package utnyilvantarto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Route {

// private LocalDate datum;

    private StringProperty datum;
    private StringProperty indulas;
    private StringProperty erkezes;
    private IntegerProperty tavolsag;
    private StringProperty ugyfel;
    private CheckBox magan;
    private CheckBox vissza;



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


    public CheckBox getMagan() {
        return magan;
    }

    public void setMagan(CheckBox magan) {
        this.magan = magan;
    }


    public CheckBox getVissza() {
        return vissza;
    }

    public void setVissza(CheckBox vissza) {
        this.vissza = vissza;
    }

    public Route(String datum, String indulas, String erkezes, Integer tavolsag, String ugyfel, CheckBox magan, CheckBox vissza) {
        this.datum = new SimpleStringProperty(datum);
        this.indulas = new SimpleStringProperty(indulas);
        this.erkezes = new SimpleStringProperty(erkezes);
        this.tavolsag = new SimpleIntegerProperty(tavolsag);
        this.ugyfel = new SimpleStringProperty(ugyfel);
        this.magan = magan;
        this.vissza = vissza;
    }

    public Route(){
        this.datum = new SimpleStringProperty("");
        this.indulas = new SimpleStringProperty("");
        this.erkezes = new SimpleStringProperty("");
        this.tavolsag = new SimpleIntegerProperty(0);
        this.ugyfel = new SimpleStringProperty("");
        this.magan = new CheckBox();
        this.vissza = new CheckBox();
    }
}