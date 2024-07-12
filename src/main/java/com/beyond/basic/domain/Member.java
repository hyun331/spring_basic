package com.beyond.basic.domain;

import lombok.Data;

import javax.persistence.*;

@Data
//entity 어노테이션을 통해 엔티티매니저에게 객체 관리 위임
//해당 클래스명으로 테이블 및 컬럼 자동 생성하고 각종 설정 정보 위임
@Entity //알아서 만들어줌
public class Member {
    @Id //이거 설정한 변수가 pk로 됨
    //GenerationType.AUTO : jpa가 자동으로 적절한 전략을 선택하도록 맡김
    //GenerationType.IDENTITY : AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //Long은 BIGINT로 변환

    //String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼으로 변환
    private String name;

    //nullable=false : not null 제약 조건
    //unique = true : unique 제약 조건
    @Column(nullable = false, length = 50, unique = true)
    private String email;
//    @Column(name = "pw")  //이렇게 할 수 있으나 컬럼명과 변수명을 일치시키는게 좋음. 혼선 방지
    private String password;


    ////////////////
    //private String peopleNum;   //이런 변수는 DB에 people_num 으로 들어감.
}
