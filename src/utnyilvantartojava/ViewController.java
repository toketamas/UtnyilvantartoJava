package utnyilvantartojava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
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

    @FXML
    TabPane tabPane;
    @FXML
    Tab tabNyilv;
    @FXML
    Tab tabBeallit;
    @FXML
    WebView WV;
    @FXML
    AnchorPane setPane;

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
    // Beállítás tab
    @FXML
    TextField txfNev;
    @FXML
    TextField txfTelep;
    @FXML
    TextField txfTelepCim;
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
    SearchableComboBox cbClient;


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
    String tempAddress;
    String targetAddress;
    String startAddress;
    Integer distance;

    Client startClient;
    Client targetClient;
    Client telephely;
    boolean priv;
    boolean sites;
    boolean back;
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
            if (chkPrivate.isSelected()) {
                DbModel db = new DbModel();
                try {
                    distance = Integer.parseInt(txtDistance.getText());
                    observableList.add(new Route(datePicker.getValue().toString(), "Magánhasználat", "Magánhasználat", distance, "Magánhasználat", false, chkBack.isSelected(), false));
                    db.addRoute(
                            observableList.get(observableList.size() - 1).getDatum(),
                            observableList.get(observableList.size() - 1).getIndulas(),
                            observableList.get(observableList.size() - 1).getErkezes(),
                            observableList.get(observableList.size() - 1).getTavolsag(),
                            observableList.get(observableList.size() - 1).getUgyfel(),
                            observableList.get(observableList.size() - 1).isMagan(),
                            observableList.get(observableList.size() - 1).isVissza(),
                            observableList.get(observableList.size() - 1).isTelephelyrol()
                    );
                    chkPrivate.setSelected(false);
                    chkSites.setSelected(true);
                    txtDepart.setText(telephely.getClient());
                    startClient = telephely;
                    targetClient = null;
                    txtArrive.clear();
                    cbClient.setDisable(false);
                    cbClient.setValue("Válaszd ki az ügyfelet");
                    txtDistance.clear();
                    db.conn.close();
                } catch (NumberFormatException e) {
                    txtDistance.setText("IDE CSAK SZÁMOT ÍRHATSZ!!!");

                }
            } else if (chkBack.isSelected()) {
                DbModel db = new DbModel();
                observableList.add(new Route(datePicker.getValue().toString(), startAddress, targetAddress, distance, targetClient.getClient(), false, chkBack.isSelected(), false));
                observableList.add(new Route(datePicker.getValue().toString(), targetAddress, startAddress, distance, telephely.getClient(), false, chkBack.isSelected(), false));
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
                db.addRoute(
                        observableList.get(observableList.size() - 1).getDatum(),
                        observableList.get(observableList.size() - 1).getIndulas(),
                        observableList.get(observableList.size() - 1).getErkezes(),
                        observableList.get(observableList.size() - 1).getTavolsag(),
                        observableList.get(observableList.size() - 1).getUgyfel(),
                        observableList.get(observableList.size() - 1).isMagan(),
                        observableList.get(observableList.size() - 1).isVissza(),
                        observableList.get(observableList.size() - 1).isTelephelyrol()
                );

                txtDistance.clear();
                chkSites.setSelected(true);
                targetAddress = "";
                targetClient = null;
                txtArrive.clear();
                db.conn.close();

            } else {

                observableList.add(new Route(datePicker.getValue().toString(), startAddress, targetAddress, distance, targetClient.getClient(), false, chkBack.isSelected(), false));
                //DbModel db1 = new DbModel();
                // /db1.addDistance(startAddress,targetAddress,distance);
                // db1.conn.close();
                DbModel db = new DbModel();

                db.addRoute(
                        observableList.get(observableList.size() - 1).getDatum(),
                        observableList.get(observableList.size() - 1).getIndulas(),
                        observableList.get(observableList.size() - 1).getErkezes(),
                        observableList.get(observableList.size() - 1).getTavolsag(),
                        observableList.get(observableList.size() - 1).getUgyfel(),
                        observableList.get(observableList.size() - 1).isMagan(),
                        observableList.get(observableList.size() - 1).isVissza(),
                        observableList.get(observableList.size() - 1).isTelephelyrol()
                );


                db.conn.close();
                txtDistance.clear();
                txtDepart.setText(targetAddress);
                startAddress = targetAddress;
                targetAddress = "";
                startClient = targetClient;
                targetClient = null;
                txtArrive.clear();


                try {
                    db.conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



       /* if (btnSel.isArmed()) {
            url1 = new URL(WV.getEngine().getLocation());
            System.out.println(url1.toString());
            String content = getURL(url1.toString());
            System.out.println(content);
        }*/

            }
        }   //beállít tab gombok
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
            checkSpecialClients();
            setPane.setDisable(true);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(0);
            setLabels();
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
            txtDepart.setEditable(false);
        } else {
            txtDepart.clear();
            txtDepart.setEditable(true);
        }

        if (chkPrivate.isSelected()) {
            //DbModel db = new DbModel();

            //targetClient=startClient;
            txtDistance.setEditable(true);
            chkSites.setSelected(false);
            chkBack.setSelected(false);
            cbClient.setDisable(true);
            txtDepart.setText("Magánhasználat");
            txtArrive.setText("Magánhasználat");
            startAddress = "";
            targetAddress = "";
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
    private void cboxTextChange(ActionEvent event) {

        DbModel db = new DbModel();
        targetClient = db.getClient(cbClient.getSelectionModel().getSelectedItem().toString());
        txtArrive.clear();


        //System.out.println(arriveCity+" "+arriveAddress);
        targetAddress = targetClient.getCity() + " " + targetClient.getAddress();
        txtArrive.appendText(targetAddress);

        if (chkSites.isSelected()) {   //le kell kérni az induló gépszámot aztán megszerezni a cél gépszámot lekérdezni a távot ha nincs meg akkor lekérdezni a téképtől beírnia textboxba aztán beírni az adatbázisba
            startAddress = startClient.getCity() + " " + startClient.getAddress();
            chkSites.setSelected(false);
        }
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        WV.getEngine().load("https://www.google.hu/maps/dir/" + startAddress + "/" + targetAddress); // lekérdezi a távolságot a google mapstól
        btnBev.setDisable(true);
        //cbClient.setDisable(true);

        WV.getEngine().getLoadWorker().stateProperty().addListener( //figyeli hogy betöltődött-e az oldal
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Worker.State> observable,
                            Worker.State oldValue, Worker.State newValue) {
                        switch (newValue) {
                            case SUCCEEDED:
                            case FAILED:
                            case CANCELLED:
                                WV
                                        .getEngine()
                                        .getLoadWorker()
                                        .stateProperty()
                                        .removeListener(this);
                        }


                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }
                        // Your logic here
                        String gotUrl = getURL(WV.getEngine().getLocation());
                        int index = gotUrl.indexOf(" km");
                        String sub = gotUrl.substring(index - 6, index);
                        sub = sub.replace(',', '.');
                        distance = (int) Math.round(Double.parseDouble(sub.substring(sub.indexOf("\"") + 1)));
                        txtDistance.setText(distance.toString());
                        btnBev.setDisable(false);
                        cbClient.setDisable(false);
                        txtDistance.setEditable(false);

                    }
                });
    }

    public static String getURL(String url) {             // URL beolvasása
        int index;
        StringBuilder response = null;
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine + "\n");
            in.close();
            /*index = response.indexOf(" Km");
            System.out.println(index);*/
        } catch (Exception e) {
            System.out.println("hiba");
        }
        return response.toString();
    }

    public void run() {
        System.out.println("elindult");
        //IOExcel ioExcel = new IOExcel();
        DbModel db = new DbModel();
        telephely = db.getClient("telephely");
        setTableData();                                                          //beállítja a táblát
        checkConfigFile();                                                       //ellenőrzi a settings.cfg meglétét
        loadFile("settings.cfg");
        setText();
        setLabels();
        chkSites.setSelected(true);
        startClient = telephely;                  // !!!!!!telephelyet állítja startclientnek ezt kell módosítani ha lesz mentett előző client  !!!!!!!
        txtDepart.setText(startClient.getClient());
        txtDepart.setDisable(true);
        //departCity = startClient.getCity();
        //departAddress = startClient.getAddress();
        cbClient.setValue("Válaszd ki az ügyfelet");
        WV.getEngine().load("https://www.google.hu/maps/");                  //betölti a WebView-ba a térképet
        datePicker.setValue(date);
        excelSource = localExcel;          //!!!!!!!!!!!!!! beállítja az excel forrását egyenlőre local ha lesz távoli akkor ezt kell módosítani!!!!!!!!!
        observableList.addAll(db.getRoutes("2020-06-01", "2020-06-07"));         // betölti az adatokat az adatbázisból
        cbClient.getItems().addAll(db.getAllClient());

        fillField(txtArrive, db.getAllCitys()); //betölti az összes lehetséges ügyfelet a combo box listájába
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        checkSpecialClients(); // ellenőrzi hogy léteznek e a spec cliensek (magán,telephely......)
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
        lblName.setText("Név: " + settings.get(0));
        lblSites.setText("T.hely: " + settings.get(1));
        lblKm.setText("Km óra: " + settings.get(7) + " Km");
    }

    private void fillField(TextField text, ArrayList list) {
        TextFields.bindAutoCompletion(text, list);
    }

    private void checkSpecialClients() {
        DbModel db = new DbModel();
        Client telephely = db.getClient("telephely");
        if (telephely == null || telephely.getCity() != settings.get(1) || telephely.getAddress() != settings.get(2) || telephely.getField() != settings.get(0)) {
            if (telephely != null) {
                db.delClient("telephely");
            }
            db.addClient(
                    "telephely",
                    "telephely",
                    "telephely",
                    "telephely",
                    0,
                    settings.get(1),
                    settings.get(2),
                    true,
                    0,
                    settings.get(0)
            );
        }
        Client diebold = db.getClient("DieboldNixdorf");
        if (diebold == null) {
            db.addClient(
                    "DieboldNixdorf",
                    "DieboldNixdorf",
                    "telephely",
                    "telephely",
                    2220,
                    "Vecsés",
                    "Lőrinci út 59-61",
                    true,
                    0,
                    "DieboldNixdorf"
            );
        }

        Client dieboldIroda = db.getClient("DNIroda");
        if (dieboldIroda == null) {
            db.addClient(
                    "DieboldNixdorf",
                    "DNIroda",
                    "telephely",
                    "telephely",
                    1138,
                    "Budapest",
                    "Népfürdő utca 22/B",
                    true,
                    0,
                    "DieboldNixdorf"
            );
        }
        Client magan = db.getClient("Magánhasználat");
        if (magan == null) {
            db.addClient(
                    "Magánhasználat",
                    "Magánhasználat",
                    "Magánhasználat",
                    "Magánhasználat",
                    0,
                    "Magánhasználat",
                    "Magánhasználat",
                    true,
                    0,
                    "Magánhasználat"
            );
        }
        try {
            db.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

