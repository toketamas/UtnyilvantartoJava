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
    Button btnDistance1;
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
    @FXML
    static
    Label lblVer;

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

    public ObservableList<Route> observableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    Settings settings = new Settings(); //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap 9.utolsó kliens
    //Változók
    URL url1;
    String remoteExcel = loadFile("link.txt")[0];
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
    boolean confCheck;   // rendben van e a konfig

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
            dataIn();
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

        if (btnDistance1.isArmed()) {
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
            String value;
           /* if (settings.getUtolso_ugyfel()==null)
                value="telephely";
            else
                value=settings.getUtolso_ugyfel();*/

            settings.setNev(txfNev.getText());
            settings.setVaros(txfTelep.getText());
            settings.setCim(txfTelepCim.getText());
            settings.setAuto(txfAuto.getText());
            settings.setRendszam(txfRendsz.getText());
            settings.setLoketterfogat(txfLoket.getText());
            settings.setFogyasztas(txfFogyaszt.getText());
            settings.setElozo_zaro(Integer.parseInt(txfElozo.getText()));
            //txtDate.getText());
            telephely.setField(settings.getNev());
            telephely.setCity(settings.getVaros());
            telephely.setAddress(settings.getCim());
            db.updateClient(telephely, "telephely");
            db.updateSettings(settings);
            setLabels();
            setText();
            tabNyilv.setDisable(false);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(0);

        }


        if (btnSet.isArmed()) {
            setPane.setDisable(false);
            btnSet.setDisable(true);
            btnSetOk.setDisable(false);
            //setLabels();
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
            settings.setAktualis_honap(workDate);
            db.updateSettings(settings);
            observableList.clear();
            observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));
        }           //Éppen aktuális hónap kiválasztása

        if (btnMakeExcel.isArmed()) {
            excelName = workDate + "_" + settings.getNev() + "_" + settings.getRendszam() + "_gkelsz.xlsx";
            makeExcel(excelName, "nyomtat");
        }       //Excel készítése

        if (btnCancel.isArmed()) {
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(startClient.getCity()+""+startClient.getAddress());
            txtArrive.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
        }           //Kész gomb az út módosításnál

        if (btnDelete.isArmed()) {               // Törlés gomb az út módosításnál
            observableList.remove(selectedRoute);
            System.out.println("route_id = "+selectedRoute.getRouteId());
            db.delRoute(selectedRoute.getRouteId());
            rebuildSpedometer();
            setLabels();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            chkBack.setSelected(false);
            chkPrivate.setSelected(false);
            startClient=startClientTemp;
            txtDepart.setText(startClient.getCity()+" "+startClient.getAddress());
            targetClient=targetClientTemp;
            txtArrive.setText(targetClient.getCity()+" "+targetClient.getAddress());
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            selectedRoute=null;
        }

        if (btnSave.isArmed()){
            selectedRoute.setDatum(datePicker.getValue().toString());
            selectedRoute.setIndulas(txtDepart.getText());
            selectedRoute.setErkezes(txtArrive.getText());
            selectedRoute.setTavolsag(Integer.parseInt(txtDistance.getText()));
            selectedRoute.setUgyfel(cbClient.getValue());
            selectedRoute.setVissza(chkBack.isSelected());
            selectedRoute.setMagan(chkPrivate.isSelected());
            selectedRoute.setFueling(Double.parseDouble(txtFueling.getText()));
            observableList.set(selectedRoute.getCellId(), selectedRoute);
            db.updateRoute(selectedRoute,selectedRoute.getRouteId());
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(startClient.getCity()+""+startClient.getAddress());
            txtArrive.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            setBoxesValue();
        }
        chkBack.setSelected(false);
    }

    @FXML
    private void chkCheck(ActionEvent event) {
        if (chkSites.isSelected()) {
            txtDepart.setText("Telephely");
            txtDepart.setEditable(false);
            //chkBackToSites.setSelected(false);
            startClient=telephely;
            targetClient=null;
            setBoxesValue();
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
    private void chkRadio(ActionEvent event) {
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

        targetClient = db.getClient(cbClient.getValue());
        if (targetClient==null){


        }else{
        txtArrive.clear();
        targetAddress = targetClient.getCity() + " " + targetClient.getAddress();
        txtArrive.appendText(targetAddress);

        if (chkSites.isSelected()) {   //le kell kérni az induló gépszámot aztán megszerezni a cél gépszámot lekérdezni a távot ha nincs meg akkor lekérdezni a téképtől beírnia textboxba aztán beírni az adatbázisba
            startAddress = startClient.getCity() + " " + startClient.getAddress();
        }
        getDistanceFromGmaps(startAddress, targetAddress);
        }
    }

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        run();
    }

    public void getDistanceFromGmaps(String sAddress, String tAddress) {
        sAddress = sAddress.replaceAll("/", "");
        tAddress = tAddress.replaceAll("/", "");
        if (txtDistance.getText() != "" || txtDistance != null)
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
        btnSetOk.setDisable(true);
        if (db.getSettings()==null) {
            db.addSettings(settings);
        }
        settings=db.getSettings();
        if (settings.getNev().trim().length()==0||
            settings.getVaros().trim().length()==0||
            settings.getCim().trim().length()==0)
        {
            tabNyilv.setDisable(true);
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
        }
        checkSpecialClients();
        telephely = db.getClient("telephely");
        setTableColumns();                        //beállítja a táblát
        System.out.println(settings);
        workDate = settings.getAktualis_honap();

        spedometer = settings.getElozo_zaro();
        megtettKM = db.getSpedometer(workDate);
        //spedometer = spedometer + megtettKM;
        setText();
        setLabels();
        chkSites.setSelected(true);
        cbClient.setValue("Válaszd ki az ügyfelet");
        WV.getEngine().load("https://www.google.hu/maps/");                  //betölti a WebView-ba a térképet
        datePicker.setValue(LocalDate.now());
        excelSource = localExcel;          //!!!!!!!!!!!!!! beállítja az excel forrását egyenlőre local ha lesz távoli akkor ezt kell módosítani!!!!!!!!!
        observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));         // betölti az adatokat az adatbázisból
        startClient = db.getClient(settings.getUtolso_ugyfel());                  // beállítja startclientnek az utolsó érkezés helyét
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

        table.scrollTo(table.getItems().size() - 1);
        setLabels();
        setText();
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
        selectedRoute = table.getSelectionModel().getSelectedItem();
        selctedRow = table.getSelectionModel().getSelectedIndex();
        datePicker.setValue(LocalDate.parse(selectedRoute.getDatum()));
        if (!selectedRoute.isMagan()) {
            cbClient.setValue(selectedRoute.getUgyfel().substring(0, selectedRoute.getUgyfel().indexOf("/")));
        } else {
            cbClient.setValue(selectedRoute.getUgyfel());
        }
        txtDepart.setText(selectedRoute.getIndulas());
        txtArrive.setText(selectedRoute.getErkezes());
        txtDistance.setText(String.valueOf(selectedRoute.getTavolsag()));
        chkPrivate.setSelected(selectedRoute.isMagan());
        selectedClientSpedometer = selectedRoute.getSpedometer();
        selectedClientOdaVissza = selectedRoute.isVissza();
        txtFueling.setText(String.valueOf(selectedRoute.getFueling()));
        startClientTemp = startClient;
        targetClientTemp = targetClient;
        startClient = db.getClientFromAddress(selectedRoute.getIndulas());
        targetClient = db.getClientFromAddress(selectedRoute.getErkezes());
        paneCorr.setVisible(true);
        paneNormal.setVisible(false);
    }

    public static void saveFile(String filename, String[] list) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        for (int i = 0; i < list.length; i++)
            writer.println(list[i]);
        writer.flush();
        writer.close();
    }

    public static String[] loadFile(String filename) {

        String[] list = new String[10];
        int rowNumber;
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < list.length; i++) {
                list[i] = sc.nextLine();
            }
            sc.close();
            System.out.println(list.toString());
        } catch (Exception e) {
            System.out.println("Nem találom a fájlt!");
        }
        return list;
    }


    public void setText() {
        //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap
        txfNev.setText(settings.getNev());
        txfTelep.setText(settings.getVaros());
        txfTelepCim.setText(settings.getCim());
        txfAuto.setText(settings.getAuto());
        txfRendsz.setText(settings.getRendszam());
        txfLoket.setText(settings.getLoketterfogat());
        txfFogyaszt.setText(settings.getFogyasztas());
        txfElozo.setText(String.valueOf(settings.getElozo_zaro()));
        txtDate.setText(settings.getAktualis_honap());
        textZaro.setText(spedometer.toString());
    }

    public void setLabels() {
        lblName.setText("Név: " + settings.getNev());
        lblSites.setText("T.hely: " + settings.getVaros());
        lblKezdo.setText("Kezdő: " + settings.getElozo_zaro() + " Km");
        lblKm.setText("Jelenlegi záró: " + spedometer.toString() + " Km");
        lblMegtett.setText("Megtett út: " + megtettKM.toString() + " Km");
    }

    private void fillField(TextField text, ArrayList list) {
        TextFields.bindAutoCompletion(text, list);
    }

    private void checkSpecialClients() {
        Client telephely = db.getClient("telephely");
        if (telephely == null) {
            db.addClient(
                    "telephely",
                    "telephely",
                    "telephely",
                    "telephely",
                    0,
                    settings.getVaros(),
                    settings.getCim(),
                    true,
                    0,
                    settings.getNev()
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
        rowToExcel.setCell(fileName, sheetName, "C3", settings.getAuto());
        rowToExcel.setCell(fileName, sheetName, "C4", settings.getRendszam());
        rowToExcel.setCell(fileName, sheetName, "C5", String.valueOf(settings.getElozo_zaro()));
        rowToExcel.setCell(fileName, sheetName, "C6", spedometer.toString());
        rowToExcel.setCell(fileName, sheetName, "G3", settings.getLoketterfogat());
        rowToExcel.setCell(fileName, sheetName, "G4", settings.getFogyasztas());
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
        try {
            Runtime.getRuntime().exec(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rebuildSpedometer() {
        int currentValue = settings.getElozo_zaro();
        //System.out.println(currentValue);
        for (int i = 0; i < observableList.size(); i++) {
            //Route route = observableList.get(i);
            currentValue = currentValue + observableList.get(i).getTavolsag();
            observableList.get(i).setSpedometer(currentValue);
            //db.updateRoute(route, route.getRouteId());
        }
        //observableList.clear();
        //observableList.addAll(db.getRoutes("'" + workDate + "-%%'"));
        //spedometer=currentValue;
        setLabels();
    }

    public void dataIn(){                                       // Adatbevitel a bevitel gombra kattintva
        String date = datePicker.getValue().toString();         //dátum beolvasás
        if (chkPrivate.isSelected()) {                          //magánút?
            try {                                                // a parseInt miatt
                distance = parseInt(txtDistance.getText());
                setLabels();                                     // beállítja a címkéket az útnyilvántartó oldalon
                observableList.add(new Route(                   //hozzáad a táblához egy új utat a mezőkből kinyert adatokkal
                        datePicker.getValue().toString(),
                        chkPrivate.isSelected(),
                        "Magánhasználat",
                        "Magánhasználat",
                        "Magánhasználat",
                        fueling,
                        spedometer,
                        distance,
                        false,
                        observableList.size()));
                settings.setUtolso_ugyfel("telephely");            //beállítja utolsó ügyfélnek a telephelyet ez lesz a következő út kezdőpontja
                db.updateSettings(settings);                       // frissíti a beállításokat az utolsó ügyfél változás miatt
                setBoxesValue();

            } catch (NumberFormatException e) {
                txtDistance.setText("IDE CSAK SZÁMOT ÍRHATSZ!!!");  //Ha sikertelen a parseInt
            }
        } else if (chkBack.isSelected()) {
            observableList.add(
                    new Route(
                            date,
                            chkPrivate.isSelected(),
                            startAddress, targetAddress,
                            targetClient.getClientNumber() + "/" + targetClient.getClient(),
                            fueling,
                            spedometer,
                            distance,
                            chkBack.isSelected(),
                            observableList.size()));
            setLabels();
            observableList.add(
                    new Route(
                            date,
                            chkPrivate.isSelected(),
                            targetAddress, startAddress,
                            startClient.getClientNumber() + "/" + startClient.getClient(),
                            fueling,
                            spedometer,
                            distance,
                            chkBack.isSelected(),
                            observableList.size()));
            settings.setUtolso_ugyfel("telephely");
            db.updateSettings(settings);
            startClient = telephely;
            setBoxesValue();
        } else {
            setLabels();
            observableList.add(new Route(date, chkPrivate.isSelected(), startAddress, targetAddress, targetClient.getClientNumber() + "/" + targetClient.getClient(), fueling, spedometer, distance, chkBack.isSelected(), observableList.size()));
            settings.setUtolso_ugyfel(targetClient.getClientNumber());
            db.updateSettings(settings);
            txtDepart.setText(targetAddress);
            startClient = targetClient;
            startAddress = targetAddress;
            setBoxesValue();
        }

        if (observableList.get(observableList.size() - 1).isVissza()) {
            db.addRoute(observableList.get(observableList.size()-2));
        }
        db.addRoute(observableList.get(observableList.size()-1));


        if (db.getDistance(startAddress, targetAddress) == 0 && db.getDistance(targetAddress, startAddress) == 0) {
            db.addDistance(startAddress, targetAddress, distance);
        }
        if (chkBackToSites.isSelected()) {
            chkSites.setSelected(true);
            chkBackToSites.setSelected(false);
            txtDepart.setText(telephely.getClient());
        }
        targetAddress = "";
        targetClient = null;
        table.scrollTo(table.getItems().size() - 1);
        setBoxesValue();
    }

    public void setBoxesValue(){             // text-, check-, és egyéb boxot a start és a targetClientnek megfelelő állapotba állít
        if (db.getClient(settings.getUtolso_ugyfel())==null) {
            settings.setUtolso_ugyfel("telephely");
        }
        startClient = db.getClient(settings.getUtolso_ugyfel());
        if (startClient.getClientNumber().toLowerCase()=="telephely"){
            chkPrivate.setSelected(false);
            chkBack.setSelected(false);
            chkSites.setSelected(true);
            chkBackToSites.setSelected(false);
            txtDepart.setText(startClient.getClientNumber());
        }else{
            chkSites.setSelected(false);
        }

        if (targetClient== null){
             txtArrive.clear();
        }else{
            cbClient.setValue("Válaszd ki az ügyfelet");
        }

    }



}

