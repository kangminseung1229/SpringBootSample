package com.example.demo.sample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface sampleRepository extends JpaRepository<boardData, Long>{

    //쿼리문을 jpaRepository naming 규칙으로 구현한다.

    //실제 복잡한 쿼리를 사용하고자 할 때는 @Query 어노테이션 활용
    //@Query(value = "SELECT * FROM boardData where title LIKE '%a%' or memo LIKE '%a%' ORDER BY ID DESC ",nativeQuery = true)
    //객체 배열인 LIST 객체를 페이지 수만큼 잘라온 객체 page, pageble 객체를 파라미터로 한다.
	Page<boardData> findByTitleContainingOrMemoContainingOrderByIdDesc(String title, String memo, Pageable pageable);

	public void deleteById(Long id);

    
}
