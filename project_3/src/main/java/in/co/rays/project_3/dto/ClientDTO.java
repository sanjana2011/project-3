package in.co.rays.project_3.dto;

import java.util.Date;

public class ClientDTO extends BaseDTO {
	private String ContactName;
	private Date Dob;
	private String Phone;
	private String Location;

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
