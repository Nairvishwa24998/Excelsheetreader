package com.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel.controller.ExcelSheetReaderMethods;
import com.excel.controller.ExcelSheetReaderOtherMethods;

public class Excelsheetreader {

	public static void main(String[] args) {
//		Initialize and fill hashmap to store markrange, respective grades and gradepoints

		Map<int[], Object[]> gradeScale = new HashMap<>();
		ExcelSheetReaderOtherMethods otherfunctions = new ExcelSheetReaderOtherMethods();
		otherfunctions.populateHashmap(gradeScale);
		Scanner scan = new Scanner(System.in);
		String filePath = "./src/main/resources/Excelsheetdata/Data.xlsx";

		try (XSSFWorkbook workbook = new XSSFWorkbook(filePath);){
			
			XSSFSheet sheet = workbook.getSheet("Data");

			// The first row contains column names, so excluded from count
			int rowcount = sheet.getPhysicalNumberOfRows() - 1;

			// initialize empty arraylist to store objects of type studentdetailsexcel and
			// fill it with objects for each student
			List<ExcelSheetReaderMethods> records = new ArrayList<>();
			otherfunctions.populateRecords(rowcount, records, sheet);

//		Creating three string variables for later use
			String input = "";
			String response;
			String finalStudent = "";
			boolean run = true;

//		loop which would only break when all conditions are met. 
			while (run) {
				System.out.println("Enter 1 for Admission Number or 2 for Name of student");
				input = scan.next();

				if (input.equals("1") || input.equals("2")) {
					System.out.println("Enter Valid information");
					response = scan.next();
					response = otherfunctions.cleanInput(response);

					if (response.length() >= 1) {

						// returns "" if no student found
						finalStudent = otherfunctions.verifyStudent(input, response, records);
						if (!finalStudent.equals("")) {
							run = false;
						} else {
							System.out.println("Invalid information entered\n");
						}
					}
				}
			}

			otherfunctions.output(gradeScale, records, finalStudent);
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			scan.close();
			
		}

	}

}
