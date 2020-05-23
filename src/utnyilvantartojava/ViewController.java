/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utnyilvantartojava;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 *
 * @author Tamas
 */
public class ViewController implements Initializable {
    
    
    
   @FXML
   TableView tableView;

// gombok
    @FXML
    Button button_bevitel;

    @FXML
    Button button_BeallitasKesz;

//combo boxok
    @FXML
    ComboBox comboBox_Indulas;
    @FXML
    ComboBox comboBox_Erkezes;
    @FXML
    ComboBox comboBox_Ugyfel;
    @FXML
    ComboBox comboBox_Tavolsag;

//date picker
    @FXML
    DatePicker datePicker;

//check boxok
    @FXML
    CheckBox checkBox_Ugyfeladatok;
    @FXML
    CheckBox checkBox_Telephelyrol;
    @FXML
    CheckBox checkBox_OdaVissza;
    @FXML
    CheckBox checkBox_Magan;
  

    private final ObservableList<Route> data = FXCollections.observableArrayList(
            new Route("2020-05-12","Székesfehérvár", "Mór", "Erste Bank", "33")
    );

    public void kiir(){
        System.out.println("Elindult");
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

        tableView.getColumns().addAll(datCol,indCol, erkCol, ugyfCol,tavCol);
         tableView.setItems(data);
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        setTableData();
    }    
    
}
