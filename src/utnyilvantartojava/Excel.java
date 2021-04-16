package utnyilvantartojava;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


class Excel {
    private String date;
    private String travelType;
    private String depart;
    private String arrive;
    private String client;
    private Integer speedometer;
    private Double fueling;
    private Integer distance;
    private String mORc;
    private int rowLength=9;
    public Excel(){
    }
    public Excel(String date, String travelType, String depart, String arrive, String client, Integer speedometer, Double fueling, Integer distance, String mORc) {
        this.date = date;
        this.travelType = travelType;
        this.depart = depart;
        this.arrive = arrive;
        this.client = client;
        this.speedometer = speedometer;
        this.fueling = fueling;
        this.distance = distance;
        this.mORc = mORc;

    }

    //<editor-fold desc="getter-setter">
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getSpeedometer() {
        return speedometer;
    }

    public void setSpeedometer(Integer speedometer) {
        this.speedometer = speedometer;
    }

    public Double getFueling() {
        return fueling;
    }

    public void setFueling(Double fueling) {
        this.fueling = fueling;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getmORc() {
        return mORc;
    }

    public void setmORc(String mORc) {
        this.mORc = mORc;
    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }
    //</editor-fold>

  private void createNewExcelFile(String fileName){
        FileInputStream file = null;
        try {
            file = new FileInputStream("blank.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            FileOutputStream outFile =new FileOutputStream(new File(fileName));
            workbook.write(outFile);
            outFile.close();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRow (String fileName, String sheetName, int rowNumber) {

        try {
            FileInputStream file = new FileInputStream(fileName);

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);
            Cell cell = null;
            Row row = sheet.getRow(rowNumber);

            //Update the value of cell
            cell = row.getCell(1);
            cell.setCellValue(this.date);
            cell= row.getCell(2);
            cell.setCellValue(this.travelType);
            cell = row.getCell(3);
            cell.setCellValue(this.depart);
            cell= row.getCell(4);
            cell.setCellValue(this.arrive);
            cell = row.getCell(5);
            cell.setCellValue(this.client);
            cell= row.getCell(6);
            cell.setCellValue(this.speedometer);
            cell = row.getCell(7);
            cell.setCellValue(this.fueling);
            cell= row.getCell(8);
            cell.setCellValue(this.distance);
            cell= row.getCell(9);
            cell.setCellValue(this.mORc);



            FileOutputStream outFile =new FileOutputStream(new File(fileName));
            workbook.write(outFile);
            outFile.close();

            file.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void setCell (String fileName, String sheetName, String cellAddr, String value) {

        //fileName="proba.xlsx";
        try {
            FileInputStream file = new FileInputStream(fileName);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Cell cell = null;
            CellAddress cellAddress = new CellAddress(cellAddr);
            Row row = sheet.getRow(cellAddress.getRow());

            //Update the value of cell
           cell = row.getCell(cellAddress.getColumn());
            cell.setCellValue(value);
            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(fileName));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void make(String fileName, String sheetName, Settings settings, ObservableList<Route> observableList) {
        //**workdate->settings.getAktualis_honap()
        Integer megtettKM = db.getSpedometer(settings.getAktualis_honap(), settings.getRendszam());
        Excel excel = new Excel();
        excel.createNewExcelFile(fileName);
        //**workdate->settings.getAktualis_honap()
        excel.setCell(fileName, sheetName, "D2", settings.getAktualis_honap());
        excel.setCell(fileName, sheetName, "C3", settings.getAuto());
        excel.setCell(fileName, sheetName, "C4", settings.getRendszam());
        excel.setCell(fileName, sheetName, "C5", String.valueOf(settings.getElozo_zaro()));
        excel.setCell(fileName, sheetName, "C6", String.valueOf(settings.getZaroKm()));
        excel.setCell(fileName, sheetName, "L174", String.valueOf(settings.getZaroKm()));
        excel.setCell(fileName, sheetName, "D4", settings.getNev());
        excel.setCell(fileName, sheetName, "G3", settings.getLoketterfogat());
        excel.setCell(fileName, sheetName, "G4", settings.getFogyasztas());
        excel.setCell(fileName, sheetName, "G5", megtettKM.toString());
        excel.setCell(fileName, sheetName, "L172", megtettKM.toString());
        //**workdate->settings.getAktualis_honap()
        String fuelValue = String.valueOf(db.getFueling(settings.getAktualis_honap(), settings.getRendszam()));
        if (fuelValue.length() > 6) {
            fuelValue = fuelValue.substring(0, 7);
        }
        excel.setCell(fileName, sheetName, "G7", fuelValue);
        //**workdate->settings.getAktualis_honap()
        Double value = 100 * db.getFueling(settings.getAktualis_honap(), settings.getRendszam()) / megtettKM;
        String dValue = "";
        if (value.toString().length() > 4) {
            dValue = value.toString().substring(0, 5);
        }
        excel.setCell(fileName, sheetName, "G6", dValue);
        //**workdate->settings.getAktualis_honap()
        excel.setCell(fileName, sheetName, "L173", String.valueOf(db.getMaganut(settings.getAktualis_honap(), settings.getRendszam())));
        //**workdate->settings.getAktualis_honap()
        Double doubleValue = (double) db.getMaganut(settings.getAktualis_honap(), settings.getRendszam()) / megtettKM * 100;
        if (doubleValue.toString().length() > 2 && doubleValue != 100) {
            dValue = doubleValue.toString().substring(0, 2);
        } else {
            dValue = doubleValue.toString();
        }

        excel.setCell(fileName, sheetName, "M173", dValue + "%");

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
            Excel row = new Excel(
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
}



