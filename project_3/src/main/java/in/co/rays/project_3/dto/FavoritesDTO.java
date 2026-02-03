package in.co.rays.project_3.dto;

import java.util.Date;

public class FavoritesDTO extends BaseDTO {
	private Integer Item;
	private Date Dob;
	private String AccountName;
	private String Notes;

	public Integer getItem() {
		return Item;
	}

	public void setItem(Integer item) {
		Item = item;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
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