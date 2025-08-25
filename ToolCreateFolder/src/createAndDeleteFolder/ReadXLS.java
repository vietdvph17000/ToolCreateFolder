package createAndDeleteFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXLS {
	public ReadXLS() {
	};

	public static void main(String[] args) {
		ReadXLS readFile = new ReadXLS();
		String sPath = "D:/hoannc/Project/ECM/Tong hop thiet ke Metadata cac du an/testReadFile.xls";
		File testRead = new File(sPath);
		ArrayList<Object> arr = readFile.readXLS(testRead);
	}

	static ArrayList<Object> readXLSX(File inputFile) {
		ArrayList<Object> arrReturn = new ArrayList<Object>();
		try {
			// Get the workbook instance for XLSX file
			XSSFWorkbook wb = new XSSFWorkbook();
			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				ArrayList<String> member = new ArrayList<String>();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					String sValueCell = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						Double d = cell.getNumericCellValue();
						sValueCell = d.toString();
						break;
					case Cell.CELL_TYPE_STRING:
						sValueCell = cell.getStringCellValue();
						break;

					case Cell.CELL_TYPE_BLANK:
						sValueCell = "";
						break;
					}
					member.add(sValueCell);
				}
				arrReturn.add(member);
			}
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
		return arrReturn;
	}

	public static ArrayList<Object> readXLS(File inputFile) {
		ArrayList<Object> arrReturn = new ArrayList<Object>();
		try {
			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell;
			Row row;
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				ArrayList<String> member = new ArrayList<String>();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					String sValueCell = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						Double d = cell.getNumericCellValue();
						Long l = d.longValue();
						sValueCell = l.toString();
						break;
					case Cell.CELL_TYPE_STRING:
						sValueCell = cell.getStringCellValue();
						break;

					case Cell.CELL_TYPE_BLANK:
						sValueCell = "";
						break;
					}
					member.add(sValueCell);
				}
				arrReturn.add(member);
			}

		}

		catch (FileNotFoundException e) {
			System.err.println("Exception" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception" + e.getMessage());
		}
		return arrReturn;
	}

}

//
// import java.io.File;
// import java.io.FileInputStream;
// import java.util.ArrayList;
// import java.util.Iterator;
//
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//
// public class readXLS {
// public readXLS() {
// };
//
// public static void main(String[] args) {
// readXLS readFile = new readXLS();
// String sPath = "D:/hoannc/Project/ECM/Tong hop thiet ke Metadata cac du
// an/testReadFile.xls";
// ArrayList<Object> arr = readFile.readFileXLS(sPath);
// }
//
// public ArrayList<Object> readFileXLS(String sPath) {
// File myFile = new File(sPath);
// ArrayList<Object> arrReturn = new ArrayList<Object>();
// try {
// FileInputStream fis = new FileInputStream(myFile);
// // Finds the workbook instance for XLSX file
// XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
// // Return first sheet from the XLSX workbook
// XSSFSheet mySheet = myWorkBook.getSheetAt(0);
// // Get iterator to all the rows in current sheet
// Iterator<Row> rowIterator = mySheet.iterator();
// // Traversing over each row of XLSX file
// while (rowIterator.hasNext()) {
// Row row = rowIterator.next();
// // For each row, iterate through each columns
// Iterator<Cell> cellIterator = row.cellIterator();
// ArrayList<String> member = new ArrayList<String>();
// while (cellIterator.hasNext()) {
// Cell cell = cellIterator.next();
// member.add(cell.getStringCellValue());
// }
// arrReturn.add(member);
// }
// } catch (Exception ex) {
//
// }
// return arrReturn;
// }
// }
