package com.orangeHRM.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/opencartregistrationdata.xlsx";
	
	public static Object[][] getDataFromExcelSheet(String sheetName)
	{
		System.out.println("Reading data from sheet name: " +  sheetName);
		Object[][] data = null;
		try {
			FileInputStream fi = new FileInputStream(TEST_DATA_SHEET_PATH);
			Workbook book = WorkbookFactory.create(fi);
			Sheet sheet = book.getSheet(sheetName);
			
			int lastRowNum = sheet.getLastRowNum();
			data = new Object[lastRowNum][];
			
			for(int i=0; i < lastRowNum; i++) {
				int lastCellNum = sheet.getRow(i+1).getLastCellNum();
				data[i] = new Object[lastCellNum];
				for(int j = 0; j < lastCellNum; j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			return data;
			
		} catch (FileNotFoundException e) {
			System.out.println("File is not found in the given location: " + TEST_DATA_SHEET_PATH);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
