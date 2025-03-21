package com.mutual.funds.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mutual.funds.entity.investEntity;

public interface investRepo extends JpaRepository<investEntity, Integer>{

	List<investEntity> findByUserId(Integer user_id);
	
	@Query(value = "SELECT o.id, o.total_amount as amount, o.date, u.name as user_name , s.name as scheme_name from invest o INNER JOIN funds_user u on u.id=o.user_id INNER join subcategory s on s.id=o.scheme_id where s.name iLIKE concat('%',:search,'%') or u.name iLIKE concat('%',:search,'%') or CAST (o.total_amount as VARCHAR) iLIKE concat('%',:search,'%') or CAST(o.id as VARCHAR) iLIKE concat('%',:search,'%') or CAST(o.date as VARCHAR) iLIKE concat('%',:search,'%')", nativeQuery = true)
	List<Map> searchByKeyword(String search);

}
