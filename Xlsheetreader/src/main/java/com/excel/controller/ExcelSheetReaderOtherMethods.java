package com.excel.controller;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelSheetReaderOtherMethods {
	public void populateHashmap(Map<int[], Object[]> gradeScale) {
		gradeScale.put(new int[] { 91, 100 }, new Object[] { "A1", 10.0 });
		gradeScale.put(new int[] { 81, 90 }, new Object[] { "A2", 9.0 });
		gradeScale.put(new int[] { 71, 80 }, new Object[] { "B1", 8.0 });
		gradeScale.put(new int[] { 61, 70 }, new Object[] { "B2", 7.0 });
		gradeScale.put(new int[] { 51, 60 }, new Object[] { "C1", 6.0 });
		gradeScale.put(new int[] { 41, 50 }, new Object[] { "C2", 5.0 });
		gradeScale.put(new int[] { 31, 40 }, new Object[] { "D", 4.0 });
		gradeScale.put(new int[] { 21, 30 }, new Object[] { "E1", 3.0 });
		gradeScale.put(new int[] { 0, 20 }, new Object[] { "E2", 2.0 });
	}

	public void populateRecords(int rowCount, List<ExcelSheetReaderMethods> records, XSSFSheet sheet) {
		for (int i = 1; i < rowCount + 1; i++) {
			// Excel's default format is double for numeric entries so converting to string
			// and choosing the part before the dot
			String admissionNumber = (sheet.getRow(i).getCell(1).getNumericCellValue() + "").split("\\.")[0];
			ExcelSheetReaderMethods studentDetails = new ExcelSheetReaderMethods(
					sheet.getRow(i).getCell(0).getStringCellValue(), admissionNumber,
					sheet.getRow(i).getCell(2).getNumericCellValue(), sheet.getRow(i).getCell(3).getNumericCellValue(),
					sheet.getRow(i).getCell(4).getNumericCellValue());
			records.add(studentDetails);
		}

	}

	public int getColumCount(XSSFSheet sheet) {

		int columnCount = 0;

		while (sheet.getRow(0).getCell(columnCount) != null) {
			columnCount = columnCount + 1;
		}
		return columnCount;
	}

	public String verifyStudent(String input, String response, List<ExcelSheetReaderMethods> records) {
		String finalStudent = "";
		if (input.equals("1")) {
			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).admissionNumber.equals(response)) {
					finalStudent = records.get(i).admissionNumber;
					break;
				}
			}
		} else {
			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).studentName.equals(response)) {
					finalStudent = response;
					break;
				}
			}
		}
		return finalStudent;
	}

// remove trailing and leading spaces and format accordingly
	public String cleanInput(String response) {
		return response.trim().substring(0, 1).toUpperCase() + response.substring(1).toLowerCase();
	}

// method to return array of Grade and and Gradepoint when marks are entered
	public Object[] marksReturn(double marks, Map<int[], Object[]> gradeScale) {

		int[] graderange = new int[2];

		for (Map.Entry<int[], Object[]> pair : gradeScale.entrySet()) {
			if (marks >= pair.getKey()[0] && marks <= pair.getKey()[1]) {
				graderange = pair.getKey();
				break;
			}
		}
		return gradeScale.get(graderange);
	}

	private String[] outputReturn(Object[] arr) {

		String gradePointLiteral = "     Gradepoint: ";
		String markLiteral = "     Mark: ";
		String gradeLiteral = "     Grade: ";
		String[] results = { "Name: " + arr[0], "Admission Number: " + arr[1],
				"Percentage:" + Math.round((Double)arr[11] * 10.0) / 10.0, "Physics:", markLiteral + arr[2],
				gradeLiteral + arr[5], gradePointLiteral + arr[8], "Chemistry:", markLiteral + arr[3],
				gradeLiteral + arr[6], gradePointLiteral + arr[9], "Mathematics:",
				markLiteral + arr[4], gradeLiteral + arr[7], gradePointLiteral + arr[10]};

		return results;
	}

	public void output(Map<int[], Object[]> gradeScale, List<ExcelSheetReaderMethods> records, String finalStudent) {
		for (int i = 0; i < records.size(); i++) {
			if (records.get(i).studentName.equals(finalStudent)
					|| records.get(i).admissionNumber.equals(finalStudent)) {
				String name = records.get(i).studentName;
				String admissionNumber = records.get(i).admissionNumber;
				double physics = records.get(i).physics;
				double chemistry = records.get(i).chemistry;
				double mathematics = records.get(i).mathematics;
				String physicsGrade = (String) marksReturn(physics, gradeScale)[0];
				String chemistryGrade = (String) marksReturn(chemistry, gradeScale)[0];
				String mathsGrade = (String) marksReturn(mathematics, gradeScale)[0];
				Double physicsPoint = (Double) marksReturn(physics, gradeScale)[1];
				Double chemistryPoint = (Double) marksReturn(chemistry, gradeScale)[1];
				Double mathsPoint = (Double) marksReturn(mathematics, gradeScale)[1];
				double percentage = records.get(i).percentageCalculator(physics, mathematics, chemistry);
				Object[] arr= new Object[]{name, admissionNumber, physics, chemistry, mathematics, physicsGrade,
					chemistryGrade, mathsGrade, physicsPoint, chemistryPoint, mathsPoint, percentage};
				String[] result = outputReturn(arr);
				for (String output : result) {
					System.out.println(output);
				}
			}

		}
	}

}
