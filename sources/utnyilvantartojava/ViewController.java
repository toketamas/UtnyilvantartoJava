package utnyilvantartojava;

import com.sun.glass.ui.Cursor;
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
import javafx.scene.input.MouseEvent;

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
    AnchorPane datePane;
    @FXML
    AnchorPane startPane;
    @FXML
    AnchorPane clientPane;

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
    Label lblVer;
    @FXML
    Label lblVerzio;
    @FXML
    Label lblAlert;
    @FXML
    Label lblAtlagFogy;
    @FXML
    Label lblRendszam;

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
    @FXML
    TextField txtDate;
    @FXML
    TextField txtFueling;

// Beállítások szakasz
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
    TextField txtBeallit;

//Saját uticélok megadása szakasz
    @FXML
    AnchorPane sajatUticelokPane;
    @FXML
    TextField txtSajatClient;
    @FXML
    TextField txtSajatClientNumber;
    @FXML
    TextField txtSajatEgyebAdat;
    @FXML
    TextField txtSajatIranyitoSzam;
    @FXML
    TextField txtSajatVaros;
    @FXML
    TextField txtSajatCim;
    @FXML
    Button btnSajatBev;
    @FXML
    Button btnSajatTorles;
    @FXML
    ComboBox cbSajat;

//Filebetöltés    
    @FXML
    TextField txtFile;

    @FXML
    RadioButton radioBtnTh;
    @FXML
    RadioButton radioBtnFile;

    @FXML
    DatePicker datePicker;

    @FXML
    SearchableComboBox<String> cbClient;

    DbModel db;
    // RemoteDb remoteDb = new RemoteDb();

    public ObservableList<Route> observableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    Settings settings; //0-név,1-telephely város,2-telephely cím, 3-auto tip., 4-rendszám, 5-lökett., 6-fogyasztás, 7-előző záró km. 8-aktuális hónap 9.utolsó kliens
    //Változók
    URL url1;
    String settingsId;
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
    Settings tempSettings;
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

    public void start() {
        db = new DbModel();
// átnevezi a lezárt tábla oszlopot active-ra        
        db.renColToActive();
        cbClient.getItems().clear();
        cbSajat.getItems().clear();
        observableList.clear();
        table.getColumns().clear();
        txtDistance.setEditable(false);

//mai dátum a DatePickerbe
        datePicker.setValue(LocalDate.now());

//betölti a settings táblából az utolsó sort ahol az active érték true
        settings = db.getLastSettings();
//ha nem sikerül megpróbálja betölteni az utolsó sort és az active értékét true-ra állítani       
        if (settings.getId() == null) {
            settings = db.getLastSettingsIfActiveNullAll();
            if (settings != null) {
                settings.setActive(true);
                db.updateSettings(settings, settings.getId());
                settings = db.getLastSettings();
            }

        }

        ///System.out.println("utolsó út:" + db.getDateOfLastRoute());
//megvizsgálja hogy van e adat a routes táblában
        if (db.getDateOfLastRoute() == null) {
//ha van kiveszi belőle az év hónapot
            workDate = LocalDate.now().toString().substring(0, 7);
        } else {
//ha nincs akkor a mai dátumból veszi ki és a settingsbe egy új 
//rendszám+ÉvHónap kulcsot készít(ez az elsődleges kulcs az adatbázisban)
            setDate();
            settings.setAktualis_honap(workDate);
            settingsId = settings.getRendszam() + settings.getAktualis_honap();
        }

        btnBev.setDisable(true);
        btnSetOk.setDisable(true);
        setLabels();

//megnézzük szerepelnek e a nem excelből hozzáadott címek
        checkSpecialClients();
        /// telephely = db.getClient("telephely");

//megvizsgáljuk, hogy az aktuális settings objektum tartalmazza-e a szükséges adatokat
        if (settings.getNev() == null
                || settings.getRendszam() == null
                || settings.getNev().trim().length() == 0
                || settings.getVaros().trim().length() == 0
                || settings.getCim().trim().length() == 0) //ha nem: üres stringre állítjuk a rendszámot,ha véletlenül null lenne meghívjuk a beállít tabot
        {
            tabNyilv.setDisable(true);
            settings.setRendszam("");
            selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
            showAlert("Kérlek állíts be minden\nadatot a program megfelelő \nműködéséhez!", true, "info");
// ha megvannak akkor folytatódik
        } else {
            runResume();
        }
    }

    public void runResume() {
// regisztrál a mysql-be        
        db.addRegToMySql(settings.getNev(), settings.getVaros(), settings.getCim(), settings.getRendszam());
// frissíti a hozzáférés idejét        
        db.updateRegMysql(settings.getNev(), settings.getVaros(), settings.getCim(), settings.getRendszam());

// kivesszük az adatbázisból a telephely címét
        telephely = db.getClient("telephely");
//beállítja a tábla oszlopokat
        setTableColumns();
        ///System.out.println(settings);

//beállítjuk a hónapot amit szerkestünk
        workDate = settings.getAktualis_honap();
        checkDateForPlusButton();
//töröljük a listát
        observableList.clear();
//beállítjuk a kilométer óra állást
        spedometer = settings.getElozo_zaro();
//textboxok beállítása
        setText();
//labelek beállítása
        setLabels();
//telphelyről chheckbox bepipálása
        chkSites.setSelected(true);
//combobox szöveg beállítása
        cbClient.setValue("Uticél");
//betölti a WebView-ba a térképet
        WV.getEngine().load("https://www.google.hu/maps/");
//!!!!!!!!!!!!!! beállítja az excel forrását egyenlőre local ha lesz távoli akkor ezt kell módosítani!!!!!!!!!
        excelSource = localExcel;
//hozzáadja a listához a rendszámhoz és a szerkesztett hónaphoz tartozó adatokat
        observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
//újraszámolja a megtett távolságot
        rebuildDistanceInDb();
//beállítja a labeleket
        setLabels();
// beállítja startclientnek az utolsó érkezés helyét
        startClient = db.getClient(settings.getUtolso_ugyfel());
        ///System.out.println("utolsó ügyfél:" + settings.getUtolso_ugyfel());
//ha a startClient a telephely,
        if (startClient.getClient().toLowerCase().startsWith("telephely")) {
//beállítjuk az indulás textbox szövegét "telephely"-re
            txtDepart.setText(startClient.getClient());
//bepipáljuk az indulás a telephelyről chkboxot
            chkSites.setSelected(true);
        } else {
//ha nem kivesszük a startclientből a címet
            txtDepart.setText(getClientFullAddress(startClient));
//kivesszük az indulás a telephelyről chkboxból a pipát
            chkSites.setSelected(false);
        }
//letiltja az indulás textboxot mivel a beállított érték ahonnan indulni kell mert előzőleg itt álltunk az autóval
        txtDepart.setEditable(false);
//hozzáadja a combobox listájához az ügyfeleket és a saját címeket az adatbázisból
        //cbClient.getItems().addAll(db.getAllClient(true));
        cbClient.getItems().addAll(db.getAllClient(false));
//hozzáadja a cb sajat comboboxhoz a saját címeket        
        cbSajat.getItems().addAll(db.getAllClient(true));

//betölti az összes lehetséges várost az érkezés textbox listájába 
        fillField(txtArrive, db.getAllCitys());
//betölti az összes lehetséges várost az indulás textbox listájába
        fillField(txtDepart, db.getAllCitys());
//a tábla végére görget
        table.scrollTo(table.getItems().size() - 1);
        setLabels();
        setText();
        if (db.checkRegMySql(settings.getNev(), settings.getVaros(), settings.getCim()) == 0) {
            showAlert("Hiba a program indítása közben!\n Validálás sikertelen!\n Van internet kapcsolat?", true, "err");
            tabNyilv.setDisable(true);
        }
    }

    @FXML
    private void btnClick(ActionEvent event) throws Exception {
//útnyilvántartó tab gombok

//Bevitel gomb
        if (btnBev.isArmed()) {
// bevitel tiltás
            btnBev.setDisable(true);
            txtDistance.setStyle(" -fx-background-color: #ffffff;");
            txtDistance.setEditable(false);
//távolság engedélyezés
            btnDistance.setDisable(false);
            //chkBack.setSelected(false);

//megvizsgálja, hogy az aktuális dátum egyezik-e a hónappal amivel dolgozunk
            if (datePicker.getValue().toString().substring(0, 7).equals(String.valueOf(workDate))) {
                insertRoute();
            } else {
                showAlert("A dátum és a hónap amivel \ndolgozol nem egyezik!! \n"
                        + "Kérlek állítsd be a megfelelő \nértéket!", true, "warn");
            }

        }
//lekéri a távolságot
        if (btnDistance.isArmed()) {
            getDist(true);
        }
//lekéri a távolságot a sor javítása közben
        if (btnDistance1.isArmed()) {
            getDist(false);
        }

        if (btnSajatBev.isArmed()) {
            addSajatCim();
        }

        if (btnSajatTorles.isArmed()) {
            delSajatCim();
        }

//A beállítás tab-on lévő gombok
//beállításoknál az ok gomb
        if (btnSetOk.isArmed()) {
            settings.setActive(false);
            db.updateSettings(settings, settings.getId());
            settings.setActive(true);
            
//ha van írva valami ezekbe a textboxokba akkor úgy vesszük, hogy ki van töltve az
//összes beállítás

            if (txfNev.getText().trim().length() != 0
                    && txfTelep.getText().trim().length() != 0
                    && txfTelepCim.getText().trim().length() != 0
                    && txfRendsz.getText().trim().length() != 0) {
//kiolvassa a textboxok tartalmát és beteszi a settings objektumba
                settings.setNev(txfNev.getText());
                settings.setVaros(txfTelep.getText());
                settings.setCim(txfTelepCim.getText());
                settings.setAuto(txfAuto.getText());
                settings.setRendszam(txfRendsz.getText());
                settings.setLoketterfogat(txfLoket.getText());
                settings.setFogyasztas(txfFogyaszt.getText());
                settings.setElozo_zaro(Integer.parseInt(txfElozo.getText().trim()));
                settingsId = txfRendsz.getText() + workDate;
                settings.setId(settingsId);
//Ha az aktuális settings objektum üres beállítjuk a szükséges 
//értékeket, hogy ne akadjon ki a program induláskor és mentjük az adatbázisba
//ez első induláskor történik utána be kell állítani a használónak az adatait
                if (db.getSettings(settingsId) == null) {
                    settings.setAktualis_honap(workDate);
                    settings.setId(settingsId);
                    settings.setUtolso_ugyfel("telephely");
                    settings.setActive(true);
                    db.addSettings(settings);
                    telephely = db.getClient("telephely");
                    spedometer = settings.getElozo_zaro();

                } else {
// ha már létezett az aktuális setting objektum akkor beállítja a megváltozott
//adatokat és frissíti az adatbázist ez olyankor ha pl. a rendszám vagy a lakcím változott
                    db.updateSettings(settings, settingsId);
                }
// betöltjük az előzőleg módosított aktuális settings objektumot 
                settings = db.getLastSettings();
// beállítjuk a telephely client értékeit
                telephely.setField(settings.getNev());
                telephely.setCity(settings.getVaros());
                telephely.setAddress(settings.getCim());
                //mentjük az adatbázisba
                db.updateClient(telephely, "telephely");
//Beállítjuk a mezők szerkeszthetőségét visszaugrunk az utnyilvántartás tab-ra és folytatodik a program
                setLabels();
                setText();
                setPane.setDisable(true);
                btnSet.setDisable(false);
                btnSetOk.setDisable(true);
                tabNyilv.setDisable(false);
                settings = db.getSettings(settingsId);
                selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0);
                showAlert("A beállítás sikerült " + settings.getNev() + "! \nMost már használhatod a programot!", true, "succ");
                runResume();

            } else {
                showAlert("Nem adtál meg minden szükséges\nadatot!", true, "err");
            }

        }

        if (btnSet.isArmed()) {
            setPane.setDisable(false);
            btnSet.setDisable(true);
            btnSetOk.setDisable(false);
            setLabels();
        }

        if (btnReadExcel.isArmed()) {
            //btnReadExcel.setDisable(true);
            if (btnReadExcel.getText() == "Kész") {
                //btnReadExcel.setDisable(false);
                btnReadExcel.setText("Excel beolvasása");
                tabNyilv.setDisable(false);
                cbClient.getItems().clear();
                start();
                selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0);
            } else {

                db.conn.close();
                btnReadExcel.setText("Kész");
                tabNyilv.setDisable(true);
                try {
                    Runtime run = Runtime.getRuntime();
                    run.exec("cmd /c start ./adatbeolvaso/Karbantart.exe /K ");
                    //run.wait();

                } catch (Exception e) {
                    System.out.println("Nem érhető el a fájl ! ");
                    e.printStackTrace();
                }

            }
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
//Mégse gomb az út módosításnál
        if (btnCancel.isArmed()) {
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(getClientFullAddress(startClient));
            txtArrive.clear();
            txtDistance.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            setLabels();
            txtDistance.setEditable(false);
            cbClient.setValue("Uticél");
        }
// Törlés gomb az út módosításnál
        if (btnDelete.isArmed()) {
//Ellenőrzi hogy az utolsó út van e kijelölve            
            if (selectedRoute == observableList.get(observableList.size() - 1)) {
                String elozoCim = getClientFullAddress(telephely);
                String elozoKliens = "telephely";
                if (observableList.size() > 1) {
                    elozoCim = observableList.get(observableList.size() - 2).getErkezes();
                    elozoKliens = observableList.get(observableList.size() - 2).getUgyfel();
                    if (elozoCim.startsWith("Magánhasználat") && observableList.size() > 2) {
                        elozoCim = observableList.get(observableList.size() - 3).getErkezes();
                        elozoKliens = getClientNumberFromRoute(observableList.get(observableList.size() - 3).getUgyfel());
                    }
                    txtDepart.setText(elozoCim);
                    startClient = db.getClientFromClientNumber(elozoKliens);
                    if(elozoKliens.contains("/"))
                        elozoKliens=elozoKliens.substring(0, elozoKliens.indexOf("/"));
                    System.out.println(elozoKliens);
                    settings.setUtolso_ugyfel(elozoKliens);
                

                observableList.remove(selectedRoute);
                db.delRoute(selectedRoute.getRouteId());
                setLabels();
                paneNormal.setVisible(true);
                paneCorr.setVisible(false);
                chkBack.setSelected(false);
                chkPrivate.setSelected(false);
                startClient = db.getClientFromAddress(elozoCim);

                //txtDepart.setText(targetClientTemp.getCity() + " " + targetClientTemp.getAddress());
                targetClient = null;
                txtArrive.clear();
                txtDistance.clear();
                cbClient.setValue("Uticél");

                /*if (targetClient != null) {
                txtArrive.setText(targetClient.getCity() + " " + targetClient.getAddress());
            } else {
                txtArrive.clear();
            }
                 */
                txtDistance.setEditable(false);
                paneNormal.setVisible(true);
                paneCorr.setVisible(false);
                selectedRoute = null;
                }
//Ha minden adatot töröltünk a táblából beállítja indulási helynek a telephelyet
                if (observableList.size() == 0) {
                    startClient = telephely;
                    targetClient = null;
                    txtArrive.clear();
                    txtDistance.clear();
                    txtDepart.setText(startClient.getClientNumber());
                    cbClient.setValue("Uticél");
                    settings.setUtolso_ugyfel(telephely.getClientNumber());
                }
                settings.setZaro_km(settings.getElozo_zaro() + db.getSpedometer(workDate, settings.getRendszam()));
                db.updateSettings(settings, (settings.getId()));
                btnSave.setDisable(false);
                chkBack.setSelected(false);

            } else {
                showAlert("Csak az utolsó sort törölheted! ", true, "warn");
                txtDistance.setEditable(false);
            }
        }
//mentés gomb az út módősításánál
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
//Elmenti az adatbázisba a distance táblába a módosított távolságot
            updateDistance(selectedRoute, settings.getUtolso_ugyfel(),txtDistance.getText());
            observableList.clear();
            observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));
            startClient = startClientTemp;
            targetClient = targetClientTemp;
            txtDepart.setText(getClientFullAddress(startClient));
            txtArrive.clear();
            txtFueling.clear();
            paneNormal.setVisible(true);
            paneCorr.setVisible(false);
            rebuildDistanceInDb();
            setLabels();
            chkBack.setSelected(false);
            cbClient.setValue("Uticél");
            txtDistance.clear();
            txtDistance.setEditable(false);
            btnDistance.setDisable(false);
            btnBev.setDisable(true);
        }

        if (btnAlertSingleOK.isArmed()) {
            alertClick = true;
            alertOkOrCancel = true;
            alertPane.setVisible(false);
        }
//a választott honap melletti plusz és minusz gomb
        if (btnPlus.isArmed()) {

            settings.setActive(false);
            db.updateSettings(settings, settings.getId());
            txtDate.setText(workDateDecOrInc("+"));
            setWorkdate("+");
            checkDateForPlusButton();
        }
        if (btnMinus.isArmed()) {

            settings.setActive(false);
            db.updateSettings(settings, settings.getId());
            txtDate.setText(workDateDecOrInc("-"));
            setWorkdate("-");
            checkDateForPlusButton();
        }

        if (btnReady.isArmed()) {

            settings.setActive(false);
            db.updateSettings(settings, settings.getId());
            setWorkdate("#");
            checkDateForPlusButton();
        }

    }
// kinyeri az adatbázisból a címeket a distances tábla frissítéséhez és frissíti

    public void updateDistance(Route selectedRoute, String elozoUgyfel,String distance) {
// mivel a Route objektum gépszám+" "+ügyfél formátumban tárolja az ügyfél adatot le kell vágni a gépszámot
        String gepszam = selectedRoute.getUgyfel();
        if (selectedRoute.getUgyfel().contains(" ")) {
            int index = selectedRoute.getUgyfel().indexOf(' ');
            gepszam = selectedRoute.getUgyfel().substring(0, index);
        }
//kiveszi a settingsből az indulás helyét(ez az utolsó gépszám vagy a telephely) ez alapján lekéri a client-et
//az adatbázisból és elkészíti a teljes címet
        String elsoCim = getClientFullAddress(db.getClientFromClientNumber(elozoUgyfel));
//ugyanaz mint feljebb csak a selectedRoute-ból kinyert címmel.
        String masodikCim = getClientFullAddress(db.getClientFromClientNumber(gepszam));
//frissíti az adatbázist.
        db.updateDistance(elsoCim, masodikCim, Integer.parseInt(distance));
        db.updateDistanceRev(elsoCim, masodikCim, Integer.parseInt(distance));
    }
//magánutat ad a listához

    public void privateRoute() {
        observableList.add(new Route(
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
//beállítja utolsó ügyfélnek az utolsó ügyfelet ez lesz a következő út kezdőpontja            
        //settings.setUtolso_ugyfel("telephely");
        chkPrivate.setSelected(false);

        startClient = db.getClientFromClientNumber(settings.getUtolso_ugyfel());

        if (startClient.getClient().toLowerCase().startsWith("telephely")) {
            chkSites.setSelected(true);
            txtDepart.setText(startClient.getClientNumber());
            System.out.println(startClient.getClientNumber());
        } else {
            chkSites.setSelected(false);
            txtDepart.setText(getClientFullAddress(startClient));
        }

        txtDistance.clear();
        txtArrive.clear();
        txtFueling.clear();
        cbClient.setValue("Uticél");
    }
//ez ad hozzá egy utat    

    public void oneRoute() {
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
    }
// ez egy visszautat ad hozzá, ez előtt van a listában egy odaút

    public void backRoute() {
        fueling = 0.0;
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

    }

    String date;

    public void insertRoute() {
//dátum beolvasás
        //fueling=0.0;
//ha üres a txtFueling  akkor a parseDouble kiakad ezért akkor 0.0 éréküvé tesszük        
        String fueltext = txtFueling.getText();
        if (fueltext.isEmpty()) {
            fueltext = "0.0";
        }
        fueling = Double.parseDouble(checkFueling(fueltext));

        date = datePicker.getValue().toString();
//kivételkezelés a parseInt miatt itt olvassa be a távolságot a textboxból
        try {
            distance = parseInt(txtDistance.getText());

// beállítja a címkéket az útnyilvántartó oldalon
            setLabels();
//ha a magánút be van pipálva
            if (chkPrivate.isSelected()) {
//hozzáad egy magánutat
                privateRoute();
//ha be van pipélva az oda-vissza
            } else if (chkBack.isSelected()) {
//hozzáadja az utat a listához   
                oneRoute();
                checkDistInDb();
//mégegyszer bekerül az út de most s kezdőcím és a cél fel van cserélve
                backRoute();
            } else {

                chkSites.setSelected(false);
//hozzáadja az utat a listához            
                oneRoute();
//beállítja utolsó ügyfélnek a cél ügyfél client numberét beírja az indulás mezőbe a teljes címet
//mert a köv. út innen indul majd 
                settings.setUtolso_ugyfel(targetClient.getClientNumber());
                settings.setActive(true);
                db.updateSettings(settings, settings.getId());
                txtDepart.setText(getClientFullAddress(targetClient));
                checkDistInDb();
                startClient = targetClient;
                targetClient = null;
            }
//megnézi a listában hogy be volt e állítva az utolsó előtti elemnél az oda vissza 
//ha igen akkor azt és az utolsót is küldi az adatbázisba
            if (observableList.get(observableList.size() - 1).isVissza()) {
                db.addRoute(observableList.get(observableList.size() - 2), settings.getRendszam());

            }
//egyébként csak az utolsó listaelem megy az adatbázisba
            db.addRoute(observableList.get(observableList.size() - 1), settings.getRendszam());
            observableList.clear();
//újra betölti az adatokat a listába az adatbázisba        
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
            settings.setZaro_km(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
            settings.setActive(true);
            db.updateSettings(settings, (settings.getRendszam() + workDate));

        } catch (NumberFormatException e) {
            showAlert("A TÁVOLSÁG MEZŐBE CSAK\n SZÁMOT ÍRHATSZ!!!!", true, "err");
            btnBev.setDisable(false);
            txtDistance.clear();
        }
    }
//Ellenőrzi hogy a benne van-e a két hely közti távolság az adatbázisban

    private void checkDistInDb() {
//odaút        
        Distance start = db.getDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient));
        System.out.println("start " + start.getDistance());
//visszaút        
        Distance target = db.getDistance(getClientFullAddress(targetClient), getClientFullAddress(startClient));
        System.out.println("target " + target.getDistance());
        // ha a visszakapott érték 0 akkor beírja az adatbázisba új távolságként
        if (start.getDistance() == 0 && target.getDistance() == 0) {
            //       if (start == null && target == null) {
            db.addDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient), Integer.parseInt(txtDistance.getText()));
            btnBev.setDisable(false);
            btnDistance.setDisable(true);
            System.out.println("beírtam");
        }

        /* Distance start1 = db.getDistanceFromMySql(getClientFullAddress(startClient), getClientFullAddress(targetClient));
        Distance target1 = db.getDistanceFromMySql(getClientFullAddress(targetClient), getClientFullAddress(startClient));
        if (start1.getDistance() == 0 && target1.getDistance() == 0) {
            db.addDistanceToMySql(getClientFullAddress(startClient), getClientFullAddress(targetClient), Integer.parseInt(txtDistance.getText()));
            btnBev.setDisable(false);
            btnDistance.setDisable(true);
        }
         */
    }
//Távolság lekérése

    private void getDist(boolean noGmaps) {
//egy odafele út
        String fullAddress = txtArrive.getText();
        int index = txtArrive.getText().indexOf(' ');
        if (fullAddress.contains(" ")) {
            String city = fullAddress.substring(0, index).trim();
            String address = fullAddress.substring(index).trim();
            targetClient.setCity(city);
            targetClient.setAddress(address);
            db.updateClient(targetClient, targetClient.getClientNumber());
        }
        Distance actualDist = db.getDistance(getClientFullAddress(startClient), getClientFullAddress(targetClient));
        //egy visszafele út       
        Distance revDist = db.getDistance(getClientFullAddress(targetClient), getClientFullAddress(startClient));
        // ha igen akkor beírja a textboxba a távot 
        if (actualDist.getDistance() != 0&& noGmaps) {
            txtDistance.setText(String.valueOf(actualDist.getDistance()));
            btnDistance.setDisable(true);
            btnBev.setDisable(false);
//ha visszafelé szerepel akkor beírja a textboxba a távot 
        } else if (revDist.getDistance() != 0&&noGmaps) {
            txtDistance.setText(String.valueOf(revDist.getDistance()));
            btnDistance.setDisable(true);
            btnBev.setDisable(false);
            // ha nincs az adatbázisban akkor lekérés a gmapstól           
        } else {
            getDistanceFromGmaps(cleanString(getClientFullAddress(startClient)), cleanString(getClientFullAddress(targetClient)));
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
            txtDistance.setStyle("-fx-background-color:  #eeffcc;");
            txtDistance.requestFocus();
            cbClient.setValue("Magánhasználat");
            txtDepart.setText("Magánhasználat");
            txtArrive.setText("Magánhasználat");
            btnBev.setDisable(false);
            btnDistance.setDisable(true);
            chkSites.setSelected(false);
            chkBackToSites.setSelected(false);
            chkBack.setSelected(false);

        } else if (chkBackToSites.isSelected()) {
            chkSites.setSelected(false);
            txtArrive.setText("Telephely");
            txtArrive.setEditable(false);
            targetClient = telephely;

        } else {
            txtArrive.clear();
            txtArrive.setEditable(true);
            txtDistance.setEditable(false);
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
            txtArrive.setEditable(true);
           
        }
    }
//ez lesz  az openstreetmaps lekérdezés ha kész lesz

    public void getDistanceFromOsm(String sAddress, String tAddress) {
        if (txtDistance.getText() != "" || txtDistance != null) {
            String startUrl = "192.168.1.20:8080/search/search?q=" + sAddress + "&format=xml&polygon_geojson=1&addressdetails=1";
            String targetUrl = "192.168.1.20:8080/search/search?q=" + tAddress + "&format=xml&polygon_geojson=1&addressdetails=1";
            System.out.println(startUrl);
            System.out.println(targetUrl);
            WV.getEngine().load(startUrl);
            String value = getURL(WV.getEngine().getLocation());
            System.out.println(value);
            //String gUrl = "192.168.1.20:5500/" + sAddress + "/" + tAddress;
            //System.out.println(gUrl);
            //  WV.getEngine().load(gUrl); // lekérdezi a távolságot a google mapstól
            //String gotUrl = getURL(WV.getEngine().getLocation());
            //System.out.println(gotUrl);
            /*int index = gotUrl.indexOf(" km");
                        String sub = gotUrl.substring(index - 6, index);
                        System.out.println(sub);
                        sub = sub.replace(',', '.');
                        distance = (int) Math.round(Double.parseDouble(sub.substring(sub.indexOf("\"") + 1)));
                        txtDistance.setText(distance.toString());
                        btnBev.setDisable(false);
                        cbClient.setDisable(false);
                        txtDistance.setEditable(false);
             */

        }

    }
// lekéri a távot a gmapstól a webview segítségével a webview nem látszik a felhasználói felületen

    public void getDistanceFromGmaps(String sAddress, String tAddress) {
        if (txtDistance.getText() != "" || txtDistance != null) {
            String gUrl = "https://www.google.hu/maps/dir/" + sAddress + "/" + tAddress;
            ///System.out.println(gUrl);
            // itt lekérdezi a távolságot a google mapstól           
            WV.getEngine().load(gUrl);

        }
        btnBev.setDisable(true);
        btnDistance.setDisable(true);
        showAlert("Távolság lekérése a Google Maps-tól", true, "info");
        btnAlertSingleOK.setDisable(true);

//figyeli hogy betöltődött-e az oldal és vár míg meg nem történt
        WV.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
            @Override
            public void changed(
                    ObservableValue<? extends Worker.State> observable,
                    Worker.State oldValue, Worker.State newValue) {
                switch (newValue) {
                    case SUCCEEDED:
                        btnDistance.setDisable(false);
                        btnBev.setDisable(false);
                        btnAlertSingleOK.setDisable(false);
                        alertPane.setVisible(false);
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
//bekerül a html oldal forráskódja (amit a gmapstól kapott) a gotUrl változóba  
                String gotUrl = getURL(WV.getEngine().getLocation());
//kikeresi a " km" szöveg kezdőindexét ez van a gmapstól visszakapott kódban a távolság után közvetlenűl               
                int index = gotUrl.indexOf(" km");
//kiszedi a távolságot megfelelően kerekíti és beírja a textboxba                
                String sub = gotUrl.substring(index - 6, index);
                System.out.println(sub);
                sub = sub.replace(',', '.');
                distance = (int) Math.round(Double.parseDouble(sub.substring(sub.indexOf("\"") + 1)));
                txtDistance.setText(distance.toString());
                btnBev.setDisable(false);
                cbClient.setDisable(false);
                txtDistance.setEditable(false);

            }
        });
    }
// URL beolvasása

    public static String getURL(String url) {
        StringBuilder response = null;
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
        } catch (Exception e) {
            System.out.println("hiba");
        }
        return response.toString();
    }
// dinamikusan állítja be a tábla oszlopait

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
        indCol.setPrefWidth(210);        //oszlop min szélesség beállítása 200 pixelre
        indCol.setResizable(false);
        indCol.setEditable(true);
        indCol.setCellValueFactory(new PropertyValueFactory<Route, StringProperty>("indulas"));  //beállítja az oszlop adatértékét az Item objektum indulas String változójára

        erkCol = new TableColumn("Érkezés");
        erkCol.setPrefWidth(210);
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

// Ha a táblában kiválasztunk egy sort
    // ide kerül a kiválasztott sor indexe
    int selectedItemIndex;

    public void tableSelected() {
        chkSites.setSelected(false);
        selectedItemIndex = table.getSelectionModel().getSelectedIndex();
        selectedRoute = table.getSelectionModel().getSelectedItem();
        selctedRow = table.getSelectionModel().getSelectedIndex();
        datePicker.setValue(LocalDate.parse(selectedRoute.getDatum()));
        if (!selectedRoute.isMagan()) {
            cbClient.setValue(getClientNumberFromRoute(selectedRoute.getUgyfel()));
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
        chkBack.setSelected(selectedRoute.isVissza());
        txtFueling.setText(String.valueOf(selectedRoute.getFueling()));
        startClientTemp = startClient;
        targetClientTemp = targetClient;
        startClient = db.getClientFromAddress(selectedRoute.getIndulas());
        targetClient = db.getClientFromAddress(selectedRoute.getErkezes());
        paneCorr.setVisible(true);
        paneNormal.setVisible(false);
    }
//Kiszedi a Route-bol a csupasz gépszámot    

    public String getClientNumberFromRoute(String clientNumber) {
        if (clientNumber.contains("/")) {
            int index = clientNumber.indexOf('/');
            return clientNumber.substring(0, index);
        } else {
            return clientNumber;
        }
    }

    public static void saveFile(String filename, String[] list) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        for (int i = 0; i < list.length; i++) {
            writer.println(list[i]);
        }
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
        System.out.println("spedometer:" + spedometer);
        textZaro.setText(spedometer.toString());
    }

    public void setLabels() {
        lblName.setText("Név: " + settings.getNev());
        lblSites.setText("T.hely: " + settings.getVaros());
        lblKezdo.setText("Kezdő: " + settings.getElozo_zaro() + " Km");
        lblKm.setText("Jelenlegi záró: " + (settings.getElozo_zaro() + db.getSpedometer(workDate, settings.getRendszam())) + " Km");
        lblMegtett.setText("Megtett út: " + db.getSpedometer(workDate, settings.getRendszam()) + " Km");
        Double atlagFogy = 100 * db.getFueling(workDate, settings.getRendszam()) / db.getSpedometer(workDate, settings.getRendszam());
        System.out.println(atlagFogy);
        lblAtlagFogy.setText("Átlag fogyasztás: " + atlagFogy.toString().substring(0, 3));
        txtFueling.clear();
        lblVerzio.setText(lblVer.getText());
        lblRendszam.setText("Rendszám: " + settings.getRendszam());

    }

    private void fillField(TextField text, ArrayList list) {
        TextFields.bindAutoCompletion(text, list);
    }

    private void checkSpecialClients() {
        Client telephely = db.getClient("telephely");

        if (telephely == null) {
            telephely = new Client("telephely",
                    "telephely",
                    "telephely",
                    "telephely",
                    0,
                    settings.getVaros(),
                    settings.getCim(),
                    true,
                    0,
                    settings.getNev());
            db.addClient(telephely, false);
        }

        Client diebold = db.getSajatClient("DieboldNixdorf");
        if (diebold == null) {
            diebold = new Client(
                    "DieboldNixdorf",
                    "DieboldNixdorf",
                    "telephely",
                    "telephely",
                    2220,
                    "Vecsés",
                    "Lőrinci út 59-61",
                    true,
                    0,
                    "DieboldNixdorf");
            db.addClient(diebold, true);
            db.addClient(diebold, false);
        }

        Client magan = db.getClient("Magánhasználat");
        if (magan == null) {
            magan = new Client(
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
            db.addClient(magan, false);
        }
    }
//Elkészíti az excel táblát

    public void makeExcel(String fileName, String sheetName) {
        Integer megtettKM = db.getSpedometer(workDate, settings.getRendszam());
        RowToExcel rowToExcel = new RowToExcel();
        rowToExcel.createNewExcelFile(fileName);
        rowToExcel.setCell(fileName, sheetName, "D2", workDate);
        rowToExcel.setCell(fileName, sheetName, "C3", settings.getAuto());
        rowToExcel.setCell(fileName, sheetName, "C4", settings.getRendszam());
        rowToExcel.setCell(fileName, sheetName, "C5", String.valueOf(settings.getElozo_zaro()));
        rowToExcel.setCell(fileName, sheetName, "C6", String.valueOf(settings.getZaroKm()));
        rowToExcel.setCell(fileName, sheetName, "L105", String.valueOf(settings.getZaroKm()));
        rowToExcel.setCell(fileName, sheetName, "D4", settings.getNev());
        rowToExcel.setCell(fileName, sheetName, "G3", settings.getLoketterfogat());
        rowToExcel.setCell(fileName, sheetName, "G4", settings.getFogyasztas());
        rowToExcel.setCell(fileName, sheetName, "G5", megtettKM.toString());
        rowToExcel.setCell(fileName, sheetName, "L103", megtettKM.toString());
        String fuelValue = String.valueOf(db.getFueling(workDate, settings.getRendszam()));
        if (fuelValue.length() > 6) {
            fuelValue = fuelValue.substring(0, 7);
        }
        rowToExcel.setCell(fileName, sheetName, "G7", fuelValue);
        Double value = 100 * db.getFueling(workDate, settings.getRendszam()) / megtettKM;
        String dValue = "";
        if (value.toString().length() > 4) {
            dValue = value.toString().substring(0, 5);
        }
        rowToExcel.setCell(fileName, sheetName, "G6", dValue);
        rowToExcel.setCell(fileName, sheetName, "L104", String.valueOf(db.getMaganut(workDate, settings.getRendszam())));
        Double doubleValue = (double) db.getMaganut(workDate, settings.getRendszam()) / megtettKM * 100;
        if (doubleValue.toString().length() > 2 && doubleValue != 100) {
            dValue = doubleValue.toString().substring(0, 2);
        } else {
            dValue = doubleValue.toString();
        }

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
//Újraszámolja a kilométeróra állást az adatbázisban

    public void rebuildDistanceInDb() {
        int currentValue = settings.getElozo_zaro();

        for (int i = 0; i < observableList.size(); i++) {
            currentValue = currentValue + observableList.get(i).getTavolsag();
            observableList.get(i).setSpedometer(currentValue);
            db.updateRoute(observableList.get(i), observableList.get(i).getRouteId());
        }
        setLabels();
    }
//Kicseréli a nemkivánatos karaktereket szóközre

    public String cleanString(String str) {
        if (str.contains("/")) {
            str = str.replaceAll("/", " ");
        }
        if (str.contains("'")) {
            str = str.replaceAll("'", " ");
        }
        if (str.contains("\\")) {
            str = str.replaceAll("\\", " ");
        }
        if (str.contains("\"")) {
            str = str.replaceAll("\"", " ");
        }

        return str;
    }
// összerakja a várost és a címet egy stringbe

    public String getClientFullAddress(Client client) {
        System.out.println(client.getCity() + " " + client.getAddress());
        return client.getCity() + " " + client.getAddress();

    }
//Single button => true , dual button => false; típusok info(vagy bármilyen string),err,warn,succ
// a kétgombos ablak nem működik, meg kellene oldani, hogy a program várjon amíg nem nyomjuk meg 
// valamelyik gombot.(Pillanatnyilag nem fontos)

    public void showAlert(String alertText, Boolean singleOrDualButton, String alertLevel) {
        alertClick = false;
//mivel pillanatnyilag csak az egy gombos működik ezért beállítottam true-ra
        singleOrDualButton = true;
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
// a logika ahhoz, hogy a + - gomb 12 hónapnak megfelelően működjön.

    public String workDateDecOrInc(String value) {

        Integer year = Integer.parseInt(workDate.substring(0, 4));
        Integer month = Integer.parseInt(workDate.substring(5, 7));
        if (value == "+") {
            if (month < 12) {
                month = month + 1;
            } else {
                year = year + 1;
                month = 1;
            }
        }

        if (value == "-") {
            if (month > 1) {
                month--;
            } else {
                year--;
                month = 12;
            }
        }
        String mnt;
        if (month < 10) {
            mnt = "0" + month.toString();
        } else {
            mnt = month.toString();
        }
        //tempSettings=settings;
        settings.setAktualis_honap(year.toString() + "-" + mnt);
        settings.setId(settings.getRendszam() + year.toString() + "-" + mnt);

        return year.toString() + "-" + mnt;

    }
//"+" = plus gomb; "-" = - gomb; Ha nem volt + vagy - akkor  "#" = beirt érték után Kész gomb

    public void setWorkdate(String plusOrMinus) {
        // checkDateForPlusButton();
        Settings prevSettings = settings;
        workDate = txtDate.getText();
        /*int yearNow = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
        int monthNow = Integer.parseInt(LocalDate.now().toString().substring(5, 7));
        int workYear = Integer.parseInt(workDate.substring(0, 4));
        int workMonth = Integer.parseInt(workDate.substring(5, 7));
        System.out.println("work: "+workYear+"; now: "+yearNow);
        if (workYear > yearNow || (workYear == yearNow && workMonth > monthNow)) {
            settings=tempSettings;
            //workDateDecOrInc("-");
            txtDate.setText(settings.getAktualis_honap());
            workDate=settings.getAktualis_honap();
            settings.setActive(true);
            db.updateSettings(settings, settings.getId());
            showAlert("A kiválasztott hónappal még nem \ndolgozhatsz,("+workDate+") mert az még a jövő!!!", true, "warn");
        } else {
         */
        if (plusOrMinus == "-" || plusOrMinus == "#") {
            settingsId = settings.getRendszam() + workDate;
            System.out.println("setworkdate1" + workDate);
            Settings s1 = db.getSettings(settingsId);
            if (s1 != null) {
                settings = s1;
                settings.setActive(true);
                db.updateSettings(settings, settings.getId());
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
            settingsId = settings.getRendszam() + workDate;
            System.out.println("plusorminus: " + workDate);
            Settings s1 = db.getSettings(settingsId);
            System.out.println();
            if (s1 != null) {
                settings = s1;
                settings.setActive(true);
                db.updateSettings(settings, settings.getId());
                observableList.clear();
                observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));

                if (settings.getElozo_zaro() != prevSettings.getZaroKm()) {
                    settings.setElozo_zaro(prevSettings.getZaroKm());
                    settings.setZaro_km(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
                }
                setLabels();

            } else {
                int closingKmValue = settings.getZaroKm(); //elmenti a zárót
                settings.setAktualis_honap(workDate);
                System.out.println("plusorminus2: " + workDate);
                settings.setElozo_zaro(closingKmValue);    //beállítja a mentett zárót nyitónak
                settings.setZaro_km(closingKmValue);        //zárónak is
                settings.setActive(true);
                db.addSettings(settings);
                System.out.println("setworkdate2" + workDate);
                observableList.clear();
                observableList.addAll(db.getRoutes(workDate, settings.getRendszam()));

                settings.setZaro_km(db.getSpedometer(workDate, settings.getRendszam()) + settings.getElozo_zaro());
                System.out.println("setworkdate3" + workDate);
                db.updateSettings(settings, (settings.getRendszam() + workDate));

                setLabels();
            }
        }
    }
    //}
//Éppen aktuális hónap kiválasztása(amivel utoljára dolgoztunk)

    public void setDate() {
        String dateValue = db.getDateOfLastRoute();
        if (dateValue != null) {
            workDate = dateValue.substring(0, 7);
            System.out.println("setDate: " + workDate);
            datePicker.setValue(LocalDate.parse(dateValue));
        }
    }
// ha vesszőt írnak a törtbe kicseréli pontra

    public String checkFueling(String fuel) {
        System.out.println(fuel);
        if (fuel.contains(",")) {
            fuel = fuel.replace(",", ".");
        }
        return fuel;
    }
// Egy saját uticélt ad az adatbázishoz

    public void addSajatCim() {

        int zipcode;
        try {
            zipcode = Integer.parseInt(txtSajatIranyitoSzam.getText());
        } catch (NumberFormatException ex) {
            zipcode = 0;
        }
        String sajatClientNumber = txtSajatClientNumber.getText();
        if (sajatClientNumber.trim().length() == 0) {
            sajatClientNumber = txtSajatClient.getText();
            if(sajatClientNumber.contains(" "))
                sajatClientNumber= sajatClientNumber.replaceAll(" ","_");

        }
        String sajatCli= txtSajatClient.getText();
        if(sajatCli.contains(" "))
                sajatCli= sajatCli.replaceAll(" ","_");
        
        Client sajatClient = new Client(
               sajatCli,
                sajatClientNumber,
                txtSajatEgyebAdat.getText(),
                txtSajatClient.getText(),
                zipcode,
                txtSajatVaros.getText(),
                txtSajatCim.getText(),
                true,
                0,
                settings.getNev());
        txtSajatClientNumber.clear();
        txtSajatClient.clear();
        txtSajatEgyebAdat.clear();
        txtSajatVaros.clear();
        txtSajatIranyitoSzam.clear();
        txtSajatCim.clear();

        String field = settings.getNev();
        db.addClient(sajatClient, true);
        db.addClient(sajatClient, false);
        cbSajat.getItems().clear();
        cbSajat.getItems().addAll(db.getAllClient(true));
        //cbClient.getEditor().clear();
        cbClient.getItems().remove(0, cbClient.getItems().size());
        cbClient.getItems().addAll(db.getAllClient(true));
        cbClient.getItems().addAll(db.getAllClient(false));
    }
//Egy saját uticélt töröl az adatbázisból

    public void delSajatCim() {
        String value = cbSajat.getValue().toString();
        db.delClient(value, true);
        db.delClient(value, false);
        cbSajat.getItems().clear();
        cbSajat.getItems().addAll(db.getAllClient(true));
        cbClient.getItems().remove(0, cbClient.getItems().size());
        cbClient.getItems().addAll(db.getAllClient(true));
        cbClient.getItems().addAll(db.getAllClient(false));
    }

    public void checkDateForPlusButton() {
        int yearNow = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
        int monthNow = Integer.parseInt(LocalDate.now().toString().substring(5, 7));
        int workYear = Integer.parseInt(workDate.substring(0, 4));
        int workMonth = Integer.parseInt(workDate.substring(5, 7));

        if (workYear == yearNow && workMonth == monthNow) {
            btnPlus.setDisable(true);
        } else {
            btnPlus.setDisable(false);
        }
    }

}
