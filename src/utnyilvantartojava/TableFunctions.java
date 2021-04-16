package utnyilvantartojava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class TableFunctions {
    private ViewController context;
    TableColumn datCol;
    TableColumn maganCol;
    TableColumn odaVisszaCol;
    TableColumn tankolCol;
    TableColumn indCol;
    TableColumn erkCol;
    TableColumn tavCol;
    TableColumn ugyfCol;
    TableColumn spedometerCol;
    NonFxFunctions nonFxFunctions;
    SqlBuilder sqlBuilder;

    public TableFunctions(ViewController context){

        this.context=context;
        nonFxFunctions=new NonFxFunctions();
        sqlBuilder = new SqlBuilder(
                Constants.SqliteDataBase.JDBC_DRIVER,
                Constants.SqliteDataBase.URL,
                Constants.SqliteDataBase.USERNAME,
                Constants.SqliteDataBase.PASSWORD);
    }



    public static TableFunctions tableFunctions(ViewController context){
        TableFunctions tableFunctions=new TableFunctions(context);
        return tableFunctions;
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

        context.table.getColumns().addAll(datCol, maganCol, indCol, erkCol, ugyfCol, spedometerCol, tankolCol, tavCol, odaVisszaCol);
        context.table.setItems(context.observableList);

        context.table.setOnMouseClicked(event -> {
            tableSelected();
        });
    }

    // Ha a táblában kiválasztunk egy sort
    // ide kerül a kiválasztott sor indexe
    int selectedItemIndex;

    public void tableSelected() {
        context.chkSites.setSelected(false);
        selectedItemIndex = context.table.getSelectionModel().getSelectedIndex();
        context.selectedRoute = context.table.getSelectionModel().getSelectedItem();
        context.selctedRow = context.table.getSelectionModel().getSelectedIndex();
        context.datePicker.setValue(LocalDate.parse(context.selectedRoute.getDatum()));
        if (!context.selectedRoute.isMagan()) {
            context.cbClient.setValue(nonFxFunctions.getClientNumberFromRoute(context.selectedRoute.getUgyfel()));
        } else {
            context.cbClient.setValue(context.selectedRoute.getUgyfel());
        }
        context.txtDistance.setEditable(true);
        context.txtDepart.setText(context.selectedRoute.getIndulas());
        context.txtArrive.setText(context.selectedRoute.getErkezes());
        context.txtDistance.setText(String.valueOf(context.selectedRoute.getTavolsag()));
        context.chkPrivate.setSelected(context.selectedRoute.isMagan());
        context.selectedClientSpedometer = context.selectedRoute.getSpedometer();
        context.selectedClientOdaVissza = context.selectedRoute.isVissza();
        context.chkBack.setSelected(context.selectedRoute.isVissza());
        context.txtFueling.setText(String.valueOf(context.selectedRoute.getFueling()));
        context.startClientTemp = context.startClient;
        context.targetClientTemp = context.targetClient;
        context.startClient = sqlBuilder.getClientFromAddress(context.selectedRoute.getIndulas());
        context.targetClient = sqlBuilder.getClientFromAddress(context.selectedRoute.getErkezes());
        context.paneCorr.setVisible(true);
        context.paneNormal.setVisible(false);
    }

}
