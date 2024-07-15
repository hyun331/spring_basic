package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

//사용자가 서버에 요청 보낼 때 -> 사용자는 id 입력하지 않아도 됨
@Data
@NoArgsConstructor  //사용자로부터 입력받은 데이터가 JSON이면 반드시 필요! 일단 화면 form 데이터로 받고 있음
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    //추후에는 빌더패턴으로 변환 - 개수와 순서 상관 없이 생성자를 사용할 수 있음!
    //MemberReqDto -> Member하는 메서드 ex)회워가입
    public Member toEntity(){
        Member member = new Member(this.name, this.email, this.password);
        return member;
    }
}
