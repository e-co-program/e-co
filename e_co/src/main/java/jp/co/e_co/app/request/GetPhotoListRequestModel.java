package jp.co.e_co.app.request;

public class GetPhotoListRequestModel {
	
	private String year;
	
	private String id;
	
	private int offset;
	
	private String ecoId;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getEcoId() {
		return ecoId;
	}

	public void setEcoId(String ecoId) {
		this.ecoId = ecoId;
	}

}
