package jp.co.e_co.app.repository;

import jp.co.e_co.app.entity.Youchien;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YouchienRepository extends JpaRepository<Youchien, Long>{
	
	Page<Youchien> findAll(Pageable pageable);
	
}
