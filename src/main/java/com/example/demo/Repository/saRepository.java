package com.example.demo.Repository;

import java.util.List;

import com.example.demo.DTO.securityAdmins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface saRepository extends JpaRepository<securityAdmins, Long>{


    // @Query("select count(*) from admins where cd = :cd and pw = :pw")
    // int findByCdAndPwNative(@Param("cd") String cd, @Param("pw") String pw);
    List<securityAdmins> findByUserid(String userid);

    @Modifying
    @Transactional
    @Query(value = "insert into AdminsRole (admins_id, role_id) VALUES (:admins_id, :role_id) ", nativeQuery = true)
    int updateAuth(Long admins_id, Long role_id);

    @Modifying
    @Transactional
    @Query(value = " delete from AdminsRole where admins_id = :admins_id", nativeQuery = true )
    int deleteAdminsRole(Long admins_id);

    //보유권한을 한 컬럼으로
    @Query(value = " SELECT GROUP_CONCAT('/', role_id,'/') AS strAuth FROM AdminsRole WHERE admins_id=:admins_id ;", nativeQuery = true)
    String strAuth(Long admins_id);

    //관리자 승인코드
    @Query(value = "SELECT code FROM adminCode where code = :code ;", nativeQuery = true)
    String  getAdminCode(String code);

    
}
