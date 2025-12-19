package com.orangeHRM.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {
	
	private static final String CSV_TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/";
	
	public static Object[][] getDataFROMCSV(String fileName){
		
		String filePath = CSV_TEST_DATA_SHEET_PATH + fileName + ".csv";
		Object[][] data = null;
		
		try {
			FileReader fr = new FileReader(filePath);
			CSVReader reader = new CSVReader(fr);
			List<String[]> rows = reader.readAll();
			reader.close();
			
			for(int i=0; i< rows.size(); i++) {
				data[i] = rows.get(i);
			}
			return data;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
		return data;
	}

}
