/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utnyilvantartojava;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 * @author Tamas
 */
public class ViewController implements Initializable {

    @FXML
    TableView tableView;

// gombok
    @FXML
    Button button_Bevitel;

    @FXML
    Button button_BeallitasKesz;

//combo boxok
    @FXML
    SearchableComboBox comboBox_Indulas;
    @FXML
    SearchableComboBox comboBox_Erkezes;
    @FXML
    SearchableComboBox comboBox_Ugyfel;
    @FXML
    SearchableComboBox comboBox_Tavolsag;

//date picker
    @FXML
    DatePicker date_Picker;

//check boxok
    @FXML
    CheckBox checkBox_Ugyfeladatok;
    @FXML
    CheckBox checkBox_Telephelyrol;
    @FXML
    CheckBox checkBox_OdaVissza;
    @FXML
    CheckBox checkBox_Magan;

    @FXML
    private void beir(ActionEvent event) {
        System.out.println("irt");
    }

    @FXML
    private void dateChanged(ActionEvent event) {
        datum = date_Picker.getValue().toString();
    }

    @FXML
    private void indTextChanged(ActionEvent event) {
        indulas = comboBox_Indulas.getValue().toString();
    }

    @FXML
    private void erkTextChanged(ActionEvent event) {
        erkezes = comboBox_Indulas.getValue().toString();
    }

    @FXML
    private void bevitelClick(ActionEvent event) {
        readBox();
    }
    String erkezes;
    String indulas;
    String datum;
    private final ObservableList<Route> tableContent = FXCollections.observableArrayList();
    //private HashSet<String> hashSet = new HashSet<>();
    ArrayList<String> list;

    /*public void sortList(ComboBox box) {
        for (int i = 0; i < box.getItems().size(); i++) {
            hashSet.add(box.getItems().get(i).toString());
        }
        box.getItems().clear();
        box.getItems().addAll(hashSet);

    }*/
    public void readBox() {
       
       // String ugyfel = comboBox_Ugyfel.getValue().toString();
       // comboBox_Ugyfel.getItems().add(ugyfel);
       // comboBox_Ugyfel.getEditor().clear();

        //String tavolsag =  comboBox_Tavolsag.getValue().toString();
        //comboBox_Tavolsag.getItems().add(tavolsag);
       // comboBox_Tavolsag.getEditor().clear();

        comboBox_Indulas.getItems().add(indulas);
       // comboBox_Ugyfel.getItems().add(ugyfel);
       // comboBox_Tavolsag.getItems().add(tavolsag);

        tableContent.add(new Route(datum, indulas, erkezes,erkezes,indulas));
        tableView.setItems(tableContent);
        comboBox_Indulas.getEditor().clear();
        comboBox_Erkezes.getEditor().clear();
    }

    public void setTableData() {

        TableColumn datCol = new TableColumn("Dátum");
        datCol.setMaxWidth(190);
        datCol.setResizable(false);
        datCol.setCellFactory(TextFieldTableCell.forTableColumn());
        datCol.setCellValueFactory(new PropertyValueFactory<Route, String>("datum"));

        TableColumn indCol = new TableColumn("Indulás");        //indulás oszlop elkészítése        
        indCol.setMinWidth(190);        //oszlop min szélesség beállítása 100 pixelre        
        indCol.setResizable(false);
        indCol.setCellFactory(TextFieldTableCell.forTableColumn());     //oszlop adattipus beállítása szövegmezőre        
        indCol.setCellValueFactory(new PropertyValueFactory<Route, String>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        TableColumn erkCol = new TableColumn("Érkezés");
        erkCol.setMinWidth(190);
        erkCol.setResizable(false);
        erkCol.setCellFactory(TextFieldTableCell.forTableColumn());
        erkCol.setCellValueFactory(new PropertyValueFactory<Route, String>("erkezes"));

        TableColumn tavCol = new TableColumn("Távolság");
        tavCol.setMinWidth(75);
        tavCol.setResizable(false);
        tavCol.setCellFactory(TextFieldTableCell.forTableColumn());
        tavCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("tavolsag"));

        TableColumn ugyfCol = new TableColumn("Ügyfél");
        ugyfCol.setMinWidth(190);
        tavCol.setResizable(false);
        ugyfCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ugyfCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ugyfel"));

        tableView.getColumns().addAll(datCol, indCol, erkCol, ugyfCol, tavCol);
        tableView.setItems(tableContent);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        setTableData();
        FileModel filemodel = new FileModel();
        list = filemodel.readFile("A:\\TT_GIT\\UtnyilvantartoJava\\telepulesek.dat");
         comboBox_Indulas.getItems().addAll(list);
        comboBox_Erkezes.getItems().addAll(list);
        readBox();
        //date_Picker.setValue(LocalDate.now());
        //int year = Calendar.YEAR;
        //int month= Calendar.MONTH;
        //int day = Calendar.DAY_OF_WEEK;

    }

}
