package utnyilvantarto;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Route {

// private LocalDate datum;

    private final DatePicker datum;
    private final TextField indulas;
    private final TextField erkezes;
    private final TextField tavolsag;
    private final ComboBox ugyfel;
    private final CheckBox magan;
    private final CheckBox vissza;
    private final CheckBox telephelyrol;


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

    public TextField getTavolsag() {
        return tavolsag;
    }

    public void setTavolsag(TextField tavolsag) {
        this.tavolsag.setText(tavolsag.toString());
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

    public CheckBox getTelephelyrol() {
        return telephelyrol;
    }

    public void setTelephelyrol(Boolean magan) {
        this.telephelyrol.setSelected(magan);
    }

    public CheckBox getVissza() {
        return vissza;
    }

    public void setVissza(Boolean vissza) {
        this.vissza.setSelected(vissza);
    }


    public Route(LocalDate datum, String indulas, String erkezes, Integer tavolsag, String ugyfel, Boolean magan, Boolean vissza, Boolean telephelyrol) {
        this.datum = new DatePicker();
        this.datum.setValue(datum);

        this.indulas = new TextField();
        this.indulas.setText(indulas);
        fillField(this.indulas, "telepulesek.dat");

        this.erkezes = new TextField();
        this.erkezes.setText(erkezes);
        fillField(this.erkezes, "telepulesek.dat");

        this.tavolsag = new TextField();
        this.tavolsag.setText(tavolsag.toString());

        this.ugyfel = new ComboBox();
        this.ugyfel.setValue(ugyfel);
        this.ugyfel.getItems().addAll(fillBox("ugyfelek.dat"));

        this.magan = new CheckBox();
        this.magan.setSelected(magan);
        if (this.magan.isSelected()) {
            this.erkezes.setText("Magán");
        }

        this.vissza = new CheckBox();
        this.vissza.setSelected(vissza);

        this.telephelyrol=new CheckBox();
        this.telephelyrol.setSelected(telephelyrol);

    }

    public Route() {
        this.datum = new DatePicker();
        this.indulas = new TextField("");
        fillField(this.indulas, "telepulesek.dat");
        this.erkezes = new TextField("");
        fillField(this.erkezes, "telepulesek.dat");
        this.tavolsag = new TextField();
        this.ugyfel = new ComboBox();
        this.ugyfel.getItems().addAll(fillBox("ugyfelek.dat"));
        this.magan = new CheckBox();
        this.vissza = new CheckBox();
        this.telephelyrol=new CheckBox();
    }

    private void fillField(TextField text, String fileName) {
        TextFields.bindAutoCompletion(text, readFile(fileName));
    }

    private ArrayList fillBox(String fileName) {

        return readFile(fileName);
    }
    ArrayList<String> list = new ArrayList<String>() ;


    public ArrayList<String> readFile(String path) {

        File file = new File(path);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                list.add(read.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        // list.sort(Comparator.naturalOrder());
        return list;
    }
}
