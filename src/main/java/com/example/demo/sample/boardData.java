package com.example.demo.sample;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data // ---> lombok
@Entity // JPA에서 사용하기 위함, 컬럼명에 언더바 를 넣지 않는 것이 편하다.
public class boardData {
    // 테이블 이름과 클래스명이 일치해야 한다.
    // 불가피할 경우 @Table(name = "board"), 컬럼 @Column(name = "subject")
    // 테이블에 대한 각 컬럼 설정을 어노테이션으로 대체할 수 있음.

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아마 auto increment ..?
    private int id;

    private String title;
    private String memo;

    @CreationTimestamp //기본값 Now() 와 같다.
    private Date created;

    @UpdateTimestamp // 업데이트 시에만 now()로 변경된다.
    private Date updated;

}
