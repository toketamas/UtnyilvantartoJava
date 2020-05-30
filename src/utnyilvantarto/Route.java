package utnyilvantarto;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;

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

    public void setDatum(LocalDate dat) {
        this.datum.setValue(dat);
    }

    public TextField getIndulas() {
        return indulas;
    }

    public void setIndulas(String indulas) {
        this.indulas.setText(indulas);
    }

    public TextField getErkezes() {
        return erkezes;
    }

    public void setErkezes(String erkezes) {
        this.erkezes.setText(erkezes);
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

    public void setUgyfel(String ugyfel) {
        this.ugyfel.setValue(ugyfel);
    }



    public CheckBox getMagan() {
        return magan;
    }

    public void setMagan(Boolean magan) {
        this.magan.setSelected(magan);
    }

    public CheckBox getVissza() {
        return vissza;
    }

    public void setVissza(Boolean vissza) {
        this.vissza.setSelected(vissza);
    }

    public Route(LocalDate datum, String indulas, String erkezes, Integer tavolsag, String ugyfel, Boolean magan, Boolean vissza) {
        this.datum = new DatePicker();
        this.datum.setValue( datum);

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
        if (this.magan.isSelected()){
            this.erkezes.setText("Magán");
        }

        this.vissza = new CheckBox();
        this.vissza.setSelected(vissza);


    }

    public Route(){
        this.datum = new DatePicker();
        this.indulas = new TextField("");
        fillField(this.indulas);
        this.erkezes = new TextField("");
        fillField(this.erkezes);
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
