package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {


    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static String filePath;

    /**
     * Method to open specific excel file we need to interact with
     *
     * @param fileName
     * @param sheetName
     */
    public static void openExcelFile(String fileName, String sheetName){
        filePath = "test_data/" + fileName + ".xlsx";

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            System.out.println("File " + fileName + " is exist!");
            sheet = workbook.getSheet(sheetName);
            System.out.println("Sheet " + sheetName + " exist!");
        } catch (Exception e) {
            System.out.println("Sheet " + sheetName + " exist!");
        }
    }


    /**
     * getting the single value from the Excel file by defining its
     * row and the cell number.
     *
     * @param rowNumber
     * @param cellNumber
     * @return
     */
    public static String getValue(int rowNumber, int cellNumber){
        row = sheet.getRow(rowNumber);
        cell = row.getCell(cellNumber);
        cell.setCellType(CellType.STRING);
        return cell.toString();
    }

    public static List<String> getColumnValues(int columnNumber){
        // Create a list to hold the column's values.
        List<String> columnValues = new ArrayList<>();


        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {

            // Get the cell's value and add it to the list
            row = sheet.getRow(i);
            cell = row.getCell(columnNumber);
            columnValues.add(cell.toString());
        }

        // Return the list of column values
        return columnValues;
    }


    public static List<String> getRowValues(int rowNumber){
        // Get the specific row.
        row = sheet.getRow(rowNumber);

        List<String> rowValues = new ArrayList<>();

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            // Get the cell's value and add it to the list
            cell = row.getCell(i);
            rowValues.add(cell.toString());
        }

        //Return the list of row values.
        return rowValues;
    }

    public static List<List<String>> getValues() {
        // Creating a List of List to store all the values from the Excel file
        List<List<String>> allValues = new ArrayList<>();

        // Creating a loop to iterate each row

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum() ; i++) {

            // creating a row in the sheet
            row = sheet.getRow(i);
            // creating a list to store row data
            List<String> eachRow = new ArrayList<>();

            // Iterating each cell in the each row
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                // get the each cell values and add them into eachRow list
                eachRow.add(getValue(i, j));
            }
            allValues.add(eachRow);
        }
        return allValues;
    }
}
