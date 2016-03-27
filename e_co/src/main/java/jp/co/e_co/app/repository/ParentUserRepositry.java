package jp.co.e_co.app.repository;

import java.util.List;

import jp.co.e_co.app.entity.ParentUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentUserRepositry extends JpaRepository<ParentUser, Long> {

	public List<ParentUser> findByYouchienCodeAndParentUserCode(
			long youchienCode, long parentUserCode);
	
	public List<ParentUser> findByMailaddAndPw(String mailadd, String pw);
	
}
