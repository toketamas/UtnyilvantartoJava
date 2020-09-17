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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    @FXML
    Pane alertPane;
    @FXML
    AnchorPane paneDualButton;
    @FXML
    AnchorPane paneSingleButton;

    @FXML
    Rectangle alertRectangle;
    @FXML
    Circle alertCircle;

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
    @FXML
    Button btnAlertDoubleCancel;
    @FXML
    Button btnAlertDoubleOk;
    @FXML
    Button btnAlertSingleOK;
    @FXML
    Button btnPlus;
    @FXML
    Button btnMinus;

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
    @FXML
    Label lblAlert;

    @FXML
    TextArea alertTextArea;

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
    // RemoteDb remoteDb = new RemoteDb();


    public ObservableList<Route> observableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    Settings settings; //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap 9.utolsó kliens
    //Változók
    URL url1;
    String remoteExcel = loadFile("link.txt")[0];
    String localExcel = "ATM_karb_*.xlsx";
    String excelSource;
    String workDate;//= LocalDate.now().toString().substring(0,7);            //a hónap amivel dolgozunk
    String excelName;
    Client startClient;             // induló kliens
    Client targetClient;            // cél kliens
    Client telephely;                // telephely kliens
    Client startClientTemp;         //itt tárolja  a startklienst amikor egy másikat módosít a felhasználó
    Client targetClientTemp;        //ugyanaz mint feljebb a célklienssel
    Route selectedRoute;            // a táblából kiválasztott út
    Double fueling = 0.0;
    boolean selectedClientOdaVissza;
    boolean alertOkOrCancel;
    boolean alertClick;
    public static boolean mySqlActive;
    Integer distance;
    Integer spedometer;
    int selctedRow;
    int selectedClientSpedometer;

    TableColumn datCol;
    TableColumn maganCol;
    TableColumn odaVisszaCol;
    TableColumn tankolCol;
    TableColumn indCol;
    TableColumn erkCol;
    TableColumn tavCol;
    TableColumn ugyfCol;
    TableColumn spedometerCol;

    //Itt Indul
    public void initialize(URL url, ResourceBundle rb) {
        start();
    }

    @FXML
    private void btnClick(ActionEvent event) throws Exception {
        //útnyilvántartó tab gombok

        //Bevitel gomb
        if (btnBev.isArmed()) {
            btnBev.setDisable(true);
            btnDistance.setDisable(false);
            if (datePicker.getValue().toString().substring(0, 7).equals(String.valueOf(workDate))) {
                insertRoute();
            } else {
                showAlert("A dátum és a hónap amivel \ndolgozol nem egyezik!! \n" +
                        "Kérlek állítsd be a megfelelő \nértéket!", true, "warn");
            }
        }

        if (btnDistance.isArmed()) {
            getDist();
        }

        if (btnDistance1.isArmed()) {
            getDist();
        }


        //beállít tab gombok
        if (btnSetOk.isArmed()) {
            if (
                    txfNev.getText().trim().length()!=0      &&
                    txfTelep.getText().trim().length()!=0    &&
                    txfTelepCim.getText().trim().length()!=0 &&
                    txfRendsz.getText().trim().length()!=0
            )
            {
                settings.setNev(txfNev.getText());
                settings.setVaros(txfTelep.getText());
                settings.setCim(txfTelepCim.getText());
                settings.setAuto(txfAuto.getText());
                settings.setRendszam(txfRendsz.getText());
                settings.setLoketterfogat(txfLoket.getText());
                settings.setFogyasztas(txfFogyaszt.getText());
                settings.setElozo_zaro(Integer.parseInt(txfElozo.getText().trim()));


                if (db.getSettings(workDate,settings.getRendszam())==null) {
                    settings.setAktualis_honap(workDate);
                    settings.setId(settings.getRendszam()+workDate);
                    settings.setUtolso_ugyfel("telephely");
                    db.addSettings(settings);
                    telephely=db.getClient("telephely");
                    db.updateClient(telephely, "telephely");  //hogy első indításnál is legyen telephely client
                    spedometer=settings.getElozo_zaro();
                   /* showAlert("A beállítás sikerült " + settings.getNev() + "! \nMost már használhatod a programot!", true, "succ");
                    selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(0);*/
                }
                else {
                    db.updateSettings(settings, (settings.getRendszam() + workDate));
                }
                telephely.setField(settings.getNev());
                telephely.setCity(settings.getVaros());
                telephely.setAddress(settings.getCim());
                setLabels();
                setText();
                setPane.setDisable(true);
                btnSet.setDisable(false);
                btnSetOk.setDisable(true);
                tabNyilv.setDisable(false);
                settings = db.getSettings(workDate, settings.getRendszam());
                selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0);
                showAlert("A beállítás sikerült " + settings.getNev() + "! \nMost már használhatod a programot!", true, "succ");
                runResume();

            }else {
                showAlert("Nem adtál meg minden szükséges\nadatot!",true,"err");
            }

        }


        if (btnSet.isArmed()) {
            setPane.setDisable(false);
            btnSet.setDisable(true);
            btnSetOk.setDisable(false);
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
            setWorkdate("#");
        }

        if (btnMakeExcel.isArmed()) {               //Excel készítése
            rebuildDistanceInDb();
            excelName = ((workDate + "_" + settings.getNev() + "_" + settings.getRendszam() + "_gkelsz.xlsx")).replaceAll(" ", "_");
            makeExcel(excelName, "nyomtat");

            try {

                Runtime.getRuntime().exec("cmd /c start excel /r " + excelName);
            } catch (Exception e) {
                System.out.println("Nem érhető el a fájl ! ");
                e.printStackTrace();
            }
        }

        if (btnCancel.isArmed()) {              //Kész gomb az út módosításnál
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(startClient.getCity() + "" + startClient.getAddress());
            txtArrive.clear();
            txtDistance.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            setLabels();
        }

        if (btnDelete.isArmed()) {               // Törlés gomb az út módosításnál
            observableList.remove(selectedRoute);
            db.delRoute(selectedRoute.getRouteId());
            setLabels();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            chkBack.setSelected(false);
            chkPrivate.setSelected(false);
            startClient = startClientTemp;
            txtDepart.setText(startClient.getCity() + " " + startClient.getAddress());
            targetClient = targetClientTemp;
            if (targetClient != null)
                txtArrive.setText(targetClient.getCity() + " " + targetClient.getAddress());
            else
                txtArrive.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            selectedRoute = null;
            if (observableList.size() == 0) {
                startClient = telephely;
                targetClient = null;
                txtArrive.clear();
                txtDistance.clear();
                txtDepart.setText(startClient.getClientNumber());
                cbClient.setValue("Válaszd ki az ügyfelet");
                settings.setUtolso_ugyfel(telephely.getClientNumber());
            }
            settings.setLezarva(settings.getElozo_zaro() + db.getSpedometer(workDate, settings.getRendszam()));
            db.updateSettings(settings, (settings.getRendszam() + workDate));

        }

        if (btnSave.isArmed()) {
            selectedRoute.setDatum(datePicker.getValue().toString());
            selectedRoute.setIndulas(txtDepart.getText());
            selectedRoute.setErkezes(txtArrive.getText());
            selectedRoute.setTavolsag(Integer.parseInt(txtDistance.getText()));
            selectedRoute.setUgyfel(cbClient.getValue());
            selectedRoute.setVissza(chkBack.isSelected());
            selectedRoute.setMagan(chkPrivate.isSelected());
            String fuel = txtFueling.getText();
            fuel = checkFueling(fuel);
            selectedRoute.setFueling(Double.parseDouble(fuel));
            observableList.set(selectedRoute.getCellId(), selectedRoute);
            db.updateRoute(selectedRoute, selectedRoute.getRouteId());
            observableList.clear();
            observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(startClient.getCity() + "" + startClient.getAddress());
            txtArrive.clear();
            txtFueling.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            rebuildDistanceInDb();
            setLabels();
            chkBack.setSelected(false);
        }

        if (btnAlertSingleOK.isArmed()) {
            alertClick = true;
            alertOkOrCancel = true;
            alertPane.setVisible(false);
        }

        if (btnPlus.isArmed()) {
            txtDate.setText(workDateDecOrInc("+"));
            setWorkdate("+");

        }
        if (btnMinus.isArmed()) {
            txtDate.setText(workDateDecOrInc("-"));
            setWorkdate("-");
        }

    }

    public void insertRoute() {
        String date = datePicker.getValue().toString();         //dátum beolvasás
        try {                                                // a parseInt miatt
            distance = parseInt(txtDistance.getText());
        } catch (NumberFormatException e) {
            showAlert("A TÁVOLSÁG MEZŐBE CSAK\n SZÁMOT ÍRHATSZ!!!!", true, "err");
            btnBev.setDisable(false);
            txtDistance.clear();
        }
        if (chkPrivate.isSelected()) {                          //magánút?

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
            settings.setUtolso_ugyfel("telephely");           //beállítja utolsó ügyfélnek a telephelyet ez lesz a következő út kezdőpontja
            chkPrivate.setSelected(false);
            chkSites.setSelected(true);
            startClient = telephely;
            txtDepart.setText(telephely.getClientNumber());
            txtDistance.clear();
            txtArrive.clear();
            txtFueling.clear();
            cbClient.setValue("Válaszd ki az ügyfelet");


        } else if (chkBack.isSelected()) {                 //oda-vissza

            observableList.add(
                    new Route(
                            date,
                            chkPrivate.isSelected(),
                            getClientFullAddress(startClient), getClientFullAddress(targetClient),
                            targetClient.getClientNumber() + "/" + targetClient.getClient(),
                            fueling,
                            spedometer,
                            distance,
                            chkBack.isSelected(),
                            observableList.size()));
            checkDistInDb();

            observableList.add(
                    new Route(
                            date,
                            chkPrivate.isSelected(),
                            getClientFullAddress(targetClient), getClientFullAddress(startClient),
                            startClient.getClientNumber() + "/" + startClient.getClient(),
                            fueling,
                            spedometer,
                            distance,
                            chkBack.isSelected(),
                            observableList.size()));
            settings.setUtolso_ugyfel("telephely");
            startClient = telephely;
            chkSites.setSelected(true);
            txtDepart.setText("Telephely");
            chkBack.setSelected(false);

        } else {

            chkSites.setSelected(false);
            observableList.add(new Route(
                    date,
                    chkPrivate.isSelected(),
                    getClientFullAddress(startClient),
                    getClientFullAddress(targetClient),
                    targetClient.getClientNumber() + "/" + targetClient.getClient(),
                    fueling,
                    spedometer,
                    distance,
                    chkBack.isSelected(),
                    observableList.size()));

            settings.setUtolso_ugyfel(targetClient.getClientNumber());
            txtDepart.setText(getClientFullAddress(targetClient));
            checkDistInDb();
            startClient = targetClient;
            targetClient = null;
        }

        if (observableList.get(observableList.size() - 1).isVissza()) {
            db.addRoute(observableList.get(observableList.size() - 2), settings.getRendszam());

        }
        db.addRoute(observableList.get(observableList.size() - 1), settings.getRendszam());
        observableList.clear();
        observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
        if (chkBackToSites.isSelected()) {
            chkSites.setSelected(true);
            chkBackToSites.setSelected(false);
            txtDepart.setText(telephely.getClient());
        }
        txtArrive.clear();
        txtDistance.clear();
        targetClient = null;
        table.scrollTo(table.getItems().size() - 1);
        btnBev.setDisable(true);
        btnDistance.setDisable(false);
        rebuildDistanceInDb();
        setLabels();
        txtArrive.clear();
        settings.setLezarva(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
        db.updateSettings(settings, (settings.getRendszam() + workDate));
    }

    private void checkDistInDb() {
        Distance start = db.getDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient));
        Distance target = db.getDistance(getClientFullAddress(targetClient), getClientFullAddress(startClient));

        if (start.getDistance() == 0 && target.getDistance() == 0) {
            db.addDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient), Integer.parseInt(txtDistance.getText()));
            btnBev.setDisable(false);
            btnDistance.setDisable(true);
        }


        Distance start1 = db.getDistanceFromMySql(getClientFullAddress(startClient), getClientFullAddress(targetClient));
        Distance target1 = db.getDistanceFromMySql(getClientFullAddress(targetClient), getClientFullAddress(startClient));
        if (start1.getDistance() == 0 && target1.getDistance() == 0) {
            db.addDistanceToMySql(getClientFullAddress(startClient), getClientFullAddress(targetClient), Integer.parseInt(txtDistance.getText()));
            btnBev.setDisable(false);
            btnDistance.setDisable(true);
        }
    }

    private void getDist() {

        Distance actualDist = db.getDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient));   //szerepel e az adatbázisban
        Distance revDist = db.getDistance(getClientFullAddress(targetClient), getClientFullAddress(startClient));      //szerepel e az adatbázisban visszafelé
        if (actualDist.getDistance() != 0) {
            txtDistance.setText(String.valueOf(actualDist.getDistance()));
            btnDistance.setDisable(true);
            btnBev.setDisable(false);
        } else if (revDist.getDistance() != 0) {
            txtDistance.setText(String.valueOf(revDist.getDistance()));
            btnDistance.setDisable(true);
            btnBev.setDisable(false);
        } else {
            getDistanceFromGmaps(cleanAddress(getClientFullAddress(startClient)), cleanAddress(getClientFullAddress(targetClient)));  // ha nincs az adatbázisban akkor lekérés a gmapstól
        }
    }

    @FXML
    private void chkCheck(ActionEvent event) {
        if (chkSites.isSelected()) {
            txtDepart.setText(telephely.getClientNumber());
            txtDepart.setEditable(false);
            chkBackToSites.setSelected(false);
            startClient = telephely;
            targetClient = null;
            txtArrive.clear();
            txtDistance.clear();
        } else {
            txtDepart.setEditable(true);
        }

        if (chkPrivate.isSelected()) {
            txtDistance.setEditable(true);
            cbClient.setValue("Magánhasználat");
            txtDepart.setText("Magánhasználat");
            txtArrive.setText("Magánhasználat");
            btnBev.setDisable(false);
            btnDistance.setDisable(true);

        } else if (chkBackToSites.isSelected()) {
            chkSites.setSelected(false);
            txtArrive.setText("Telephely");
            txtArrive.setEditable(false);
            targetClient = telephely;

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
        if (targetClient != null) {
            txtArrive.clear();
            txtArrive.appendText(getClientFullAddress(targetClient));
        }
    }

    public void getDistanceFromGmaps(String sAddress, String tAddress) {
        if (txtDistance.getText() != "" || txtDistance != null) {
            String gUrl = "https://www.google.hu/maps/dir/" + sAddress + "/" + tAddress;
            System.out.println(gUrl);
            WV.getEngine().load(gUrl); // lekérdezi a távolságot a google mapstól

        }
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
                                btnDistance.setDisable(true);
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
        } catch (Exception e) {
            System.out.println("hiba");
        }
        return response.toString();
    }

    public void start() {
        datePicker.setValue(LocalDate.now());
        settings = db.getLastSettings();
        System.out.println(settings.getRendszam());
        System.out.println(settings.getAktualis_honap());
        System.out.println(settings.getId());
        settings.setId(settings.getRendszam() + settings.getAktualis_honap());
        System.out.println("utolsó út:" + db.getDateOfLastRoute());
        if (db.getDateOfLastRoute() == null)                                    //megvizsgálja hogy van e adat a routes táblában
            workDate = LocalDate.now().toString().substring(0, 7);
        else {
            setDate();
        }

        settings.setAktualis_honap(workDate);
        btnBev.setDisable(true);
        btnSetOk.setDisable(true);
        setLabels();

       /* if (db.getSettings(workDate,settings.getRendszam()) == null) {
            db.addSettings(settings);
        }
        settings = db.getSettings(workDate,settings.getRendszam());
        System.out.println(settings);*/
//
        System.out.println("Név:" + settings.getNev());
        System.out.println("Rendszám:" + settings.getRendszam());
        System.out.println("hónap:" + settings.getAktualis_honap());
        checkSpecialClients();
       // telephely = db.getClient("telephely");
        if (
                settings.getNev() == null ||
                        settings.getRendszam() == null ||
                        settings.getNev().trim().length() == 0 ||
                        settings.getVaros().trim().length() == 0 ||
                        settings.getCim().trim().length() == 0) {
            tabNyilv.setDisable(true);
            settings.setRendszam("");
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
            showAlert("Kérlek állíts be minden\nadatot a program megfelelő \nműködéséhez!", true, "info");
        } else {
            runResume();
        }
    }
        public void runResume(){

            telephely=db.getClient("telephely");
            setTableColumns();                        //beállítja a táblát
            //System.out.println(settings);
            workDate = settings.getAktualis_honap();

            spedometer = settings.getElozo_zaro();
            setText();
            setLabels();
            chkSites.setSelected(true);
            cbClient.setValue("Válaszd ki az ügyfelet");
            WV.getEngine().load("https://www.google.hu/maps/");                  //betölti a WebView-ba a térképet

            excelSource = localExcel;          //!!!!!!!!!!!!!! beállítja az excel forrását egyenlőre local ha lesz távoli akkor ezt kell módosítani!!!!!!!!!
            observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
            rebuildDistanceInDb();
            // betölti az adatokat az adatbázisból
            //rebuildSpedometer();
            setLabels();
            startClient = db.getClient(settings.getUtolso_ugyfel());                  // beállítja startclientnek az utolsó érkezés helyét
            System.out.println("utolsó ügyfél:"+settings.getUtolso_ugyfel());
            if (startClient.getClient().toLowerCase().startsWith("telephely")) {
                txtDepart.setText(startClient.getClient());
                chkSites.setSelected(true);
            } else {
                txtDepart.setText(getClientFullAddress(startClient));
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
            cbClient.setValue(cleanAddress(selectedRoute.getUgyfel()));
        } else {
            cbClient.setValue(selectedRoute.getUgyfel());
        }
        txtDistance.setEditable(true);
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
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < list.length; i++) {
                list[i] = sc.nextLine();
            }
            sc.close();
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
        System.out.println("spedometer:"+spedometer);
        textZaro.setText(spedometer.toString());
    }

    public void setLabels() {
        lblName.setText("Név: " + settings.getNev());
        lblSites.setText("T.hely: " + settings.getVaros());
        lblKezdo.setText("Kezdő: " + settings.getElozo_zaro() + " Km");
        lblKm.setText("Jelenlegi záró: " + (settings.getElozo_zaro() + db.getSpedometer(workDate, settings.getRendszam())) + " Km");
        lblMegtett.setText("Megtett út: " + db.getSpedometer(workDate, settings.getRendszam()) + " Km");
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
        Integer megtettKM = db.getSpedometer(workDate, settings.getRendszam());
        RowToExcel rowToExcel = new RowToExcel();
        rowToExcel.createNewExcelFile(fileName);
        rowToExcel.setCell(fileName, sheetName, "D2", workDate);
        rowToExcel.setCell(fileName, sheetName, "C3", settings.getAuto());
        rowToExcel.setCell(fileName, sheetName, "C4", settings.getRendszam());
        rowToExcel.setCell(fileName, sheetName, "C5", String.valueOf(settings.getElozo_zaro()));
        rowToExcel.setCell(fileName, sheetName, "C6", String.valueOf(settings.getLezarva()));
        rowToExcel.setCell(fileName, sheetName, "L105", String.valueOf(settings.getLezarva()));
        rowToExcel.setCell(fileName, sheetName, "D4", settings.getNev());
        rowToExcel.setCell(fileName, sheetName, "G3", settings.getLoketterfogat());
        rowToExcel.setCell(fileName, sheetName, "G4", settings.getFogyasztas());
        rowToExcel.setCell(fileName, sheetName, "G5", megtettKM.toString());
        rowToExcel.setCell(fileName, sheetName, "L103", megtettKM.toString());
        String fuelValue = String.valueOf(db.getFueling(workDate, settings.getRendszam()));
        if (fuelValue.length() > 6)
            fuelValue = fuelValue.substring(0, 7);
        rowToExcel.setCell(fileName, sheetName, "G7", fuelValue);
        Double value = 100 * db.getFueling(workDate, settings.getRendszam()) / megtettKM;
        String dValue = "";
        if (value.toString().length() > 4)
            dValue = value.toString().substring(0, 5);
        rowToExcel.setCell(fileName, sheetName, "G6", dValue);
        rowToExcel.setCell(fileName, sheetName, "L104", String.valueOf(db.getMaganut(workDate, settings.getRendszam())));
        Double doubleValue = (double) db.getMaganut(workDate, settings.getRendszam()) / megtettKM * 100;
        if (doubleValue.toString().length() > 2 && doubleValue != 100)
            dValue = doubleValue.toString().substring(0, 2);
        else
            dValue = doubleValue.toString();

        rowToExcel.setCell(fileName, sheetName, "M104", dValue + "%");


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

    public void rebuildDistanceInDb() {
        int currentValue = settings.getElozo_zaro();

        for (int i = 0; i < observableList.size(); i++) {
            currentValue = currentValue + observableList.get(i).getTavolsag();
            observableList.get(i).setSpedometer(currentValue);
            db.updateRoute(observableList.get(i), observableList.get(i).getRouteId());
        }
        setLabels();
    }

    public String cleanAddress(String address) {
        if (address.contains("/"))
            address = address.replaceAll("/", "");

        return address;
    }

    public String getClientFullAddress(Client client) {
        return client.getCity() + " " + client.getAddress();

    }

    public void showAlert(String alertText, Boolean singleOrDualButton, String alertLevel) {        //Single button => true , dual button => false; típusok info(vagy bármilyen string),err,warn,succ
        alertClick = false;

        String color = "83b1f5";//kék
        lblAlert.textFillProperty().setValue(Paint.valueOf("ffffff"));
        lblAlert.setText("Információ");
        if (alertLevel == "err") {
            color = "ff4d4d";//piros
            lblAlert.setText("Hiba!");
        }
        if (alertLevel == "warn") {
            color = "f2cc5a";//sárga
            lblAlert.textFillProperty().setValue(Paint.valueOf("000000"));
            lblAlert.setText("Figyelmeztetés!");
        }
        if (alertLevel == "succ") {
            color = "8bdb70";//zöld
            lblAlert.textFillProperty().setValue(Paint.valueOf("ffffff"));
            lblAlert.setText("Siker!");
        }
        alertPane.setVisible(true);
        alertTextArea.clear();
        alertTextArea.setText(alertText);
        //alertCircle.fillProperty().setValue(Paint.valueOf(color));
        alertRectangle.fillProperty().setValue(Paint.valueOf(color));
        if (singleOrDualButton) {
            paneSingleButton.setVisible(true);
            paneDualButton.setVisible(false);

        } else {
            paneDualButton.setVisible(true);
            paneSingleButton.setVisible(false);
        }
    }

    public String workDateDecOrInc(String value) {
        Integer year = Integer.parseInt(workDate.substring(0, 4));
        Integer month = Integer.parseInt(workDate.substring(5, 7));
        if (value == "+") {
            if (month < 12)
                month = month + 1;
            else {
                year = year + 1;
                month = 1;
            }
        }

        if (value == "-") {
            if (month > 1)
                month--;
            else {
                year--;
                month = 12;
            }
        }
        String mnt;
        if (month < 10)
            mnt = "0" + month.toString();
        else
            mnt = month.toString();
        settings.setAktualis_honap(year.toString() + "-" + mnt);
        settings.setId(settings.getRendszam() + year.toString() + "-" + mnt);

        return year.toString() + "-" + mnt;

    }

    public void setWorkdate(String plusOrMinus) {           //"+" = plus gomb; "-" = - gomb; "#" = beirt érték után Kész gomb

        Settings prevSettings = settings;
        if (plusOrMinus == "-" || plusOrMinus == "#") {
            workDate = txtDate.getText();
            System.out.println("setworkdate1" + workDate);
            Settings s1 = db.getSettings(workDate, settings.getRendszam());
            if (s1 != null) {
                settings = s1;
                observableList.clear();
                observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
                setLabels();
            } else {
                observableList.clear();
                showAlert(workDate + " hónapban nincsenek adatok. ", true, "info");
            }
        }

        if (plusOrMinus == "+") {

            workDate = txtDate.getText();
            System.out.println("plusorminus: " + workDate);
            Settings s1 = db.getSettings(workDate, settings.getRendszam());
            System.out.println();
            if (s1 != null) {
                settings = s1;
                observableList.clear();
                observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));

                if (settings.getElozo_zaro() != prevSettings.getLezarva()) {
                    settings.setElozo_zaro(prevSettings.getLezarva());
                    settings.setLezarva(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
                }
                setLabels();

            } else {
                int closingKmValue = settings.getLezarva(); //elmenti a zárót
                settings.setAktualis_honap(workDate);
                System.out.println("plusorminus2: " + workDate);
                settings.setElozo_zaro(closingKmValue);    //beállítja a mentett zárót nyitónak
                settings.setLezarva(closingKmValue);        //zárónak is
                db.addSettings(settings);
                System.out.println("setworkdate2" + workDate);
                observableList.clear();
                observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
                settings.setLezarva(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
                System.out.println("setworkdate3" + workDate);
                db.updateSettings(settings, (settings.getRendszam() + workDate));

                setLabels();
            }
        }
    }           //Éppen aktuális hónap kiválasztása

    public void setDate() {
        String dateValue = db.getDateOfLastRoute();
        if (dateValue != null) {
            workDate = dateValue.substring(0, 7);
            System.out.println("setDate: " + workDate);
            datePicker.setValue(LocalDate.parse(dateValue));
        }
    }

    public String checkFueling(String fuel) {

        if (fuel.contains(","))
            fuel = fuel.replace(",", ".");
        return fuel;
    }
}

