package jp.co.e_co.app.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tm_youchien")
public class Youchien {
	
	@Id
	@Column(name="youchien_code")
	private long youchienCode;
	
	@Column
	private String yname;

	@Column
	private String ykana;
	
	@Column
	private String zipcode;
	
	@Column
	private String adr;
	
	@Column
	private String tel;
	
	@Column
	private String fax;
	
	@Column
	private String daihyousya;
	
	@Column
	private String tantousya;
	
	@Column
	private String mailadd;
	
	@Column
	private String biko;
	
	@Column
	private int delflg;
	
	@Column
	private Timestamp updatetime;
	
	public long getYouchienCode() {
		return youchienCode;
	}

	public void setYouchienCode(long youchienCode) {
		this.youchienCode = youchienCode;
	}

	public String getYname() {
		return yname;
	}

	public void setYname(String yname) {
		this.yname = yname;
	}

	public String getYkana() {
		return ykana;
	}

	public void setYkana(String ykana) {
		this.ykana = ykana;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDaihyousya() {
		return daihyousya;
	}

	public void setDaihyousya(String daihyousya) {
		this.daihyousya = daihyousya;
	}

	public String getTantousya() {
		return tantousya;
	}

	public void setTantousya(String tantousya) {
		this.tantousya = tantousya;
	}

	public String getMailadd() {
		return mailadd;
	}

	public void setMailadd(String mailadd) {
		this.mailadd = mailadd;
	}

	public String getBiko() {
		return biko;
	}

	public void setBiko(String biko) {
		this.biko = biko;
	}

	public int getDelflg() {
		return delflg;
	}

	public void setDelflg(int delflg) {
		this.delflg = delflg;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}
