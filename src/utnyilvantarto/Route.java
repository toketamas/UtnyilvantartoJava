package utnyilvantarto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

public class Route {

// private LocalDate datum;

    private DatePicker datum;
    private TextField indulas;
    private TextField erkezes;
    private ComboBox tavolsag;
    private ComboBox ugyfel;
    private CheckBox magan;
    private CheckBox vissza;

    public DatePicker getDatum() {
        return datum;
    }

    public void setDatum(DatePicker datum) {
        this.datum = datum;
    }

    public TextField getIndulas() {
        return indulas;
    }

    public void setIndulas(TextField indulas) {
        this.indulas = indulas;
    }

    public TextField getErkezes() {
        return erkezes;
    }

    public void setErkezes(TextField erkezes) {
        this.erkezes = erkezes;
    }

    public ComboBox getTavolsag() {
        return tavolsag;
    }

    public void setTavolsag(ComboBox tavolsag) {
        this.tavolsag = tavolsag;
    }

    public ComboBox getUgyfel() {
        return ugyfel;
    }

    public void setUgyfel(ComboBox ugyfel) {
        this.ugyfel = ugyfel;
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

    public Route(DatePicker datum, String indulas, String erkezes, Integer tavolsag, String ugyfel, Boolean magan, Boolean vissza) {
        this.datum = datum;

        this.indulas = new TextField();
        this.indulas.setText(indulas);
        fillField(this.indulas);

        this.erkezes = new TextField();
        this.erkezes.setText(erkezes);
        fillField(this.erkezes);

        this.tavolsag = new ComboBox();
        this.tavolsag.setValue(tavolsag);

        this.ugyfel = new ComboBox();
        this.ugyfel.setValue(ugyfel);

        this.magan = new CheckBox();
        this.magan.setSelected(magan);

        this.vissza = new CheckBox();
        this.vissza.setSelected(vissza);
    }

    public Route(){
        this.datum = new DatePicker();
        this.indulas = new TextField("");
        fillField(this.indulas);
        this.erkezes = new TextField("");
        this.tavolsag = new ComboBox();
        this.ugyfel = new ComboBox();
        this.magan = new CheckBox();
        this.vissza = new CheckBox();
    }

    private void fillField(TextField text){
        FileModel fm = new FileModel();
        TextFields.bindAutoCompletion(text,fm.readFile("telepulesek.dat"));
    }
}
