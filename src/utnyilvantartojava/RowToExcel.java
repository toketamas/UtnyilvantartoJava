package utnyilvantartojava;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


class RowToExcel {
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
    public RowToExcel(){

    }
    public RowToExcel(String date, String travelType, String depart, String arrive, String client, Integer speedometer, Double fueling, Integer distance, String mORc) {
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

    public void createNewExcelFile(String fileName){
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

    public void setRow (String fileName, String sheetName, int rowNumber) {

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



    public void setCell (String fileName, String sheetName, String cellAddr, String value) {

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

    }




