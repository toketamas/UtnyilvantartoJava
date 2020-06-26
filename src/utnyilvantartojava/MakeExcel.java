package utnyilvantartojava;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class MakeExcel {

    public Cell getCell(String fileName, String sheetName, String cellAddr) {
        FileInputStream inputStream = null;
        Workbook workbook = null;

        try {
            inputStream = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Nem lehet megnyitn a fájlt!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Nem lehet a fájlból olvasi!");
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(sheetName);
        CellAddress cellAddress = new CellAddress(cellAddr);
        Row row = sheet.getRow(cellAddress.getRow());
        Cell cell = row.getCell(cellAddress.getColumn());
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cell;
    }

    public void setCell(String fileName, String sheetName, String cellAddr) {
        FileOutputStream outputStream = null;
        Workbook workbook = null;
    }

    public void getRow(String fileName, String sheetName, int rowNumber, int rowLength) {
        for (int j = 65; j < 65 + rowLength; j++) {
            //System.out.println(((char) j)+""+rowNumber);
            System.out.print(getCell(fileName, sheetName, (char) j + "" + rowNumber) + " ");
        }
    }
}

