package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadingFromExcel {

    public static void main(String[] args) throws IOException {

        // Assign file path to string value
        String excelFilePath = "test_data/ReadData.xlsx";

        // Reaching out the file
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);

        // Opening the file where we specified the path
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        // Going into specific sheet in the workbook (excel file)
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        // Getting the first name of the first row
        String name = sheet.getRow(1).getCell(0).getStringCellValue();
        System.out.println(name);

        // Getting the dept name of second row.
        String dept = sheet.getRow(2).getCell(2).getStringCellValue();
        System.out.println(dept);

        // getting the last row number
        int lastRow = sheet.getLastRowNum();
        System.out.println("Amount of rows are " + lastRow);

        // getting last cell number
        int lastCell = sheet.getRow(1).getLastCellNum();
        System.out.println("Amount of cells are " + lastCell);
    }
}
