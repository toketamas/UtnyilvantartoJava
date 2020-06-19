package com.company;

public class Main {

    public static void main(String[] args) {

        DBModel db = new DBModel();
        IOExcel ioExcel = new IOExcel();

        String fileName = "ATM_karb_20200525.xlsx";
        String sheetName = "ADATOK";
        int i = 2;

        while (ioExcel.getCell(fileName, sheetName, "A" + i) != null || ioExcel.getCell(fileName, sheetName, "A" + i).getStringCellValue() != "") {

            String client;
            try {
                client = ioExcel.getCell(fileName, sheetName, "A" + i).getStringCellValue();
            }catch (Exception e){
                client="nincs adat";
            }
            String clientNumber;
            try {
                clientNumber = ioExcel.getCell(fileName, sheetName, "B" + i).getStringCellValue();
            }catch (Exception e){
                clientNumber="nincs adat";
            }
            String type;
            try {
                type = ioExcel.getCell(fileName, sheetName, "C" + i).getStringCellValue();
            }catch (Exception e){
                type="nincs adat";
            }
            String factoryNumber;
            try {
                factoryNumber = ioExcel.getCell(fileName, sheetName, "D" + i).getStringCellValue();
            }catch (Exception e){
                factoryNumber="nincs adat";
            }
            int zipCode;
            try{
                zipCode= Integer.parseInt(ioExcel.getCell(fileName, sheetName, "E" + i).getStringCellValue());
            }catch (Exception e){
                zipCode=0;
            }
            String city;
            try{
                city= ioExcel.getCell(fileName, sheetName, "F" + i).getStringCellValue();
            }catch (Exception e){
                city="nincs adat";
            }
            String address;
            try{
                address= ioExcel.getCell(fileName, sheetName, "G" + i).getStringCellValue();
            }catch (Exception e){
                address="nincs adat";
            }

            boolean exist;
            try{
                exist= db.convertBool(ioExcel.getCell(fileName,sheetName,"H"+i).getStringCellValue());
            }catch (Exception e){
                exist=false;
            }
            int maintenancePerYear;
            try {
                 maintenancePerYear = (int) ioExcel.getCell(fileName, sheetName, "I" + i).getNumericCellValue();
            }catch (Exception e){
                maintenancePerYear=0;
            }
            String field;
            try
            {
                field= ioExcel.getCell(fileName, sheetName, "K" + i).getStringCellValue();
            }catch (Exception e){
                field="nincs adat";
            }
            String txt=client+" "+clientNumber+" "+type+" "+factoryNumber+" "+zipCode+" "+city+" "+address+" "+exist+" "+maintenancePerYear+" "+field+"\n";



            db.addClient(client,clientNumber,type,factoryNumber,zipCode,city,address,exist,maintenancePerYear,field);

            System.out.println(i + ".  " + txt);

            i++;

        }
    }

}
