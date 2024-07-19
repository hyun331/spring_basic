package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String contents;

    //1:1인 경우 oneToOne
    //@OneToOne
    //@JoinColumn(name="member_id" unique=true)
    ///////////////////////////////////////////
    //ManyToOne, OneToOne 어노테이션은 default설정이 eager이므로 lazy로 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //JPA의 영속성컨텍스트(persistence)에 의해 Member가 관리
    private Member member;
    //spring에서는 member_id가 아닌 member 객체를 가져다 씀.
    // 단순히 member_id만 쓰면 활용도가 낮음. jpa, entity manager가 알아서 처리
    //db에서는 member_id가 들어감



    public PostResDto fromEntity(){
        return new PostResDto(this.id, this.title, this.contents);
    }


}
