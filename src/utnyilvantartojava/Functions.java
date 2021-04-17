package utnyilvantartojava;

import java.time.LocalDate;

public class Functions {
    private ViewController context;
    private SqlBuilder sqlBuilder;

    public Functions(ViewController context) {

        this.context=context;
        sqlBuilder = new SqlBuilder(
                Constants.SqliteDataBase.JDBC_DRIVER,
                Constants.SqliteDataBase.URL,
                Constants.SqliteDataBase.USERNAME,
                Constants.SqliteDataBase.PASSWORD);
    }

    public static Functions functions(ViewController context){
        Functions functions=new Functions(context);
        return functions;
    }


    //Éppen aktuális hónap kiválasztása(amivel utoljára dolgoztunk)
    public  void setDate() {
        String dateValue = sqlBuilder.getDateOfLastRoute();
        if (dateValue != null) {
            context.workDate = dateValue.substring(0, 7);
            System.out.println("setDate: " + context.workDate);
            context.datePicker.setValue(LocalDate.parse(dateValue));
        }
    }





    public void addSajatCim() {

        int zipcode;
        try {
            zipcode = Integer.parseInt(context.txtSajatIranyitoSzam.getText());
        } catch (NumberFormatException ex) {
            zipcode = 0;
        }
        String sajatClientNumber = context.txtSajatClientNumber.getText();
        if (sajatClientNumber.trim().length() == 0) {
            sajatClientNumber = context.txtSajatClient.getText();
            if (sajatClientNumber.contains(" ")) {
                sajatClientNumber = sajatClientNumber.replaceAll(" ", "_");
            }

        }
        String sajatCli = context.txtSajatClient.getText();
        if (sajatCli.contains(" ")) {
            sajatCli = sajatCli.replaceAll(" ", "_");
        }

        Client sajatClient = new Client(
                sajatCli,
                sajatClientNumber,
                context.txtSajatEgyebAdat.getText(),
                context.txtSajatClient.getText(),
                zipcode,
                context.txtSajatVaros.getText(),
                context.txtSajatCim.getText(),
                true,
                0,
                context.settings.getNev());
        context.txtSajatClientNumber.clear();
        context.txtSajatClient.clear();
        context.txtSajatEgyebAdat.clear();
        context.txtSajatVaros.clear();
        context.txtSajatIranyitoSzam.clear();
        context.txtSajatCim.clear();

        String field = context.settings.getNev();
        sqlBuilder.insert(sajatClient.doubleList(), "sajat_cimek");
        sqlBuilder.insert(sajatClient.doubleList(), "clients");
        context.cbSajat.getItems().clear();
        context.cbSajat.getItems().addAll(context.db.getAllClient(true));
        //cbClient.getEditor().clear();
        context.cbClient.getItems().remove(0, context.cbClient.getItems().size());
        context.cbClient.getItems().addAll(context.db.getAllClient(true));
        context.cbClient.getItems().addAll(context.db.getAllClient(false));
    }
//Egy saját uticélt töröl az adatbázisból

    public void delSajatCim() {
        String value = context.cbSajat.getValue().toString();
        sqlBuilder.delClient(value, true);
        sqlBuilder.delClient(value, false);
        context.cbSajat.getItems().clear();
        context.cbSajat.getItems().addAll(context.db.getAllClient(true));
        context.cbClient.getItems().remove(0, context.cbClient.getItems().size());
        context.cbClient.getItems().addAll(context.db.getAllClient(true));
        context.cbClient.getItems().addAll(context.db.getAllClient(false));
    }

    public void checkDateForPlusButton() {
        int yearNow = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
        int monthNow = Integer.parseInt(LocalDate.now().toString().substring(5, 7));
        int workYear = Integer.parseInt(context.workDate.substring(0, 4));
        int workMonth = Integer.parseInt(context.workDate.substring(5, 7));

        if (workYear == yearNow && workMonth == monthNow) {
            context.btnPlus.setDisable(true);
        } else {
            context.btnPlus.setDisable(false);
        }
    }

    public void setLabelMaganKm() {
        double km = 0;
        double osszKm = 0;
        double eredmeny = 0;
        for (int i = 0; i < context.observableList.size(); i++) {
            osszKm = osszKm + context.observableList.get(i).getTavolsag();
            System.out.println(context.observableList.get(i).isMagan());
            if (context.observableList.get(i).isMagan()) {
                km = km + context.observableList.get(i).getTavolsag();
            }
        }
        System.out.println("km=" + km);
        System.out.println("összKm=" + osszKm);
//         eredmeny=(km/osszKm)*100;
        try {
            eredmeny = (km / osszKm) * 100;
        } catch (Exception e) {
            eredmeny = 0;
        }

        context.lblMaganKm.setText("Magánút: " + (int) km + " km" + ", " + (int) eredmeny + "%");
        if (eredmeny >= 10 || km >= 500)
            context.lblMaganKm.setStyle(" -fx-text-fill: red");
        else
            context.lblMaganKm.setStyle(" -fx-text-fill: green");

    }

}
