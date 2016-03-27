package jp.co.e_co.app.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tm_parent_user")
public class ParentUser {
	
	@Id
	@Column(name="youchien_code")
	private long youchienCode;
	
	@Column(name="parent_user_code")
	private long parentUserCode;
	
	@Column
	private String pw;
	
	@Column
	private String uname;
	
	@Column
	private String ukana;
	
	@Column
	private String zipcode;
	
	@Column
	private String adr;
	
	@Column
	private String tel;
	
	@Column
	private String mobtel;
	
	@Column
	private String kentel;
	
	@Column
	private String kenmtel;
	
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

	public long getParentUserCode() {
		return parentUserCode;
	}

	public void setParentUserCode(long parentUserCode) {
		this.parentUserCode = parentUserCode;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUkana() {
		return ukana;
	}

	public void setUkana(String ukana) {
		this.ukana = ukana;
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

	public String getMobtel() {
		return mobtel;
	}

	public void setMobtel(String mobtel) {
		this.mobtel = mobtel;
	}

	public String getKentel() {
		return kentel;
	}

	public void setKentel(String kentel) {
		this.kentel = kentel;
	}

	public String getKenmtel() {
		return kenmtel;
	}

	public void setKenmtel(String kenmtel) {
		this.kenmtel = kenmtel;
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
