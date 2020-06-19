package exceltodb;

public class Main {

    public static void main(String[] args) {

        DBModel db = new DBModel();
        ExcelToDB excelToDB = new ExcelToDB();

        String fileName = args[0];                         // filenév
        String sheetName = args[1];                        // munkalap neve
        int startNumber = Integer.parseInt(args[2]);       // kezdősor

        while (excelToDB.getCell(fileName, sheetName, "A" + startNumber) != null || excelToDB.getCell(fileName, sheetName, "A" + startNumber).getStringCellValue() != "") {

            String client;
            try {
                client = excelToDB.getCell(fileName, sheetName, "A" + startNumber).getStringCellValue();
            }catch (Exception e){
                client="nincs adat";
            }
            String clientNumber;
            try {
                clientNumber = excelToDB.getCell(fileName, sheetName, "B" + startNumber).getStringCellValue();
            }catch (Exception e){
                clientNumber="nincs adat";
            }
            String type;
            try {
                type = excelToDB.getCell(fileName, sheetName, "C" + startNumber).getStringCellValue();
            }catch (Exception e){
                type="nincs adat";
            }
            String factoryNumber;
            try {
                factoryNumber = excelToDB.getCell(fileName, sheetName, "D" + startNumber).getStringCellValue();
            }catch (Exception e){
                factoryNumber="nincs adat";
            }
            int zipCode;
            try{
                zipCode= Integer.parseInt(excelToDB.getCell(fileName, sheetName, "E" + startNumber).getStringCellValue());
            }catch (Exception e){
                zipCode=0;
            }
            String city;
            try{
                city= excelToDB.getCell(fileName, sheetName, "F" + startNumber).getStringCellValue();
            }catch (Exception e){
                city="nincs adat";
            }
            String address;
            try{
                address= excelToDB.getCell(fileName, sheetName, "G" + startNumber).getStringCellValue();
            }catch (Exception e){
                address="nincs adat";
            }

            boolean exist;
            try{
                exist= db.convertBool(excelToDB.getCell(fileName,sheetName,"H"+startNumber).getStringCellValue());
            }catch (Exception e){
                exist=false;
            }
            int maintenancePerYear;
            try {
                 maintenancePerYear = (int) excelToDB.getCell(fileName, sheetName, "I" + startNumber).getNumericCellValue();
            }catch (Exception e){
                maintenancePerYear=0;
            }
            String field;
            try
            {
                field= excelToDB.getCell(fileName, sheetName, "K" + startNumber).getStringCellValue();
            }catch (Exception e){
                field="nincs adat";
            }
            String txt=client+" "+clientNumber+" "+type+" "+factoryNumber+" "+zipCode+" "+city+" "+address+" "+exist+" "+maintenancePerYear+" "+field+"\n";



            db.addClient(client,clientNumber,type,factoryNumber,zipCode,city,address,exist,maintenancePerYear,field);

            System.out.println(startNumber + ".  " + txt);

            startNumber++;

        }
    }

}
