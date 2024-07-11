package com.beyond.basic.domain;

import lombok.Data;

//사용자가 서버에 요청 보낼 때 -> 사용자는 id 입력하지 않아도 됨
@Data
public class MemberReqDto {
    private String name;
    private String email;
    private String password;
}
