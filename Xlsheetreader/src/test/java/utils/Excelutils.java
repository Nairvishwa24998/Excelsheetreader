package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelutils {

	public static void populatehashmap(HashMap<int[], Object[]> Gradescale) {
		Gradescale.put(new int[] { 91, 100 }, new Object[] { "A1", 10.0 });
		Gradescale.put(new int[] { 81, 90 }, new Object[] { "A2", 9.0 });
		Gradescale.put(new int[] { 71, 80 }, new Object[] { "B1", 8.0 });
		Gradescale.put(new int[] { 61, 70 }, new Object[] { "B2", 7.0 });
		Gradescale.put(new int[] { 51, 60 }, new Object[] { "C1", 6.0 });
		Gradescale.put(new int[] { 41, 50 }, new Object[] { "C2", 5.0 });
		Gradescale.put(new int[] { 31, 40 }, new Object[] { "D", 4.0 });
		Gradescale.put(new int[] { 21, 30 }, new Object[] { "E1", 3.0 });
		Gradescale.put(new int[] { 0, 20 }, new Object[] { "E2", 2.0 });
	}

	public static int getColumCount(XSSFSheet sheet) {

		int columnCount = 0;

		while (sheet.getRow(0).getCell(columnCount) != null) {
			columnCount = columnCount + 1;
		}
		return columnCount;
	}

	public static String verifyStudent(String input, String response, ArrayList<studentdetailsexcel> records) {
		String finalstudent = "";
		if (input.equals("1")) {
			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).admissionnumber.equals(response)) {
					finalstudent = records.get(i).admissionnumber;
					break;
				}
			}
		} else {
			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).studentname.equals(response)) {
					finalstudent = response;
					break;
				}
			}
		}
		return finalstudent;
	}

// remove trailing and leading spaces and format accordingly
	public static String cleaninput(String response) {
		return response.trim().substring(0, 1).toUpperCase() + response.substring(1).toLowerCase();
	}

// method to return array of Grade and and Gradepoint when marks are entered
	public static Object[] marksreturn(double marks, HashMap<int[], Object[]> Gradescale) {

		int[] graderange = new int[2];

		for (Map.Entry<int[], Object[]> pair : Gradescale.entrySet()) {
			if (marks >= pair.getKey()[0] && marks <= pair.getKey()[1]) {
				graderange = pair.getKey();
				break;
			}
		}
		return Gradescale.get(graderange);
	}

	public static String[] outputreturn(String name, String admissionNumber, double physics, double chemistry,
			double mathematics, String physicsGrade, String chemistryGrade, String mathsGrade, Double physicsPoint,
			Double chemistryPoint, Double mathsPoint, double percentage) {
		String[] results = { "Name: " + name, "Admission Number: " + admissionNumber,
				"Percentage:" + Math.round(percentage * 10.0) / 10.0, "Physics:", "     Mark: " + physics,
				"     Grade: " + physicsGrade, "     Gradepoint: " + physicsPoint, "Chemistry:",
				"     Mark: " + chemistry, "     Grade: " + chemistryGrade, "     Gradepoint: " + chemistryPoint,
				"Mathematics:", "     Mark: " + mathematics, "     Grade: " + mathsGrade,
				"     Gradepoint: " + mathsPoint };

		return results;
	}

	public static void main(String[] args) {

//	Initialize and fill hashmap to store markrange, respective grades and gradepoints
		HashMap<int[], Object[]> Gradescale = new HashMap<int[], Object[]>();
		populatehashmap(Gradescale);

		try {
			String filepath = "./Excelsheetdata/Data.xlsx";
			XSSFWorkbook workbook = new XSSFWorkbook(filepath);
			XSSFSheet sheet = workbook.getSheet("Data");

			// The first row contains column names, so excluded from count
			int rowcount = sheet.getPhysicalNumberOfRows() - 1;
			int columncount = getColumCount(sheet);

			// initialize empty arraylist to store objects of type studentdetailsexcel
			ArrayList<studentdetailsexcel> records = new ArrayList<studentdetailsexcel>();
			for (int i = 1; i < rowcount + 1; i++) {

				// Excel's default format is double for numeric entries so converting to string
				// and choosing the part before the dot
				String admissionnumber = (sheet.getRow(i).getCell(1).getNumericCellValue() + "").split("\\.")[0];
				studentdetailsexcel studentdetails = new studentdetailsexcel(
						sheet.getRow(i).getCell(0).getStringCellValue(), admissionnumber,
						sheet.getRow(i).getCell(2).getNumericCellValue(),
						sheet.getRow(i).getCell(3).getNumericCellValue(),
						sheet.getRow(i).getCell(4).getNumericCellValue());
				records.add(studentdetails);
			}

			Scanner scan = new Scanner(System.in);

//	Creating three string variables for later use
			String input = "";
			String response;
			String finalstudent = "";

			boolean run = true;

//	loop which would only break when all conditions are met. 
			while (run) {
				System.out.println("Enter 1 for Admission Number or 2 for Name of student");
				input = scan.next();

				if (input.equals("1") || input.equals("2")) {
					System.out.println("Enter Valid information");
					response = scan.next();
					response = cleaninput(response);

					if (response.length() >= 1) {

						// returns "" if no student found
						finalstudent = verifyStudent(input, response, records);
						if (!finalstudent.equals("")) {
							run = false;
						} else {
							System.out.println("Invalid information entered\n");
						}
					}
				}
			}

			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).studentname.equals(finalstudent)
						|| records.get(i).admissionnumber.equals(finalstudent)) {
					String name = records.get(i).studentname;
					String admissionNumber = records.get(i).admissionnumber;
					double physics = records.get(i).physics;
					double chemistry = records.get(i).chemistry;
					double mathematics = records.get(i).mathematics;
					String physicsGrade = (String) marksreturn(physics, Gradescale)[0];
					String chemistryGrade = (String) marksreturn(chemistry, Gradescale)[0];
					String mathsGrade = (String) marksreturn(mathematics, Gradescale)[0];
					Double physicsPoint = (Double) marksreturn(physics, Gradescale)[1];
					Double chemistryPoint = (Double) marksreturn(chemistry, Gradescale)[1];
					Double mathsPoint = (Double) marksreturn(mathematics, Gradescale)[1];
					double percentage = records.get(i).percentagecalculator(physics, mathematics, chemistry);
					String[] result = outputreturn(name, admissionNumber, physics, chemistry, mathematics, physicsGrade,
							chemistryGrade, mathsGrade, physicsPoint, chemistryPoint, mathsPoint, percentage);
					for (String output : result) {
						System.out.println(output);
					}
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
