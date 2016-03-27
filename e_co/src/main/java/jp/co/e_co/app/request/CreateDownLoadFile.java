package jp.co.e_co.app.request;

import java.util.ArrayList;
import java.util.List;

public class CreateDownLoadFile {
	
	private String ecoId;
	
	private List<String> photoIds = new ArrayList<>();

	public String getEcoId() {
		return ecoId;
	}

	public void setEcoId(String ecoId) {
		this.ecoId = ecoId;
	}

	public List<String> getPhotoIds() {
		return photoIds;
	}

	public void setPhotoIds(List<String> photoIds) {
		this.photoIds = photoIds;
	}

}
