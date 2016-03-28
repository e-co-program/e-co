package jp.co.e_co.app.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class ParentUserPk implements Serializable {
	
	private long youchienCode;

	private long parentUserCode;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
        if (!(obj instanceof ParentUserPk)) return false;
        if (obj == null) return false;
        ParentUserPk pk = (ParentUserPk) obj;
        return pk.youchienCode == youchienCode && pk.parentUserCode == parentUserCode;
	}
}
