package utnyilvantarto;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.internal.codegen.types.BooleanType;
import org.controlsfx.control.textfield.CustomTextField;

import java.beans.EventHandler;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.EventListener;
import java.util.ResourceBundle;

public class View implements Initializable {

    @FXML
    TableView table;

    // gombok
    @FXML
    Button btnUjSor;
    private EventHandler checkBoxEventHandler;

    @FXML
    private void btnClick(ActionEvent event) {
        observableList.add(new Route());
        if(observableList.get(observableList.size()-2).getMagan().isSelected()){
            observableList.get(observableList.size()-2).setIndulas("Magán");
            observableList.get(observableList.size()-2).setErkezes("Magán");
            observableList.get(observableList.size()-2).setUgyfel("Magán");
            observableList.get(observableList.size()-2).setVissza(false);
        }


        System.out.println(observableList.get(0).getDatum().getValue());
        System.out.print(observableList.get(0).getIndulas().getText() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getErkezes().getText() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getTavolsag().getValue() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getUgyfel().getValue() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getMagan().isSelected() + " ");
        System.out.println(observableList.get(observableList.size() - 2).getVissza().isSelected());
    }


    LocalDate date = LocalDate.now();
    TableColumn datCol;
    TableColumn checkMagan;
    TableColumn checkVissza;
    TableColumn indCol;
    TableColumn erkCol;
    TableColumn tavCol;
    TableColumn ugyfCol;

    public ObservableList<Route> observableList = FXCollections.observableArrayList();

    //
    public void setTableData() {

        datCol = new TableColumn("Dátum");
        datCol.setPrefWidth(150);
        datCol.setResizable(false);
        datCol.setCellValueFactory(new PropertyValueFactory<Route, LocalDate>("datum"));

        checkMagan = new TableColumn("Magán");
        checkMagan.setPrefWidth(50);
        checkMagan.setResizable(false);

        checkMagan.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("magan"));

        checkVissza = new TableColumn("Vissza");
        checkVissza.setPrefWidth(50);
        checkVissza.setResizable(false);
        checkVissza.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("vissza"));

        indCol = new TableColumn("Indulás");        //indulás oszlop elkészítése
        indCol.setPrefWidth(150);        //oszlop min szélesség beállítása 100 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, String>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(150);
        erkCol.setResizable(false);
        erkCol.setCellValueFactory(new PropertyValueFactory<Route, String>("erkezes"));

        tavCol = new TableColumn("Távolság");
        tavCol.setPrefWidth(100);
        tavCol.setResizable(false);
        tavCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("tavolsag"));

        ugyfCol = new TableColumn("Ügyfél");
        ugyfCol.setPrefWidth(120);
        tavCol.setResizable(false);
        ugyfCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ugyfel"));


        table.getColumns().addAll(datCol, indCol, erkCol, ugyfCol, tavCol, checkMagan, checkVissza);

        table.setItems(observableList);

    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        setTableData();
        observableList.add(new Route(date, "Székesfehérvár", "Pécs", 123, "Erste Bank", true, false));


    }
}