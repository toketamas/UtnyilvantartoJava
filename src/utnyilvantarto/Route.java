package utnyilvantarto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class Route {

// private LocalDate datum;

    private DatePicker datum;
    private ComboBox indulas;
    private StringProperty erkezes;
    private IntegerProperty tavolsag;
    private StringProperty ugyfel;
    private CheckBox magan;
    private CheckBox vissza;


    public DatePicker getDatum() {
        return datum;
    }

    public void setDatum(DatePicker datum) {
        this.datum = datum;
    }

    public ComboBox getIndulas() {
        return indulas;
    }

    public void setIndulas(ComboBox indulas) {
        this.indulas = indulas;
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

    public Route(DatePicker datum, ComboBox indulas, String erkezes, Integer tavolsag, String ugyfel, CheckBox magan, CheckBox vissza) {
        this.datum = datum;
        this.indulas = indulas;
        this.erkezes = new SimpleStringProperty(erkezes);
        this.tavolsag = new SimpleIntegerProperty(tavolsag);
        this.ugyfel = new SimpleStringProperty(ugyfel);
        this.magan = magan;
        this.vissza = vissza;
    }

    public Route(){
        this.datum = new DatePicker();
        this.indulas = new ComboBox();
        this.erkezes = new SimpleStringProperty("");
        this.tavolsag = new SimpleIntegerProperty(0);
        this.ugyfel = new SimpleStringProperty("");
        this.magan = new CheckBox();
        this.vissza = new CheckBox();
    }
}