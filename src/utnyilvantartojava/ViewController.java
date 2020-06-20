package utnyilvantartojava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ViewController implements Initializable {

    @FXML TabPane tabPane;
    @FXML Tab tabNyilv;
    @FXML Tab tabBeallit;
    @FXML WebView WV;
    @FXML AnchorPane setPane;

    //Tábla
    @FXML TableView table;

    // gombok
    @FXML Button btnBev;
    @FXML Button btnSetOk;
    @FXML Button btnSet;
    @FXML Button btnSel;
    @FXML Button btnReadExcel;

    // Checkboxok
    @FXML CheckBox chkBack;
    @FXML CheckBox chkSites;
    @FXML CheckBox chkPrivate;

    // Labelek
    @FXML Label lblName;
    @FXML Label lblKm;
    @FXML Label lblSites;

    //textfildek
        // Utnyilvántartó tab
    @FXML TextField txtDepart;
    @FXML TextField txtArrive;
    @FXML TextField txtDistance;
        // Beállítás tab
    @FXML TextField txfNev;
    @FXML TextField txfTelep;
    @FXML TextField txfTelepCim;
    @FXML TextField txfAuto;
    @FXML TextField txfRendsz;
    @FXML TextField txfLoket;
    @FXML TextField txfElozo;
    @FXML TextField textZaro;
    @FXML TextField txfFogyaszt;
    @FXML TextField txtFile;

    @FXML RadioButton radioBtnTh;
    @FXML RadioButton radioBtnFile;

    @FXML DatePicker datePicker;

    @FXML SearchableComboBox cbClient;


    //Változók
    URL url1;
    LocalDate date = LocalDate.now();
    public ObservableList<Route> observableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    ArrayList<String> settings = new ArrayList<>(); //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km.
    String remoteExcel = loadFile("link.txt").get(0).toString();
    String localExcel = "ATM_karb_*.xlsx";
    String excelSource;
    String departCity;
    String departAddress;
    String arriveCity;
    String arriveAddress;
    String client;
    int distance;
    LocalDate workDate;
    TableColumn datCol;
    TableColumn checkMagan;
    TableColumn checkVissza;
    TableColumn checkTeleph;
    TableColumn indCol;
    TableColumn erkCol;
    TableColumn tavCol;
    TableColumn ugyfCol;

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        run();
    }

    @FXML
    private void btnClick(ActionEvent event) throws Exception {
        //útnyilvántartó tab gombok

        //Bevitel gomb
        if (btnBev.isArmed()) {
            DbModel db = new DbModel();
            if (chkPrivate.isSelected()) {
                observableList.get(observableList.size() - 2).setMagan(true);
                observableList.get(observableList.size() - 2).setIndulas("Magán");
                observableList.get(observableList.size() - 2).setErkezes("Magán");
                observableList.get(observableList.size() - 2).setUgyfel("Magán");
                observableList.get(observableList.size() - 2).setVissza(false);
                observableList.get(observableList.size() - 2).setTelephelyrol(false);
            }

            if (chkSites.isSelected()) {
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

        if (btnSel.isArmed()) {
            url1 = new URL(WV.getEngine().getLocation());
            System.out.println(url1.toString());
            String content = getURL(url1.toString());
            System.out.println(content);
        }

        //beállít tab gombok
        if (btnSetOk.isArmed()) {
            settings.clear();
            settings.add(txfNev.getText());
            settings.add(txfTelep.getText());
            settings.add(txfTelepCim.getText());
            settings.add(txfAuto.getText());
            settings.add(txfRendsz.getText());
            settings.add(txfLoket.getText());
            settings.add(txfFogyaszt.getText());
            settings.add(txfElozo.getText());
            saveFile("settings.cfg", settings);
            setPane.setDisable(true);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(0);
        }

        if (btnSet.isArmed()) {
            setPane.setDisable(false);
            setLabels();

        }
        if (btnReadExcel.isArmed()) {
            try {
                Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"java -jar ExcelToDB.jar " + excelSource + "  ADATOK  2\"");
            } catch (Exception e) {
                System.out.println("Nem érhető el a fájl ! ");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void chkCheck(ActionEvent event) {
        if (chkSites.isSelected()) {
            System.out.println(chkSites.getText());
            txtDepart.setText("Telephely");
            txtDepart.setDisable(true);
        } else {
            txtDepart.clear();
            txtDepart.setDisable(false);
        }
    }

    @FXML
    private void radioCheck(ActionEvent event) {
        if (radioBtnTh.isSelected()) {
            txtFile.setText(remoteExcel);
            excelSource = remoteExcel;
        }

        if (radioBtnFile.isSelected()) {
            txtFile.setText(localExcel);
            excelSource = localExcel;
        }
    }
    @FXML
    private void cboxTextChange(ActionEvent event){
      DbModel db = new DbModel();
      ArrayList<String> list = null;
      String targetClient;
      String startClient;
        if(cbClient.isManaged()){
            targetClient=cbClient.getValue().toString();
            list=db.getClient(targetClient);
            arriveAddress=list.get(1);
            arriveCity=list.get(0);
            txtArrive.clear();
            System.out.println(arriveCity+" "+arriveAddress);
            txtArrive.appendText(arriveCity+" "+arriveAddress);
            if (txtDepart.getText().contentEquals("Telephely")){   //le kell kérni az induló gépszámot aztán megszerezni
                startClient=settings.get(1)+" "+settings.get(2);        // a cél gépszámot lekérdezni a távot ha nincs meg
            }else{                                                     //akkor lekérdezni a téképtől beírnia textboxba
                db.getDistance(db,targetClient)                         // aztán beírni az adatbázisba

            }

        }
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    public void run() {
        System.out.println("elindult");

        //IOExcel ioExcel = new IOExcel();
        DbModel db = new DbModel();
        setTableData();                                                          //beállítja a táblát
        checkConfigFile();                                                       //ellenőrzi a settings.cfg meglétét
        loadFile("settings.cfg");
        setText();
        setLabels();
        cbClient.accessibleTextProperty().setValue("ddd");
        chkSites.setSelected(true);
        txtDepart.setText("Telephely");
        txtDepart.setDisable(true);
        departCity=settings.get(1);
        departAddress=settings.get(2);
        WV.getEngine().load("https://www.google.hu/maps");                  //betölti a WebViev-ba a térképet
        datePicker.setValue(date);
        excelSource = localExcel;
        observableList.addAll(db.getRoutes("2020-06-01", "2020-06-07"));         // betölti az adatokat az adatbázisból
        cbClient.getItems().addAll(db.getAllClient());
        //fillField(txtClient,db.getAllClient());
        fillField(txtArrive,db.getAllCitys());
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        indCol.setPrefWidth(220);        //oszlop min szélesség beállítása 200 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(220);
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
            saveFile("settings.cfg", null);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
        }
    }

    public void setText() {
        //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km.
        txfNev.setText(settings.get(0));
        txfTelep.setText(settings.get(1));
        txfTelepCim.setText(settings.get(2));
        txfAuto.setText(settings.get(3));
        txfRendsz.setText(settings.get(4));
        txfLoket.setText(settings.get(5));
        txfFogyaszt.setText(settings.get(6));
        txfElozo.setText(settings.get(7));

    }

    public void setLabels() {
        lblName.setText("Név: "+settings.get(0));
        lblSites.setText("T.hely: "+settings.get(1));
        lblKm.setText("Km óra: "+settings.get(7)+" Km");
    }

    private void fillField(TextField text, ArrayList list)
    {
        TextFields.bindAutoCompletion(text, list);
    }
}

