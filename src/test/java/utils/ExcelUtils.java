package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    // READ DATA FROM EXCEL 
    public static Object[][] getTestData(String filePath, String sheetName) throws Exception {

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        DataFormatter formatter = new DataFormatter();

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {

            Row row = sheet.getRow(i);

            if (row == null) continue;

            for (int j = 0; j < colCount; j++) {

                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                // conversion to string
                data[i - 1][j] = formatter.formatCellValue(cell);
            }
        }

        workbook.close();
        return data;
    }

    //Write result
    public static void setCellDataByTCID(String filePath, String sheetName,
                                         String tcId, int colNum, String value) throws Exception {

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        DataFormatter formatter = new DataFormatter(); 

        int rowCount = sheet.getLastRowNum();

        for (int i = 1; i <= rowCount; i++) {

            Row row = sheet.getRow(i);

            if (row == null) continue;

            Cell tcCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            String tcValue = formatter.formatCellValue(tcCell);

            if (tcValue.equals(tcId)) {

                Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                cell.setCellValue(value); 
            }
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);

        fos.close();
        workbook.close();
    }
}