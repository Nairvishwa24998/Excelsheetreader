package utils;


public class studentdetailsexcel {
	
	String studentname;
	String admissionnumber;
	double physics;
	double mathematics;
	double chemistry;
	String input;
	String relevantproperty;
	
	public studentdetailsexcel (String studentname,String admissionnumber,double physics, double mathematics,double chemistry) {
		this.studentname = studentname;
		this.admissionnumber = admissionnumber;
		this.physics = physics;
		this.mathematics = mathematics;
		this.chemistry = chemistry;
	}
	
	
	
	public double totalmarks (double physics, double mathematics,double chemistry) {
		return physics + mathematics + chemistry;
	}
	
	
	
//	using previous method to call function
	public double percentagecalculator(double physics, double mathematics,double chemistry) {
	return this.totalmarks(physics, mathematics, chemistry)/3;
	}

	

}
