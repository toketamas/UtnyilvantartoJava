package utnyilvantarto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.internal.codegen.types.BooleanType;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML TableView table;

    // gombok
    @FXML Button btnUjSor;

    @FXML private void  btnClick( ActionEvent event){
        observableList.add(new Route());
        System.out.println(observableList.size());
        System.out.print(observableList.get(observableList.size()-2).getIndulas().getText()+" ");
        System.out.print(observableList.get(observableList.size()-2).getErkezes().getText()+" ");
        System.out.print(observableList.get(observableList.size()-2).getTavolsag().getValue()+" ");
        System.out.print(observableList.get(observableList.size()-2).getUgyfel().getValue()+" ");
        System.out.print(observableList.get(observableList.size()-2).getMagan().isSelected()+" ");
        System.out.println(observableList.get(observableList.size()-2).getVissza().isSelected());
    }

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        setTableData();
        FileModel filemodel = new FileModel();
        //list = filemodel.readFile("A:\\TT_GIT\\UtnyilvantartoJava\\telepulesek.dat");
        CheckBox chk =new CheckBox();         //teszt
        CheckBox chk1 = new CheckBox();
        DatePicker dtp = new DatePicker();
        TextField cbi = new TextField();

        dtp.setValue(LocalDate.now());
        chk.setSelected(true);
        chk1.setSelected(true);    //teszt
       observableList.add(new Route(dtp,"Székesfehérvár","Pécs",123,"Erste Bank",true,false));

    }

    private  ObservableList<Route> observableList = FXCollections.observableArrayList ();
    //
    public void setTableData() {

        TableColumn datCol = new TableColumn("Dátum");
        datCol.setPrefWidth(150);
        datCol.setResizable(false);
        datCol.setCellValueFactory(new PropertyValueFactory<Route, LocalDate>("datum"));

        TableColumn checkMagan = new TableColumn("Magán");
        checkMagan.setPrefWidth(50);
        checkMagan.setResizable(false);

        checkMagan.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("magan"));

        TableColumn checkVissza = new TableColumn("Vissza");
        checkVissza.setPrefWidth(50);
        checkVissza.setResizable(false);
        checkVissza.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("vissza"));

        TableColumn indCol = new TableColumn("Indulás");        //indulás oszlop elkészítése
        indCol.setPrefWidth(150);        //oszlop min szélesség beállítása 100 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, String>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        TableColumn erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(150);
        erkCol.setResizable(false);
        erkCol.setCellValueFactory(new PropertyValueFactory<Route, String>("erkezes"));

        TableColumn tavCol = new TableColumn("Távolság");
        tavCol.setPrefWidth(100);
        tavCol.setResizable(false);
        tavCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("tavolsag"));

        TableColumn ugyfCol = new TableColumn("Ügyfél");
        ugyfCol.setPrefWidth(120);
        tavCol.setResizable(false);
        ugyfCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ugyfel"));



        table.getColumns().addAll(datCol, indCol, erkCol, ugyfCol, tavCol, checkMagan,checkVissza);

        table.setItems(observableList);

    }


}