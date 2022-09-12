package com.excel.controller;

public class ExcelSheetReaderMethods {

	String studentName;
	String admissionNumber;
	double physics;
	double mathematics;
	double chemistry;
	String input;

	
	public ExcelSheetReaderMethods (String studentName,String admissionNumber,double physics, double mathematics,double chemistry) {
		this.studentName = studentName;
		this.admissionNumber = admissionNumber;
		this.physics = physics;
		this.mathematics = mathematics;
		this.chemistry = chemistry;
	}
		
	
	private double totalMarks (double physics, double mathematics,double chemistry) {
		return physics + mathematics + chemistry;
	}
	
	
	
//	using previous method to call function
	public double percentageCalculator(double physics, double mathematics,double chemistry) {
	return this.totalMarks(physics, mathematics, chemistry)/3;
	}



}
