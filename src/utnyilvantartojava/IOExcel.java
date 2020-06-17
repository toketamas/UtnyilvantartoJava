package utnyilvantartojava;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOExcel {

   public /*Object*/void  getData(String fileName, String sheetName,String cellAddr){
       FileInputStream inputStream=null;
       Workbook workbook = null;

       try {
           inputStream= new FileInputStream(new File(fileName));
           workbook = new XSSFWorkbook(inputStream);
       } catch (FileNotFoundException e) {
           System.out.println("Nem lehet megnyitn a fájlt!"); e.printStackTrace();
       } catch (IOException e) {
           System.out.println("Nem lehet a fájlból olvasi!"); e.printStackTrace();
       }
     //  Sheet sheet = workbook.getSheet(sheetName);
       //CellAddress cellAddress = new CellAddress(cellAddr);
      // Row row = sheet.getRow(cellAddress.getRow());
      // Cell cell = row.getCell(cellAddress.getColumn());
       //System.out.println(cell.toString());
       //return cell;



   }
}
