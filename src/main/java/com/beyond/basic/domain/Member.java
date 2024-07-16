package com.beyond.basic.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
//entity 어노테이션을 통해 엔티티매니저에게 객체 관리 위임
//해당 클래스명으로 테이블 및 컬럼 자동 생성하고 각종 설정 정보 위임
@Entity //알아서 만들어줌
@NoArgsConstructor  //기본생성자는 JPA에서 필수
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

    //member 조회할 때 Post까지 같이 조회. 근데 필수는 아님
    // Post의 memeber와 연결되어 있음을 나타냄
    @OneToMany(mappedBy = "member")
    private List<Post> posts;


    //이런 캐멀케이스 변수는 DB에 _(언더바)로 들어감 -> created_time 으로 들어감.
    @CreationTimestamp  //DB에는 current_timestamp가 적용되지 않음
    private LocalDateTime createdTime;

    @UpdateTimestamp    //데이터 업데이트 될 때마다 갱신
    private LocalDateTime updateTime;

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public Member(Long id, String name, String email){
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public Member(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //entity -> dto
    public MemberDetResDto detFromEntity(){
        LocalDateTime createdTime = this.createdTime;
        String value = createdTime.getYear()+"년"+createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
        MemberDetResDto memberDetResDto = new MemberDetResDto(this.id, this.name, this.email, this.password, value);
        return memberDetResDto;
    }

    //entity -> dto
    public MemberResDto listFromEntity(){
        MemberResDto memberResDto = new MemberResDto(this.id, this.name, this.email);
        return memberResDto;
    }

    //password 변수 상단에 @Setter를 두어 특정 변수만 setter사용이 가능하나
    //일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장
    public void updatePW(String password) {
        this.password = password;
    }
}
