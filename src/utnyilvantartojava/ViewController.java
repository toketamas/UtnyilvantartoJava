package utnyilvantartojava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import java.beans.EventHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewController implements Initializable {


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
    @FXML
    Button btnSel;

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
    @FXML
    WebView WV;

    DbModel db = new DbModel();


    private EventHandler checkBoxEventHandler;
    URL url1;
   // String inputLine;

    @FXML
    private void btnClick(ActionEvent event) {
        observableList.add(new Route());
        if (observableList.get(observableList.size() - 2).getMagan().isSelected()) {
            observableList.get(observableList.size() - 2).setIndulas("Magán");
            observableList.get(observableList.size() - 2).setErkezes("Magán");
            observableList.get(observableList.size() - 2).setUgyfel("Magán");
            observableList.get(observableList.size() - 2).setVissza(false);
            observableList.get(observableList.size() - 2).setTelephelyrol(false);
        }

        System.out.print(observableList.get(0).getDatum() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getIndulas() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getErkezes() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getTavolsag() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getUgyfel() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getMagan() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getVissza() + " ");
        System.out.println(observableList.get(observableList.size() - 2).getTelephelyrol());

        db.addRoute(
                observableList.get(observableList.size() - 2).getDatum().getValue(),
                observableList.get(observableList.size() - 2).getIndulas().getText(),
                observableList.get(observableList.size() - 2).getErkezes().getText(),
                Integer.parseInt(observableList.get(observableList.size() - 2).getTavolsag().getText()),
                observableList.get(observableList.size() - 2).getUgyfel().getEditor().getText(),
                observableList.get(observableList.size() - 2).getMagan().isSelected(),
                observableList.get(observableList.size() - 2).getVissza().isSelected(),
                observableList.get(observableList.size() - 2).getTelephelyrol().isSelected());
    }

    @FXML
    private void btnSelClick(ActionEvent event) throws Exception {

        url1 = new URL(WV.getEngine().getLocation());
        System.out.println(url1.toString());
        String content = getText(url1.toString());
        System.out.println(content);
     /*
        System.out.println(url1);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url1.openStream()));


        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine = in.readLine());

            //inputLine = inputLine.substring(0, inputLine.indexOf('<'));
            in.close();


        /*WV.getEngine().load(inputLine);

        String inputLine2;
       BufferedReader in2 = new BufferedReader(
                new InputStreamReader(url1.openStream()));
        while ((inputLine2 = in2.readLine()) != null)

            System.out.println(inputLine2);
        in.close();*/
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
        checkMagan.setPrefWidth(60);
        checkMagan.styleProperty();
        checkMagan.setResizable(false);

        checkMagan.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("magan"));

        checkVissza = new TableColumn("Vissza");
        checkVissza.setPrefWidth(60);
        checkVissza.setResizable(false);
        checkVissza.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("vissza"));

        checkTeleph = new TableColumn("Telephelyről");
        checkTeleph.setPrefWidth(80);
        checkTeleph.setResizable(false);
        checkTeleph.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("telephelyrol"));

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
        table.getColumns().addAll(checkMagan, datCol, indCol, erkCol, ugyfCol, tavCol, checkTeleph, checkVissza);
        table.setItems(observableList);


    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");


        WV.getEngine().load("https://www.google.hu/maps");
        //String a = WV.getInputMethodRequests().getSelectedText();
        //System.out.println(a);
        setTableData();
        observableList.addAll(db.getRoutes("2020-06-01", "2020-06-07"));
        // observableList.add(new Route());
        //db.getRoutes("2020-06-01", "2020-06-07");


    }
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine+"\n");

        //System.out.println(inputLine);

        in.close();

        return response.toString();
    }
}