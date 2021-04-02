package utnyilvantartojava;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import utnyilvantartojava.ViewController;


public class Alert   {


    public Alert( ViewController context,Boolean singleOrDualButton, String alertLevel, String alertText) {


        // public void showAlert(String alertText, Boolean singleOrDualButton, String alertLevel) {

        context.alertClick = false;
//mivel pillanatnyilag csak az egy gombos működik ezért beállítottam true-ra
        singleOrDualButton = true;
        String color = "83b1f5";//kék
        context.lblAlert.textFillProperty().setValue(Paint.valueOf("ffffff"));
        context.lblAlert.setText("Információ");
        if (alertLevel == "err") {
            color = "ff4d4d";//piros
            context.lblAlert.setText("Hiba!");
        }
        if (alertLevel == "warn") {
            color = "f2cc5a";//sárga
          context.lblAlert.textFillProperty().setValue(Paint.valueOf("000000"));
            context.lblAlert.setText("Figyelmeztetés!");
        }
        if (alertLevel == "succ") {
            color = "8bdb70";//zöld
            context.lblAlert.textFillProperty().setValue(Paint.valueOf("ffffff"));
            context.lblAlert.setText("Siker!");
        }
        context.alertPane.setVisible(true);
        context.alertTextArea.clear();
        context.alertTextArea.setText(alertText);
        //alertCircle.fillProperty().setValue(Paint.valueOf(color));
        context.alertRectangle.fillProperty().setValue(Paint.valueOf(color));
        if (singleOrDualButton) {
            context.paneSingleButton.setVisible(true);
            context.paneDualButton.setVisible(false);

        } else {
            context.paneDualButton.setVisible(true);
            context.paneSingleButton.setVisible(false);
        }
    }

    public static void showAlert(ViewController context, String alertText, Boolean singleOrDualButton, String alertLevel) {
        Alert alert = new Alert(context,singleOrDualButton, alertLevel, alertText);
    }
}