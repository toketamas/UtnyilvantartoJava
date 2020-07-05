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
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ViewController implements Initializable {

    @FXML
    TabPane tabPane;
    @FXML
    AnchorPane setPane;
    @FXML
    Pane paneCorr;
    @FXML
    Pane paneNormal;

    //Tábla
    @FXML
    TableView<Route> table;
    @FXML
    Tab tabNyilv;
    @FXML
    Tab tabBeallit;

    //webview
    @FXML
    WebView WV;

    // gombok
    @FXML
    Button btnBev;
    @FXML
    Button btnSetOk;
    @FXML
    Button btnSet;
    @FXML
    Button btnDistance;
    @FXML
    Button btnReadExcel;
    @FXML
    Button btnReady;
    @FXML
    Button btnMakeExcel;
    @FXML
    Button btnSave;
    @FXML
    Button btnInsert;
    @FXML
    Button btnDelete;
    @FXML
    Button btnCancel;

    // Checkboxok
    @FXML
    CheckBox chkBack;
    @FXML
    CheckBox chkSites;
    @FXML
    CheckBox chkPrivate;
    @FXML
    CheckBox chkBackToSites;


    // Labelek
    @FXML
    Label lblName;
    @FXML
    Label lblKm;
    @FXML
    Label lblSites;
    @FXML
    Label lblMegtett;
    @FXML
    Label lblKezdo;

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
    TextField txtDate;
    @FXML
    TextField txtFueling;

    @FXML
    RadioButton radioBtnTh;
    @FXML
    RadioButton radioBtnFile;

    @FXML
    DatePicker datePicker;

    @FXML
    SearchableComboBox<String> cbClient;

    DbModel db = new DbModel();
    LocalDate date = LocalDate.now();
    public ObservableList<Route> observableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    ArrayList<String> settings = new ArrayList<>(); //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap
    //Változók
    URL url1;
    String remoteExcel = loadFile("link.txt").get(0).toString();
    String localExcel = "ATM_karb_*.xlsx";
    String excelSource;
    String targetAddress;        // érkezés címe
    String startAddress;         // indulás címe
    String workDate;            //a hónap amivel dolgozunk
    String excelName;

    Client startClient;             // induló kliens
    Client targetClient;            // cél kliens
    Client telephely;                // telephely kliens
    Client startClientTemp;         //itt tárolja  a startklienst amikor egy másikat módosít a felhasználó
    Client targetClientTemp;        //ugyanaz mint feljebb a célklienssel

    Route selectedRoute;            // a táblából kiválasztott út

    Double fueling = 0.0;

    int selectedClientSpedometer;
    boolean selectedClientOdaVissza;

    Integer distance;
    Integer spedometer;
    Integer megtettKM;
    int selctedRow;

    TableColumn datCol;
    TableColumn maganCol;
    TableColumn odaVisszaCol;
    TableColumn tankolCol;
    TableColumn indCol;
    TableColumn erkCol;
    TableColumn tavCol;
    TableColumn ugyfCol;
    TableColumn spedometerCol;


    @FXML
    private void btnClick(ActionEvent event) throws Exception {
        //útnyilvántartó tab gombok

        //Bevitel gomb
        if (btnBev.isArmed()) {
            String date = datePicker.getValue().toString();

            if (chkPrivate.isSelected()) {
                try {
                    distance = parseInt(txtDistance.getText());
                    spedometer = spedometer + distance;
                    setLabels();
                    observableList.add(new Route(date, chkPrivate.isSelected(), "Magánhasználat", "Magánhasználat", "Magánhasználat", fueling, spedometer, distance, false,observableList.size()));
                    settings.set(9, "telephely");
                    saveFile("settings.cfg", settings);
                    chkPrivate.setSelected(false);
                    chkSites.setSelected(true);
                    txtDepart.setText(telephely.getClient());
                    startClient = telephely;
                    cbClient.setDisable(false);
                } catch (NumberFormatException e) {
                    txtDistance.setText("IDE CSAK SZÁMOT ÍRHATSZ!!!");
                }
            } else if (chkBack.isSelected()) {
                spedometer = spedometer + distance;
                observableList.add(new Route(date, chkPrivate.isSelected(), startAddress, targetAddress, targetClient.getClientNumber() + "/" + targetClient.getClient(), fueling, spedometer, distance, chkBack.isSelected(),observableList.size()));
                spedometer = spedometer + distance;
                setLabels();
                observableList.add(new Route(date, chkPrivate.isSelected(), targetAddress, startAddress, startClient.getClientNumber() + "/" + startClient.getClient(), fueling, spedometer, distance, chkBack.isSelected(),observableList.size()));
                settings.set(9, "telephely");
                saveFile("settings.cfg", settings);
                chkSites.setSelected(true);
                txtDepart.setText(telephely.getClient());
                startClient = telephely;
            } else {
                spedometer = spedometer + distance;
                setLabels();
                observableList.add(new Route(date, chkPrivate.isSelected(), startAddress, targetAddress, targetClient.getClientNumber() + "/" + targetClient.getClient(), fueling, spedometer, distance, chkBack.isSelected(),observableList.size()));
                settings.set(9, targetClient.getClientNumber());
                saveFile("settings.cfg", settings);
                txtDepart.setText(targetAddress);
                startClient = targetClient;
                startAddress = targetAddress;
                chkSites.setSelected(false);
            }

            if (observableList.get(observableList.size() - 1).isVissza()) {
                db.addRoute(
                        observableList.get(observableList.size() - 2).getDatum(),
                        observableList.get(observableList.size() - 2).isMagan(),
                        observableList.get(observableList.size() - 2).getIndulas(),
                        observableList.get(observableList.size() - 2).getErkezes(),
                        observableList.get(observableList.size() - 2).getUgyfel(),
                        observableList.get(observableList.size() - 2).getSpedometer(),
                        observableList.get(observableList.size() - 2).getFueling(),
                        observableList.get(observableList.size() - 2).getTavolsag(),
                        observableList.get(observableList.size() - 2).isVissza(),
                        observableList.get(observableList.size() - 2).getCellId()
                );
            }
            db.addRoute(
                    observableList.get(observableList.size() - 1).getDatum(),
                    observableList.get(observableList.size() - 1).isMagan(),
                    observableList.get(observableList.size() - 1).getIndulas(),
                    observableList.get(observableList.size() - 1).getErkezes(),
                    observableList.get(observableList.size() - 1).getUgyfel(),
                    observableList.get(observableList.size() - 1).getSpedometer(),
                    observableList.get(observableList.size() - 1).getFueling(),
                    observableList.get(observableList.size() - 1).getTavolsag(),
                    observableList.get(observableList.size() - 1).isVissza(),
                    observableList.get(observableList.size() - 1).getCellId());


            if (db.getDistance(startAddress, targetAddress) == 0 && db.getDistance(targetAddress, startAddress) == 0) {
                System.out.println(db.getDistance(startAddress, targetAddress) + " " + db.getDistance(targetAddress, startAddress));
                System.out.println(startAddress + " " + targetAddress);
                db.addDistance(startAddress, targetAddress, distance);
            }
            if (chkBackToSites.isSelected()) {
                chkSites.setSelected(true);
                chkBackToSites.setSelected(false);
                txtDepart.setText(telephely.getClient());
            }
            txtDistance.clear();
            targetAddress = "";
            targetClient = null;
            txtArrive.clear();
            cbClient.setValue("Válaszd ki az ügyfelet");
            table.scrollTo(table.getItems().size() - 1);
        }

        if (btnDistance.isArmed()) {
            startAddress = txtDepart.getText();
            if (txtDepart.getText().toLowerCase().equals("telephely")) {
                startAddress = telephely.getCity() + " " + telephely.getAddress();
            } else {
                startAddress = txtDepart.getText();
            }
            targetAddress = txtArrive.getText();
            getDistanceFromGmaps(startAddress, targetAddress);
        }

        //beállít tab gombok
        if (btnSetOk.isArmed()) {
            settings.clear();
            settings.set(0, txfNev.getText());
            settings.set(1, txfTelep.getText());
            settings.set(2, txfTelepCim.getText());
            settings.set(3, txfAuto.getText());
            settings.set(4, txfRendsz.getText());
            settings.set(5, txfLoket.getText());
            settings.set(6, txfFogyaszt.getText());
            settings.set(7, txfElozo.getText());
            settings.set(8, txtDate.getText());
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
        if (btnReady.isArmed()) {
            workDate = txtDate.getText();
            settings.set(8, workDate);
            saveFile("settings.cfg", settings);
            observableList.clear();
            observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));
            //loadFile("settings.cfg");
        }

        if (btnMakeExcel.isArmed()) {
            excelName = workDate + "_" + settings.get(0) + "_" + settings.get(4) + "_gkelsz.xlsx";
            makeExcel(excelName, "nyomtat");
        }
                
        if (btnCancel.isArmed()){
            startClient=startClientTemp;
            targetClient=targetClientTemp;
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
        }
        if (btnDelete.isArmed()){
            db.delRoute(selectedRoute.getRouteId());
            System.out.println(selectedRoute.getRouteId());
            rebuildSpedometer();
        }

        //if (btn)

    }

    @FXML
    private void chkCheck(ActionEvent event) {
        if (chkSites.isSelected()) {
            txtDepart.setText("Telephely");
            txtDepart.setEditable(false);
            chkBackToSites.setSelected(false);
        } else {
            //txtDepart.clear();
            txtDepart.setEditable(true);
        }

        if (chkPrivate.isSelected()) {
            txtDistance.setEditable(true);
            chkSites.setSelected(false);
            chkBackToSites.setSelected(false);
            chkBack.setSelected(false);
            cbClient.setDisable(true);
            txtArrive.setEditable(false);
            txtDepart.setText("Magánhasználat");
            txtArrive.setText("Magánhasználat");
            startAddress = "";
            targetAddress = "";
        } else if (chkBackToSites.isSelected()) {
            txtArrive.setText("Telephely");
            txtArrive.setEditable(false);
            chkSites.setSelected(false);
            targetClient = telephely;
            startAddress = startClient.getCity() + " " + startClient.getAddress();
            targetAddress = telephely.getCity() + " " + telephely.getAddress();
            getDistanceFromGmaps(startAddress, targetAddress);

        } else {
            txtArrive.clear();
            txtArrive.setEditable(true);


        }
    }

    @FXML
    private void chkRabio(ActionEvent event) {
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

        //DbModel db = new DbModel();
        targetClient = db.getClient(cbClient.getSelectionModel().getSelectedItem());
        txtArrive.clear();

        //System.out.println(arriveCity+" "+arriveAddress);
        targetAddress = targetClient.getCity() + " " + targetClient.getAddress();
        txtArrive.appendText(targetAddress);

        if (chkSites.isSelected()) {   //le kell kérni az induló gépszámot aztán megszerezni a cél gépszámot lekérdezni a távot ha nincs meg akkor lekérdezni a téképtől beírnia textboxba aztán beírni az adatbázisba
            startAddress = startClient.getCity() + " " + startClient.getAddress();

        }
        getDistanceFromGmaps(startAddress, targetAddress);
    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        run();
    }

    public void getDistanceFromGmaps(String sAddress, String tAddress) {
        sAddress = sAddress.replaceAll("/", "");
        tAddress = tAddress.replaceAll("/", "");
        if (txtDistance.getText()!=""||txtDistance!=null)
        WV.getEngine().load("https://www.google.hu/maps/dir/" + sAddress + "/" + tAddress); // lekérdezi a távolságot a google mapstól
        btnBev.setDisable(true);
        btnDistance.setDisable(true);

        WV.getEngine().getLoadWorker().stateProperty().addListener( //figyeli hogy betöltődött-e az oldal
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Worker.State> observable,
                            Worker.State oldValue, Worker.State newValue) {
                        switch (newValue) {
                            case SUCCEEDED:
                                btnDistance.setDisable(false);
                                btnBev.setDisable(false);
                                break;
                            case FAILED:
                                System.out.println("Az oldal betöltése sikertelen");
                                break;
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
        checkConfigFile();                                              //ellenőrzi a settings.cfg meglétét
        telephely = db.getClient("telephely");
        setTableColumns();                                                          //beállítja a táblát
        workDate = settings.get(8);
        spedometer = Integer.parseInt(settings.get(7));
        megtettKM = db.getSpedometer(workDate);
        spedometer = spedometer + megtettKM;
        setText();
        setLabels();
        System.out.println(spedometer);
        chkSites.setSelected(true);
        cbClient.setValue("Válaszd ki az ügyfelet");
        WV.getEngine().load("https://www.google.hu/maps/");                  //betölti a WebView-ba a térképet
        datePicker.setValue(date);
        excelSource = localExcel;          //!!!!!!!!!!!!!! beállítja az excel forrását egyenlőre local ha lesz távoli akkor ezt kell módosítani!!!!!!!!!
        observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));         // betölti az adatokat az adatbázisból
        startClient = db.getClient(settings.get(9));                  // beállítja startclientnek az utolsó érkezés helyét
        if (startClient.getClient().toLowerCase().startsWith("telephely")) {
            txtDepart.setText(startClient.getClient());
            chkSites.setSelected(true);
        } else {
            txtDepart.setText(startClient.getCity() + " " + startClient.getAddress());
            chkSites.setSelected(false);
        }
        txtDepart.setEditable(false);
        cbClient.getItems().addAll(db.getAllClient());
        fillField(txtArrive, db.getAllCitys()); //betölti az összes lehetséges ügyfelet a combo box listájába
        checkSpecialClients(); // ellenőrzi hogy léteznek e a spec cliensek (magán,telephely......)
        table.scrollTo(table.getItems().size() - 1);
    }

    public void setTableColumns() {
        datCol = new TableColumn("Dátum");
        datCol.setPrefWidth(92);
        datCol.setResizable(false);
        datCol.setCellValueFactory(new PropertyValueFactory<Route, LocalDate>("datum"));

        maganCol = new TableColumn("Magán");
        maganCol.setPrefWidth(60);
        maganCol.styleProperty();
        maganCol.setResizable(false);

        maganCol.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("magan"));

        odaVisszaCol = new TableColumn("Oda-V.");
        odaVisszaCol.setPrefWidth(55);
        odaVisszaCol.setResizable(false);
        odaVisszaCol.setCellValueFactory(new PropertyValueFactory<Route, Boolean>("vissza"));

        tankolCol = new TableColumn("KmÓ");
        tankolCol.setPrefWidth(57);
        tankolCol.setResizable(false);
        tankolCol.setCellValueFactory(new PropertyValueFactory<Route, Double>("spedometer"));


        spedometerCol = new TableColumn("Tankol");
        spedometerCol.setPrefWidth(57);
        spedometerCol.setResizable(false);
        spedometerCol.setCellValueFactory(new PropertyValueFactory<Route, Double>("fueling"));


        indCol = new TableColumn("Indulás");        //indulás oszlop elkészítése
        indCol.setPrefWidth(220);        //oszlop min szélesség beállítása 200 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(220);
        erkCol.setResizable(false);
        erkCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("erkezes"));

        tavCol = new TableColumn("Táv.");
        tavCol.setPrefWidth(48);
        tavCol.setResizable(false);
        tavCol.setCellValueFactory(new PropertyValueFactory<Route, IntegerProperty>("tavolsag"));

        ugyfCol = new TableColumn("Ügyfél");
        ugyfCol.setPrefWidth(170);
        tavCol.setResizable(false);
        ugyfCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("ugyfel"));



        table.getColumns().addAll(datCol, maganCol, indCol, erkCol, ugyfCol, spedometerCol, tankolCol, tavCol, odaVisszaCol);
        table.setItems(observableList);

        table.setOnMouseClicked(event -> {
            tableSelected();
        });
    }

    public void tableSelected() {
        selectedRoute=table.getSelectionModel().getSelectedItem();
        selctedRow=table.getSelectionModel().getSelectedIndex();
        datePicker.setValue(LocalDate.parse(selectedRoute.getDatum()));
        if (!selectedRoute.isMagan()) {
            cbClient.setValue(selectedRoute.getUgyfel().substring(0, selectedRoute.getUgyfel().indexOf("/")));
        }else{
            cbClient.setValue(selectedRoute.getUgyfel());
        }
        txtDepart.setText(selectedRoute.getIndulas());
        txtArrive.setText(selectedRoute.getErkezes());
        txtDistance.setText(String.valueOf(selectedRoute.getTavolsag()));
        chkPrivate.setSelected(selectedRoute.isMagan());
        selectedClientSpedometer=selectedRoute.getSpedometer();
        selectedClientOdaVissza=selectedRoute.isVissza();
        txtFueling.setText(String.valueOf(selectedRoute.getFueling()));
        startClientTemp=startClient;
        targetClientTemp=targetClient;
        startClient=db.getClientFromAddress(selectedRoute.getIndulas());
        targetClient=db.getClientFromAddress(selectedRoute.getErkezes());
        paneCorr.setVisible(true);
        paneNormal.setVisible(false);

        // selectedRoute.getErkezes());
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
        } catch (Exception e) {
            System.out.println("Nem találom a fájlt!");
        }
        return list;
    }

    public void checkConfigFile() {
        File file = new File("settings.cfg");
        if (!file.exists()) {
            selectionModel = tabPane.getSelectionModel();
            saveFile("settings.cfg", null);
            selectionModel.select(1);
        } else {

            settings = loadFile("settings.cfg");
            System.out.println(settings.toString());
        }
    }

    public void setText() {
        //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap
        txfNev.setText(settings.get(0));
        txfTelep.setText(settings.get(1));
        txfTelepCim.setText(settings.get(2));
        txfAuto.setText(settings.get(3));
        txfRendsz.setText(settings.get(4));
        txfLoket.setText(settings.get(5));
        txfFogyaszt.setText(settings.get(6));
        txfElozo.setText(settings.get(7));
        txtDate.setText(settings.get(8));
        textZaro.setText(spedometer.toString());

    }

    public void setLabels() {
        lblName.setText("Név: " + settings.get(0));
        lblSites.setText("T.hely: " + settings.get(1));
        lblKezdo.setText("Kezdő: " + settings.get(7) + " Km");
        lblKm.setText("Jelenlegi záró: " + spedometer.toString() + " Km");
        lblMegtett.setText("Megtett út: " + megtettKM.toString() + " Km");
    }

    private void fillField(TextField text, ArrayList list) {
        TextFields.bindAutoCompletion(text, list);
    }

    private void checkSpecialClients() {
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
    }

    public void makeExcel(String fileName, String sheetName) {

        RowToExcel rowToExcel = new RowToExcel();
        rowToExcel.createNewExcelFile(fileName);
        rowToExcel.setCell(fileName, sheetName, "D2", workDate);
        rowToExcel.setCell(fileName, sheetName, "C3", settings.get(3));
        rowToExcel.setCell(fileName, sheetName, "C4", settings.get(4));
        rowToExcel.setCell(fileName, sheetName, "C5", settings.get(7));
        rowToExcel.setCell(fileName, sheetName, "C6", spedometer.toString());
        rowToExcel.setCell(fileName, sheetName, "G3", settings.get(5));
        rowToExcel.setCell(fileName, sheetName, "G4", settings.get(6));
        rowToExcel.setCell(fileName, sheetName, "G5", megtettKM.toString());

        for (int i = 0; i < observableList.size(); i++) {
            String utCelja;
            String mORc;
            if (observableList.get(i).isMagan()) {
                utCelja = "Magán";
                mORc = "M";
            } else {
                utCelja = "Hibajavítás";
                mORc = "C";
            }
            RowToExcel row = new RowToExcel(
                    observableList.get(i).getDatum(),
                    utCelja,
                    observableList.get(i).getIndulas(),
                    observableList.get(i).getErkezes(),
                    observableList.get(i).getUgyfel(),
                    observableList.get(i).getSpedometer(),
                    observableList.get(i).getFueling(),
                    observableList.get(i).getTavolsag(),
                    mORc);
            row.setRow(fileName, sheetName, i + 9);
        }
    }

    public void rebuildSpedometer(){
        int currentValue=Integer.parseInt(settings.get(7));
        System.out.println(currentValue);
        for (int i=0;i<observableList.size();i++){
            Route route=observableList.get(i);

            System.out.println(currentValue);
            route.setSpedometer(currentValue);
            currentValue=currentValue+route.getTavolsag();
            db.updateRoute(route,route.getRouteId());
        }
        observableList.clear();
        observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));
    }
}

