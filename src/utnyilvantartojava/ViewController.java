package utnyilvantartojava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ViewController implements Initializable {


    @FXML
    TabPane tabPane;
    @FXML
    Tab tabNyilv;
    @FXML
    Tab tabBeallit;
    //Tábla
    @FXML
    TableView table;

    // gombok
    @FXML
    Button btnBev;
    @FXML
    Button btnSetOk;
    @FXML
    Button btnSet;
    @FXML
    Button btnSel;
    @FXML
    Button btnReadExcel;

    // Checkboxok
    @FXML
    CheckBox chkBack;
    @FXML
    CheckBox chkSites;
    @FXML
    CheckBox chkPrivate;

    // Labelek
    @FXML
    Label lblName;
    @FXML
    Label lblKm;
    @FXML
    Label lblSites;


    //textfildek
    // Utnyilvántartó tab
    @FXML
    TextField txtDepart;
    @FXML
    TextField txtArrive;
    @FXML
    TextField txtDistance;
    @FXML
    TextField txtClient;
    // Beállítás tab
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
    TextField txtFile;

    @FXML
    RadioButton radioBtnTh;
    @FXML
    RadioButton radioBtnFile;

    @FXML
    DatePicker datePicker;

    @FXML
    WebView WV;

    URL url1;
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
    SingleSelectionModel<Tab> selectionModel;
    ArrayList<String> settings = new ArrayList<>(); //0-név,1-telephely,2-auto tip., 3-rendszám,4-lökett., 5-fogyasztás, 6-előző záró km.
    String remotExcel = loadFile("link.txt").get(0).toString();
    String localExcel = "ATM_karb_*.xlsx";
    String excelSource;



    @FXML
    private void btnBevitelClick(ActionEvent event) {
        DbModel db = new DbModel();
        if (chkPrivate.isSelected()){
            observableList.get(observableList.size() - 2).setMagan(true);
            observableList.get(observableList.size() - 2).setIndulas("Magán");
            observableList.get(observableList.size() - 2).setErkezes("Magán");
            observableList.get(observableList.size() - 2).setUgyfel("Magán");
            observableList.get(observableList.size() - 2).setVissza(false);
            observableList.get(observableList.size() - 2).setTelephelyrol(false);
        }

        if (chkSites.isSelected()){
            txtArrive.setText(settings.get(1));
        }


        System.out.print(observableList.get(0).getDatum() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getIndulas() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getErkezes() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getTavolsag() + " ");
        System.out.print(observableList.get(observableList.size() - 2).getUgyfel() + " ");
        System.out.print(observableList.get(observableList.size() - 2).isMagan() + " ");
        System.out.print(observableList.get(observableList.size() - 2).isVissza() + " ");
        System.out.println(observableList.get(observableList.size() - 2).isTelephelyrol());

        db.addRoute(
                observableList.get(observableList.size() - 2).getDatum(),
                observableList.get(observableList.size() - 2).getIndulas(),
                observableList.get(observableList.size() - 2).getErkezes(),
                observableList.get(observableList.size() - 2).getTavolsag(),
                observableList.get(observableList.size() - 2).getUgyfel(),
                observableList.get(observableList.size() - 2).isMagan(),
                observableList.get(observableList.size() - 2).isVissza(),
                observableList.get(observableList.size() - 2).isTelephelyrol()
        );
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    private void btnSelClick(ActionEvent event) throws Exception {

        url1 = new URL(WV.getEngine().getLocation());
        System.out.println(url1.toString());
        String content = getURL(url1.toString());
        System.out.println(content);
    }

    @FXML
    private void btnSetClick(ActionEvent event) {
        settings.clear();
        settings.add(txfNev.getText());
        settings.add(txfTelep.getText());
        settings.add(txfAuto.getText());
        settings.add(txfRendsz.getText());
        settings.add(txfLoket.getText());
        settings.add(txfFogyaszt.getText());
        settings.add(txfElozo.getText());
        saveFile("settings.cfg", settings);
        setLabel();
    }

    @FXML
    private void setBtnSetOkClick(ActionEvent event) {
        selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);
    }

    @FXML
    public  void btnReadExcelClick(ActionEvent event) {

        try
        {
           Runtime.getRuntime().exec( "cmd /c start cmd.exe /K \"java -jar ExcelToDB.jar "+excelSource+"  ADATOK  2\"");
        }
        catch (Exception e)
        {
            System.out.println("Nem érhető el a fájl ! ");
            e.printStackTrace();
        }

    }
    @FXML
    private void radioBtnThCheck(ActionEvent event){
        if(radioBtnTh.isSelected()){
            txtFile.setText(remotExcel);
            excelSource= remotExcel;
        }
    }

    @FXML
    private void radioBtnFileCheck(ActionEvent event){
        if (radioBtnFile.isSelected()){
            txtFile.setText(localExcel);
            excelSource= localExcel;
        }
    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("elindult");
        WV.getEngine().load("https://www.google.hu/maps");                   //betölti a WebViev-ba a térképet
        setTableData();                                                          //beállítja a táblát
        checkConfigFile();                                                       //ellenőrzi a settings.cfg meglétét
        loadFile("settings.cfg");
        setText();
        setLabel();
        IOExcel ioExcel = new IOExcel();
        DbModel db = new DbModel();
        excelSource=localExcel;
        observableList.addAll(db.getRoutes("2020-06-01", "2020-06-07"));         // betölti az adatokat az adatbázisból
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        chkSites.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                txtArrive.appendText(settings.get(1));
            }
        });
    }

    public static String getURL(String url) throws Exception {             // URL beolvasása
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine + "\n");
        in.close();
        return response.toString();
    }

    public void setTableData() {
        datCol = new TableColumn("Dátum");
        datCol.setPrefWidth(100);
        datCol.setResizable(false);
        datCol.setCellValueFactory(new PropertyValueFactory<Route, LocalDate>("datum"));

        checkMagan = new TableColumn("Magán");
        checkMagan.setPrefWidth(60);
        checkMagan.styleProperty();
        checkMagan.setResizable(false);

        checkMagan.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("magan"));

        checkVissza = new TableColumn("Oda-V.");
        checkVissza.setPrefWidth(60);
        checkVissza.setResizable(false);
        checkVissza.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("vissza"));

        checkTeleph = new TableColumn("Teleph.");
        checkTeleph.setPrefWidth(60);
        checkTeleph.setResizable(false);
        checkTeleph.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("telephelyrol"));

        indCol = new TableColumn("Indulás");        //indulás oszlop elkészítése
        indCol.setPrefWidth(250);        //oszlop min szélesség beállítása 200 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(250);
        erkCol.setResizable(false);
        erkCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("erkezes"));

        tavCol = new TableColumn("Távolság");
        tavCol.setPrefWidth(60);
        tavCol.setResizable(false);
        tavCol.setCellValueFactory(new PropertyValueFactory<Route, IntegerProperty>("tavolsag"));

        ugyfCol = new TableColumn("Ügyfél");
        ugyfCol.setPrefWidth(200);
        tavCol.setResizable(false);
        ugyfCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("ugyfel"));
        table.getColumns().addAll(datCol, indCol, erkCol, ugyfCol, tavCol, checkTeleph, checkVissza, checkMagan);
        table.setItems(observableList);
    }

    public static void saveFile(String filename, ArrayList<String> list) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        for (int i = 0; i < list.size(); i++)
            writer.println(list.get(i));
        writer.flush();
        writer.close();
    }

    public static ArrayList loadFile(String filename) {

        ArrayList<String> list = new ArrayList<>();
        int rowNumber;
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
            sc.close();
            System.out.println(list.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void checkConfigFile() {
        File file = new File("settings.cfg");
        if (file.exists()) {
            settings = loadFile("settings.cfg");
            System.out.println(settings.toString());
        } else {
            saveFile("settings.cfg",null);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
        }
    }

    public void setText() {
        //0-név,1-telephely,2-auto tip., 3-rendszám,4-lökett., 5-fogyasztás, 6-előző záró km.
        txfNev.setText(settings.get(0));
        txfTelep.setText(settings.get(1));
        txfAuto.setText(settings.get(2));
        txfRendsz.setText(settings.get(3));
        txfLoket.setText(settings.get(4));
        txfElozo.setText(settings.get(6));
        txfFogyaszt.setText(settings.get(5));
    }

    public void setLabel() {
        lblName.setText(settings.get(0));
        lblSites.setText(settings.get(1));
        lblKm.setText(settings.get(6));
    }

}

