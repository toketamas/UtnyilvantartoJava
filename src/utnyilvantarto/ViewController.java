package utnyilvantarto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    DbModel db;

    //Tábla
    @FXML
    TableView table;
    @FXML
    Tab tabNyilv;
    @FXML
    Tab tabBeallit;

    // gombok
    @FXML
    Button btnBev;
    @FXML
    Button btnSetOk;
    @FXML
    Button btnSet;

    //textfildek
    @FXML
    TextField txfNev;
    @FXML
    TextField txfTelep;
    @FXML
    TextField txfAuto;
    @FXML
    TextField txfRendsz;
    @FXML
    TextField txfLoket;
    @FXML
    TextField txfElozo;
    @FXML
    TextField textZaro;
    @FXML
    TextField txfFogyaszt;


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



        System.out.print(observableList.get(observableList.size() - 2).getDatum().getValue()+" ");
        System.out.print(observableList.get(observableList.size() - 2).getIndulas().getText() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getErkezes().getText() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getTavolsag().getText() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getUgyfel().getValue() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getMagan().isSelected() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getVissza().isSelected()+" ");
        System.out.println(observableList.get(observableList.size() - 2).getTelephelyrol().isSelected());



        /*db.addRoute(
                observableList.get(observableList.size()-2).getDatum().getValue().toString(),
                observableList.get(observableList.size()-2).getIndulas().getText(),
                observableList.get(observableList.size() - 2).getErkezes().getText(),
                Integer.parseInt(observableList.get(observableList.size() - 2).getTavolsag().getText()),
                observableList.get(observableList.size() - 2).getUgyfel().getValue().toString(),
                boolToInt(observableList.get(observableList.size() - 2).getMagan().isSelected()),
                boolToInt(observableList.get(observableList.size() - 2).getVissza().isSelected()),
                boolToInt(observableList.get(observableList.size()-2).getTelephelyrol().isSelected())
                );
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        db.addRoute1("2","4","5",4,"3",1,0,1);

    }

    public Integer boolToInt(Boolean value){
        int convertedValue;
        if(value)
            convertedValue=1;
        else
            convertedValue=0;
        return convertedValue;
    }


    LocalDate date = LocalDate.now();
    TableColumn datCol;
    TableColumn checkMagan;
    TableColumn checkVissza;
    TableColumn checkTeleph;
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

        checkTeleph = new TableColumn("Telephelyről");
        checkTeleph.setPrefWidth(50);
        checkTeleph.setResizable(false);
        checkTeleph.setCellValueFactory(new PropertyValueFactory<Route,Boolean>("telephelyrol"));

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


        table.getColumns().addAll(checkMagan,datCol, indCol, erkCol, ugyfCol, tavCol, checkTeleph,  checkVissza);

        table.setItems(observableList);

    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        db = new DbModel();
        setTableData();
        observableList.add(new Route());



    }
}