package in.co.rays.project_3.dto;

import java.util.Date;

public class PositionDTO extends BaseDTO {

	private String Designation;
	private Date Dob;
	private String ReqExp;
	private String Conditions;

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getReqExp() {
		return ReqExp;
	}

	public void setReqExp(String reqExp) {
		ReqExp = reqExp;
	}

	public String getConditions() {
		return Conditions;
	}

	public void setConditions(String conditions) {
		Conditions = conditions;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return Conditions;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Conditions;
	}

}
